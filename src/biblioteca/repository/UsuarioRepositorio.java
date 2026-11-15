package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.Emprestimo;
import biblioteca.model.usuario.Usuario;

public class UsuarioRepositorio implements Repositorio<Usuario>, RepositorioRemovivel<Usuario> {

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

	public List<Usuario> listarPorNome(String nome) {

		List<Usuario> usuariosFiltrados = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			if (usuario.getNome().contains(nome)) {
				usuariosFiltrados.add(usuario);
			}
		}

		return usuariosFiltrados;
	}

	public List<Usuario> listarPorLogin(String login)  {

		List<Usuario> usuariosFiltrados = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().contains(login)) {
				usuariosFiltrados.add(usuario);
			}
		}

		return usuariosFiltrados;
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
