package com.example.StudyFlow.services;

import com.example.StudyFlow.dto.*;
import com.example.StudyFlow.models.*;
import com.example.StudyFlow.repositories.AtividadeRepository;
import com.example.StudyFlow.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AtividadeResponse criar(AtividadeRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }

        Prioridade prioridade = Prioridade.valueOf(request.getPrioridade().toUpperCase());
        Atividade atividade;

        if ("PROVA".equalsIgnoreCase(request.getTipo())) {
            Prova prova = new Prova();
            prova.setDisciplina(request.getDisciplina());
            prova.setNotaEsperada(request.getNotaEsperada());
            atividade = prova;
        } else {
            Tarefa tarefa = new Tarefa();
            tarefa.setTipoDaTarefa(request.getTipoDaTarefa());
            atividade = tarefa;
        }

        atividade.setTitulo(request.getTitulo());
        atividade.setDescricao(request.getDescricao());
        atividade.setData(request.getData());
        atividade.setPrioridade(prioridade);
        atividade.setStatus(Status.PENDENTE);
        atividade.setUsuario(usuario);

        atividade = atividadeRepository.save(atividade);
        return toResponse(atividade);
    }

    public List<AtividadeResponse> listarTodas() {
        return atividadeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AtividadeResponse buscarPorId(UUID id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
        return toResponse(atividade);
    }

    public List<AtividadeResponse> listarPorUsuario(UUID usuarioId) {
        return atividadeRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeResponse atualizarStatus(UUID id, StatusRequest request) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
        atividade.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        atividade = atividadeRepository.save(atividade);
        return toResponse(atividade);
    }

    @Transactional
    public AtividadeResponse atualizarPrioridade(UUID id, PrioridadeRequest request) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
        atividade.setPrioridade(Prioridade.valueOf(request.getPrioridade().toUpperCase()));
        atividade = atividadeRepository.save(atividade);
        return toResponse(atividade);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada");
        }
        atividadeRepository.deleteById(id);
    }

    private AtividadeResponse toResponse(Atividade atividade) {
        AtividadeResponse response = new AtividadeResponse();
        response.setId(atividade.getId());
        response.setTitulo(atividade.getTitulo());
        response.setDescricao(atividade.getDescricao());
        response.setData(atividade.getData());
        response.setPrioridade(atividade.getPrioridade().name());
        response.setStatus(atividade.getStatus().name());
        response.setUsuarioId(atividade.getUsuario().getId());
        response.setUsuarioNome(atividade.getUsuario().getNome());

        if (atividade instanceof Tarefa) {
            Tarefa tarefa = (Tarefa) atividade;
            response.setTipo("TAREFA");
            response.setTipoDaTarefa(tarefa.getTipoDaTarefa());
        } else if (atividade instanceof Prova) {
            Prova prova = (Prova) atividade;
            response.setTipo("PROVA");
            response.setDisciplina(prova.getDisciplina());
            response.setNotaEsperada(prova.getNotaEsperada());
            response.setNotaAlcancada(prova.getNotaAlcancada());
        }

        return response;
    }
}
