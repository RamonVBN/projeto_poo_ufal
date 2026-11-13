package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.Emprestimo;

public class EmprestimoRepositorio implements Repositorio<Emprestimo> {

    private final ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	
	@Override
    public void adicionar(Emprestimo emprestimo) throws ItemDuplicadoException {

        for (Emprestimo e : emprestimos) {
            if (e.getId() == emprestimo.getId()) {
                throw new ItemDuplicadoException("empréstimo");
            }
        }

        emprestimos.add(emprestimo);
    }
	
	@Override
    public Emprestimo buscarPorId(int emprestimoId) throws RegistroNaoEncontradoException {

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getId() == emprestimoId) {
                return emprestimo;
            }
        }

        throw new RegistroNaoEncontradoException();
    }
	
	@Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<Emprestimo>(emprestimos);
    }

    public List<Emprestimo> listarTodos(int usuarioId) {
        List<Emprestimo> resultado = new ArrayList<>();

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getUsuario().getID() == (usuarioId)) {
                resultado.add(emprestimo);
            }
        }

        return resultado;
    }

    @Override
    public void atualizar(Emprestimo emprestimo) throws RegistroNaoEncontradoException {

        for (int i = 0; i < emprestimos.size(); i++) {

            if (emprestimos.get(i).getId() == emprestimo.getId()) {
                emprestimos.set(i, emprestimo);
                return;
            }
        }

        throw new RegistroNaoEncontradoException();
    }
    
    @Override
    public void remover(int emprestimoId) throws RegistroNaoEncontradoException {

        Emprestimo emprestimo = buscarPorId(emprestimoId);
        emprestimos.remove(emprestimo);
    }
}
