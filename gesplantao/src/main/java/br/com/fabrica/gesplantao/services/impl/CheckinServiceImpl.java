package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.response.CheckinResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusCheckinEnum;
import br.com.fabrica.gesplantao.enumeration.StatusPlantaoEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.CheckinCheckout;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.repository.CheckinRepository;
import br.com.fabrica.gesplantao.repository.InternoRepository;
import br.com.fabrica.gesplantao.repository.PlantaoRepository;
import br.com.fabrica.gesplantao.services.CheckinService;
import br.com.fabrica.gesplantao.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CheckinServiceImpl implements CheckinService {

    public static final int MINUTOS_TOLERANCIA = 15;
    public static final int ACRESCIMO_ATRASO_PRESENCA = 1;

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private InternoRepository internoRepository;

    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioService usuarioService;

    public CheckinResponseDTO realizarCheckin(Long idPlantao) {
        Long id = usuarioService.retornaUsuarioAutenticado();
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
        var plantao = plantaoRepository.findById(idPlantao)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Plantão não encontrado"));
        var rodizio = plantao.getRodizio();
        var equipe = rodizio.getEquipe();
        LocalTime horaInicioPlantao = plantao.getHoraInicio();
        LocalTime horaAtual = LocalTime.now();
        LocalTime horaLimitePresente = horaInicioPlantao.plusMinutes(MINUTOS_TOLERANCIA);
        LocalTime horaLimiteAtrasado = horaLimitePresente.plusMinutes(MINUTOS_TOLERANCIA);

        if (!equipe.getInternos().contains(interno)) {
            throw new UsuarioNaoEncontradoException("Interno não participa desse rodizio");
        }
        if (plantao.getStatus() != StatusPlantaoEnum.EM_ANDAMENTO) {
            throw new BadRequestException("Não é possível realizar o check-in nesse plantão");
        }
        if (horaAtual.isAfter(horaLimiteAtrasado)) {
            throw new BadRequestException("Ultrapassou o limite de tolerancia, impossivel realizar Check-in");
        }

        var checkin = new CheckinCheckout();
        if (horaAtual.isBefore(horaLimitePresente)) {
            checkin.setStatusCheckin(StatusCheckinEnum.PRESENTE);
            interno.setQuantPresenca(interno.getQuantPresenca() + ACRESCIMO_ATRASO_PRESENCA);
        }
        if (horaAtual.isBefore(horaLimiteAtrasado)) {
            checkin.setStatusCheckin(StatusCheckinEnum.ATRASADO);
            interno.setQuantAtraso(interno.getQuantAtraso() + ACRESCIMO_ATRASO_PRESENCA);
        }
        checkin.setDataEntrada(LocalDate.now());
        checkin.setHoraEntrada(LocalTime.now());
        checkin.setInterno(interno);
        checkin.setPlantao(plantao);
        checkinRepository.save(checkin);
        return modelMapper.map(checkin, CheckinResponseDTO.class);
    }

    public CheckinResponseDTO realizarCheckout(Long idPlantao) {
        Long id = usuarioService.retornaUsuarioAutenticado();
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
        var plantao = plantaoRepository.findById(idPlantao)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Plantão não encontrado"));
        var rodizio = plantao.getRodizio();
        var equipe = rodizio.getEquipe();

        LocalTime horaFimPlantao = plantao.getHoraFim();
        LocalTime horaAtual = LocalTime.now();
        LocalTime horaLimiteCheckout = horaFimPlantao.plusMinutes(MINUTOS_TOLERANCIA);
        boolean checkinRealizado = plantao.getCheckins().stream()
                .anyMatch(checkin -> checkin.getInterno().equals(interno));

        if (!checkinRealizado) {
            throw new BadRequestException("Interno não realizou check-in nesse plantão");
        }

        if (!equipe.getInternos().contains(interno)) {
            throw new UsuarioNaoEncontradoException("Interno não participa desse rodizio");
        }

        if (horaAtual.isAfter(horaLimiteCheckout)) {
            throw new BadRequestException("Horário de check-out expirado");
        }

        var checkout = new CheckinCheckout();
        checkout.setDataSaida(LocalDate.now());
        checkout.setHoraSaida(LocalTime.now());
        checkout.setInterno(interno);
        checkout.setPlantao(plantao);
        checkinRepository.save(checkout);
        return modelMapper.map(checkout, CheckinResponseDTO.class);
    }
}
