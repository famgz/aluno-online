package br.com.alunoonline.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.client.ViaCepClient;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AlunoService {
    private AlunoRepository alunoRepository;
    private ViaCepClient viaCepClient;

    public Aluno create(Aluno aluno) {
        log.info("Iniciando criacao de aluno");
        obterEnderecoPorCEP(aluno);
        alunoRepository.save(aluno);
        return aluno;
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno buscarAlunoPorEmaileCpf(String email,
            String cpf) {
        return alunoRepository.buscarAlunoPorEmaileCpf(email, cpf);
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
        log.info("Iniciando exclusão de alunos");
        alunoRepository.deleteById(id);
        log.info("Encerrando exclusão de alunos");
    }

    private void obterEnderecoPorCEP(Aluno aluno) {
        var cep = aluno.getEndereco().getCep();
        var enderecoResponse = viaCepClient.consultaCep(cep);
        var alunoEndereco = aluno.getEndereco();
        alunoEndereco.setLogradouro(enderecoResponse.getLogradouro());
        alunoEndereco.setComplemento(enderecoResponse.getComplemento());
        alunoEndereco.setBairro(enderecoResponse.getBairro());
        alunoEndereco.setLocalidade(enderecoResponse.getLocalidade());
        alunoEndereco.setUf(enderecoResponse.getUf());
    }

}
