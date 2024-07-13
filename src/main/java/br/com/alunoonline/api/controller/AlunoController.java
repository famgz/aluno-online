package br.com.alunoonline.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.AlunoRelatorioListaDTO;
import br.com.alunoonline.api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno create(@Valid @RequestBody Aluno aluno) {
        log.info("Iniciando criação de aluno");
        var alunoCriado = alunoService.create(aluno);
        log.info("Encerrando criação de aluno");
        return alunoCriado;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> findAll() {
        return alunoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> findById(@PathVariable Long id) {
        return alunoService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @RequestBody Aluno aluno) {
        alunoService.updateById(id, aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        alunoService.deleteById(id);
    }

    @GetMapping("/relatorio/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlunoRelatorioListaDTO emitirRelatorio(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id).get();
        AlunoRelatorioListaDTO dto = new AlunoRelatorioListaDTO();
        dto.setName(aluno.getName());
        dto.setEmail(aluno.getEmail());
        return dto;
    }

    @GetMapping("/{email}/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarPorEmaileCpf(@PathVariable String email,
            @PathVariable String cpf) {
        return Optional.of(
                alunoService.buscarAlunoPorEmaileCpf(email, cpf));
    }
}
