package br.com.fabrica.gesplantao.job;

import br.com.fabrica.gesplantao.enumeration.StatusCheckinEnum;
import br.com.fabrica.gesplantao.enumeration.StatusPlantaoEnum;
import br.com.fabrica.gesplantao.model.CheckinCheckout;
import br.com.fabrica.gesplantao.repository.CheckinRepository;
import br.com.fabrica.gesplantao.repository.EquipeRepository;
import br.com.fabrica.gesplantao.repository.InternoRepository;
import br.com.fabrica.gesplantao.repository.PlantaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobServices {

    public static final int ACRESCIMO_FALTAS = 1;
    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private InternoRepository internoRepository;

    @Scheduled(cron = "0 */1 * * * *")
    public void iniciarPlantao() {
        plantaoRepository.findAll().stream().map(plantao -> {
            LocalDate dataAtual = LocalDate.now();
            LocalTime horaAtual = LocalTime.now();

            if (plantao.getDataInicioPlantao().isEqual(dataAtual) && plantao.getHoraInicio().until(horaAtual, ChronoUnit.MINUTES) == 0) {
                plantao.setStatus(StatusPlantaoEnum.EM_ANDAMENTO);
                plantaoRepository.save(plantao);
                log.info("JOB plantão OK!");
            }
            return plantao;
        }).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void concluirPlantao() {
        plantaoRepository.findAll().stream().map(plantao -> {
            LocalDate dataAtual = LocalDate.now();
            LocalTime horaAtual = LocalTime.now();

            if (plantao.getDataFimPlantao().isEqual(dataAtual) && plantao.getHoraFim().until(horaAtual, ChronoUnit.MINUTES) == 0) {
                plantao.setStatus(StatusPlantaoEnum.CONCLUIDO);
                var rodizio = plantao.getRodizio();
                equipeRepository.findById(rodizio.getEquipe().getId()).ifPresent(equipe -> {
                    equipe.getInternos().forEach(interno -> {
                        if (!checkinRepository.existsByInternoAndPlantao(interno, plantao)) {
                            var checkinCheckout = new CheckinCheckout();
                            checkinCheckout.setStatusCheckin(StatusCheckinEnum.FALTA);
                            Integer faltasAtualizadas = interno.getQuantfalta() + ACRESCIMO_FALTAS;
                            interno.setQuantfalta(faltasAtualizadas);
                            checkinCheckout.setInterno(interno);
                            checkinCheckout.setPlantao(plantao);
                            checkinRepository.save(checkinCheckout);
                            internoRepository.save(interno);
                        }
                    });
                });
                plantaoRepository.save(plantao);
                log.info("JOB plantão OK!");
            }
            return plantao;
        }).collect(Collectors.toList());
    }

}
