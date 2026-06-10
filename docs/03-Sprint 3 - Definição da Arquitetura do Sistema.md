# Arquitetura do Sistema

O sistema foi organizado utilizando uma **arquitetura em camadas**, com o objetivo de separar as responsabilidades do projeto e deixar a estrutura mais simples de entender, manter e evoluir.

A arquitetura escolhida segue o seguinte fluxo:

```text
Interface / Cliente
        |
        v
Controller
        |
        v
Service
        |
        v
Repository
        |
        v
Banco de Dados
```

---

## Descrição da Arquitetura

A arquitetura em camadas divide o sistema em partes com responsabilidades bem definidas.

Cada camada possui uma função específica dentro do projeto, evitando que regras de negócio, acesso ao banco de dados e comunicação com o usuário fiquem misturados no mesmo lugar.

Essa organização deixa o sistema mais limpo, facilita futuras alterações e permite que novas funcionalidades sejam adicionadas com menos impacto no restante do código.

---

## Responsabilidades das Camadas

### Controller

A camada **Controller** é responsável por receber as requisições feitas pela interface ou pelo cliente da aplicação.

Ela funciona como a porta de entrada do sistema, recebendo os dados enviados pelo usuário, chamando os serviços necessários e retornando uma resposta.

Exemplos de controllers:

```text
UsuarioController
AtividadeController
TarefaController
```

---

### Service

A camada **Service** concentra as regras de negócio do sistema.

Ela é responsável por executar as principais ações da aplicação, como criar atividades, cadastrar tarefas, alterar status, definir prioridade e validar informações antes de salvar no banco de dados.

Exemplos de services:

```text
UsuarioService
AtividadeService
TarefaService
```

---

### Repository

A camada **Repository** é responsável pela comunicação com o banco de dados.

Ela realiza operações como salvar, buscar, atualizar e remover dados, mantendo o acesso ao banco separado das regras de negócio.

Exemplos de repositories:

```text
UsuarioRepository
AtividadeRepository
TarefaRepository
```

---

### Model

A camada **Model** representa as entidades principais do sistema.

Essas entidades armazenam os dados da aplicação e seguem os relacionamentos definidos no diagrama de classes.

Principais models:

```text
Usuario
Atividade
Tarefa
Prioridade
Status
```

---

## Organização das Pastas

```text
src/
├── controllers/
│   ├── UsuarioController
│   ├── AtividadeController
│   └── TarefaController
│
├── services/
│   ├── UsuarioService
│   ├── AtividadeService
│   └── TarefaService
│
├── repositories/
│   ├── UsuarioRepository
│   ├── AtividadeRepository
│   └── TarefaRepository
│
├── models/
│   ├── Usuario
│   ├── Atividade
│   ├── Tarefa
│   ├── Prioridade
│   └── Status
│
└── database/
    └── Configuração e conexão com o banco de dados
```

---

## Comunicação entre os Componentes

A comunicação entre os componentes acontece de forma sequencial, seguindo o fluxo da arquitetura em camadas.

A **Interface** envia uma solicitação para o **Controller**.

O **Controller** recebe a requisição e encaminha para o **Service** correto.

O **Service** executa as regras de negócio necessárias.

Quando é preciso acessar ou salvar dados, o **Service** utiliza o **Repository**.

O **Repository** se comunica diretamente com o **Banco de Dados**.

Depois que a operação é concluída, a resposta retorna pelo caminho inverso até chegar novamente ao usuário.

---

## Exemplo de Fluxo

Exemplo de fluxo para criação de uma tarefa:

```text
Interface
   -> TarefaController
      -> TarefaService
         -> TarefaRepository
            -> Banco de Dados
```

---

## Comunicação via REST

Caso o sistema seja utilizado como uma aplicação web, a comunicação poderá ser feita por meio de uma **API REST**.

Exemplos de rotas:

```text
POST   /usuarios
GET    /usuarios/{id}

POST   /atividades
GET    /atividades
GET    /atividades/{id}

POST   /tarefas
GET    /tarefas
GET    /tarefas/{id}
PUT    /tarefas/{id}/status
PUT    /tarefas/{id}/prioridade
DELETE /tarefas/{id}
```

---

## Justificativa da Arquitetura

A arquitetura em camadas foi escolhida por ser uma estrutura simples, organizada e adequada para o sistema.

Como o projeto possui entidades relacionadas, como **Usuario**, **Atividade**, **Tarefa**, **Prioridade** e **Status**, separar as responsabilidades ajuda a manter o código mais claro.

Com essa organização, cada parte do sistema tem uma função bem definida:

```text
Controller  -> recebe as requisições
Service     -> executa as regras de negócio
Repository  -> acessa o banco de dados
Model       -> representa as entidades do sistema
```

Essa divisão facilita a manutenção, melhora a organização do projeto e permite que o sistema seja expandido no futuro com menos dificuldade.

Também permite que o projeto evolua para uma aplicação web com API REST, mantendo uma estrutura limpa e fácil de entender.
