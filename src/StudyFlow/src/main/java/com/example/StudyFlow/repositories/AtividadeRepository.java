package com.example.StudyFlow.repositories;

import com.example.StudyFlow.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AtividadeRepository extends JpaRepository<Atividade, UUID> {
    List<Atividade> findByUsuarioId(UUID usuarioId);
}
