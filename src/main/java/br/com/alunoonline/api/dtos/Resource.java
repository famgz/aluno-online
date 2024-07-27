package br.com.alunoonline.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resource {
    private Long id;
    private String detalhe;
}
