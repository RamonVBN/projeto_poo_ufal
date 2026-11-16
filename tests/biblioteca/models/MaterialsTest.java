package biblioteca.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import biblioteca.model.material.Jornal;
import biblioteca.model.material.Livro;
import biblioteca.model.material.Revista;

public class MaterialsTest {

	@Test
    void livroDeveExibirSeusDetalhes() {
        Livro livro = new Livro(
            "Clean Code", 2008, "Robert C. Martin",
            "9780132350884", "Prentice Hall"
        );

        String detalhes = livro.exibirDetalhes();

        assertTrue(detalhes.contains("Clean Code"));
        assertTrue(detalhes.contains("Robert C. Martin"));
        assertTrue(detalhes.contains("2008"));
        assertTrue(detalhes.contains("9780132350884"));
        assertTrue(detalhes.contains("Prentice Hall"));
    }

    @Test
    void revistaDeveExibirSeusDetalhes() {
        Revista revista = new Revista(
            "National Geographic", 2024, "Editora Abril",
            150, "01/2024"
        );

        String detalhes = revista.exibirDetalhes();

        assertTrue(detalhes.contains("National Geographic"));
        assertTrue(detalhes.contains("2024"));
        assertTrue(detalhes.contains("150"));
        assertTrue(detalhes.contains("Editora Abril"));
    }

    @Test
    void jornalDeveExibirSeusDetalhes() {
        Jornal jornal = new Jornal(
            "Jornal da Cidade", 2025, "Editora Local",
            42, "10/03/2025"
        );

        String detalhes = jornal.exibirDetalhes();

        assertTrue(detalhes.contains("Jornal da Cidade"));
        assertTrue(detalhes.contains("2025"));
        assertTrue(detalhes.contains("42"));
        assertTrue(detalhes.contains("Editora Local"));
    }

    @Test
    void materialDeveComecarDisponivelEPermitirAlternarDisponibilidade() {
        Livro livro = new Livro(
            "Clean Code", 2008, "Robert C. Martin",
            "9780132350884", "Prentice Hall"
        );

        assertTrue(livro.estaDisponivel(),
            "Um material recém-cadastrado deve estar disponível.");

        livro.marcarComoIndisponivel();

        assertFalse(livro.estaDisponivel(),
            "Após alterar a disponibilidade, o material não deve poder ser emprestado.");

        livro.marcarComoDisponivel();

        assertTrue(livro.estaDisponivel(),
            "Após alternar novamente, o material deve voltar a ficar disponível.");
    }
}
