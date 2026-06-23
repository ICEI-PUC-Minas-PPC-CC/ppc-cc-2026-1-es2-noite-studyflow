package com.example.StudyFlow.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioResponse {
    private UUID id;
    private String nome;
}
