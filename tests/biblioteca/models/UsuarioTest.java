package biblioteca.models;

import static org.junit.jupiter.api.Assertions.*;

import biblioteca.model.usuario.*;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void usuarioComumDeveExibirSeusDados() {
        Usuario usuario = new Usuario(
            "Ana", "ana123", "senha"
        );

        String dados = usuario.exibirDados();

        assertTrue(dados.contains("Leitor: Ana"));
        assertTrue(dados.contains("Login: ana123"));
        assertTrue(dados.contains(String.valueOf(usuario.getID())));
    }

    @Test
    void bibliotecarioDeveExibirSeusDados() {
        Usuario bibliotecario = new Usuario(
            "Carlos", "carlos", "senha", Cargo.BIBLIOTECARIO
        );

        String dados = bibliotecario.exibirDados();

        assertTrue(dados.contains("Bibliotecario: Carlos"));
        assertTrue(dados.contains("Login: carlos"));
    }

    @Test
    void administradorDeveExibirSeusDados() {
        Usuario administrador = new Usuario(
            "Marina", "marina", "senha", Cargo.ADMINISTRADOR
        );

        String dados = administrador.exibirDados();

        assertTrue(dados.contains("Administrador: Marina"));
        assertTrue(dados.contains("Login: marina"));
    }
}

