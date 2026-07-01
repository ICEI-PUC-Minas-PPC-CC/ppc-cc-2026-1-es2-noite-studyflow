import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { UsuarioRequest } from '../models/usuario-request.model';
import { UsuarioResponse } from '../models/usuario-response.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient,
    private api: ApiService
  ) {}

  criar(request: UsuarioRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.api.baseUrl}/usuarios`, request);
  }

  buscarPorId(id: string): Observable<UsuarioResponse> {
    return this.http.get<UsuarioResponse>(`${this.api.baseUrl}/usuarios/${id}`);
  }

  login(request: UsuarioRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.api.baseUrl}/usuarios/login`, request);
  }
}
