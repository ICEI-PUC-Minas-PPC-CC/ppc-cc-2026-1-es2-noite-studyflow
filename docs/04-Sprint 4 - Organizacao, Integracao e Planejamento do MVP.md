# SPRINT 4 - ORGANIZAÇÃO, INTEGRAÇÃO E PLANEJAMENTO DO MVP

## 1. Objetivo da Sprint

O objetivo desta etapa é conectar a modelagem orientada a objetos (Sprint 2) e a arquitetura em camadas (Sprint 3) com a fase de implementação. Para isso, estruturamos o fluxo principal do sistema, definimos o escopo do Produto Mínimo Viável (MVP) e elaboramos o planejamento técnico para a construção da aplicação.

---

## 2. Relação das Classes com a Arquitetura e Integração

As classes identificadas nas entregas anteriores foram distribuídas dentro da arquitetura MVC/Camadas (Model, Repository, Service, Controller) para garantir a separação de responsabilidades e facilitar a integração:

* **Camada Model (Entidades):** Representam os dados reais.
* `Usuario`, `Atividade` (abstrata), `Tarefa`, `Prova`, além dos Enums `Status` e `Prioridade`.


* **Camada Repository (Persistência):** Interfaces responsáveis por salvar e buscar os Models no banco de dados.
* `UsuarioRepository` e `AtividadeRepository` (que gerenciará tanto Tarefas quanto Provas).


* **Camada Service (Regras de Negócio):** Onde a lógica acontece.
* `UsuarioService` (ex: validação de senha nula) e `AtividadeService` (ex: cálculo de notas de provas, validação de datas limite).


* **Camada Controller (Entrada/Saída):** Recebem as requisições REST e devolvem respostas ao cliente.
* `UsuarioController` e `AtividadeController`.



---

## 3. Descrição do Fluxo Principal do Sistema

O fluxo principal escolhido para o sistema é o **Gerenciamento de Atividades (Criação e Visualização de Tarefas e Provas)**.

Abaixo, detalhamos como os componentes se integram passo a passo quando o usuário decide cadastrar uma nova atividade:

1. **Ação do Usuário:** O usuário preenche os dados de uma nova Prova ou Tarefa na interface e clica em "Salvar".
2. **Requisição (Interface → Controller):** A interface envia um JSON (via método `POST`) para o `AtividadeController`.
3. **Delegação (Controller → Service):** O Controller recebe o JSON, converte para um objeto e o repassa para o `AtividadeService`.
4. **Validação (Service):** O Service aplica as regras de negócio. Por exemplo: verifica se a data da atividade não está no passado, checa se os campos obrigatórios (título, usuário vinculado) estão preenchidos e define o status inicial como `PENDENTE`.
5. **Persistência (Service → Repository):** Com os dados validados, o Service chama o método `save()` do `AtividadeRepository`.
6. **Banco de Dados (Repository → BD):** O Repository executa o comando SQL correspondente (ex: `INSERT INTO atividade...`) e salva o registro no banco de dados.
7. **Resposta (Caminho Inverso):** O banco confirma a gravação, o Repository avisa o Service, que devolve o objeto criado para o Controller. O Controller então responde à interface com um HTTP Status `201 Created`, e o usuário vê sua nova atividade na tela.

---

## 4. Definição do Escopo do MVP

Para o **Produto Mínimo Viável (MVP)**, o foco será entregar o valor principal do software: permitir que o estudante organize sua rotina acadêmica. Funcionalidades complexas (como notificações por e-mail, geração de relatórios em PDF ou integração com Google Calendar) ficarão para versões futuras.

**O MVP contemplará exclusivamente as seguintes funcionalidades:**

* **Cadastro de Usuário:** Criação de conta simples (Nome, Senha) para que as atividades sejam vinculadas a um dono.
* **CRUD de Atividades:**
* **Criar (Create):** Adicionar novas Tarefas e Provas, informando título, descrição, data, prioridade e (no caso de provas) disciplina.
* **Ler (Read):** Listar todas as atividades cadastradas pelo usuário.
* **Atualizar (Update):** Alterar o Status da atividade (ex: de `PENDENTE` para `CONCLUIDO`) e inserir a "Nota Alcançada" em uma Prova.
* **Deletar (Delete):** Remover uma atividade cadastrada por engano ou que não é mais necessária.



---

## 5. Planejamento Técnico da Implementação

Para transformar o MVP em código, a implementação será dividida em 5 etapas sequenciais, construindo o sistema "de baixo para cima" (do banco de dados até as rotas da API):

### Passo 1: Configuração Inicial do Projeto

* Criar o projeto base (ex: utilizando Spring Boot no ecossistema Java).
* Configurar a conexão com o banco de dados relacional (ex: PostgreSQL ou MySQL) no arquivo de configuração (`application.properties` ou equivalente).
* Estruturar as pastas (`/models`, `/repositories`, `/services`, `/controllers`).

### Passo 2: Mapeamento Objeto-Relacional (Models)

* Codificar as classes `Usuario`, `Atividade`, `Tarefa` e `Prova`.
* Adicionar as anotações de persistência (ex: `@Entity`, `@Id`, `@OneToMany`) para que o sistema ORM (Object-Relational Mapping) crie as tabelas no banco de dados automaticamente.

### Passo 3: Implementação dos Repositories

* Criar as interfaces de repositório estendendo bibliotecas padrão (ex: `JpaRepository`), garantindo acesso imediato a métodos como `save()`, `findAll()` e `deleteById()`.

### Passo 4: Implementação dos Services (Lógica de Negócio)

* Criar as classes `UsuarioService` e `AtividadeService`.
* Implementar os métodos que farão as validações (ex: `cadastrarAtividade(Atividade novaAtividade)`) e que farão a injeção de dependência dos repositórios.

### Passo 5: Exposição da API (Controllers)

* Criar as classes `UsuarioController` e `AtividadeController`.
* Mapear os endpoints REST que serão consumidos pelo Front-end:
* `POST /api/usuarios`
* `POST /api/atividades`
* `GET /api/atividades/usuario/{id}`
* `PUT /api/atividades/{id}/status`


* Realizar testes das rotas utilizando ferramentas como Postman ou Insomnia.