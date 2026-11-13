package biblioteca.exception;

public class OperacaoNaoPermitidaException extends Exception{

    public OperacaoNaoPermitidaException() {
        super("Perfil ou usuario não possui autorização para realizar essa operação");
    }
}
