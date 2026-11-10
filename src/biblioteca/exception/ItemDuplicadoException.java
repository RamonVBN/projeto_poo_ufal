package biblioteca.exception;

public class ItemDuplicadoException extends Exception {

	public ItemDuplicadoException() {
		super("Já existe um item com esse ID");
	}
	
	public ItemDuplicadoException(String item) {
		super("Já existe um "+item+" com esse ID");
	}
}
