package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.material.Jornal;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Permissao;
import biblioteca.repository.MaterialRepositorio;

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
            String editora
    ) throws CredenciaisInvalidasException {
        this.validarStrings(titulo);
        this.validarStrings(editora);
        this.validarStrings(autor);
        this.validarStrings(isbn);
        this.validarAnoPublicacao(anoPublicacao);
    }

    private void validarJornalRevista
            (
                String titulo, int anoPublicacao,
                String editora, int numeroEdicao,
                String dataPublicacao
            ) throws CredenciaisInvalidasException {

        this.validarStrings(titulo);
        this.validarStrings(editora);
        this.validarStrings(dataPublicacao);
        this.validarAnoPublicacao(anoPublicacao);

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
                    String editora
            )
            throws OperacaoNaoPermitidaException, ItemDuplicadoException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        this.validarLivro(titulo, anoPublicacao, autor, isbn, editora);
        Livro novoLivro = new Livro(titulo, anoPublicacao, autor, isbn, editora);
        repositorio.adicionar(novoLivro);

        return novoLivro;
    }

    public Jornal cadastrarJornalRevista
            (
                String titulo,
                int anoPublicacao,
                String editora,
                int numeroEdicao,
                String dataPublicacao
            )
            throws OperacaoNaoPermitidaException, ItemDuplicadoException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        this.validarJornalRevista(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao);
        Jornal novoJornal = new Jornal(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao);
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


    public List<MaterialBibliografico> listarMateriais(String titulo) throws OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_CATALOGO);

        return repositorio.listarPorTitulo(titulo);
    }


    public List<MaterialBibliografico> listarMateriais() throws OperacaoNaoPermitidaException, UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_CATALOGO);

        return repositorio.listarTodos();
    }

    public void atualizarLivro(
            String titulo,
            int anoPublicacao,
            String autor,
            String isbn,
            String editora,
            int livroId
    )
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico material = repositorio.buscarPorId(livroId);

        if (!(material instanceof Livro livro)) {
            throw new TipoMaterialInvalidoException(material);
        }

        this.validarLivro(titulo, anoPublicacao, autor, isbn, editora);
        Livro livroAtualizado = new Livro(titulo, anoPublicacao, autor, isbn, editora, livroId);
        repositorio.atualizar(livroAtualizado);
    }

    public void atualizarJornalRevista
            (
                    String titulo,
                    int anoPublicacao,
                    String editora,
                    int numeroEdicao,
                    String dataPublicacao,
                    int jornalRevistaId
            )
            throws OperacaoNaoPermitidaException, RegistroNaoEncontradoException, UsuarioNaoAutenticadoException, TipoMaterialInvalidoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico material = repositorio.buscarPorId(jornalRevistaId);

        if (!(material instanceof Jornal jornalRevista)) {
            throw new TipoMaterialInvalidoException(material);
        }

        this.validarJornalRevista(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao);
        Jornal jornalRevistaAtualizado = new Jornal(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, jornalRevistaId);
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
