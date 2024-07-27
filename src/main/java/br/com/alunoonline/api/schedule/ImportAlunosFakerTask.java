package br.com.alunoonline.api.schedule;

import br.com.alunoonline.api.client.AlunoFakerClient;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Endereco;
import br.com.alunoonline.api.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportAlunosFakerTask {

    private final AlunoService alunoService;
    private final AlunoFakerClient alunoFakerClient;

    @Scheduled(fixedDelay = 1000 * 1000)
    private void obterAlunosFaker() {
        log.info("Start import faker");
        var fakerAlunos = alunoFakerClient.getListAlunos();
        List<Aluno> novosAlunos = new ArrayList<>();
        for (int i = 0; i < fakerAlunos.size(); i++) {
            if (i >= 10) {
                break;
            }

            var aluno = fakerAlunos.get(i);
            var endereco = new Endereco();
            endereco.setNumero(aluno.getNumeroCasa());
            endereco.setCep(aluno.getCep());

            Aluno novoAluno = Aluno
                    .builder()
                    .name(aluno.getName())
                    .email(aluno.getEmail())
                    .anoNascimento(aluno.getAnoNascimento())
                    .endereco(endereco)
                    .build();

            alunoService.obterEnderecoPorCEP(novoAluno);
            novosAlunos.add(novoAluno);
        }
        alunoService.createAll(novosAlunos);
        log.info("{} alunos adicionados com sucesso", fakerAlunos.size());
    }
}
