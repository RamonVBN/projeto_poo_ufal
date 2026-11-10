package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.model.usuario.Usuario;

public class UsuarioRepositorio extends Repositorio<Usuario> {

	private ArrayList<Usuario> usuarios = new ArrayList<>();
	
	@Override
	public void adicionar(Usuario usuario) {

	    for (Usuario u : usuarios) {
	        if (u.getID() == usuario.getID()) {
	            throw new IllegalArgumentException(
	                "Já existe um usuário com esse ID."
	            );
	        }
	    }

	    usuarios.add(usuario);
	}
	
	@Override
	public Usuario buscarPorId(int id) {

	    for (Usuario usuario : usuarios) {
	        if (usuario.getID() == id) {
	            return usuario;
	        }
	    }

	    throw new IllegalArgumentException(
	        "Usuário não encontrado."
	    );
	}
	
	@Override
	public List<Usuario> listarTodos() {
	    return new ArrayList<>(usuarios);
	}
	
	@Override
	public void atualizar(Usuario usuario) {

	    for (int i = 0; i < usuarios.size(); i++) {

	        if (usuarios.get(i).getID() == usuario.getID()) {
	            usuarios.set(i, usuario);
	            return;
	        }
	    }

	    throw new IllegalArgumentException(
	        "Usuário não encontrado."
	    );
	}
	
	@Override
	public void remover(int id) {

	    Usuario usuario = buscarPorId(id);
	    usuarios.remove(usuario);
	}
	
}
