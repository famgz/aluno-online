package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    ProfessorRepository professorRepository;

    public void create(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public Optional<Professor> findById(Long id) {
        Optional<Professor> professorFromDb = professorRepository.findById(id);
        if (professorFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado");
        }
        return professorFromDb;
    }

    public void updateById(Long id, Professor professor) {
        Optional<Professor> professorFromDb = findById(id);

        Professor professorUpdated = professorFromDb.get();

        professorUpdated.setName(professor.getName());
        professorUpdated.setEmail(professor.getEmail());
        professorUpdated.setCpf(professor.getCpf());

        professorRepository.save(professorUpdated);
    }

    public void deleteById(Long id) {
        findById(id);
        professorRepository.deleteById(id);
    }

}
