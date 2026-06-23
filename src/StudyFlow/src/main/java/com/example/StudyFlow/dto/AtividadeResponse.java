package com.example.StudyFlow.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AtividadeResponse {
    private UUID id;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private String prioridade;
    private String status;
    private String tipo;

    private String tipoDaTarefa;

    private String disciplina;
    private Double notaEsperada;
    private Double notaAlcancada;

    private UUID usuarioId;
    private String usuarioNome;
}
