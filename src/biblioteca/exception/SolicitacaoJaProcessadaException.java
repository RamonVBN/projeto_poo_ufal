package biblioteca.exception;

public class SolicitacaoJaProcessadaException extends RuntimeException {
    public SolicitacaoJaProcessadaException() {
        super("Esta solicitação já foi processada.");
    }
}
