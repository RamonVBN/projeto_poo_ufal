package biblioteca.repository;

import java.util.List;

public abstract class Repositorio<N> {

	public abstract void adicionar(N objeto);

    public abstract N buscarPorId(int id);

    public abstract List<N> listarTodos();

    public abstract void atualizar(N objeto);

    public abstract void remover(int id);
}
