package br.com.alunoonline.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.alunoonline.api.client.AlunoFakerClient;
import br.com.alunoonline.api.model.Endereco;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
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
    private AlunoFakerClient fakerClient;

    private List<Aluno> alunos = new ArrayList<>();

    public Aluno create(Aluno aluno) {
        log.info("Iniciando criacao de aluno");
        obterEnderecoPorCEP(aluno);
        alunoRepository.save(aluno);
        return aluno;
    }

    public void createAll(List<Aluno> novosAlunos) {
        alunoRepository.saveAll(novosAlunos);
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void adicionarAlunoPeriodicamente() {
        Aluno aluno = new Aluno(1L, "Joao", "joao@email.com", "12345678900", 1980, null);
        alunos.add(aluno);
        log.info("Aluno adicionado {}", aluno);
    }

    @Scheduled(fixedRate = 15 * 1000)
    public void atualizarAlunoPeriodicamente() {
        if (!alunos.isEmpty()) {
            Aluno aluno = alunos.get(0);
            aluno.setName("Joao atualizado");
            log.info("Aluno atualizado {}", aluno);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void removerAlunoPeriodicamente() {
        if (!alunos.isEmpty()) {
            Aluno aluno = alunos.remove(0);
            log.info("Aluno removido {}", aluno);
        }
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

    public void obterEnderecoPorCEP(Aluno aluno) {
        var cep = aluno.getEndereco().getCep();
        log.info("Consultando CEP {}", cep);
        try {
            var enderecoResponse = viaCepClient.consultaCep(cep);
            var alunoEndereco = aluno.getEndereco();
            alunoEndereco.setLogradouro(enderecoResponse.getLogradouro());
            alunoEndereco.setComplemento(enderecoResponse.getComplemento());
            alunoEndereco.setBairro(enderecoResponse.getBairro());
            alunoEndereco.setLocalidade(enderecoResponse.getLocalidade());
            alunoEndereco.setUf(enderecoResponse.getUf());
        } catch (Exception e) {
            log.warn("Erro ao obter dados do CEP {}", cep);
        }
    }
}
