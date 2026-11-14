package biblioteca;

import javax.swing.SwingUtilities;

import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;
import biblioteca.service.CatalogoService;
import biblioteca.service.EmprestimoService;
import biblioteca.service.UsuarioService;
import biblioteca.view.TelaLogin;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			
			// repository
			
			final UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
			
			final MaterialRepositorio materialRepositorio = new MaterialRepositorio();
			
			final EmprestimoRepositorio emprestimoRepositorio = new EmprestimoRepositorio();
			
			// services
            
			final UsuarioService usuarioService = new UsuarioService(usuarioRepositorio);

            final CatalogoService catalogoService = new CatalogoService(materialRepositorio);

            final EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepositorio, materialRepositorio, usuarioRepositorio);
            
            // Tela inicial
			TelaLogin telalogin = new TelaLogin(usuarioService, catalogoService, emprestimoService);
			telalogin.setVisible(true);
		});

	}

}
