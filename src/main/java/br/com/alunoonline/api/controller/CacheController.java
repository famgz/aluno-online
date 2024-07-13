package br.com.alunoonline.api.controller;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/cache")
@AllArgsConstructor
public class CacheController {

    private CacheManager cacheManager;

    @GetMapping
    @Cacheable("CACHE_PALAVRA")
    public String getPalavra() {
        log.info("GET PALAVRA ACIONADO");
        return "Texto Cacheado";
    }

    @DeleteMapping
    public void resetCache() {
        cacheManager.getCache("CACHE_PALAVRA").clear();
    }
}
