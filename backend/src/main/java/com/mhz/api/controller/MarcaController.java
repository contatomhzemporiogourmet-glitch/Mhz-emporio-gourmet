package com.mhz.api.controller;

import com.mhz.api.dto.MarcaDTO;
import com.mhz.api.service.MarcaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "https://mhzemporiogourmet.com.br"})
public class MarcaController {
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> obterTodas() {
        return ResponseEntity.ok(marcaService.obterTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> obterPorId(@PathVariable String id) {
        return ResponseEntity.ok(marcaService.obterPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarcaDTO> criar(@RequestBody MarcaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarcaDTO> atualizar(@PathVariable String id, @RequestBody MarcaDTO dto) {
        return ResponseEntity.ok(marcaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        marcaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}