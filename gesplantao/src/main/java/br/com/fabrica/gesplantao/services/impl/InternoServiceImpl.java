package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.InternoRequestDTO;
import br.com.fabrica.gesplantao.dto.request.InternoUpdateRequest;
import br.com.fabrica.gesplantao.dto.response.InternoResponseDTO;
import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.enumeration.StatusInternoEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EntidadeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.repository.InternoRepository;
import br.com.fabrica.gesplantao.services.InternoService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternoServiceImpl implements InternoService {

    public static final int VALOR_ATRIBUTO_INICIO = 0;
    private final InternoRepository internoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;


    public InternoServiceImpl(InternoRepository internoRepository) {
        this.internoRepository = internoRepository;
    }

    public List<InternoResponseDTO> listarTodos() {
        return internoRepository.findAll().stream()
                .map(interno -> modelMapper.map(interno, InternoResponseDTO.class)).collect(Collectors.toList());
    }

    public InternoResponseDTO buscarPorId(Long id) {
        return internoRepository.findById(id).map(interno -> modelMapper.map(interno, InternoResponseDTO.class))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
    }

    public InternoResponseDTO cadastrarInterno(InternoRequestDTO internoRequestDTO) {
        if(internoRepository.existsByEmail(internoRequestDTO.getEmail())){
            throw new BadRequestException("E-mail existente");
        }
        var interno = internoRepository.findByRgm(internoRequestDTO.getRgm())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        String senhaCriptografada = encoder.encode(internoRequestDTO.getSenha());
        internoRequestDTO.setSenha(senhaCriptografada);
        interno.setCurso(internoRequestDTO.getCurso());
        interno.setSenha(internoRequestDTO.getSenha());
        interno.setCpf(internoRequestDTO.getCpf());
        interno.setEmail(internoRequestDTO.getEmail());
        interno.setTelefone(internoRequestDTO.getTelefone());
        interno.setQuantPresenca(VALOR_ATRIBUTO_INICIO);
        interno.setQuantfalta(VALOR_ATRIBUTO_INICIO);
        interno.setQuantAtraso(VALOR_ATRIBUTO_INICIO);
        interno.setStatus(StatusInternoEnum.DISPONIVEL);
        interno.setDataCriacao(LocalDate.now());
        interno.setPerfil(PerfilEnum.INTERNO);
        internoRepository.save(interno);
        return modelMapper.map(interno, InternoResponseDTO.class);
    }


    public InternoResponseDTO atualizarInterno(Long id, InternoUpdateRequest internoUpdateRequest) {
        return internoRepository.findById(id).map(interno -> {

            interno.setTelefone(internoUpdateRequest.getTelefone());
            interno.setNome(internoUpdateRequest.getNome());
            interno.setEmail(internoUpdateRequest.getEmail());
            interno.setCurso(internoUpdateRequest.getCurso());
            return modelMapper.map(interno, InternoResponseDTO.class);
        }).orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
    }


    public void excluir(Long internoId) {
        try {
            internoRepository.deleteById(internoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de interno com o código %d", internoId));
        }
    }

    public void addInterno(String arquivo) {
        List<Interno> internos = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#");

        try {
            var file = new FileInputStream(arquivo);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Interno interno = new Interno();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            interno.setNome(cell.getStringCellValue());
                            break;
                        case 1:
                            double rgmValue = cell.getNumericCellValue();
                            String rgmString = decimalFormat.format(rgmValue);
                            interno.setRgm(rgmString);
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                interno.setPerfil(PerfilEnum.INDEFINIDO_INTERNO);
                interno.setQuantPresenca(VALOR_ATRIBUTO_INICIO);
                interno.setQuantfalta(VALOR_ATRIBUTO_INICIO);
                interno.setQuantAtraso(VALOR_ATRIBUTO_INICIO);
                interno.setStatus(StatusInternoEnum.DISPONIVEL);
                if (internoRepository.existsByRgm(interno.getRgm())) {
                    continue;
                }
                internos.add(interno);
            }
            internoRepository.saveAll(internos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
