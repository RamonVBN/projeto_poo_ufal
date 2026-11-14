package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.*;
import biblioteca.model.Emprestimo;
import biblioteca.model.StatusEmprestimo;
import biblioteca.model.material.Jornal;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Permissao;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoService extends Service {

    private final EmprestimoRepositorio emprestimoRepositorio;
    private final MaterialRepositorio materialRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public EmprestimoService
            (
                    EmprestimoRepositorio emprestimoRepositorio,
                    MaterialRepositorio materialRepositorio,
                    UsuarioRepositorio usuarioRepositorio,
                    AutorizacaoService autorizacaoService,
                    Sessao sessao
            ) {
        super(autorizacaoService, sessao);
        this.emprestimoRepositorio = emprestimoRepositorio;
        this.materialRepositorio = materialRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void solicitarEmprestimo() {
        // TODO -> O leitor precisa solicitar empréstimo para que algum bibliotecário ou admin registre.
    }

    public void registrarEmprestimo
            (
                    int usuarioId,
                    int materialId
            )
            throws UsuarioNaoAutenticadoException,
            OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            MaterialIndisponivelException,
            LimiteEmprestimosException,
            ItemDuplicadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.REALIZAR_EMPRESTIMO);

        MaterialBibliografico material = materialRepositorio.buscarPorId(materialId);
        Usuario usuario = usuarioRepositorio.buscarPorId(usuarioId);

        if (!material.estaDisponivel()) {
            throw new MaterialIndisponivelException();
        }

        List<Emprestimo> emprestimosDoUsuario = emprestimoRepositorio.listarTodos(usuario.getID());
        List<Emprestimo> emprestimosAtivos = new ArrayList<>();

        for (Emprestimo emprestimo : emprestimosDoUsuario) {
            if (emprestimo.getStatus().equals(StatusEmprestimo.ATIVO)) {
                emprestimosAtivos.add(emprestimo);
            }
        }

        if (emprestimosAtivos.size() >= 3) {
            throw new LimiteEmprestimosException();
        }

        LocalDate dataPrevistaDevolucao = LocalDate.now();

        if (material instanceof Livro) {
            dataPrevistaDevolucao = dataPrevistaDevolucao.plusDays(14);
        } else if (material instanceof Jornal) {
            dataPrevistaDevolucao = dataPrevistaDevolucao.plusDays(3);
        } else {
            dataPrevistaDevolucao = dataPrevistaDevolucao.plusDays(2);
        }

        Emprestimo novoEmprestimo = new Emprestimo(dataPrevistaDevolucao, material, usuario);
        emprestimoRepositorio.adicionar(novoEmprestimo);
        material.marcarComoIndisponivel();
    }

    public void registrarDevolucao(int emprestimoId)
            throws
            OperacaoNaoPermitidaException,
            RegistroNaoEncontradoException,
            MaterialDevolvidoException,
            UsuarioNaoAutenticadoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.REALIZAR_DEVOLUCAO);

        Emprestimo emprestimoParaDevolver = emprestimoRepositorio.buscarPorId(emprestimoId);
        emprestimoParaDevolver.registrarDevolucao();
        emprestimoParaDevolver.getMaterial().marcarComoDisponivel();
    }
    public Emprestimo buscarEmprestimo(int emprestimoId)
            throws UsuarioNaoAutenticadoException, OperacaoNaoPermitidaException, RegistroNaoEncontradoException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_TODOS_EMPRESTIMOS);

        return emprestimoRepositorio.buscarPorId(emprestimoId);
    }

    public List<Emprestimo> listarEmprestimos() throws UsuarioNaoAutenticadoException, OperacaoNaoPermitidaException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_TODOS_EMPRESTIMOS);

        return emprestimoRepositorio.listarTodos();
    }
    public List<Emprestimo> listarEmprestimosPorUsuario(int usuarioId) throws UsuarioNaoAutenticadoException, OperacaoNaoPermitidaException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_TODOS_EMPRESTIMOS);

        return emprestimoRepositorio.listarTodos(usuarioId);
    }
    public List<Emprestimo> listarMeusEmprestimos() throws UsuarioNaoAutenticadoException, OperacaoNaoPermitidaException {

        this.verificarAutenticacao();
        autorizacaoService.exigirPermissao(sessao.getUsuarioLogado(), Permissao.CONSULTAR_PROPRIOS_EMPRESTIMOS);

        return emprestimoRepositorio.listarTodos(sessao.getUsuarioLogado().getID());
    }
}
