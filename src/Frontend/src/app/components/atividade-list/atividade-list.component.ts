import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AtividadeService } from '../../services/atividade.service';
import { AuthService } from '../../services/auth.service';
import { Atividade } from '../../models/atividade.model';

@Component({
  selector: 'app-atividade-list',
  imports: [RouterLink],
  templateUrl: './atividade-list.component.html'
})
export class AtividadeListComponent implements OnInit {
  atividades: Atividade[] = [];
  carregando = true;
  erro = '';

  constructor(
    private atividadeService: AtividadeService,
    protected auth: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  ngOnInit(): void {
    if (!this.auth.isLogado()) {
      this.router.navigate(['/login']);
      return;
    }
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.erro = '';
    const usuarioId = this.auth.getUsuarioId()!;

    this.atividadeService.listarPorUsuario(usuarioId).subscribe({
      next: (data) => {
        this.zone.run(() => {
          this.atividades = data;
          this.carregando = false;
          this.cdr.detectChanges();
        });
      },
      error: () => {
        this.zone.run(() => {
          this.erro = 'Erro ao carregar atividades.';
          this.carregando = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  alterarStatus(atividade: Atividade): void {
    const ordem = ['PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDO', 'ADIADO'];
    const idx = ordem.indexOf(atividade.status);
    const prox = ordem[(idx + 1) % ordem.length];
    this.atividadeService.atualizarStatus(atividade.id, prox).subscribe({
      next: () => this.carregar()
    });
  }

  alterarPrioridade(atividade: Atividade): void {
    const ordem = ['BAIXA', 'MEDIA', 'ALTA'];
    const idx = ordem.indexOf(atividade.prioridade);
    const prox = ordem[(idx + 1) % ordem.length];
    this.atividadeService.atualizarPrioridade(atividade.id, prox).subscribe({
      next: () => this.carregar()
    });
  }

  excluir(id: string): void {
    if (!confirm('Excluir esta atividade?')) return;
    this.atividadeService.deletar(id).subscribe({
      next: () => this.carregar()
    });
  }

  statusBadge(status: string): string {
    const map: Record<string, string> = {
      PENDENTE: 'bg-warning text-dark',
      EM_ANDAMENTO: 'bg-info text-dark',
      CONCLUIDO: 'bg-success',
      ADIADO: 'bg-secondary'
    };
    return map[status] || 'bg-secondary';
  }

  prioridadeBadge(pri: string): string {
    const map: Record<string, string> = {
      BAIXA: 'bg-success',
      MEDIA: 'bg-warning text-dark',
      ALTA: 'bg-danger'
    };
    return map[pri] || 'bg-secondary';
  }
}
