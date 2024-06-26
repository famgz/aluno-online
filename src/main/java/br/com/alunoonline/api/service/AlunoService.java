package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;

    public void create(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(Long id) {
        Optional<Aluno> alunoFromDb = alunoRepository.findById(id);
        if (alunoFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        return alunoFromDb;
    }

    public void updateById(Long id, Aluno aluno) {
        Optional<Aluno> alunoFromDb = findById(id);

        Aluno alunoUpdated = alunoFromDb.get();

        alunoUpdated.setName(aluno.getName());
        alunoUpdated.setEmail(aluno.getEmail());
        alunoUpdated.setCpf(aluno.getCpf());

        alunoRepository.save(alunoUpdated);
    }

    public void deleteById(Long id) {
        Optional<Aluno> alunoFromDb = findById(id);
        alunoRepository.deleteById(id);
    }

}
