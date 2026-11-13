package biblioteca.auth;

import biblioteca.model.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessaoTest {

    private Sessao sessao;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        sessao = new Sessao();

        usuario = new Usuario(
                "João Silva",
                "joao",
                "123456"
        );
    }

    @Test
    void deveIniciarSessao() {
        sessao.iniciarSessao(usuario);

        assertTrue(sessao.estaAutenticado());
    }

    @Test
    void deveRetornarUsuarioLogado() {
        sessao.iniciarSessao(usuario);

        assertEquals(usuario, sessao.getUsuarioLogado());
    }

    @Test
    void deveIniciarSemUsuarioAutenticado() {
        assertFalse(sessao.estaAutenticado());
    }

    @Test
    void deveEncerrarSessao() {
        sessao.iniciarSessao(usuario);

        sessao.encerrarSessao();

        assertFalse(sessao.estaAutenticado());
    }

    @Test
    void devePermitirTrocarUsuarioLogado() {
        Usuario outroUsuario = new Usuario(
                "Maria Silva",
                "maria",
                "654321"
        );

        sessao.iniciarSessao(usuario);
        sessao.iniciarSessao(outroUsuario);

        assertTrue(sessao.estaAutenticado());
        assertEquals(outroUsuario, sessao.getUsuarioLogado());
    }
}
