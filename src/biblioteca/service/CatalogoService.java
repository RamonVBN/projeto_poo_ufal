package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.material.Jornal;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Permissao;
import biblioteca.repository.MaterialRepositorio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class CatalogoService extends Service {

    private final MaterialRepositorio repositorio;

    public CatalogoService(
            MaterialRepositorio repositorio,
            AutorizacaoService autorizacaoService,
            Sessao sessao
    )
    {
        super(autorizacaoService, sessao);
        this.repositorio = repositorio;
    }

    private void validarAnoPublicacao(int anoPublicacao) throws CredenciaisInvalidasException {
        if (anoPublicacao <= 0 || anoPublicacao > LocalDate.now().getYear()) {
            throw new CredenciaisInvalidasException();
        }
    }

    private void validarLivro(
            String titulo,
            int anoPublicacao,
            String autor,
            String isbn,
            String editora,
            Path capa
    ) throws CredenciaisInvalidasException {
        this.validarStrings(titulo);
        this.validarStrings(editora);
        this.validarStrings(autor);
        this.validarStrings(isbn);
        this.validarAnoPublicacao(anoPublicacao);
        this.validarCapa(capa);
    }

    private void validarCapa(Path capa) throws CredenciaisInvalidasException{
		if (!Files.exists(capa)) {
			throw new CredenciaisInvalidasException();
		} if (!Files.isRegularFile(capa)) {
			throw new CredenciaisInvalidasException();
		} if (!Files.isReadable(capa)) {
			throw new CredenciaisInvalidasException();
		}
	}

	private void validarJornalRevista
            (
                String titulo, int anoPublicacao,
                String editora, int numeroEdicao,
                String dataPublicacao, Path capa
            ) throws CredenciaisInvalidasException {

        this.validarStrings(titulo);
        this.validarStrings(editora);
        this.validarStrings(dataPublicacao);
        this.validarAnoPublicacao(anoPublicacao);
        this.validarCapa(capa);

        if (numeroEdicao <= 0) {
            throw new CredenciaisInvalidasException();
        }
    }

    public Livro cadastrarLivro
            (
                    String titulo,
                    int anoPublicacao,
                    String autor,
                    String isbn,
                    String editora,
                    Path capa
            )
            throws OperacaoNaoPermitidaException, ItemDuplicadoException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        this.validarLivro(titulo, anoPublicacao, autor, isbn, editora, capa);
        Livro novoLivro = new Livro(titulo, anoPublicacao, autor, isbn, editora, capa);
        repositorio.adicionar(novoLivro);

        return novoLivro;
    }

    public Jornal cadastrarPeriodico
            (
                String titulo,
                int anoPublicacao,
                String editora,
                int numeroEdicao,
                String dataPublicacao,
                Path capa
            )
            throws OperacaoNaoPermitidaException, ItemDuplicadoException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        this.validarJornalRevista(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, capa);
        Jornal novoJornal = new Jornal(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, capa);
        repositorio.adicionar(novoJornal);
        return novoJornal;
    }

    public MaterialBibliografico buscarMaterial(int materialId)
            throws
            OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_CATALOGO);

        return repositorio.buscarPorId(materialId);
    }

    public List<MaterialBibliografico> listarMateriais() throws OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_CATALOGO);

        return repositorio.listarTodos();
    }

    public List<MaterialBibliografico> listarMateriaisPorTitulo(String titulo) throws OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_CATALOGO);

        return repositorio.listarPorTitulo(titulo);
    }

    public void atualizarLivro(
            String titulo,
            int anoPublicacao,
            String autor,
            String isbn,
            String editora,
            int livroId,
            Path capa
    )
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico material = repositorio.buscarPorId(livroId);

        if (!(material instanceof Livro)) {
            throw new TipoMaterialInvalidoException(material);
        }

        this.validarLivro(titulo, anoPublicacao, autor, isbn, editora, capa);
        Livro livroAtualizado = new Livro(titulo, anoPublicacao, autor, isbn, editora, livroId, capa);
        repositorio.atualizar(livroAtualizado);
    }

    public void atualizarPeriodico
            (
                    String titulo,
                    int anoPublicacao,
                    String editora,
                    int numeroEdicao,
                    String dataPublicacao,
                    int jornalRevistaId,
                    Path capa
            )
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico material = repositorio.buscarPorId(jornalRevistaId);

        if (!(material instanceof Jornal)) {
            throw new TipoMaterialInvalidoException(material);
        }

        this.validarJornalRevista(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, capa);
        Jornal jornalRevistaAtualizado = new Jornal(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, jornalRevistaId, capa);
        repositorio.atualizar(jornalRevistaAtualizado);

    }
    public void removerMaterial(int materialId) throws
            OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            MaterialIndisponivelException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico materialParaRemover = repositorio.buscarPorId(materialId);

        if (!materialParaRemover.estaDisponivel()) {
            throw new MaterialIndisponivelException();
        }

        repositorio.remover(materialId);
    }
}
