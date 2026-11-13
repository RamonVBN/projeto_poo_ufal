package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.UsuarioNaoAutenticadoException;

public class Service {

    protected final AutorizacaoService autorizacaoService;
    protected final Sessao sessao;

    public Service(
            AutorizacaoService autorizacaoService,
            Sessao sessao
            ) {
        this.autorizacaoService =  autorizacaoService;
        this.sessao = sessao;
    }

    protected  void verificarAutenticacao() throws UsuarioNaoAutenticadoException {
        if (!sessao.estaAutenticado()) {
            throw new UsuarioNaoAutenticadoException();
        }
    }
}
