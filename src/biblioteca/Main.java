package biblioteca;

import javax.swing.SwingUtilities;

import biblioteca.auth.Sessao;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;
import biblioteca.service.AutorizacaoService;
import biblioteca.service.CatalogoService;
import biblioteca.service.EmprestimoService;
import biblioteca.service.UsuarioService;
import biblioteca.view.TelaLogin;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			
			// session
			
			final Sessao sessao = new Sessao();
			
			// repository
			
			final UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
			
			final MaterialRepositorio materialRepositorio = new MaterialRepositorio();
			
			final EmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorio();
			
			// services
			
			final AutorizacaoService autorizacaoService = new AutorizacaoService();
            
			final UsuarioService usuarioService = new UsuarioService(usuarioRepositorio, emprestimoRepositorio, autorizacaoService, sessao);

			final CatalogoService catalogoService = new CatalogoService(materialRepositorio, autorizacaoService, sessao);

			final EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepositorio, materialRepositorio, usuarioRepositorio, autorizacaoService, sessao);
            
			// Tela inicial
			TelaLogin telalogin = new TelaLogin(usuarioService, catalogoService, emprestimoService, sessao);
			telalogin.setVisible(true);
		});

	}

}
