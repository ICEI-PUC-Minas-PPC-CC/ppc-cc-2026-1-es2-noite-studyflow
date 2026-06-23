package com.example.StudyFlow.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa extends Atividade {

    private String tipoDaTarefa;
}
