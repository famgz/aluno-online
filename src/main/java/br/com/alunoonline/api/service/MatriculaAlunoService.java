package br.com.alunoonline.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.dto.AtualizarNotasRequest;
import br.com.alunoonline.api.dto.DisciplinasAlunoResponse;
import br.com.alunoonline.api.dto.HistoricoAlunoResponse;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;

@Service
public class MatriculaAlunoService {

    public static final double MEDIA_PARA_SER_APROVADO = 7.00;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void create(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public List<MatriculaAluno> findAll() {
        return matriculaAlunoRepository.findAll();
    }

    public Optional<MatriculaAluno> findById(Long id) {
        Optional<MatriculaAluno> matriculaAlunoFromDb = matriculaAlunoRepository.findById(id);
        if (matriculaAlunoFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MatriculaAluno não encontrada");
        }
        return matriculaAlunoFromDb;
    }

    public List<MatriculaAluno> findByAlunoId(Long alunoId) {
        return matriculaAlunoRepository.findByAlunoId(alunoId);
    }

    public List<MatriculaAluno> findByDisciplinaId(Long disciplinaId) {
        return matriculaAlunoRepository.findByDisciplinaId(disciplinaId);
    }

    public void updateById(Long id, MatriculaAluno matriculaAluno) {
        Optional<MatriculaAluno> matriculaAlunoFromDb = findById(id);

        MatriculaAluno matriculaAlunoUpdated = matriculaAlunoFromDb.get();

        matriculaAlunoUpdated.setNota1(matriculaAluno.getNota1());
        matriculaAlunoUpdated.setNota2(matriculaAluno.getNota2());
        matriculaAlunoUpdated.setStatus(matriculaAluno.getStatus());

        matriculaAlunoRepository.save(matriculaAlunoUpdated);
    }

    public void deleteById(Long id) {
        Optional<MatriculaAluno> matriculaAlunoFromDb = findById(id);
        matriculaAlunoRepository.deleteById(id);
    }

    public void atualizarNotas(long matriculaAlunoId, AtualizarNotasRequest atualizarNotasRequest) {

        MatriculaAluno matriculaAluno = matriculaAlunoRepository
                .findById(matriculaAlunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula  não encontrada"));

        if (atualizarNotasRequest.getNota1() != null) {
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }

        if (atualizarNotasRequest.getNota2() != null) {
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }

        Double novaNota1 = matriculaAluno.getNota1();
        Double novaNota2 = matriculaAluno.getNota2();

        if (novaNota1 != null && novaNota2 != null) {
            double media = calcularMedia(novaNota1, novaNota2);
            matriculaAluno.setStatus(
                    (media >= MEDIA_PARA_SER_APROVADO)
                            ? MatriculaAlunoStatusEnum.APROVADO
                            : MatriculaAlunoStatusEnum.REPROVADO);
        }

        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void atualizarStatusParaTrancado(Long matriculaAlunoId) {
        MatriculaAluno matriculaAluno = matriculaAlunoRepository
                .findById(matriculaAlunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula  não encontrada"));

        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só é possível trancar matrícula com status MATRICULADO");
        }

        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);

        matriculaAlunoRepository.save(matriculaAluno);
    }

    public HistoricoAlunoResponse emitirHistoricoAluno(Long alunoId) {

        List<MatriculaAluno> matriculasAluno = matriculaAlunoRepository.findByAlunoId(alunoId);

        if (matriculasAluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foram encontradas matriculas para este aluno");
        }

        HistoricoAlunoResponse historicoAluno = new HistoricoAlunoResponse();

        Aluno aluno = matriculasAluno.get(0).getAluno();

        historicoAluno.setNomeAluno(aluno.getName());
        historicoAluno.setCpfAluno(aluno.getCpf());
        historicoAluno.setEmailAluno(aluno.getEmail());

        List<DisciplinasAlunoResponse> disciplinasList = new ArrayList<>();

        for (MatriculaAluno matriculaAluno : matriculasAluno) {
            DisciplinasAlunoResponse disciplinasAlunoResponse = new DisciplinasAlunoResponse();

            disciplinasAlunoResponse.setNomeDisciplina(matriculaAluno.getDisciplina().getName());
            disciplinasAlunoResponse.setNomeProfessor(matriculaAluno.getDisciplina().getProfessor().getName());
            disciplinasAlunoResponse.setNota1(matriculaAluno.getNota1());
            disciplinasAlunoResponse.setNota2(matriculaAluno.getNota2());
            disciplinasAlunoResponse.setMedia(calcularMedia(matriculaAluno.getNota1(), matriculaAluno.getNota2()));
            disciplinasAlunoResponse.setStatus(matriculaAluno.getStatus());

            disciplinasList.add(disciplinasAlunoResponse);
        }

        historicoAluno.setDisciplinas(disciplinasList);

        return historicoAluno;
    }

    private Double calcularMedia(Double nota1, Double nota2) {
        if (nota1 == null || nota2 == null) {
            return null;
        }
        return (nota1 + nota2) / 2;
    }
}
