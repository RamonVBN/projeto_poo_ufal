
package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.Emprestimo;
import biblioteca.model.StatusEmprestimo;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoServiceTest {

    private EmprestimoService service;
    private EmprestimoRepositorio emprestimoRepositorio;
    private MaterialRepositorio materialRepositorio;
    private UsuarioRepositorio usuarioRepositorio;
    private AutorizacaoService autorizacaoService;
    private Sessao sessao;

    private Usuario usuarioPermitido;
    private MaterialBibliografico material;

    @BeforeEach
    void setUp() throws ItemDuplicadoException {
        emprestimoRepositorio = new EmprestimoRepositorio();
        materialRepositorio = new MaterialRepositorio();
        usuarioRepositorio = new UsuarioRepositorio();
        autorizacaoService = new AutorizacaoService();
        sessao = new Sessao();

        service = new EmprestimoService(
                emprestimoRepositorio,
                materialRepositorio,
                usuarioRepositorio,
                autorizacaoService,
                sessao
        );

        usuarioPermitido = new Usuario(
                "João Silva",
                "joao",
                "123456",
                Cargo.BIBLIOTECARIO
        );

        material = new Livro(
                "Clean Code",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall"
        );

        usuarioRepositorio.adicionar(usuarioPermitido);
        materialRepositorio.adicionar(material);
        sessao.iniciarSessao(usuarioPermitido);
    }

    // REGISTRO DE EMPRÉSTIMOS

    @Test
    void deveRegistrarEmprestimo() throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        List<Emprestimo> emprestimos =
                emprestimoRepositorio.listarTodos();

        assertEquals(1, emprestimos.size());
        assertTrue(emprestimos.getFirst().estaAtivo());
    }

    @Test
    void deveMarcarMaterialComoIndisponivelAposEmprestimo()
            throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        MaterialBibliografico encontrado =
                materialRepositorio.buscarPorId(material.getId());

        assertFalse(encontrado.estaDisponivel());
    }

    @Test
    void deveImpedirEmprestimoDeMaterialIndisponivel()
            throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        assertThrows(
                MaterialIndisponivelException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        usuarioPermitido.getID(),
                        material.getId()
                )
        );
    }

    @Test
    void deveImpedirEmprestimoComDataInvalida() {
        assertThrows(
                DataInvalidaException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now(),
                        usuarioPermitido.getID(),
                        material.getId()
                )
        );
    }

    @Test
    void deveImpedirEmprestimoComUsuarioInexistente() {
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        -1,
                        material.getId()
                )
        );
    }

    @Test
    void deveImpedirEmprestimoComMaterialInexistente() {
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        usuarioPermitido.getID(),
                        -1
                )
        );
    }

    @Test
    void deveImpedirMaisDeTresEmprestimosAtivos()
            throws Exception {
        MaterialBibliografico livro2 = new Livro(
                "Clean Architecture",
                2017,
                "Robert C. Martin",
                "9780134494166",
                "Prentice Hall"
        );

        MaterialBibliografico livro3 = new Livro(
                "Domain-Driven Design",
                2003,
                "Eric Evans",
                "9780321125217",
                "Addison-Wesley"
        );

        MaterialBibliografico livro4 = new Livro(
                "Refactoring",
                1999,
                "Martin Fowler",
                "9780201485677",
                "Addison-Wesley"
        );

        materialRepositorio.adicionar(livro2);
        materialRepositorio.adicionar(livro3);
        materialRepositorio.adicionar(livro4);

        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                livro2.getId()
        );

        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                livro3.getId()
        );

        assertThrows(
                LimiteEmprestimosException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        usuarioPermitido.getID(),
                        livro4.getId()
                )
        );
    }

    @Test
    void devePermitirNovoEmprestimoAposDevolucao()
            throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        Emprestimo emprestimo =
                emprestimoRepositorio.listarTodos().getFirst();

        service.registrarDevolucao(emprestimo.getId());

        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        assertEquals(2, emprestimoRepositorio.listarTodos().size());
    }

    // DEVOLUÇÃO DE EMPRÉSTIMOS

    @Test
    void deveRegistrarDevolucao() throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        Emprestimo emprestimo =
                emprestimoRepositorio.listarTodos().getFirst();

        service.registrarDevolucao(emprestimo.getId());

        assertFalse(emprestimo.estaAtivo());
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimo.getStatus());
    }

    @Test
    void deveMarcarMaterialComoDisponivelAposDevolucao()
            throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        Emprestimo emprestimo =
                emprestimoRepositorio.listarTodos().getFirst();

        service.registrarDevolucao(emprestimo.getId());

        MaterialBibliografico encontrado =
                materialRepositorio.buscarPorId(material.getId());

        assertTrue(encontrado.estaDisponivel());
    }

    @Test
    void deveImpedirDevolucaoDuplicada() throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        Emprestimo emprestimo =
                emprestimoRepositorio.listarTodos().getFirst();

        service.registrarDevolucao(emprestimo.getId());

        assertThrows(
                MaterialDevolvidoException.class,
                () -> service.registrarDevolucao(emprestimo.getId())
        );
    }

    // CONSULTA DE EMPRÉSTIMOS

    @Test
    void deveBuscarEmprestimoPorId() throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        Emprestimo esperado =
                emprestimoRepositorio.listarTodos().getFirst();

        Emprestimo encontrado =
                service.buscarEmprestimo(esperado.getId());

        assertEquals(esperado.getId(), encontrado.getId());
    }

    @Test
    void deveImpedirBuscaDeEmprestimoInexistente() {
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> service.buscarEmprestimo(-1)
        );
    }

    @Test
    void deveListarTodosOsEmprestimos() throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        List<Emprestimo> emprestimos =
                service.listarEmprestimos();

        assertEquals(1, emprestimos.size());
    }

    @Test
    void deveListarEmprestimosDeUmUsuario()
            throws Exception {
        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                usuarioPermitido.getID(),
                material.getId()
        );

        List<Emprestimo> emprestimos =
                service.listarEmprestimosPorUsuario(
                        usuarioPermitido.getID()
                );

        assertEquals(1, emprestimos.size());
    }

    @Test
    void deveListarMeusEmprestimos() throws Exception {
        Usuario leitor = new Usuario(
                "Maria Silva",
                "maria",
                "123456",
                Cargo.LEITOR
        );

        usuarioRepositorio.adicionar(leitor);

        MaterialBibliografico livro = new Livro(
                "Java Efetivo",
                2018,
                "Joshua Bloch",
                "9780134685991",
                "Alta Books"
        );

        materialRepositorio.adicionar(livro);

        service.registrarEmprestimo(
                LocalDate.now().plusDays(7),
                leitor.getID(),
                livro.getId()
        );

        sessao.iniciarSessao(leitor);

        List<Emprestimo> emprestimos =
                service.listarMeusEmprestimos();

        assertEquals(1, emprestimos.size());
        assertEquals(
                leitor.getID(),
                emprestimos.getFirst().getUsuario().getID()
        );
    }

    // AUTENTICAÇÃO E AUTORIZAÇÃO

    @Test
    void deveImpedirEmprestimoSemAutenticacao() {
        sessao.encerrarSessao();

        assertThrows(
                UsuarioNaoAutenticadoException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        usuarioPermitido.getID(),
                        material.getId()
                )
        );
    }

    @Test
    void deveImpedirEmprestimoSemPermissao()
            throws Exception {
        Usuario leitor = new Usuario(
                "Maria Silva",
                "maria",
                "123456",
                Cargo.LEITOR
        );

        sessao.iniciarSessao(leitor);

        assertThrows(
                OperacaoNaoPermitidaException.class,
                () -> service.registrarEmprestimo(
                        LocalDate.now().plusDays(7),
                        usuarioPermitido.getID(),
                        material.getId()
                )
        );
    }
}
