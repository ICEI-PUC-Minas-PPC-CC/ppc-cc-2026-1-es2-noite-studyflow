import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AtividadeService } from '../../services/atividade.service';
import { AuthService } from '../../services/auth.service';
import { Atividade } from '../../models/atividade.model';

@Component({
  selector: 'app-atividade-detail',
  imports: [FormsModule, RouterLink],
  templateUrl: './atividade-detail.component.html'
})
export class AtividadeDetailComponent implements OnInit {
  atividade: Atividade | null = null;
  carregando = true;
  erro = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private atividadeService: AtividadeService,
    private auth: AuthService,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  ngOnInit(): void {
    if (!this.auth.isLogado()) {
      this.router.navigate(['/login']);
      return;
    }
    const id = this.route.snapshot.paramMap.get('id')!;
    this.atividadeService.buscarPorId(id).subscribe({
      next: (data) => {
        this.zone.run(() => {
          this.atividade = data;
          this.carregando = false;
          this.cdr.detectChanges();
        });
      },
      error: () => {
        this.zone.run(() => {
          this.erro = 'Atividade nao encontrada.';
          this.carregando = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  alterarStatus(status: string): void {
    if (!this.atividade) return;
    this.atividadeService.atualizarStatus(this.atividade.id, status).subscribe({
      next: (data) => { this.atividade = data; }
    });
  }

  alterarPrioridade(prioridade: string): void {
    if (!this.atividade) return;
    this.atividadeService.atualizarPrioridade(this.atividade.id, prioridade).subscribe({
      next: (data) => { this.atividade = data; }
    });
  }

  excluir(): void {
    if (!this.atividade) return;
    if (confirm('Excluir esta atividade?')) {
      this.atividadeService.deletar(this.atividade.id).subscribe({
        next: () => this.router.navigate(['/atividades'])
      });
    }
  }
}
