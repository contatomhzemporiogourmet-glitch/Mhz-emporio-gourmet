package com.mhz.api.controller;

import com.mhz.api.dto.ProdutoDTO;
import com.mhz.api.dto.ProdutoCreateUpdateDTO;
import com.mhz.api.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "https://mhzemporiogourmet.com.br"})
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> obterTodos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.obterTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> obterPorId(@PathVariable String id) {
        return ResponseEntity.ok(produtoService.obterPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProdutoDTO> obterPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(produtoService.obterPorSlug(slug));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<Page<ProdutoDTO>> obterPorCategoria(@PathVariable String categoriaId, Pageable pageable) {
        return ResponseEntity.ok(produtoService.obterPorCategoria(categoriaId, pageable));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<Page<ProdutoDTO>> obterPorMarca(@PathVariable String marcaId, Pageable pageable) {
        return ResponseEntity.ok(produtoService.obterPorMarca(marcaId, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProdutoDTO>> buscar(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscar(termo, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDTO> criar(@RequestBody ProdutoCreateUpdateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable String id, @RequestBody ProdutoCreateUpdateDTO dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}