package com.example.StudyFlow.services;

import com.example.StudyFlow.dto.UsuarioRequest;
import com.example.StudyFlow.dto.UsuarioResponse;
import com.example.StudyFlow.models.Usuario;
import com.example.StudyFlow.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse criar(UsuarioRequest request) {
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (request.getSenha() == null || request.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .senha(request.getSenha())
                .build();

        usuario = usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    public UsuarioResponse buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toResponse(usuario);
    }

    public UsuarioResponse login(UsuarioRequest request) {
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        return usuarioRepository.findByNome(request.getNome())
                .map(this::toResponse)
                .orElseGet(() -> criar(request));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        return response;
    }
}
