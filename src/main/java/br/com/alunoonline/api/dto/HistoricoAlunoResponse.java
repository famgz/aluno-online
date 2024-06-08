package br.com.alunoonline.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class HistoricoAlunoResponse {
    private String nomeAluno;
    private String emailAluno;
    private String cpfAluno;
    private List<DisciplinasAlunoResponse> disciplinas;
}
