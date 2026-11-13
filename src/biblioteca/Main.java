package biblioteca;

import biblioteca.auth.SenhaHashUtil;
import biblioteca.auth.Sessao;
import biblioteca.exception.CredenciaisInvalidasException;
import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.Emprestimo;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Cargo;
import biblioteca.model.usuario.Usuario;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;
import biblioteca.service.AutorizacaoService;
import biblioteca.service.CatalogoService;
import biblioteca.service.Service;
import biblioteca.service.UsuarioService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

	public static void main(String[] args) {

	}

}
