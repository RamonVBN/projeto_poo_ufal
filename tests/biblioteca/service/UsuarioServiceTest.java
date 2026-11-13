package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
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
        service = new UsuarioService(usuarioRepositorio, emprestimoRepositorio, autorizacaoService, sessao);

    }

    @Test
    void deveCadastrarUsuario() throws
            RegistroNaoEncontradoException,
            ItemDuplicadoException,
            CredenciaisInvalidasException
    {
        Usuario usuarioCadastrado = service.cadastrarUsuario("João Silva", "joao", "123456");
        Usuario encontrado = usuarioRepositorio.buscarPorId(usuarioCadastrado.getID());

        assertNotNull(encontrado);
        assertEquals(usuarioCadastrado.getID(), encontrado.getID());
    }

    @Test
    void deveCadastrarUsuarioComCargoEspecifico() throws
            RegistroNaoEncontradoException,
            ItemDuplicadoException,
            CredenciaisInvalidasException,
            OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {
        Usuario usuarioPermitido = new Usuario("ADMINISTRADOR", "admin", "123456", Cargo.ADMINISTRADOR);
        usuarioRepositorio.adicionar(usuarioPermitido);
        sessao.iniciarSessao(usuarioPermitido);

        Usuario usuarioTeste = service.cadastrarUsuario("João Silva", "joao", "123456", Cargo.BIBLIOTECARIO);
        Usuario encontrado = usuarioRepositorio.buscarPorId(usuarioTeste.getID());

        assertNotNull(encontrado);
        assertEquals(usuarioTeste.getID(), encontrado.getID());
        assertEquals(usuarioTeste.getCargo(), encontrado.getCargo());
    }

    @Test
    void deveListarUsuariosCadastrados() throws OperacaoNaoPermitidaException,
            ItemDuplicadoException,
            CredenciaisInvalidasException, UsuarioNaoAutenticadoException {
        Usuario usuarioPermitido = new Usuario("Bibliotecario", "lib", "123456", Cargo.BIBLIOTECARIO);
        usuarioRepositorio.adicionar(usuarioPermitido);
        sessao.iniciarSessao(usuarioPermitido);

        Usuario usuarioTeste = service.cadastrarUsuario("João Silva", "joao", "123456");
        List<Usuario> usuarios = service.listarUsuarios();

        assertEquals(2, usuarios.size());
        assertTrue(usuarios.contains(usuarioTeste));
    }

    @Test
    void deveBuscarUsuarioPorId() throws OperacaoNaoPermitidaException,
            ItemDuplicadoException,
            CredenciaisInvalidasException,
            RegistroNaoEncontradoException, UsuarioNaoAutenticadoException {
        Usuario usuarioPermitido = new Usuario("Bibliotecario", "lib", "123456", Cargo.BIBLIOTECARIO);
        usuarioRepositorio.adicionar(usuarioPermitido);
        sessao.iniciarSessao(usuarioPermitido);

        Usuario usuarioTeste = service.cadastrarUsuario("João Silva", "joao", "123456");
        Usuario usuarioBuscado = service.buscarUsuario(usuarioTeste.getID());

        assertEquals(usuarioBuscado.getID(), usuarioTeste.getID());
    }

    @Test
    void deveRemoverUsuarioCadastrado() throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            ItemDuplicadoException,
            CredenciaisInvalidasException, UsuarioNaoAutenticadoException, UsuarioPossuiEmprestimosAtivosException {
        Usuario usuarioPermitido = new Usuario("ADMINISTRADOR", "admin", "123456", Cargo.ADMINISTRADOR);
        usuarioRepositorio.adicionar(usuarioPermitido);
        sessao.iniciarSessao(usuarioPermitido);

        Usuario usuarioTeste = service.cadastrarUsuario("João Silva", "joao", "123456");
        service.removerUsuario(usuarioTeste.getID());

        assertThrowsExactly(RegistroNaoEncontradoException.class, () -> usuarioRepositorio.buscarPorId(usuarioTeste.getID()));
    }

    @Test
    void deveAtualizarUsuarioCadastrado() throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            ItemDuplicadoException,
            CredenciaisInvalidasException, UsuarioNaoAutenticadoException {
        Usuario usuarioPermitido = new Usuario("ADMINISTRADOR", "admin", "123456", Cargo.ADMINISTRADOR);
        usuarioRepositorio.adicionar(usuarioPermitido);
        sessao.iniciarSessao(usuarioPermitido);

        Usuario usuarioTeste = service.cadastrarUsuario("João Silva", "joao", "123456");
        service.atualizarUsuario("Maria Silva", "maria", usuarioTeste.getID());

        Usuario encontrado = usuarioRepositorio.buscarPorId(usuarioTeste.getID());

        assertNotNull(encontrado);
        assertEquals(usuarioTeste.getID(), encontrado.getID());
    }
}
