package biblioteca.auth;

import biblioteca.model.usuario.Usuario;

public class Sessao {

    private Usuario usuarioLogado;

    public void iniciarSessao(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void encerrarSessao() {
        this.usuarioLogado = null;
    }

    public boolean estaAutenticado() {
        return usuarioLogado != null;
    }
}