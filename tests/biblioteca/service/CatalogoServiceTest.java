
package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.material.Jornal;
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
    void setUp() throws ItemDuplicadoException,
            OperacaoNaoPermitidaException,
            UsuarioNaoAutenticadoException,
            CredenciaisInvalidasException {

        repositorio = new MaterialRepositorio();
        autorizacaoService = new AutorizacaoService();
        sessao = new Sessao();
        service = new CatalogoService(repositorio, autorizacaoService, sessao);

        Usuario usuarioPermitido = new Usuario(
                "Bibliotecario", "lib", "123456", Cargo.BIBLIOTECARIO
        );

        sessao.iniciarSessao(usuarioPermitido);

        livroTeste = service.cadastrarLivro(
                "Clean Code",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall"
        );
    }

    // CADASTRO

    @Test
    void deveCadastrarMaterial()
            throws Exception {

        MaterialBibliografico encontrado =
                service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
    }

    @Test
    void deveCadastrarJornalRevista()
            throws OperacaoNaoPermitidaException,
            UsuarioNaoAutenticadoException,
            CredenciaisInvalidasException,
            ItemDuplicadoException,
            RegistroNaoEncontradoException {

        Jornal jornal = service.cadastrarJornalRevista(
                "National Geographic",
                2024,
                "National Geographic Society",
                150,
                "15/05/2024"
        );

        MaterialBibliografico encontrado =
                service.buscarMaterial(jornal.getId());

        assertNotNull(encontrado);
        assertInstanceOf(Jornal.class, encontrado);
        assertEquals(jornal.getId(), encontrado.getId());
    }

    @Test
    void deveImpedirCadastroDeLivroComTituloInvalido() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        " ",
                        2008,
                        "Robert C. Martin",
                        "9780132350884",
                        "Prentice Hall"
                ));
    }

    @Test
    void deveImpedirCadastroDeLivroComAnoInvalido() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        "Clean Code",
                        0,
                        "Robert C. Martin",
                        "9780132350884",
                        "Prentice Hall"
                ));
    }

    @Test
    void deveImpedirCadastroDeLivroComAnoFuturo() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        "Clean Code",
                        java.time.LocalDate.now().getYear() + 1,
                        "Robert C. Martin",
                        "9780132350884",
                        "Prentice Hall"
                ));
    }

    @Test
    void deveImpedirCadastroDeLivroComAutorInvalido() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        "Clean Code",
                        2008,
                        " ",
                        "9780132350884",
                        "Prentice Hall"
                ));
    }

    @Test
    void deveImpedirCadastroDeLivroComIsbnInvalido() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        "Clean Code",
                        2008,
                        "Robert C. Martin",
                        " ",
                        "Prentice Hall"
                ));
    }

    @Test
    void deveImpedirCadastroDeLivroComEditoraInvalida() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarLivro(
                        "Clean Code",
                        2008,
                        "Robert C. Martin",
                        "9780132350884",
                        ""
                ));
    }

    @Test
    void deveImpedirCadastroDeJornalComNumeroEdicaoInvalido() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarJornalRevista(
                        "National Geographic",
                        2024,
                        "National Geographic Society",
                        0,
                        "15/05/2024"
                ));
    }

    @Test
    void deveImpedirCadastroDeJornalComDataInvalida() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.cadastrarJornalRevista(
                        "National Geographic",
                        2024,
                        "National Geographic Society",
                        150,
                        " "
                ));
    }

    // BUSCA E LISTAGEM

    @Test
    void deveListarMateriaisCadastrados()
            throws OperacaoNaoPermitidaException,
            UsuarioNaoAutenticadoException {

        List<MaterialBibliografico> materiais =
                service.listarMateriais();

        assertEquals(1, materiais.size());
        assertTrue(materiais.contains(livroTeste));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverMateriais()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException {

        repositorio.remover(livroTeste.getId());

        List<MaterialBibliografico> materiais =
                service.listarMateriais();

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    void deveListarMateriaisPorTitulo()
            throws OperacaoNaoPermitidaException,
            UsuarioNaoAutenticadoException {

        List<MaterialBibliografico> materiais =
                service.listarMateriaisPorTitulo("Clean Code");

        assertEquals(1, materiais.size());
        assertTrue(materiais.contains(livroTeste));
    }

    @Test
    void deveRetornarListaVaziaQuandoTituloNaoForEncontrado()
            throws OperacaoNaoPermitidaException,
            UsuarioNaoAutenticadoException {

        List<MaterialBibliografico> materiais =
                service.listarMateriaisPorTitulo("Java");

        assertNotNull(materiais);
        assertTrue(materiais.isEmpty());
    }

    @Test
    void deveBuscarMaterialPorId()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException {

        MaterialBibliografico encontrado =
                service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarMaterialInexistente() {

        assertThrows(RegistroNaoEncontradoException.class,
                () -> service.buscarMaterial(Integer.MIN_VALUE));
    }

    // ATUALIZAÇÃO

    @Test
    void deveAtualizarMaterialCadastrado()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        service.atualizarLivro(
                "Clean Code - Nova versão",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall",
                livroTeste.getId()
        );

        MaterialBibliografico encontrado =
                service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
        assertEquals("Clean Code - Nova versão", encontrado.getTitulo());
    }

    @Test
    void deveAtualizarJornalRevistaCadastrado()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException,
            ItemDuplicadoException {

        Jornal jornal = service.cadastrarJornalRevista(
                "National Geographic",
                2024,
                "National Geographic Society",
                150,
                "15/05/2024"
        );

        service.atualizarJornalRevista(
                "National Geographic - Nova edição",
                2025,
                "National Geographic Society",
                151,
                "15/06/2025",
                jornal.getId()
        );

        MaterialBibliografico encontrado =
                service.buscarMaterial(jornal.getId());

        assertNotNull(encontrado);
        assertEquals(jornal.getId(), encontrado.getId());
        assertEquals(
                "National Geographic - Nova edição",
                encontrado.getTitulo()
        );
    }

    @Test
    void deveImpedirAtualizacaoDeLivroComTipoIncorreto()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            CredenciaisInvalidasException,
            ItemDuplicadoException {

        Jornal jornal = service.cadastrarJornalRevista(
                "National Geographic",
                2024,
                "National Geographic Society",
                150,
                "15/05/2024"
        );

        assertThrows(TipoMaterialInvalidoException.class,
                () -> service.atualizarLivro(
                        "Livro",
                        2024,
                        "Autor",
                        "9780132350884",
                        "Editora",
                        jornal.getId()
                ));
    }

    @Test
    void deveImpedirAtualizacaoDeJornalComTipoIncorreto() {

        assertThrows(TipoMaterialInvalidoException.class,
                () -> service.atualizarJornalRevista(
                        "National Geographic",
                        2024,
                        "National Geographic Society",
                        150,
                        "15/05/2024",
                        livroTeste.getId()
                ));
    }

    @Test
    void deveImpedirAtualizacaoDeMaterialInexistente() {

        assertThrows(RegistroNaoEncontradoException.class,
                () -> service.atualizarLivro(
                        "Livro",
                        2024,
                        "Autor",
                        "9780132350884",
                        "Editora",
                        Integer.MIN_VALUE
                ));
    }

    @Test
    void deveImpedirAtualizacaoDeLivroComDadosInvalidos() {

        assertThrows(CredenciaisInvalidasException.class,
                () -> service.atualizarLivro(
                        "",
                        2008,
                        "Robert C. Martin",
                        "9780132350884",
                        "Prentice Hall",
                        livroTeste.getId()
                ));
    }

    // REMOÇÃO

    @Test
    void deveRemoverMaterialCadastrado()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            MaterialIndisponivelException {

        service.removerMaterial(livroTeste.getId());

        assertTrue(service.listarMateriais().isEmpty());
    }

    @Test
    void deveImpedirRemocaoDeMaterialInexistente() {

        assertThrows(RegistroNaoEncontradoException.class,
                () -> service.removerMaterial(Integer.MIN_VALUE));
    }

    @Test
    void deveImpedirRemocaoDeMaterialIndisponivel() {

        livroTeste.marcarComoIndisponivel();

        assertThrows(MaterialIndisponivelException.class,
                () -> service.removerMaterial(livroTeste.getId()));

        assertDoesNotThrow(
                () -> repositorio.buscarPorId(livroTeste.getId())
        );
    }

    // AUTENTICAÇÃO

    @Test
    void deveImpedirBuscaSemAutenticacao() {

        sessao.encerrarSessao();

        assertThrows(UsuarioNaoAutenticadoException.class,
                () -> service.buscarMaterial(livroTeste.getId()));
    }

    @Test
    void deveImpedirCadastroSemAutenticacao() {

        sessao.encerrarSessao();

        assertThrows(UsuarioNaoAutenticadoException.class,
                () -> service.cadastrarLivro(
                        "Livro",
                        2024,
                        "Autor",
                        "9780132350884",
                        "Editora"
                ));
    }

    @Test
    void deveImpedirRemocaoSemAutenticacao() {

        sessao.encerrarSessao();

        assertThrows(UsuarioNaoAutenticadoException.class,
                () -> service.removerMaterial(livroTeste.getId()));
    }

    // AUTORIZAÇÃO

    @Test
    void deveImpedirCadastroPorUsuarioSemPermissao() {

        Usuario leitor = new Usuario(
                "Leitor", "leitor", "123456", Cargo.LEITOR
        );

        sessao.iniciarSessao(leitor);

        assertThrows(OperacaoNaoPermitidaException.class,
                () -> service.cadastrarLivro(
                        "Livro",
                        2024,
                        "Autor",
                        "9780132350884",
                        "Editora"
                ));
    }

    @Test
    void deveImpedirAtualizacaoPorUsuarioSemPermissao() {

        Usuario leitor = new Usuario(
                "Leitor", "leitor", "123456", Cargo.LEITOR
        );

        sessao.iniciarSessao(leitor);

        assertThrows(OperacaoNaoPermitidaException.class,
                () -> service.atualizarLivro(
                        "Novo título",
                        2008,
                        "Robert C. Martin",
                        "9780132350884",
                        "Prentice Hall",
                        livroTeste.getId()
                ));
    }

    @Test
    void deveImpedirRemocaoPorUsuarioSemPermissao() {

        Usuario leitor = new Usuario(
                "Leitor", "leitor", "123456", Cargo.LEITOR
        );

        sessao.iniciarSessao(leitor);

        assertThrows(OperacaoNaoPermitidaException.class,
                () -> service.removerMaterial(livroTeste.getId()));
    }

    @Test
    void devePermitirConsultaPorUsuarioLeitor()
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException {

        Usuario leitor = new Usuario(
                "Leitor", "leitor", "123456", Cargo.LEITOR
        );

        sessao.iniciarSessao(leitor);

        MaterialBibliografico encontrado =
                service.buscarMaterial(livroTeste.getId());

        assertNotNull(encontrado);
        assertEquals(livroTeste.getId(), encontrado.getId());
    }
}
