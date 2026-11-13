package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.usuario.Usuario;

public class UsuarioRepositorio implements Repositorio<Usuario> {

	private final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	@Override
	public void adicionar(Usuario usuario) throws ItemDuplicadoException {

	    for (Usuario u : usuarios) {
	        if (u.getID() == usuario.getID()) {
	            throw new ItemDuplicadoException("usuário");
	        }

			if (u.getLogin().equals(usuario.getLogin())) {
				throw new ItemDuplicadoException("usuário");
			}
	    }

	    usuarios.add(usuario);
	}
	
	@Override
	public Usuario buscarPorId(int usuarioId) throws RegistroNaoEncontradoException {

	    for (Usuario usuario : usuarios) {
	        if (usuario.getID() == usuarioId) {
	            return usuario;
	        }
	    }

	    throw new RegistroNaoEncontradoException();
	}

	public Usuario buscarPorLogin(String login) throws RegistroNaoEncontradoException {

		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login)) {
				return usuario;
			}
		}

		throw new RegistroNaoEncontradoException();
	}
	
	@Override
	public List<Usuario> listarTodos() {
	    return new ArrayList<>(usuarios);
	}
	
	@Override
	public void atualizar(Usuario usuario) throws RegistroNaoEncontradoException {

	    for (int i = 0; i < usuarios.size(); i++) {

	        if (usuarios.get(i).getID() == usuario.getID()) {
	            usuarios.set(i, usuario);
	            return;
	        }
	    }

	    throw new RegistroNaoEncontradoException();
	}
	
	@Override
	public void remover(int usuarioId) throws RegistroNaoEncontradoException {

	    Usuario usuario = buscarPorId(usuarioId);
	    usuarios.remove(usuario);
	}
	
}
