package biblioteca.repository;

import biblioteca.exception.RegistroNaoEncontradoException;

public interface RepositorioRemovivel<T> extends Repositorio<T> {

    void remover(int id) throws RegistroNaoEncontradoException;;
}
