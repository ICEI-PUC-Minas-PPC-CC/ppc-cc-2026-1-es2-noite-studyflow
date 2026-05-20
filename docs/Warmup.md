# Warmup Projeto

## Escolha de uma funcionalidade principal

Criação, edição, visualização e deleção de tarefas e provas com prioridade e status.

## Descrição do fluxo completo

O usuário clica no botão de nova tarefa, cadastra os dados dessa tarefa, clica em concluir. Após isso, a nova tarefa aparece na tela de listagem anterior, onde é possível editar ou excluir a tarefa.

## Mapeamento técnico

O `TarefaController` recebe a requisição e a encaminha para a classe `TarefaService`, que realiza as validações e chama o `TarefaRepository` para persistir os dados no banco de dados.

## Organização arquitetural

- **Controller:** recebe a requisição do usuário e encaminha a ação para a camada
- **Service:** valida os dados, aplica regras de negócio e decide se chama o `TarefaRepository` ou retorna um erro ao usuário
- **Repository:** salva, consulta, atualiza ou remove os dados no banco.
- **Fluxo simplificado:** Usuário → TarefaController → TarefaService → TarefaRepository → Banco de Dados

## Identificação de problemas

Sim, o sistema segue um padrão bem comum em aplicações atuais de mercado. Talvez tenha um acoplamento grande na classe de service, que poderia ter responsabilidades excessivas. Pois ela cuida das validações por exemplo, poderia ter uma classe de validação, porém nesse momento seria desnecessário por ter poucas validações.

## Definição de classes

### Classe Tarefa

```java
class Tarefa {
    Long id;
    String titulo;
    String descricao;
    String tipo;
    String prioridade;
    Enum status; // (EM_ANDAMENTO, CONCLUIDO, ADIADO, etc...)
    LocalDate data;
    Long usuarioId;
}
```

### Classe Usuario

```java
class Usuario {
    Long id;
    String nome;
    String senha;
}
```

### Responsabilidades básicas

**Tarefa:**

- Representar uma tarefa ou prova
- Armazenar todos os dados relacionados
- Manter vínculo com o usuário

**Usuario:**

- Definir a entidade de usuário do domínio
- Persistir seus atributos principais
- Estabelecer vínculo com as tarefas/provas

**Status (enum):**

- Representar o estado da tarefa
- Controlar fluxo (pendente → andamento → concluída)

**Prioridade (enum):**

- Padronizar níveis de prioridade
- Evitar valores inválidos
