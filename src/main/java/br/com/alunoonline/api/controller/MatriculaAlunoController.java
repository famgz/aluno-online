package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dto.AtualizarNotasRequest;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {
    @Autowired
    MatriculaAlunoService matriculaAlunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MatriculaAluno matriculaAluno) {
        matriculaAlunoService.create(matriculaAluno);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MatriculaAluno> findAll() {
        return matriculaAlunoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<MatriculaAluno> findById(@PathVariable Long id) {
        return matriculaAlunoService.findById(id);
    }

    @GetMapping("/aluno/{alunoId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MatriculaAluno> findByAlunoId(@PathVariable Long alunoId) {
        return matriculaAlunoService.findByAlunoId(alunoId);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MatriculaAluno> findByDisciplinaId(@PathVariable Long disciplinaId) {
        return matriculaAlunoService.findByDisciplinaId(disciplinaId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @RequestBody MatriculaAluno matriculaAluno) {
        matriculaAlunoService.updateById(id, matriculaAluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        matriculaAlunoService.deleteById(id);
    }

    @PatchMapping("/atualizar-notas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarNotas(@PathVariable Long id, @RequestBody AtualizarNotasRequest atualizarNotasRequest) {
        matriculaAlunoService.atualizarNotas(id, atualizarNotasRequest);
    }

    @PatchMapping("/trancar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarStatusParaTrancado(@PathVariable Long id) {
        matriculaAlunoService.atualizarStatusParaTrancado(id);
    }
}
