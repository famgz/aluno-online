package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.Resource;
import br.com.alunoonline.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ResourceNotFoundException("Resource not found" + id);
        }

        Resource resource = new Resource(id, "Sample resource");
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
