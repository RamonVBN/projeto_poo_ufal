package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.material.Periodico;
import biblioteca.model.material.TipoPeriodico;
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

    private void validarPeriodico
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

    public Periodico cadastrarPeriodico
            (
                String titulo,
                int anoPublicacao,
                String editora,
                int numeroEdicao,
                String dataPublicacao,
                TipoPeriodico tipoPeriodico
            )
            throws OperacaoNaoPermitidaException, ItemDuplicadoException, UsuarioNaoAutenticadoException, CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        this.validarPeriodico(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao);
        Periodico novoPeriodico = new Periodico(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, tipoPeriodico);
        repositorio.adicionar(novoPeriodico);
        return novoPeriodico;
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
            int livroId
    )
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);

        MaterialBibliografico livroParaAtualizar = repositorio.buscarPorId(livroId);

        if (!(livroParaAtualizar instanceof Livro)) {
            throw new TipoMaterialInvalidoException(livroParaAtualizar);
        }

        this.validarLivro(titulo, anoPublicacao, autor, isbn, editora);
        Livro livroAtualizado = new Livro(titulo, anoPublicacao, autor, isbn, editora, livroId);
        repositorio.atualizar(livroAtualizado);
    }

    public void atualizarPeriodico
            (
                    String titulo,
                    int anoPublicacao,
                    String editora,
                    int numeroEdicao,
                    String dataPublicacao,
                    int periodicoId
            )
            throws OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            UsuarioNaoAutenticadoException,
            TipoMaterialInvalidoException,
            CredenciaisInvalidasException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.GERENCIAR_CATALOGO);
        MaterialBibliografico periodicoParaAtualizar = repositorio.buscarPorId(periodicoId);

        if (!(periodicoParaAtualizar instanceof Periodico)) {
            throw new TipoMaterialInvalidoException(periodicoParaAtualizar);
        }

        this.validarPeriodico(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao);
        TipoPeriodico tipoPeriodico = ((Periodico) periodicoParaAtualizar).getTipoPeriodico();
        Periodico jornalRevistaAtualizado = new Periodico(titulo, anoPublicacao, editora, numeroEdicao, dataPublicacao, tipoPeriodico, periodicoId);
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
