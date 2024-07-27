package br.com.alunoonline.api.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class AlunoFakerResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private Integer anoNascimento;
    private String cep;
    private String numeroCasa;
}
