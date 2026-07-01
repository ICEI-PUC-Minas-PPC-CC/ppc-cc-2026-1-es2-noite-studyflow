export interface AtividadeRequest {
  titulo: string;
  descricao: string;
  data: string;
  prioridade: string;
  tipo: string;
  tipoDaTarefa?: string;
  disciplina?: string;
  notaEsperada?: number;
  usuarioId: string;
}
