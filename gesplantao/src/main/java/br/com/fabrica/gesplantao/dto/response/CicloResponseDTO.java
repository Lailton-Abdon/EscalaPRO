package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.model.Ciclo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CicloResponseDTO {
    private Long id;

    private String codigo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate inicioCiclo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate fimCiclo;

    private String duracaoCiclo;

    private List<RodizioResponseDTO> rodizios;
    public CicloResponseDTO(Ciclo ciclo){
        this.id = ciclo.getId();
        this.codigo = ciclo.getCodigo();
        this.inicioCiclo = ciclo.getInicioCiclo();
        this.fimCiclo = ciclo.getFimCiclo();
    }
    public CicloResponseDTO() {
    }

    public CicloResponseDTO(Long id, String codigo, LocalDate inicioCiclo, LocalDate fimCiclo) {
        this.id = id;
        this.codigo = codigo;
        this.inicioCiclo = inicioCiclo;
        this.fimCiclo = fimCiclo;
    }
}
