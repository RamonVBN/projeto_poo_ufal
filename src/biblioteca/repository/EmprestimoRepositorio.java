package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.model.Emprestimo;

public class EmprestimoRepositorio extends Repositorio<Emprestimo> {

	private ArrayList<Emprestimo> emprestimos = new ArrayList<>();
	
	@Override
    public void adicionar(Emprestimo emprestimo) {

        for (Emprestimo e : emprestimos) {
            if (e.getId() == emprestimo.getId()) {
                throw new IllegalArgumentException(
                    "Já existe um empréstimo com esse ID."
                );
            }
        }

        emprestimos.add(emprestimo);
    }
	
	@Override
    public Emprestimo buscarPorId(int id) {

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getId() == id) {
                return emprestimo;
            }
        }

        throw new IllegalArgumentException(
            "Empréstimo não encontrado."
        );
    }
	
	@Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    @Override
    public void atualizar(Emprestimo emprestimo) {

        for (int i = 0; i < emprestimos.size(); i++) {

            if (emprestimos.get(i).getId() == emprestimo.getId()) {
                emprestimos.set(i, emprestimo);
                return;
            }
        }

        throw new IllegalArgumentException(
            "Empréstimo não encontrado."
        );
    }
    
    @Override
    public void remover(int id) {

        Emprestimo emprestimo = buscarPorId(id);
        emprestimos.remove(emprestimo);
    }
}
