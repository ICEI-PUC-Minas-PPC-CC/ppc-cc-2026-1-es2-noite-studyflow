package com.example.StudyFlow.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prova extends Atividade {

    private String disciplina;

    private Double notaEsperada;

    private Double notaAlcancada;
}
