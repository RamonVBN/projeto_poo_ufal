package biblioteca;

import javax.swing.SwingUtilities;

import biblioteca.view.TelaLogin;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			TelaLogin telalogin = new TelaLogin();
			telalogin.setVisible(true);
		});

	}

}
