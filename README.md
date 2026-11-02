# Projeto final de POO 

# Sistema de Gerenciamento de Biblioteca

## 1. Visão geral

Sistema desenvolvido em Java para administrar uma biblioteca, seus usuários, materiais bibliográficos e empréstimos. O projeto tem finalidade acadêmica e prioriza a aplicação prática dos conceitos de Programação Orientada a Objetos.

**Escopo inicial:** aplicação em terminal, utilizando `Scanner` para interação e coleções (`ArrayList`) para armazenar os dados em memória. Não utilizar banco de dados nem frameworks nesta primeira versão.

## 2. Objetivos

- Cadastrar e gerenciar usuários.
- Cadastrar, consultar, atualizar e remover materiais bibliográficos.
- Disponibilizar diferentes tipos de materiais: livros, revistas e jornais.
- Registrar empréstimos e devoluções.
- Aplicar herança, polimorfismo, encapsulamento, abstração, interfaces, associações, composição, exceções e um padrão de projeto.

## 3. Perfis de usuário e permissões

A classe abstrata `Usuario` representa os dados comuns: identificador, nome, login e senha. As subclasses definem os perfis:

| Perfil | Permissões sugeridas |
|---|---|
| `UsuarioComum` | Consultar catálogo, pesquisar materiais, visualizar seus empréstimos, solicitar empréstimo e devolver material |
| `Bibliotecario` | Consultar catálogo, cadastrar/editar/remover materiais, registrar empréstimos e devoluções, consultar empréstimos e usuários |
| `Administrador` | Todas as operações; gerenciar usuários e perfis de acesso |

As permissões devem ser verificadas na camada de serviço, e não apenas escondendo opções no menu. Esconder uma opção melhora a interface, mas não substitui a validação da regra de acesso.

**Regra de segurança para o trabalho:** não permitir que um usuário comum altere seu próprio perfil para bibliotecário ou administrador. A criação e alteração desses perfis deve ser restrita ao administrador.

## 4. Materiais bibliográficos

A classe abstrata `MaterialBibliografico` concentra os atributos e comportamentos comuns:

- `id`
- `titulo`
- `anoPublicacao`
- `disponivel`

Subclasses:

- `Livro`: autor, ISBN e editora.
- `Revista`: editora, número/edição e mês ou data de publicação.
- `Jornal`: editora, edição e data de publicação.

Cada subtipo implementa `exibirDetalhes()` de acordo com suas informações específicas. Isso demonstra polimorfismo: o catálogo pode percorrer uma coleção de `MaterialBibliografico` e chamar o mesmo método sem precisar conhecer o subtipo concreto.

## 5. Empréstimos e regras de negócio

A classe `Emprestimo` relaciona um usuário a um material e registra:

- identificador;
- usuário;
- material;
- data do empréstimo;
- data prevista para devolução;
- data efetiva da devolução;
- status (`ATIVO`, `DEVOLVIDO` ou `ATRASADO`, se desejar).

### Regras iniciais sugeridas

1. Um material só pode possuir um empréstimo ativo por vez.
2. Um usuário comum pode possuir, por exemplo, até 3 empréstimos ativos.
3. Um material emprestado não pode ser removido do catálogo.
4. Não é possível devolver um empréstimo que já foi finalizado.
5. A devolução deve atualizar o status do empréstimo e a disponibilidade do material.
6. Bibliotecários podem registrar empréstimos e devoluções.
7. Apenas administradores podem criar, editar ou remover contas de bibliotecário e administrador.

**Decisão a tomar:** definir se revistas e jornais podem ser emprestados. Uma regra simples seria permitir empréstimo de livros e revistas e deixar jornais apenas para consulta. Caso escolha essa regra, implemente um método como `podeSerEmprestado()` em `MaterialBibliografico`, sobrescrito por cada subtipo.

Os prazos de empréstimo também podem variar por tipo de material. Evite fixar esses valores em vários pontos do código: concentre a regra em um serviço ou método específico.

## 6. Funcionalidades (CRUD)

### Usuários
- Cadastrar usuário.
- Listar usuários.
- Pesquisar usuário por ID.
- Atualizar dados.
- Remover usuário (desde que não tenha empréstimos ativos).
- Criar bibliotecário/administrador (somente administrador).

### Materiais
- Cadastrar livro, revista ou jornal.
- Listar catálogo.
- Pesquisar por ID ou título.
- Atualizar informações.
- Remover material (desde que não esteja emprestado).

### Empréstimos
- Registrar empréstimo.
- Listar empréstimos.
- Pesquisar empréstimo por ID.
- Consultar empréstimos de um usuário.
- Registrar devolução.

O CRUD de empréstimos não precisa permitir editar livremente um empréstimo antigo. A atualização deve ocorrer por operações de negócio, como registrar devolução, para preservar o histórico.

## 7. Arquitetura sugerida

Uma separação simples em pacotes ajuda a organizar o código sem introduzir complexidade desnecessária:

