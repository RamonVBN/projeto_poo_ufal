package biblioteca.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import biblioteca.model.usuario.Administrador;
import biblioteca.model.usuario.Bibliotecario;
import biblioteca.model.usuario.UsuarioComum;

class UsuarioTest {

    @Test
    void usuarioComumDeveExibirSeusDados() {
        UsuarioComum usuario = new UsuarioComum(
            "Ana", "ana123", "senha"
        );

        String dados = usuario.exibirDados();

        assertTrue(dados.contains("Usuario: Ana"));
        assertTrue(dados.contains("Login: ana123"));
        assertTrue(dados.contains(String.valueOf(usuario.getID())));
    }

    @Test
    void bibliotecarioDeveExibirSeusDados() {
        Bibliotecario bibliotecario = new Bibliotecario(
            "Carlos", "carlos", "senha"
        );

        String dados = bibliotecario.exibirDados();

        assertTrue(dados.contains("Bibliotecario: Carlos"));
        assertTrue(dados.contains("Login: carlos"));
    }

    @Test
    void administradorDeveExibirSeusDados() {
        Administrador administrador = new Administrador(
            "Marina", "marina", "senha"
        );

        String dados = administrador.exibirDados();

        assertTrue(dados.contains("Administrador: Marina"));
        assertTrue(dados.contains("Login: marina"));
    }
}

