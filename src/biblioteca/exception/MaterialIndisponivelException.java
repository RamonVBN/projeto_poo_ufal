package biblioteca.exception;

public class MaterialIndisponivelException extends Exception {
	
	public MaterialIndisponivelException(String material) {
		super("%s já foi emprestado, aguarde a devolução dele!".formatted(material));
	}
	
	public MaterialIndisponivelException() {
		super("Este material já foi emprestado, aguarde a devolução dele!");
	}
}
