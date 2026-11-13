package biblioteca.repository;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.usuario.Usuario;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositorioTest {

    private UsuarioRepositorio repositorio;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        repositorio = new UsuarioRepositorio();
        usuario = new Usuario(
            "João da Silva",
            "joao",
            "123456"
        );
    }

    @Test
    void deveCadastrarUsuario() throws ItemDuplicadoException, RegistroNaoEncontradoException {
        repositorio.adicionar(usuario);

        Usuario encontrado = repositorio.buscarPorId(usuario.getID());

        assertNotNull(encontrado);
        assertEquals(usuario.getID(), encontrado.getID());
    }

    @Test
    void deveListarUsuariosCadastrados() throws ItemDuplicadoException {
        repositorio.adicionar(usuario);

        List<Usuario> usuarios = repositorio.listarTodos();

        assertEquals(1, usuarios.size());
        assertTrue(usuarios.contains(usuario));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {
        List<Usuario> usuarios = repositorio.listarTodos();

        assertNotNull(usuarios);
        assertTrue(usuarios.isEmpty());
    }

    @Test
    void deveRemoverUsuarioCadastrado() throws ItemDuplicadoException, RegistroNaoEncontradoException {
        repositorio.adicionar(usuario);

        repositorio.remover(usuario.getID());

        assertTrue(repositorio.listarTodos().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> repositorio.buscarPorId(-1)
        );
    }

    @Test
    void deveLancarExcecaoAoRemoverUsuarioInexistente() {
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> repositorio.remover(-1)
        );
    }
}
