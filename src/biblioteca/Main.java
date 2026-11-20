package biblioteca;

import javax.swing.SwingUtilities;

import biblioteca.auth.Sessao;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.MultaRepositorio;
import biblioteca.repository.UsuarioRepositorio;
import biblioteca.service.*;
import biblioteca.view.TelaLogin;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			// session

			final Sessao sessao = new Sessao();

			// repository

			final UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
			usuarioRepositorio.cadastrarUsuariosIniciais();

			final MaterialRepositorio materialRepositorio = new MaterialRepositorio();
			materialRepositorio.cadastrarMateriaisIniciais();

			final EmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorio();

			final MultaRepositorio multaRepositorio = new MultaRepositorio();

			// services

			final AutorizacaoService autorizacaoService = new AutorizacaoService();

			final UsuarioService usuarioService = new UsuarioService(usuarioRepositorio, emprestimoRepositorio, autorizacaoService, sessao);

			final CatalogoService catalogoService = new CatalogoService(materialRepositorio, autorizacaoService, sessao);

			final MultaService multaService = new MultaService(autorizacaoService, sessao, multaRepositorio);

			final EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepositorio, materialRepositorio, usuarioRepositorio, autorizacaoService, sessao, multaService);

			// Tela inicial
			TelaLogin telalogin = new TelaLogin(usuarioService, catalogoService, emprestimoService, sessao);
			telalogin.setVisible(true);
		});

	}

}
