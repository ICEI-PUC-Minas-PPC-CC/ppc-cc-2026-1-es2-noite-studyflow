import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AtividadeService } from '../../services/atividade.service';
import { AuthService } from '../../services/auth.service';
import { AtividadeRequest } from '../../models/atividade-request.model';

@Component({
  selector: 'app-atividade-form',
  imports: [FormsModule, RouterLink],
  templateUrl: './atividade-form.component.html'
})
export class AtividadeFormComponent {
  tipo = 'TAREFA';
  titulo = '';
  descricao = '';
  data = '';
  prioridade = 'MEDIA';
  tipoDaTarefa = '';
  disciplina = '';
  notaEsperada: number | null = null;
  erro = '';
  salvando = false;

  constructor(
    private atividadeService: AtividadeService,
    private auth: AuthService,
    private router: Router
  ) {}

  salvar(): void {
    if (!this.titulo || !this.data) {
      this.erro = 'Titulo e data sao obrigatorios.';
      return;
    }

    this.salvando = true;
    this.erro = '';

    const usuarioId = this.auth.getUsuarioId();
    if (!usuarioId) {
      this.router.navigate(['/login']);
      return;
    }

    const request: AtividadeRequest = {
      titulo: this.titulo,
      descricao: this.descricao,
      data: this.data,
      prioridade: this.prioridade,
      tipo: this.tipo,
      usuarioId
    };

    if (this.tipo === 'TAREFA') {
      request.tipoDaTarefa = this.tipoDaTarefa;
    } else {
      request.disciplina = this.disciplina;
      request.notaEsperada = this.notaEsperada ?? undefined;
    }

    this.atividadeService.criar(request).subscribe({
      next: () => this.router.navigate(['/atividades']),
      error: () => {
        this.erro = 'Erro ao salvar atividade.';
        this.salvando = false;
      }
    });
  }
}
