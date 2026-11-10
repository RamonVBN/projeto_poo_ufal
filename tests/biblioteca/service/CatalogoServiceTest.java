package biblioteca.service;

import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.repository.MaterialRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoServiceTest {

    private CatalogoService service;
    private MaterialRepositorio repositorio;
    private MaterialBibliografico material;

    @BeforeEach
    void setUp() {
        repositorio = new MaterialRepositorio();
        service = new CatalogoService(repositorio);

        material = new Livro(
                "Clean Code",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall"
        );
    }

    @Test
    void deveCadastrarMaterial() {
        service.cadastrarMaterial(material);

        MaterialBibliografico encontrado = service.buscarMaterial(material.getId());

        assertNotNull(encontrado);
        assertEquals(material.getId(), encontrado.getId());
    }

    @Test
    void deveListarMateriaisCadastrados() {
        service.cadastrarMaterial(material);

        List<MaterialBibliografico> materiais = service.listarMateriais();

        assertEquals(1, materiais.size());
        assertTrue(materiais.contains(material));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverMateriais() {
        List<MaterialBibliografico> materiais = service.listarMateriais();

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    void deveRemoverMaterialCadastrado() {
        service.cadastrarMaterial(material);

        service.removerMaterial(material.getId());

        assertTrue(service.listarMateriais().isEmpty());
    }

    @Test
    void deveAtualizarMaterialCadastrado() {
        service.cadastrarMaterial(material);

        service.atualizarMaterial(material);

        MaterialBibliografico encontrado = service.buscarMaterial(material.getId());

        assertNotNull(encontrado);
        assertEquals(material.getId(), encontrado.getId());
    }
}
