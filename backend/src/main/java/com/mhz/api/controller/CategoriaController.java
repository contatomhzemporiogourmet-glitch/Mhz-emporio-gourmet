package com.mhz.api.controller;

import com.mhz.api.dto.CategoriaDTO;
import com.mhz.api.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "https://mhzemporiogourmet.com.br"})
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obterTodas() {
        return ResponseEntity.ok(categoriaService.obterTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obterPorId(@PathVariable String id) {
        return ResponseEntity.ok(categoriaService.obterPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoriaDTO> obterPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(categoriaService.obterPorSlug(slug));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> criar(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable String id, @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}