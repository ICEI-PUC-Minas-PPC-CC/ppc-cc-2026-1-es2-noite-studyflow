package com.example.StudyFlow.services;

import com.example.StudyFlow.dto.*;
import com.example.StudyFlow.models.*;
import com.example.StudyFlow.repositories.AtividadeRepository;
import com.example.StudyFlow.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtividadeServiceTest {

    @Mock
    private AtividadeRepository atividadeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AtividadeService atividadeService;

    private Usuario criarUsuario() {
        return Usuario.builder()
                .id(UUID.randomUUID())
                .nome("João")
                .senha("123456")
                .build();
    }

    private AtividadeRequest criarRequestTarefa(UUID usuarioId) {
        AtividadeRequest request = new AtividadeRequest();
        request.setTitulo("Estudar Java");
        request.setDescricao("Revisar polimorfismo");
        request.setData(LocalDate.of(2026, 7, 1));
        request.setPrioridade("ALTA");
        request.setTipo("TAREFA");
        request.setTipoDaTarefa("Exercício");
        request.setUsuarioId(usuarioId);
        return request;
    }

    private AtividadeRequest criarRequestProva(UUID usuarioId) {
        AtividadeRequest request = new AtividadeRequest();
        request.setTitulo("Prova de ES2");
        request.setDescricao("Matéria: arquitetura em camadas");
        request.setData(LocalDate.of(2026, 7, 15));
        request.setPrioridade("MEDIA");
        request.setTipo("PROVA");
        request.setDisciplina("Engenharia de Software 2");
        request.setNotaEsperada(80.0);
        request.setUsuarioId(usuarioId);
        return request;
    }

    @Test
    @DisplayName("Deve criar Tarefa com sucesso")
    void criarTarefaComSucesso() {
        Usuario usuario = criarUsuario();
        AtividadeRequest request = criarRequestTarefa(usuario.getId());

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(UUID.randomUUID());
        tarefaSalva.setTitulo(request.getTitulo());
        tarefaSalva.setDescricao(request.getDescricao());
        tarefaSalva.setData(request.getData());
        tarefaSalva.setPrioridade(Prioridade.ALTA);
        tarefaSalva.setStatus(Status.PENDENTE);
        tarefaSalva.setTipoDaTarefa(request.getTipoDaTarefa());
        tarefaSalva.setUsuario(usuario);

        when(atividadeRepository.save(any(Atividade.class))).thenReturn(tarefaSalva);

        AtividadeResponse response = atividadeService.criar(request);

        assertNotNull(response);
        assertEquals(tarefaSalva.getId(), response.getId());
        assertEquals("TAREFA", response.getTipo());
        assertEquals("Estudar Java", response.getTitulo());
        assertEquals("Exercício", response.getTipoDaTarefa());
        assertEquals("ALTA", response.getPrioridade());
        assertEquals("PENDENTE", response.getStatus());
        assertNull(response.getDisciplina());
        verify(atividadeRepository).save(any(Atividade.class));
    }

    @Test
    @DisplayName("Deve criar Prova com sucesso")
    void criarProvaComSucesso() {
        Usuario usuario = criarUsuario();
        AtividadeRequest request = criarRequestProva(usuario.getId());

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Prova provaSalva = new Prova();
        provaSalva.setId(UUID.randomUUID());
        provaSalva.setTitulo(request.getTitulo());
        provaSalva.setDescricao(request.getDescricao());
        provaSalva.setData(request.getData());
        provaSalva.setPrioridade(Prioridade.MEDIA);
        provaSalva.setStatus(Status.PENDENTE);
        provaSalva.setDisciplina(request.getDisciplina());
        provaSalva.setNotaEsperada(request.getNotaEsperada());
        provaSalva.setUsuario(usuario);

        when(atividadeRepository.save(any(Atividade.class))).thenReturn(provaSalva);

        AtividadeResponse response = atividadeService.criar(request);

        assertNotNull(response);
        assertEquals(provaSalva.getId(), response.getId());
        assertEquals("PROVA", response.getTipo());
        assertEquals("Engenharia de Software 2", response.getDisciplina());
        assertEquals(80.0, response.getNotaEsperada());
        assertEquals("MEDIA", response.getPrioridade());
        assertNull(response.getTipoDaTarefa());
        verify(atividadeRepository).save(any(Atividade.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar atividade com usuário inexistente")
    void criarComUsuarioInexistente() {
        UUID usuarioId = UUID.randomUUID();
        AtividadeRequest request = criarRequestTarefa(usuarioId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> atividadeService.criar(request));
        assertEquals("Usuário não encontrado", ex.getMessage());
        verify(atividadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar atividade com título em branco")
    void criarComTituloEmBranco() {
        Usuario usuario = criarUsuario();
        AtividadeRequest request = criarRequestTarefa(usuario.getId());
        request.setTitulo("");

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> atividadeService.criar(request));
        assertEquals("Título é obrigatório", ex.getMessage());
        verify(atividadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todas as atividades")
    void listarTodas() {
        Usuario usuario = criarUsuario();

        Tarefa tarefa = new Tarefa();
        tarefa.setId(UUID.randomUUID());
        tarefa.setTitulo("Tarefa 1");
        tarefa.setPrioridade(Prioridade.BAIXA);
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setTipoDaTarefa("Leitura");
        tarefa.setUsuario(usuario);

        Prova prova = new Prova();
        prova.setId(UUID.randomUUID());
        prova.setTitulo("Prova 1");
        prova.setPrioridade(Prioridade.ALTA);
        prova.setStatus(Status.EM_ANDAMENTO);
        prova.setDisciplina("Matemática");
        prova.setUsuario(usuario);

        when(atividadeRepository.findAll()).thenReturn(List.of(tarefa, prova));

        List<AtividadeResponse> responses = atividadeService.listarTodas();

        assertEquals(2, responses.size());
        assertEquals("Tarefa 1", responses.get(0).getTitulo());
        assertEquals("TAREFA", responses.get(0).getTipo());
        assertEquals("Prova 1", responses.get(1).getTitulo());
        assertEquals("PROVA", responses.get(1).getTipo());
    }

    @Test
    @DisplayName("Deve buscar atividade por ID com sucesso")
    void buscarPorIdComSucesso() {
        Usuario usuario = criarUsuario();
        UUID id = UUID.randomUUID();

        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setTitulo("Estudar");
        tarefa.setPrioridade(Prioridade.MEDIA);
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setTipoDaTarefa("Revisão");
        tarefa.setUsuario(usuario);

        when(atividadeRepository.findById(id)).thenReturn(Optional.of(tarefa));

        AtividadeResponse response = atividadeService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("Estudar", response.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar atividade inexistente")
    void buscarPorIdNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(atividadeRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> atividadeService.buscarPorId(id));
        assertEquals("Atividade não encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("Deve listar atividades por usuário")
    void listarPorUsuario() {
        Usuario usuario = criarUsuario();
        UUID usuarioId = usuario.getId();

        Tarefa tarefa = new Tarefa();
        tarefa.setId(UUID.randomUUID());
        tarefa.setTitulo("Tarefa do João");
        tarefa.setPrioridade(Prioridade.ALTA);
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setUsuario(usuario);

        when(atividadeRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(tarefa));

        List<AtividadeResponse> responses = atividadeService.listarPorUsuario(usuarioId);

        assertEquals(1, responses.size());
        assertEquals("Tarefa do João", responses.get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve atualizar status com sucesso")
    void atualizarStatusComSucesso() {
        Usuario usuario = criarUsuario();
        UUID id = UUID.randomUUID();

        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setTitulo("Estudar");
        tarefa.setPrioridade(Prioridade.MEDIA);
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setUsuario(usuario);

        when(atividadeRepository.findById(id)).thenReturn(Optional.of(tarefa));
        when(atividadeRepository.save(any(Atividade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StatusRequest request = new StatusRequest();
        request.setStatus("CONCLUIDO");

        AtividadeResponse response = atividadeService.atualizarStatus(id, request);

        assertEquals("CONCLUIDO", response.getStatus());
        assertEquals(Status.CONCLUIDO, tarefa.getStatus());
        verify(atividadeRepository).save(tarefa);
    }

    @Test
    @DisplayName("Deve atualizar prioridade com sucesso")
    void atualizarPrioridadeComSucesso() {
        Usuario usuario = criarUsuario();
        UUID id = UUID.randomUUID();

        Prova prova = new Prova();
        prova.setId(id);
        prova.setTitulo("Prova");
        prova.setPrioridade(Prioridade.BAIXA);
        prova.setStatus(Status.PENDENTE);
        prova.setUsuario(usuario);

        when(atividadeRepository.findById(id)).thenReturn(Optional.of(prova));
        when(atividadeRepository.save(any(Atividade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrioridadeRequest request = new PrioridadeRequest();
        request.setPrioridade("ALTA");

        AtividadeResponse response = atividadeService.atualizarPrioridade(id, request);

        assertEquals("ALTA", response.getPrioridade());
        assertEquals(Prioridade.ALTA, prova.getPrioridade());
        verify(atividadeRepository).save(prova);
    }

    @Test
    @DisplayName("Deve deletar atividade com sucesso")
    void deletarComSucesso() {
        UUID id = UUID.randomUUID();
        when(atividadeRepository.existsById(id)).thenReturn(true);

        atividadeService.deletar(id);

        verify(atividadeRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar atividade inexistente")
    void deletarNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(atividadeRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> atividadeService.deletar(id));
        assertEquals("Atividade não encontrada", ex.getMessage());
        verify(atividadeRepository, never()).deleteById(any());
    }
}
