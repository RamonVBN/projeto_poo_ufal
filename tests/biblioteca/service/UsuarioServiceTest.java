
package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.Emprestimo;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService service;
    private UsuarioRepositorio usuarioRepositorio;
    private EmprestimoRepositorio emprestimoRepositorio;
    private AutorizacaoService autorizacaoService;
    private Sessao sessao;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = new UsuarioRepositorio();
        emprestimoRepositorio = new EmprestimoRepositorio();
        autorizacaoService = new AutorizacaoService();
        sessao = new Sessao();

        service = new UsuarioService(
                usuarioRepositorio,
                emprestimoRepositorio,
                autorizacaoService,
                sessao
        );
    }

    // Método auxiliar para autenticar usuários com determinado cargo.

    private Usuario autenticar(Cargo cargo) throws ItemDuplicadoException {
        Usuario usuario = new Usuario(
                "Usuario Teste",
                "teste",
                "123456",
                cargo
        );

        usuarioRepositorio.adicionar(usuario);
        sessao.iniciarSessao(usuario);

        return usuario;
    }

    // CADASTRO

    @Test
    void deveCadastrarUsuario() throws Exception {
        Usuario usuarioCadastrado = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        Usuario encontrado = usuarioRepositorio.buscarPorId(
                usuarioCadastrado.getID()
        );

        assertNotNull(encontrado);
        assertEquals(usuarioCadastrado.getID(), encontrado.getID());
    }

    @Test
    void deveCadastrarUsuarioComCargoEspecifico() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva",
                "joao",
                "123456",
                Cargo.BIBLIOTECARIO
        );

        Usuario encontrado = usuarioRepositorio.buscarPorId(
                usuarioTeste.getID()
        );

        assertNotNull(encontrado);
        assertEquals(usuarioTeste.getID(), encontrado.getID());
        assertEquals(Cargo.BIBLIOTECARIO, encontrado.getCargo());
    }

    @Test
    void deveImpedirCadastroComNomeInvalido() {
        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario(null, "joao", "123456")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("", "joao", "123456")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("   ", "joao", "123456")
        );
    }

    @Test
    void deveImpedirCadastroComLoginInvalido() {
        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", null, "123456")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "", "123456")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "   ", "123456")
        );
    }

    @Test
    void deveImpedirCadastroComSenhaInvalida() {
        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "joao", null)
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "joao", "")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "joao", "12345")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.cadastrarUsuario("João", "joao", "12345678901234567")
        );
    }

    @Test
    void deveImpedirCadastroDeUsuarioDuplicado() throws Exception {
        service.cadastrarUsuario("João Silva", "joao", "123456");

        assertThrowsExactly(
                ItemDuplicadoException.class,
                () -> service.cadastrarUsuario(
                        "Outro João", "joao", "654321"
                )
        );
    }

    @Test
    void deveImpedirCadastroComCargoSemPermissao() throws Exception {
        autenticar(Cargo.BIBLIOTECARIO);

        assertThrowsExactly(
                OperacaoNaoPermitidaException.class,
                () -> service.cadastrarUsuario(
                        "João Silva",
                        "joao",
                        "123456",
                        Cargo.LEITOR
                )
        );
    }

    @Test
    void deveImpedirCadastroComCargoSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.cadastrarUsuario(
                        "João Silva",
                        "joao",
                        "123456",
                        Cargo.LEITOR
                )
        );
    }

    // AUTENTICAÇÃO

    @Test
    void deveRealizarLogin() throws Exception {
        Usuario usuario = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        service.logar("joao", "123456");

        assertTrue(sessao.estaAutenticado());
        assertEquals(usuario.getID(), sessao.getUsuarioLogado().getID());
    }

    @Test
    void deveImpedirLoginComSenhaIncorreta() throws Exception {
        service.cadastrarUsuario("João Silva", "joao", "123456");

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.logar("joao", "654321")
        );

        assertFalse(sessao.estaAutenticado());
    }

    @Test
    void deveImpedirLoginComUsuarioInexistente() {
        assertThrowsExactly(
                RegistroNaoEncontradoException.class,
                () -> service.logar("inexistente", "123456")
        );
    }

    @Test
    void deveImpedirLoginComCredenciaisInvalidas() {
        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.logar("", "123456")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.logar("joao", "")
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.logar("joao", "12345")
        );
    }

    @Test
    void deveEncerrarSessao() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        service.deslogar();

        assertFalse(sessao.estaAutenticado());
        assertNull(sessao.getUsuarioLogado());
    }

    @Test
    void deveImpedirLogoutSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.deslogar()
        );
    }

    // CONSULTA

    @Test
    void deveListarUsuariosCadastrados() throws Exception {
        autenticar(Cargo.BIBLIOTECARIO);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        List<Usuario> usuarios = service.listarUsuarios();

        assertEquals(2, usuarios.size());
        assertTrue(usuarios.contains(usuarioTeste));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        autenticar(Cargo.BIBLIOTECARIO);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        Usuario usuarioBuscado = service.buscarUsuario(
                usuarioTeste.getID()
        );

        assertEquals(usuarioTeste.getID(), usuarioBuscado.getID());
    }

    @Test
    void deveImpedirBuscaSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.buscarUsuario(1)
        );
    }

    @Test
    void deveImpedirListagemSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.listarUsuarios()
        );
    }

    @Test
    void deveImpedirConsultaSemPermissao() throws Exception {
        autenticar(Cargo.LEITOR);

        assertThrowsExactly(
                OperacaoNaoPermitidaException.class,
                () -> service.buscarUsuario(1)
        );

        assertThrowsExactly(
                OperacaoNaoPermitidaException.class,
                () -> service.listarUsuarios()
        );
    }

    @Test
    void deveImpedirBuscaDeUsuarioInexistente() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        assertThrowsExactly(
                RegistroNaoEncontradoException.class,
                () -> service.buscarUsuario(Integer.MIN_VALUE)
        );
    }

    // ATUALIZAÇÃO

    @Test
    void deveAtualizarUsuarioCadastrado() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        service.atualizarUsuario(
                "Maria Silva",
                "maria",
                usuarioTeste.getID()
        );

        Usuario encontrado = usuarioRepositorio.buscarPorId(
                usuarioTeste.getID()
        );

        assertNotNull(encontrado);
        assertEquals(usuarioTeste.getID(), encontrado.getID());
        assertEquals("Maria Silva", encontrado.getNome());
        assertEquals("maria", encontrado.getLogin());
    }

    @Test
    void deveImpedirAtualizacaoSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.atualizarUsuario(
                        "Maria Silva", "maria", 1
                )
        );
    }

    @Test
    void deveImpedirAtualizacaoSemPermissao() throws Exception {
        autenticar(Cargo.BIBLIOTECARIO);

        assertThrowsExactly(
                OperacaoNaoPermitidaException.class,
                () -> service.atualizarUsuario(
                        "Maria Silva", "maria", 1
                )
        );
    }

    @Test
    void deveImpedirAtualizacaoComNomeInvalido() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuario = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.atualizarUsuario(
                        null, "maria", usuario.getID()
                )
        );
    }

    @Test
    void deveImpedirAtualizacaoComLoginInvalido() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuario = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        assertThrowsExactly(
                CredenciaisInvalidasException.class,
                () -> service.atualizarUsuario(
                        "Maria Silva", "", usuario.getID()
                )
        );
    }

    @Test
    void deveImpedirAtualizacaoDeUsuarioInexistente() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        assertThrowsExactly(
                RegistroNaoEncontradoException.class,
                () -> service.atualizarUsuario(
                        "Maria Silva",
                        "maria",
                        Integer.MIN_VALUE
                )
        );
    }

    // REMOÇÃO

    @Test
    void deveRemoverUsuarioCadastrado() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        service.removerUsuario(usuarioTeste.getID());

        assertThrowsExactly(
                RegistroNaoEncontradoException.class,
                () -> usuarioRepositorio.buscarPorId(
                        usuarioTeste.getID()
                )
        );
    }

    @Test
    void deveImpedirRemocaoSemAutenticacao() {
        assertThrowsExactly(
                UsuarioNaoAutenticadoException.class,
                () -> service.removerUsuario(1)
        );
    }

    @Test
    void deveImpedirRemocaoSemPermissao() throws Exception {
        autenticar(Cargo.BIBLIOTECARIO);

        assertThrowsExactly(
                OperacaoNaoPermitidaException.class,
                () -> service.removerUsuario(1)
        );
    }

    @Test
    void deveImpedirRemocaoDeUsuarioComEmprestimoAtivo() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuarioTeste);

        emprestimoRepositorio.adicionar(emprestimo);

        assertThrowsExactly(
                UsuarioPossuiEmprestimosAtivosException.class,
                () -> service.removerUsuario(usuarioTeste.getID())
        );

        assertNotNull(
                usuarioRepositorio.buscarPorId(usuarioTeste.getID())
        );
    }

    @Test
    void devePermitirRemocaoDeUsuarioComEmprestimoDevolvido() throws Exception {
        autenticar(Cargo.ADMINISTRADOR);

        Usuario usuarioTeste = service.cadastrarUsuario(
                "João Silva", "joao", "123456"
        );

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuarioTeste);

        emprestimo.registrarDevolucao();
        emprestimoRepositorio.adicionar(emprestimo);

        service.removerUsuario(usuarioTeste.getID());

        assertThrowsExactly(
                RegistroNaoEncontradoException.class,
                () -> usuarioRepositorio.buscarPorId(usuarioTeste.getID())
        );
    }
}
