package br.com.alunoonline.api.client;

import br.com.alunoonline.api.dtos.AlunoFakerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "faker", url = "http://localhost:9191")
public interface AlunoFakerClient {

    @GetMapping("/alunos")
    List<AlunoFakerResponse> getListAlunos();
}
