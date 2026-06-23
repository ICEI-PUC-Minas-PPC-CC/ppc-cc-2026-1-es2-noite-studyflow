package com.example.StudyFlow.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AtividadeRequest {
    private String titulo;
    private String descricao;
    private LocalDate data;
    private String prioridade;
    private String tipo;

    private String tipoDaTarefa;

    private String disciplina;
    private Double notaEsperada;

    private UUID usuarioId;
}
