export interface Atividade {
  id: string;
  titulo: string;
  descricao: string;
  data: string;
  prioridade: string;
  status: string;
  tipo: string;
  tipoDaTarefa?: string;
  disciplina?: string;
  notaEsperada?: number;
  notaAlcancada?: number;
  usuarioId: string;
  usuarioNome: string;
}