```text
src/
└── biblioteca/
    ├── Main.java
    ├── model/
    │   ├── usuario/
    │   │   ├── Usuario.java
    │   │   ├── UsuarioComum.java
    │   │   ├── Bibliotecario.java
    │   │   └── Administrador.java
    │   ├── material/
    │   │   ├── MaterialBibliografico.java
    │   │   ├── Livro.java
    │   │   ├── Revista.java
    │   │   └── Jornal.java
    │   └── Emprestimo.java
    ├── repository/
    │   ├── Repositorio.java
    │   ├── UsuarioRepositorio.java
    │   ├── MaterialRepositorio.java
    │   └── EmprestimoRepositorio.java
    ├── service/
    │   ├── UsuarioService.java
    │   ├── CatalogoService.java
    │   └── EmprestimoService.java
    ├── exception/
    │   ├── RegistroNaoEncontradoException.java
    │   ├── MaterialIndisponivelException.java
    │   ├── LimiteEmprestimosException.java
    │   └── OperacaoNaoPermitidaException.java
    └── view/
        ├── MenuPrincipal.java
        ├── MenuUsuario.java
        ├── MenuBibliotecario.java
        └── MenuAdministrador.java
```

Você pode começar com menos arquivos e separar os pacotes gradualmente. Não é necessário criar uma classe para cada menu se isso não ajudar.

### Responsabilidade de cada camada

- **model:** representa os objetos e seus dados.
- **repository:** armazena e recupera objetos nas coleções.
- **service:** aplica regras de negócio e coordena operações.
- **exception:** contém exceções específicas do domínio.
- **view:** apresenta menus, recebe entradas e mostra resultados.
- **Main:** inicializa repositórios, serviços e a interface.

## 8. Conceitos de POO demonstrados

| Requisito | Onde demonstrar |
|---|---|
| Classes, objetos e construtores | Usuários, materiais e empréstimos |
| Herança | `UsuarioComum`, `Bibliotecario` e `Administrador` herdam de `Usuario`; Livro, Revista e Jornal herdam de `MaterialBibliografico` |
| Polimorfismo | Sobrescrita de `exibirDetalhes()` e `podeSerEmprestado()` |
| Encapsulamento | Atributos `private`; validação nos construtores e métodos; getters quando necessários |
| Exceções | Material indisponível, limite de empréstimos, registro inexistente e operação não permitida |
| Classe abstrata | `Usuario` e `MaterialBibliografico` |
| Interface | `Repositorio<T>` |
| Associação | `Emprestimo` referencia um `Usuario` e um `MaterialBibliografico` |
| Composição | `Biblioteca` pode ser responsável pelo ciclo de vida de seus repositórios, se essa classe for adotada; alternativamente, uma entidade `Pedido` não é necessária neste domínio |
| Coleções | `ArrayList<Usuario>`, `ArrayList<MaterialBibliografico>` e `ArrayList<Emprestimo>` |
| Padrão de projeto | Repository, encapsulando o acesso às coleções |

### Exemplo de interface Repository

A interface pode declarar operações genéricas de persistência em memória:

```java
public interface Repositorio<T> {
    void adicionar(T objeto);
    T buscarPorId(int id);
    List<T> listarTodos();
    void atualizar(T objeto);
    void remover(int id);
}
```

Cada repositório concreto mantém seu próprio `ArrayList` e implementa essas operações. Ajuste as assinaturas conforme o tipo de identificador escolhido.

## 9. Exceções de domínio

Crie exceções próprias para situações esperadas do sistema, por exemplo:

- `MaterialIndisponivelException`: tentativa de emprestar material já emprestado.
- `LimiteEmprestimosException`: usuário atingiu o limite de empréstimos ativos.
- `RegistroNaoEncontradoException`: ID não existe.
- `OperacaoNaoPermitidaException`: perfil não possui autorização ou regra impede a operação.

Capture essas exceções na interface para apresentar mensagens compreensíveis. Evite usar `catch (Exception)` para esconder qualquer erro indiscriminadamente.

## 10. Ordem recomendada de desenvolvimento

1. Criar as classes abstratas `Usuario` e `MaterialBibliografico`.
2. Criar as subclasses dos usuários e materiais.
3. Criar `Emprestimo` e definir seus relacionamentos.
4. Implementar os repositórios com `ArrayList` e testar o CRUD.
5. Implementar `CatalogoService`, `UsuarioService` e `EmprestimoService`.
6. Adicionar validações e exceções.
7. Implementar autenticação e controle de permissões.
8. Criar os menus com `Scanner`.
9. Testar cenários válidos e inválidos.
10. Revisar o UML para que corresponda ao código final.

## 11. Casos de teste importantes

- Cadastrar e pesquisar um livro, revista e jornal.
- Tentar cadastrar dois materiais com o mesmo ID.
- Emprestar um material disponível.
- Tentar emprestar novamente um material já emprestado.
- Tentar ultrapassar o limite de empréstimos.
- Devolver um material e verificar se ele voltou a ficar disponível.
- Tentar devolver duas vezes o mesmo empréstimo.
- Tentar remover material com empréstimo ativo.
- Tentar que um usuário comum cadastre materiais ou gerencie usuários.
- Verificar se cada subtipo apresenta seus detalhes corretamente.

## 12. Fora do escopo inicial

Para manter o projeto compatível com a proposta da disciplina, deixar para uma possível evolução:

- Banco de dados.
- Spring ou outros frameworks.
- API web.
- Autenticação criptográfica avançada.
- Interface gráfica, caso o prazo seja curto.

## 13. Critério de conclusão

O projeto estará em uma boa versão inicial quando for possível cadastrar usuários e materiais, pesquisar e listar registros, registrar empréstimos e devoluções, aplicar as permissões de cada perfil e demonstrar os conceitos de POO previstos na avaliação.

Os dados ficam em memória e são perdidos ao encerrar o programa. Isso é esperado nesta etapa, pois o objetivo é demonstrar os conceitos da disciplina.