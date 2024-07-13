package br.com.alunoonline.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class DisciplinaService {
    DisciplinaRepository disciplinaRepository;

    @CacheEvict(value = "LISTA_DISCIPLINAS", allEntries = true)
    public void create(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> findById(Long id) {
        Optional<Disciplina> disciplinaFromDb = disciplinaRepository.findById(id);
        if (disciplinaFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada");
        }
        return disciplinaFromDb;
    }

    @Cacheable("LISTA_DISCIPLINAS")
    public List<Disciplina> findByProfessorId(Long professorId) {
        log.info("Listando disciplinas");
        return disciplinaRepository.findByProfessorId(professorId);
    }

    public void updateById(Long id, Disciplina disciplina) {
        Optional<Disciplina> disciplinaFromDb = findById(id);

        Disciplina disciplinaUpdated = disciplinaFromDb.get();

        disciplinaUpdated.setName(disciplina.getName());

        disciplinaRepository.save(disciplinaUpdated);
    }

    public void deleteById(Long id) {
        Optional<Disciplina> disciplinaFromDb = findById(id);
        disciplinaRepository.deleteById(id);
    }
}
