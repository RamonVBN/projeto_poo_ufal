package biblioteca.service;

import biblioteca.auth.Sessao;
import biblioteca.exception.CredenciaisInvalidasException;
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

    protected void validarStrings(String s) throws CredenciaisInvalidasException {
        if (s == null || s.isBlank()) {
            throw new CredenciaisInvalidasException();
        }
    }

    protected  void verificarAutenticacao() throws UsuarioNaoAutenticadoException {
        if (!sessao.estaAutenticado()) {
            throw new UsuarioNaoAutenticadoException();
        }
    }
}
