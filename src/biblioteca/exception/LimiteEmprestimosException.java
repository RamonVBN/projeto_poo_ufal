package biblioteca.exception;

public class LimiteEmprestimosException extends Exception {
	
	public LimiteEmprestimosException() {
		super("Limite de emprestimos ativos excedido!");
	}
}
