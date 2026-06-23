package com.example.StudyFlow.controllers;

import com.example.StudyFlow.dto.*;
import com.example.StudyFlow.services.AtividadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<AtividadeResponse> criar(@RequestBody AtividadeRequest request) {
        AtividadeResponse response = atividadeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AtividadeResponse>> listarTodas() {
        List<AtividadeResponse> responses = atividadeService.listarTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeResponse> buscarPorId(@PathVariable UUID id) {
        AtividadeResponse response = atividadeService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AtividadeResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<AtividadeResponse> responses = atividadeService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AtividadeResponse> atualizarStatus(@PathVariable UUID id, @RequestBody StatusRequest request) {
        AtividadeResponse response = atividadeService.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/prioridade")
    public ResponseEntity<AtividadeResponse> atualizarPrioridade(@PathVariable UUID id, @RequestBody PrioridadeRequest request) {
        AtividadeResponse response = atividadeService.atualizarPrioridade(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
