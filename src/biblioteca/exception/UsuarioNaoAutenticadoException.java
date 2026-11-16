package biblioteca.exception;

public class UsuarioNaoAutenticadoException extends  Exception {

    public UsuarioNaoAutenticadoException() {
        super("O usuário não está autenticado.");
    }
}
