package biblioteca.exception;

public class DataInvalidaException extends Exception {

    public DataInvalidaException() {
        super("A data de devolução não pode ser posterior a data de empréstimo.");
    }
}
