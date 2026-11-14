package biblioteca.view;

import biblioteca.model.Emprestimo;
import biblioteca.model.usuario.Usuario;
import biblioteca.service.EmprestimoService;
import biblioteca.service.UsuarioService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MeusDados extends JPanel {

	
	// atributos
	
    private Usuario usuarioLogado;
    private EmprestimoService emprestimos;
    private UsuarioService us;
    
    // construtor
    
    public MeusDados(Usuario usuarioLogado, UsuarioService us , EmprestimoService emprestimos) {
        this.usuarioLogado = usuarioLogado;
        this.us = us;
        this.emprestimos = emprestimos;

        configurarPainel();
        criarComponentes();
    }
    
    // metodos

    private void configurarPainel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(220, 220, 220));
    }

    private void criarComponentes() {
    	setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(40, 40, 30, 40));
        setBackground(new Color(220, 220, 220));

        // Painel superior: alteração dos dados
        JPanel painelDados = new JPanel(new BorderLayout(0, 15));
        painelDados.setBackground(new Color(220, 220, 220));
        
        JPanel campos = new JPanel(new GridLayout(1, 3, 30, 0));
        campos.setBackground(new Color(220, 220, 220));

        // Campo de login
        JPanel campoLogin = new JPanel(new BorderLayout(0, 5));
        campoLogin.setBackground(new Color(220, 220, 220));

        JLabel labelLogin = new JLabel("Login atual: "+this.usuarioLogado.getLogin());
        JTextField inputLogin = new JTextField();
        inputLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
        inputLogin.setText("Alterar login");

        campoLogin.add(labelLogin, BorderLayout.NORTH);
        campoLogin.add(inputLogin, BorderLayout.CENTER);

        // Campo de senha
        JPanel campoSenha = new JPanel(new BorderLayout(0, 5));
        campoSenha.setBackground(new Color(220, 220, 220));

        JLabel labelSenha = new JLabel("Senha atual: *****");
        JTextField inputSenha = new JTextField();
        inputSenha.setBorder(new EmptyBorder(5, 5, 5, 5));

        campoSenha.add(labelSenha, BorderLayout.NORTH);
        campoSenha.add(inputSenha, BorderLayout.CENTER);

        // Campo de nome
        JPanel campoNome = new JPanel(new BorderLayout(0, 5));
        campoNome.setBackground(new Color(220, 220, 220));

        JLabel labelNome = new JLabel("Nome atual: "+this.usuarioLogado.getNome());
        JTextField inputNome = new JTextField();
        inputNome.setBorder(new EmptyBorder(5, 5, 5, 5));

        campoNome.add(labelNome, BorderLayout.NORTH);
        campoNome.add(inputNome, BorderLayout.CENTER);

        campos.add(campoLogin);
        campos.add(campoSenha);
        campos.add(campoNome);
        
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.setBackground(new Color(220, 220, 220));
        
        // Botão para atualizar os dados
        JButton botaoAtualizar = new JButton("Atualizar dados");
        
        painelBotao.add(botaoAtualizar);

        botaoAtualizar.addActionListener(e -> {
            String novoLogin = inputLogin.getText();
            String novaSenha = new String(inputSenha.getText());
            String novoNome = inputNome.getText();

            // TODO atualizar dados no service
        });
        
        painelDados.add(campos, BorderLayout.CENTER);
        painelDados.add(painelBotao, BorderLayout.SOUTH);

        add(painelDados, BorderLayout.NORTH);


        // Painel de empréstimos
        JPanel painelEmprestimos = new JPanel();
        painelEmprestimos.setLayout(new BoxLayout(painelEmprestimos, BoxLayout.Y_AXIS));
        painelEmprestimos.setBackground(new Color(220, 220, 220));

        JLabel tituloEmprestimos = new JLabel("Empréstimos");
        tituloEmprestimos.setAlignmentX(Component.LEFT_ALIGNMENT);
        tituloEmprestimos.setBorder(new EmptyBorder(5, 0, 20, 0));

        painelEmprestimos.add(tituloEmprestimos);
        
        for (Emprestimo emprestimo : this.emprestimos.listarEmprestimosPorUsuario(this.usuarioLogado.getID())) {
            painelEmprestimos.add(criarMaterial(emprestimo));
            painelEmprestimos.add(Box.createVerticalStrut(15));
        }
        
        add(painelEmprestimos, BorderLayout.CENTER);
    }
    

	private JPanel criarMaterial(Emprestimo emprestimo) {
	    JPanel itemEmprestimo = new JPanel(new BorderLayout());
	
	    itemEmprestimo.setBackground(Color.WHITE);
	    itemEmprestimo.setBorder(new EmptyBorder(20, 30, 20, 30));
	    itemEmprestimo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
	    itemEmprestimo.setAlignmentX(Component.LEFT_ALIGNMENT);
	
	    // Informações do material
	    long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), emprestimo.getDataDevolucao());
	    // TODO se os dias forem negativos, formatar em vermelho
	    
	    JLabel informacao = new JLabel(
	        emprestimo.getMaterial().getTitulo() + " Tempo restante: " + String.valueOf(diasRestantes)
	    );
	
	    // Botão de devolução
	    JButton botaoDevolver = new JButton("Devolver");
	
	    botaoDevolver.setBorderPainted(false);
	    botaoDevolver.setContentAreaFilled(false);
	    botaoDevolver.setFocusPainted(false);
	
	    botaoDevolver.addActionListener(e -> {
	        // TODO: Solicitar confirmação da devolução.
	        // TODO: Registrar a devolução pelo EmprestimoService.
	        // TODO: Atualizar a lista de empréstimos.
	    });
	
	    itemEmprestimo.add(informacao, BorderLayout.CENTER);
	    itemEmprestimo.add(botaoDevolver, BorderLayout.EAST);
	
	    return itemEmprestimo;
	}

}