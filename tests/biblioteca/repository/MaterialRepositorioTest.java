package biblioteca.repository;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialRepositorioTest {

    private MaterialRepositorio repositorio;
    private MaterialBibliografico material;

    @BeforeEach
    void setUp() {
        repositorio = new MaterialRepositorio();

        material = new Livro(
            "Clean Code",
            2008,
            "Robert C. Martin",
            "9780132350884",
            "Prentice Hall"
        );
    }

    @Test
    void deveCadastrarMaterial() throws ItemDuplicadoException, RegistroNaoEncontradoException {
        repositorio.adicionar(material);

        MaterialBibliografico encontrado =
            repositorio.buscarPorId(material.getId());

        assertNotNull(encontrado);
        assertEquals(material.getId(), encontrado.getId());
    }

    @Test
    void deveListarMateriaisCadastrados() throws ItemDuplicadoException {
        repositorio.adicionar(material);

        List<MaterialBibliografico> materiais =
            repositorio.listarTodos();

        assertEquals(1, materiais.size());
        assertTrue(materiais.contains(material));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverMateriais() {
        List<MaterialBibliografico> materiais =
            repositorio.listarTodos();

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    void deveRemoverMaterialCadastrado() throws ItemDuplicadoException, RegistroNaoEncontradoException {
        repositorio.adicionar(material);

        repositorio.remover(material.getId());

        assertTrue(repositorio.listarTodos().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoBuscarMaterialInexistente() {
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> repositorio.buscarPorId(-1)
        );
    }

    @Test
    void deveLancarExcecaoAoRemoverMaterialInexistente() {
        assertThrows(
            RegistroNaoEncontradoException.class,
            () -> repositorio.remover(-1)
        );
    }

    @Test
    void deveAtualizarMaterialCadastrado() throws ItemDuplicadoException, RegistroNaoEncontradoException {
        repositorio.adicionar(material);
        MaterialBibliografico materialAtualizado =
            new Livro(
                "Clean Code - Nova edição",
                2024,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall",
                    material.getId()
            );

        // A atualização deve preservar o ID do material original.
        // Ajuste o ID conforme o mecanismo de identificação do seu modelo.
        repositorio.atualizar(materialAtualizado);

        MaterialBibliografico encontrado =
            repositorio.buscarPorId(materialAtualizado.getId());

        assertNotNull(encontrado);
    }
}