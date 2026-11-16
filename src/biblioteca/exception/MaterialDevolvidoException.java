package biblioteca.exception;

public class MaterialDevolvidoException extends Exception {

    public MaterialDevolvidoException() {
        super("Não é possível devolver um material mais de uma vez para o mesmo empréstimo.");
    }
}
