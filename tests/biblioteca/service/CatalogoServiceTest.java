package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.MaterialRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoServiceTest {

    private CatalogoService service;
    private MaterialRepositorio repositorio;
    private AutorizacaoService autorizacaoService;
    private Sessao sessao;
    private MaterialBibliografico livroTeste;

    @BeforeEach
    void setUp() throws ItemDuplicadoException, OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {
        repositorio = new MaterialRepositorio();
        autorizacaoService = new AutorizacaoService();
        sessao = new Sessao();
        service = new CatalogoService(repositorio, autorizacaoService, sessao);

        Usuario usuarioPermitido = new Usuario("Bibliotecario", "lib", "123456", Cargo.BIBLIOTECARIO);
        sessao.iniciarSessao(usuarioPermitido);

        livroTeste = service.cadastrarLivro(
                "Clean Code",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall"
        );

    }

    @Test
    void deveCadastrarMaterial() throws OperacaoNaoPermitidaException, RegistroNaoEncontradoException, UsuarioNaoAutenticadoException {

        MaterialBibliografico encontrado = service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
    }

    @Test
    void deveListarMateriaisCadastrados() throws OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {

        List<MaterialBibliografico> materiais = service.listarMateriais();

        assertEquals(1, materiais.size());
        assertTrue(materiais.contains(livroTeste));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverMateriais() throws OperacaoNaoPermitidaException, RegistroNaoEncontradoException, UsuarioNaoAutenticadoException {

        repositorio.remover(livroTeste.getId());
        List<MaterialBibliografico> materiais = service.listarMateriais();

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    void deveRemoverMaterialCadastrado() throws OperacaoNaoPermitidaException, RegistroNaoEncontradoException, UsuarioNaoAutenticadoException, MaterialIndisponivelException {

        service.removerMaterial(livroTeste.getId());

        assertTrue(service.listarMateriais().isEmpty());
    }

    @Test
    void deveAtualizarMaterialCadastrado() throws OperacaoNaoPermitidaException, RegistroNaoEncontradoException, UsuarioNaoAutenticadoException, TipoMaterialInvalidoException, CredenciaisInvalidasException {

        service.atualizarLivro("Clean Code - Nova versão",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall",
                livroTeste.getId());

        MaterialBibliografico encontrado = service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
    }
}
