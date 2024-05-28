package br.com.alunoonline.api.service;

import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaAlunoService {
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
}
