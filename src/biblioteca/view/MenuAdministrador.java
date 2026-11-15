package biblioteca.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import biblioteca.model.usuario.Usuario;
import biblioteca.service.UsuarioService;

public class MenuAdministrador extends JPanel{

	// atributos
	
	private UsuarioService usuarios;
	private JPanel painelListaUsuarios;

	public MenuAdministrador(UsuarioService usuarios) {
	this.usuarios = usuarios;
		configurarPainel();
		criarComponentes();
    }

    private void configurarPainel() {
        setLayout(new BorderLayout());
        setBackground(new Color(220, 220, 220));
        setBorder(new EmptyBorder(25, 30, 30, 30));
    }

    private void criarComponentes() {

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new Color(220, 220, 220));

        // =================================
        // PESQUISAR USUÁRIO
        // =================================

        JLabel tituloPesquisa = new JLabel("Pesquisar usuário");
        tituloPesquisa.setFont(new Font("Arial", Font.PLAIN, 16));

        painelPrincipal.add(tituloPesquisa);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Campo de pesquisa e botão
        JPanel painelPesquisa = new JPanel(new BorderLayout(15, 0));
        painelPesquisa.setBackground(new Color(220, 220, 220));
        painelPesquisa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        
        // Opções de pesquisa
        JComboBox<String> comboPesquisa = new JComboBox<>(new String[]{"Nome", "Login"});
        
        comboPesquisa.setPreferredSize(new Dimension(110, 38));

        JTextField campoPesquisa = new JTextField();
        JButton botaoBuscar = new JButton("Buscar Usuário");

        botaoBuscar.setBackground(Color.WHITE);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setBorderPainted(false);

        // Organização dos elementos
        painelPesquisa.add(comboPesquisa, BorderLayout.WEST);
        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(botaoBuscar, BorderLayout.EAST);

        botaoBuscar.addActionListener(e -> {
            String pesquisa = campoPesquisa.getText();
            String criterio = (String) comboPesquisa.getSelectedItem();
            List<Usuario> resultado;
            switch (criterio) {
            	case "Nome":
            		resultado = this.usuarios.listarUsuariosPorNome(pesquisa);
            		break;
            	case "Login":
            		resultado = this.usuarios.listarUsuariosPorLogin(pesquisa);
            		break;
            }
            this.criarUsuarios(resultado);
        });

        painelPrincipal.add(painelPesquisa);
        painelPrincipal.add(Box.createVerticalStrut(22));

        // Lista de usuários encontrados
        painelListaUsuarios = new JPanel();
        painelListaUsuarios.setLayout(
            new BoxLayout(painelListaUsuarios, BoxLayout.Y_AXIS)
        );
        painelListaUsuarios.setBackground(new Color(220, 220, 220));

        painelPrincipal.add(painelListaUsuarios);
        painelPrincipal.add(Box.createVerticalStrut(65));

        // =================================
        // CADASTRAR USUÁRIO
        // =================================

        JLabel tituloCadastro = new JLabel("Cadastrar usuário");
        tituloCadastro.setFont(new Font("Arial", Font.PLAIN, 16));

        painelPrincipal.add(tituloCadastro);
        painelPrincipal.add(Box.createVerticalStrut(5));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(new Color(220, 220, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField campoNome = new JTextField();
        JTextField campoLogin = new JTextField();
        JTextField campoSenha = new JTextField();

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Usuário comum","Bibliotecário","Administrador"});

        // Nome
        adicionarCampo(formulario, gbc, 0, "Nome", campoNome);

        // Login
        adicionarCampo(formulario, gbc, 1, "Login", campoLogin);

        // Tipo de usuário
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        formulario.add(new JLabel("Tipo de usuário"), gbc);

        comboTipo.setPreferredSize(new Dimension(110, 25));

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(comboTipo, gbc);

        // Senha
        adicionarCampo(formulario, gbc, 3, "Senha", campoSenha);

        painelPrincipal.add(formulario);
        
        painelPrincipal.add(Box.createVerticalStrut(15));

	     // Botão adicionar usuário
	    JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	    painelBotao.setBackground(new Color(220, 220, 220));
	
	    JButton botaoAdicionar = new JButton("Adicionar usuário");
	
	    botaoAdicionar.setFocusPainted(false);
	    botaoAdicionar.setBackground(Color.WHITE);
	    botaoAdicionar.setBorderPainted(false);
	
	    botaoAdicionar.addActionListener(e -> {
	    	String nome = campoNome.getText();
	    	String login = campoLogin.getText();
	    	String tipo = (String) comboTipo.getSelectedItem();
	    	String senha = new String(campoSenha.getText());
	    	Cargo cargo;
	    	switch (tipo) {
	    		case "Administrador":
	    			Cargo cargo = Cargo.ADMINISTRADOR;
	    		case "Bibliotecário":
	    			Cargo cargo = Cargo.BIBLIOTECARIO;
    			default:
	    			Cargo cargo = Cargo.LEITOR
	    	}
	    	this.usuarios.cadastrarUsuario(nome, login, senha, cargo);
	    });
	
		painelBotao.add(botaoAdicionar);
	
		painelPrincipal.add(painelBotao);

        JScrollPane scroll = new JScrollPane(painelPrincipal);
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(15);

        add(scroll, BorderLayout.CENTER);
    }

    private void adicionarCampo(
        JPanel painel,
        GridBagConstraints gbc,
        int linha,
        String texto,
        JTextField campo
    ) {
        gbc.gridy = linha;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel(texto), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campo.setPreferredSize(new Dimension(400, 25));
        painel.add(campo, gbc);
    }

    // =================================
    // CRIAR COMPONENTES DOS USUÁRIOS
    // =================================

    private void criarUsuarios(List<Usuario> usuarios) {

        painelListaUsuarios.removeAll();

        for (Usuario usuario : usuarios) {

            JPanel itemUsuario = new JPanel(new BorderLayout());
            itemUsuario.setBackground(Color.WHITE);
            itemUsuario.setBorder(
                new EmptyBorder(5, 12, 5, 20)
            );
            itemUsuario.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 27)
            );

            JLabel nomeUsuario = new JLabel(usuario.getNome());

            JButton botaoRemover = new JButton("Remover");
            botaoRemover.setBackground(new Color(220, 20, 30));
            botaoRemover.setForeground(Color.WHITE);
            botaoRemover.setFocusPainted(false);
            botaoRemover.setBorderPainted(false);

            botaoRemover.addActionListener(e -> {
            	int opcao = JOptionPane.showConfirmDialog(this, "Deseja remover esse usuario?", "Remover usuario", JOptionPane.YES_NO_OPTION);
            	if (opcao == JOptionPane.YES_OPTION) {
            		this.usuarios.remover(usuario.getID());
            		this.criarComponentes();
            	}
            });

            itemUsuario.add(nomeUsuario, BorderLayout.CENTER);
            itemUsuario.add(botaoRemover, BorderLayout.EAST);

            painelListaUsuarios.add(itemUsuario);
            painelListaUsuarios.add(Box.createVerticalStrut(10));
        }

        painelListaUsuarios.revalidate();
    	painelListaUsuarios.repaint();
    }
}
