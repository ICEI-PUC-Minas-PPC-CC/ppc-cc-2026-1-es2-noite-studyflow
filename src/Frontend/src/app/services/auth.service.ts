import { Injectable } from '@angular/core';

const USER_KEY = 'studyflow_user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {}

  salvarSessao(id: string, nome: string): void {
    localStorage.setItem(USER_KEY, JSON.stringify({ id, nome }));
  }

  getUsuarioId(): string | null {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user).id : null;
  }

  getUsuarioNome(): string | null {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user).nome : null;
  }

  isLogado(): boolean {
    return !!this.getUsuarioId();
  }

  logout(): void {
    localStorage.removeItem(USER_KEY);
  }
}
