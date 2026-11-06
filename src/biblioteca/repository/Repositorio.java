package biblioteca.repository;

import java.util.List;

public interface Repositorio<T> {

	public void adicionar();
	
	public T buscarPorId();
	
	public List<T> listarTodos();
	
	public void atualizar(T objeto);
	
	public void remover(int id);
}
