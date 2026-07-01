import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Atividade } from '../models/atividade.model';
import { AtividadeRequest } from '../models/atividade-request.model';

@Injectable({
  providedIn: 'root'
})
export class AtividadeService {

  constructor(
    private http: HttpClient,
    private api: ApiService
  ) {}

  criar(request: AtividadeRequest): Observable<Atividade> {
    return this.http.post<Atividade>(`${this.api.baseUrl}/atividades`, request);
  }

  listarPorUsuario(usuarioId: string): Observable<Atividade[]> {
    return this.http.get<Atividade[]>(`${this.api.baseUrl}/atividades/usuario/${usuarioId}`);
  }

  buscarPorId(id: string): Observable<Atividade> {
    return this.http.get<Atividade>(`${this.api.baseUrl}/atividades/${id}`);
  }

  atualizarStatus(id: string, status: string): Observable<Atividade> {
    return this.http.put<Atividade>(`${this.api.baseUrl}/atividades/${id}/status`, { status });
  }

  atualizarPrioridade(id: string, prioridade: string): Observable<Atividade> {
    return this.http.put<Atividade>(`${this.api.baseUrl}/atividades/${id}/prioridade`, { prioridade });
  }

  deletar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.api.baseUrl}/atividades/${id}`);
  }
}
