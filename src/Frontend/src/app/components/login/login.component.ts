import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  nome = '';
  senha = '';
  erro = '';

  constructor(
    private usuarioService: UsuarioService,
    private auth: AuthService,
    private router: Router
  ) {}

  entrar(): void {
    if (!this.nome || !this.senha) {
      this.erro = 'Preencha nome e senha.';
      return;
    }
    this.usuarioService.login({ nome: this.nome, senha: this.senha }).subscribe({
      next: (res) => {
        this.auth.salvarSessao(res.id, res.nome);
        this.router.navigate(['/atividades']);
      },
      error: () => {
        this.erro = 'Erro ao autenticar. Verifique os dados.';
      }
    });
  }
}
