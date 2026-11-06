package biblioteca.service;

import biblioteca.model.usuario.Usuario;
import biblioteca.model.usuario.UsuarioComum;
import biblioteca.repository.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService service;
    private UsuarioRepositorio repositorio;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        repositorio = new UsuarioRepositorio();
        service = new UsuarioService(repositorio);

        usuario = new UsuarioComum(
                "João Silva",
                "joao",
                "123456"
        );
    }

    @Test
    void deveCadastrarUsuario() {
        service.cadastrarUsuario(usuario);

        Usuario encontrado = service.buscarUsuario(usuario.getID());

        assertNotNull(encontrado);
        assertEquals(usuario.getID(), encontrado.getID());
    }

    @Test
    void deveListarUsuariosCadastrados() {
        service.cadastrarUsuario(usuario);

        List<Usuario> usuarios = service.listarUsuarios();

        assertEquals(1, usuarios.size());
        assertTrue(usuarios.contains(usuario));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {
        List<Usuario> usuarios = service.listarUsuarios();

        assertNotNull(usuarios);
        assertTrue(usuarios.isEmpty());
    }

    @Test
    void deveRemoverUsuarioCadastrado() {
        service.cadastrarUsuario(usuario);

        service.removerUsuario(usuario.getID());

        assertTrue(service.listarUsuarios().isEmpty());
    }

    @Test
    void deveAtualizarUsuarioCadastrado() {
        service.cadastrarUsuario(usuario);

        service.atualizarUsuario(usuario);

        Usuario encontrado = service.buscarUsuario(usuario.getID());

        assertNotNull(encontrado);
        assertEquals(usuario.getID(), encontrado.getID());
    }
}
