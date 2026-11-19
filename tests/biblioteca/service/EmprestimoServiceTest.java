package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.UsuarioPossuiMultasPendentes;
import biblioteca.model.Emprestimo;
import biblioteca.model.Multa;
import biblioteca.model.StatusMulta;
import biblioteca.model.material.Livro;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.MultaRepositorio;
import biblioteca.repository.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoServiceMultaTest {

    private EmprestimoService emprestimoService;
    private MultaService multaService;

    private EmprestimoRepositorio emprestimoRepositorio;
    private MaterialRepositorio materialRepositorio;
    private UsuarioRepositorio usuarioRepositorio;
    private MultaRepositorio multaRepositorio;

    private Sessao sessao;

    private Usuario bibliotecario;
    private Usuario leitor;

    @BeforeEach
    void setUp() throws Exception {
        emprestimoRepositorio = new EmprestimoRepositorio();
        materialRepositorio = new MaterialRepositorio();
        usuarioRepositorio = new UsuarioRepositorio();
        multaRepositorio = new MultaRepositorio();

        sessao = new Sessao();

        AutorizacaoService autorizacaoService =
                new AutorizacaoService();

        multaService = new MultaService(
                autorizacaoService,
                sessao,
                multaRepositorio
        );

        emprestimoService = new EmprestimoService(
                emprestimoRepositorio,
                materialRepositorio,
                usuarioRepositorio,
                autorizacaoService,
                sessao,
                multaService
        );

        bibliotecario = new Usuario(
                "João",
                "joao",
                "123456",
                Cargo.BIBLIOTECARIO
        );

        leitor = new Usuario(
                "Maria",
                "maria",
                "123456",
                Cargo.LEITOR
        );

        usuarioRepositorio.adicionar(bibliotecario);
        usuarioRepositorio.adicionar(leitor);

        sessao.iniciarSessao(bibliotecario);
    }

    private Livro criarLivro(String titulo, String isbn) {
        return new Livro(
                titulo,
                2020,
                "Autor",
                isbn,
                "Editora"
        );
    }

    private Emprestimo criarEmprestimoAtrasado(Livro livro)
            throws Exception {

        Emprestimo emprestimo = new Emprestimo(
                LocalDate.now().minusDays(5),
                livro,
                leitor
        );

        emprestimoRepositorio.adicionar(emprestimo);

        return emprestimo;
    }

    @Test
    void deveGerarMultaAoDevolverEmprestimoAtrasado()
            throws Exception {

        Livro livro = criarLivro("Clean Code", "1111111111111");

        materialRepositorio.adicionar(livro);

        Emprestimo emprestimo = criarEmprestimoAtrasado(livro);

        emprestimoService.registrarDevolucao(emprestimo.getId());

        assertEquals(1, multaRepositorio.listarTodos().size());

        Multa multa = multaRepositorio.listarTodos().getFirst();

        assertEquals(5, multa.getDiasAtraso());
        assertEquals(StatusMulta.PENDENTE, multa.getStatus());
        assertEquals(emprestimo.getId(), multa.getEmprestimo().getId());
    }

    @Test
    void naoDeveGerarMultaAoDevolverEmprestimoNoPrazo()
            throws Exception {

        Livro livro = criarLivro("Clean Code", "2222222222222");

        materialRepositorio.adicionar(livro);

        Emprestimo emprestimo = new Emprestimo(
                LocalDate.now(),
                livro,
                leitor
        );

        emprestimoRepositorio.adicionar(emprestimo);

        emprestimoService.registrarDevolucao(emprestimo.getId());

        assertTrue(multaRepositorio.listarTodos().isEmpty());
    }

    @Test
    void deveImpedirNovoEmprestimoQuandoUsuarioPossuiMultaPendente()
            throws Exception {

        Livro livroAtrasado = criarLivro(
                "Livro Atrasado",
                "3333333333333"
        );

        Livro novoLivro = criarLivro(
                "Novo Livro",
                "4444444444444"
        );

        materialRepositorio.adicionar(livroAtrasado);
        materialRepositorio.adicionar(novoLivro);

        Emprestimo emprestimoAtrasado =
                criarEmprestimoAtrasado(livroAtrasado);

        multaService.cadastrarMulta(5, emprestimoAtrasado);

        assertThrows(
                UsuarioPossuiMultasPendentes.class,
                () -> emprestimoService.registrarEmprestimo(
                        leitor.getID(),
                        novoLivro.getId()
                )
        );
    }

    @Test
    void devePermitirNovoEmprestimoDepoisDoPagamentoDaMulta()
            throws Exception {

        Livro livroAtrasado = criarLivro(
                "Livro Atrasado",
                "5555555555555"
        );

        Livro novoLivro = criarLivro(
                "Novo Livro",
                "6666666666666"
        );

        materialRepositorio.adicionar(livroAtrasado);
        materialRepositorio.adicionar(novoLivro);

        Emprestimo emprestimoAtrasado =
                criarEmprestimoAtrasado(livroAtrasado);

        multaService.cadastrarMulta(5, emprestimoAtrasado);

        Multa multa = multaRepositorio.listarTodos().getFirst();

        multaService.pagarMulta(multa.getId());

        assertDoesNotThrow(
                () -> emprestimoService.registrarEmprestimo(
                        leitor.getID(),
                        novoLivro.getId()
                )
        );
    }

    @Test
    void devePermitirNovoEmprestimoDepoisDoCancelamentoDaMulta()
            throws Exception {

        Livro livroAtrasado = criarLivro(
                "Livro Atrasado",
                "7777777777777"
        );

        Livro novoLivro = criarLivro(
                "Novo Livro",
                "8888888888888"
        );

        materialRepositorio.adicionar(livroAtrasado);
        materialRepositorio.adicionar(novoLivro);

        Emprestimo emprestimoAtrasado =
                criarEmprestimoAtrasado(livroAtrasado);

        multaService.cadastrarMulta(5, emprestimoAtrasado);

        Multa multa = multaRepositorio.listarTodos().getFirst();

        multaService.cancelarMulta(multa.getId());

        assertDoesNotThrow(
                () -> emprestimoService.registrarEmprestimo(
                        leitor.getID(),
                        novoLivro.getId()
                )
        );
    }

    @Test
    void deveDeixarMaterialDisponivelDepoisDaDevolucaoAtrasada()
            throws Exception {

        Livro livro = criarLivro("Clean Code", "9999999999999");

        materialRepositorio.adicionar(livro);

        Emprestimo emprestimo = criarEmprestimoAtrasado(livro);

        livro.marcarComoIndisponivel();

        emprestimoService.registrarDevolucao(emprestimo.getId());

        assertTrue(livro.estaDisponivel());
    }
}