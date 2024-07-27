package br.com.alunoonline.api.dtos;

import lombok.Data;

@Data
public class EnderecoViaCepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
