package biblioteca.exception;

public class RegistroNaoEncontradoException extends Exception {
	
	public RegistroNaoEncontradoException() {
		super("ID não existe ou não foi encontrado!");
	}
}
