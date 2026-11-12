package biblioteca.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import biblioteca.model.usuario.Usuario;

public class TelaLogin extends JFrame{

	// atributos
	
	private JTextField login;
	private JPasswordField senha;
	
	private JButton blogin;
	private JButton bsair;
	
	// construtor
	
	public TelaLogin() {
		configurarJanela();
		inicializarComponentes();
	}
	
	private void configurarJanela() {
        setTitle("Biblioteca"); // titulo
        setSize(500, 350); // tamanho
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
	
	private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo de login
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        JLabel labelLogin = new JLabel("Login");
        labelLogin.setFont(new Font("Arial", Font.PLAIN, 22));
        painelPrincipal.add(labelLogin, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        login = new JTextField(15);
        login.setFont(new Font("Arial", Font.PLAIN, 16));
        login.setPreferredSize(new Dimension(200, 40));
        painelPrincipal.add(login, gbc);

        // Campo de senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 22));
        painelPrincipal.add(labelSenha, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        senha = new JPasswordField(15);
        senha.setFont(new Font("Arial", Font.PLAIN, 16));
        senha.setPreferredSize(new Dimension(200, 40));
        painelPrincipal.add(senha, gbc);

        // Espaçamento entre campos e botões
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.5;

        painelPrincipal.add(Box.createVerticalStrut(20), gbc);

        // Botão sair
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 0;

        bsair = new JButton("Sair");
        estilizarBotao(bsair);

        bsair.addActionListener(e -> {
            System.exit(0);
        });

        painelPrincipal.add(bsair, gbc);

        // Botão fazer login
        gbc.gridx = 1;

        blogin = new JButton("Fazer login");
        estilizarBotao(blogin);

        blogin.addActionListener(e -> {
        	// TODO: Validar login e senha pelo UsuarioService.

            // TODO: Recuperar o objeto Usuario autenticado.
            Usuario usuarioAutenticado = null;

            if (usuarioAutenticado != null) {
                MenuPrincipal tela = new MenuPrincipal(usuarioAutenticado);

                tela.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Login ou senha incorretos!",
                        "Erro de autenticação",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        painelPrincipal.add(blogin, gbc);

        add(painelPrincipal);
    }
	
	private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.PLAIN, 18));
        botao.setPreferredSize(new Dimension(150, 50));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(220, 220, 220));
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}
