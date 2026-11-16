package biblioteca.exception;

public class CredenciaisInvalidasException extends  Exception {

    public CredenciaisInvalidasException() {
        super("Dados de cadastro/login inválidos.");
    }
}
