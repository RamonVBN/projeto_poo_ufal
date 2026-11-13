package biblioteca;

import biblioteca.auth.Sessao;
import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.service.AutorizacaoService;
import biblioteca.service.CatalogoService;

public class Main {

	public static void main(String[] args) throws ItemDuplicadoException, RegistroNaoEncontradoException {
		MaterialRepositorio repositorio = new MaterialRepositorio();

		Livro livroTeste = new Livro("Clean Code",
				2008,
				"Robert C. Martin",
				"9780132350884",
				"Prentice Hall");

		repositorio.adicionar(livroTeste);
	}

}
