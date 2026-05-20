# SPRINT 1 - ANÁLISE DOS REQUISITOS E IDENTIFICAÇÃO DAS CLASSES

## Objetivo da Sprint

Transformar as histórias de usuário em uma visão inicial da estrutura do sistema, identificando entidades, classes, responsabilidades e os principais comportamentos esperados da aplicação.

---

# Revisão das histórias de usuário

Com base na funcionalidade escolhida, as principais histórias de usuário identificadas foram:

- Como usuário, desejo criar tarefas e provas para organizar meus estudos.
- Como usuário, desejo visualizar minhas tarefas cadastradas.
- Como usuário, desejo editar uma tarefa existente.
- Como usuário, desejo excluir tarefas que não sejam mais necessárias.
- Como usuário, desejo definir prioridade para cada tarefa.
- Como usuário, desejo acompanhar o status das tarefas.

---

# Identificação das principais entidades do domínio

As principais entidades identificadas foram:

- Usuário
- Tarefa
- Status
- Prioridade

Essas entidades representam os elementos centrais do sistema de gerenciamento de tarefas e provas.

---

# Lista das classes identificadas

## Classe `Tarefa`

```java
class Tarefa {
    Long id;
    String titulo;
    String descricao;
    String tipo;
    Prioridade prioridade;
    Status status;
    LocalDate data;
    Long usuarioId;
}
```

### Responsabilidades

- Representar tarefas e provas cadastradas pelo usuário
- Armazenar informações da tarefa
- Definir prioridade e status
- Manter vínculo com o usuário responsável
- Permitir operações de criação, edição e exclusão

---

## Classe `Usuario`

```java
class Usuario {
    Long id;
    String nome;
    String senha;
}
```

### Responsabilidades

- Representar o usuário do sistema
- Armazenar dados básicos de autenticação
- Manter vínculo com as tarefas cadastradas

---

## Enum `Status`

```java
enum Status {
    PENDENTE,
    EM_ANDAMENTO,
    CONCLUIDO,
    ADIADO
}
```

### Responsabilidades

- Representar o estado atual da tarefa
- Controlar o fluxo de progresso das atividades
- Padronizar os valores utilizados no sistema

---

## Enum `Prioridade`

```java
enum Prioridade {
    BAIXA,
    MEDIA,
    ALTA
}
```

### Responsabilidades

- Definir o nível de importância da tarefa
- Padronizar prioridades
- Evitar valores inválidos

---

## Classe `TarefaController`

```java
class TarefaController {
}
```

### Responsabilidades

- Receber requisições do usuário
- Encaminhar ações para a camada de serviço
- Retornar respostas para a interface

---

## Classe `TarefaService`

```java
class TarefaService {
}
```

### Responsabilidades

- Aplicar regras de negócio
- Validar dados das tarefas
- Coordenar operações de criação, edição, consulta e remoção
- Comunicar-se com o repositório

---

## Classe `TarefaRepository`

```java
class TarefaRepository {
}
```

### Responsabilidades

- Persistir dados no banco
- Salvar tarefas
- Consultar tarefas
- Atualizar registros
- Remover tarefas do banco de dados

---

# Relação das histórias de usuário com as classes

## Criação de tarefas

Relaciona com:

- `Tarefa`
- `TarefaController`
- `TarefaService`
- `TarefaRepository`

### Fluxo

```text
Usuário → TarefaController → TarefaService → TarefaRepository
```

---

## Visualização de tarefas

Relaciona com:

- `Tarefa`
- `Usuario`
- `TarefaRepository`

O sistema busca as tarefas associadas ao usuário e exibe na interface.

---

## Edição de tarefas

Relaciona com:

- `Tarefa`
- `TarefaService`
- `TarefaRepository`

Permite alterar informações como título, descrição, prioridade e status.

---

## Exclusão de tarefas

Relaciona com:

- `Tarefa`
- `TarefaRepository`

Responsável por remover tarefas cadastradas do sistema.

---

## Controle de prioridade e status

Relaciona com:

- `Prioridade`
- `Status`
- `Tarefa`

Esses elementos permitem organizar e acompanhar o andamento das tarefas.

---

# Observações finais

A estrutura identificada segue uma arquitetura em camadas bastante utilizada no mercado:

- `Controller` → entrada das requisições
- `Service` → regras de negócio
- `Repository` → persistência de dados

Essa separação melhora a organização do sistema, facilita manutenção e permite evolução futura da aplicação.
