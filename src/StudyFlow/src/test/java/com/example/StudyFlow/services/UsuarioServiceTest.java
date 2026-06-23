package com.example.StudyFlow.services;

import com.example.StudyFlow.dto.UsuarioRequest;
import com.example.StudyFlow.dto.UsuarioResponse;
import com.example.StudyFlow.models.Usuario;
import com.example.StudyFlow.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void criarComSucesso() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("João");
        request.setSenha("123456");

        UUID id = UUID.randomUUID();
        Usuario usuarioSalvo = Usuario.builder()
                .id(id)
                .nome("João")
                .senha("123456")
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponse response = usuarioService.criar(request);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("João", response.getNome());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome estiver em branco")
    void criarComNomeEmBranco() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("");
        request.setSenha("123456");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.criar(request));
        assertEquals("Nome é obrigatório", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha estiver em branco")
    void criarComSenhaEmBranco() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("João");
        request.setSenha("");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.criar(request));
        assertEquals("Senha é obrigatória", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void buscarPorIdComSucesso() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(id)
                .nome("João")
                .senha("123456")
                .build();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioResponse response = usuarioService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("João", response.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente")
    void buscarPorIdNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usuarioService.buscarPorId(id));
        assertEquals("Usuário não encontrado", ex.getMessage());
    }
}
