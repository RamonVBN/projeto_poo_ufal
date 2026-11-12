package biblioteca.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import biblioteca.model.usuario.Administrador;
import biblioteca.model.usuario.Bibliotecario;
import biblioteca.model.usuario.Usuario;

public class MenuPrincipal extends JFrame{

	// atributos
	
	private JTextField campoPesquisa;
    private JPanel painelMateriais;
    private Usuario usuarioLogado;
    
    // construtor
    
    public MenuPrincipal(Usuario usuario) {
    	this.usuarioLogado = usuario;
    	configurarJanela();
        criarMenu();
        criarConteudo();
        carregarMateriaisExemplo();
	}
    
    // metodos
    
    private void configurarJanela() {
        setTitle("Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 500));
    }
    
    private void criarMenu() {
    	JMenuBar barraMenu = new JMenuBar();

        JMenu menuPrincipal = new JMenu("Menu");

        JMenuItem itemInicio = new JMenuItem("Página inicial");
        JMenuItem itemMeusDados = new JMenuItem("Meus dados");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemInicio.addActionListener(e -> {
            // TODO: Atualizar a tela principal.
        });

        itemMeusDados.addActionListener(e -> {
            // TODO: Abrir tela de dados do usuário.
        });

        itemSair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair?",
                    "Sair",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        menuPrincipal.add(itemInicio);
        menuPrincipal.add(itemMeusDados);
        menuPrincipal.addSeparator();
        menuPrincipal.add(itemSair);

        barraMenu.add(menuPrincipal);

        // BIBLIOTECARIO
        if (usuarioLogado instanceof Bibliotecario) {

            JMenu menuMateriais = new JMenu("Materiais");

            JMenuItem itemGerenciar = new JMenuItem("Gerenciar materiais");

            itemGerenciar.addActionListener(e -> {
                // TODO: Abrir tela de cadastro de materiais.
            });

            menuMateriais.add(itemGerenciar);

            barraMenu.add(menuMateriais);
        }

        // SOMENTE ADMINISTRADOR
        if (usuarioLogado instanceof Administrador) {

            JMenu menuUsuarios = new JMenu("Usuários");

            JMenuItem itemGerenciarUsuarios =
                    new JMenuItem("Gerenciar usuários");

            itemGerenciarUsuarios.addActionListener(e -> {
                // TODO: Abrir tela de gerenciamento de usuários.
            });

            menuUsuarios.add(itemGerenciarUsuarios);

            barraMenu.add(menuUsuarios);
        }

        setJMenuBar(barraMenu);
    }

    private void criarConteudo() {
    	
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(new Color(220, 220, 220));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Área de pesquisa
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelPesquisa.setBackground(new Color(220, 220, 220));

        campoPesquisa = new JTextField(25);
        campoPesquisa.setPreferredSize(new Dimension(250, 35));

        JButton botaoBuscar = new JButton("Buscar obra");

        botaoBuscar.addActionListener(e -> {
            // TODO: Pesquisar materiais pelo título.
        });

        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(botaoBuscar);

        painelPrincipal.add(painelPesquisa, BorderLayout.NORTH);

        // Lista de materiais
        painelMateriais = new JPanel();
        painelMateriais.setLayout(
                new BoxLayout(painelMateriais, BoxLayout.Y_AXIS)
        );
        painelMateriais.setBackground(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(painelMateriais);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(15);

        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);
    }
    
    private void carregarMateriaisExemplo() {
        adicionarMaterial(
                "Clean Code",
                "Robert C. Martin",
                "2008",
                "9780132350884",
                "Prentice Hall",
                true
        );

        adicionarMaterial(
                "Revista Ciência Hoje",
                "Vários autores",
                "2024",
                "Edição 395",
                "SBPC",
                false
        );

        adicionarMaterial(
                "Jornal da Cidade",
                "Redação",
                "2025",
                "Edição 120",
                "Editora Local",
                true
        );
    }

    private void adicionarMaterial(
            String titulo,
            String autor,
            String ano,
            String identificacao,
            String editora,
            boolean disponivel
    ) {
        JPanel card = new JPanel(new BorderLayout(15, 5));

        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(12, 12, 12, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setPreferredSize(new Dimension(750, 120));

        // CAPA
        JPanel capa = new JPanel(new GridBagLayout());

        capa.setPreferredSize(new Dimension(75, 90));
        capa.setBackground(new Color(210, 210, 210));

        capa.add(new JLabel("CAPA"));

        card.add(capa, BorderLayout.WEST);

        // INFORMACOES
        JPanel informacoes = new JPanel();

        informacoes.setLayout(
                new BoxLayout(informacoes, BoxLayout.Y_AXIS)
        );

        informacoes.setBackground(Color.WHITE);

        informacoes.add(new JLabel("Título: " + titulo));
        informacoes.add(Box.createVerticalStrut(5));
        informacoes.add(new JLabel("Autor: " + autor));
        informacoes.add(Box.createVerticalStrut(5));
        informacoes.add(new JLabel("Ano de publicação: " + ano));
        informacoes.add(Box.createVerticalStrut(5));
        informacoes.add(new JLabel("ISBN / Edição: " + identificacao));
        informacoes.add(Box.createVerticalStrut(5));
        informacoes.add(new JLabel("Editora: " + editora));

        card.add(informacoes, BorderLayout.CENTER);

        // DISPONIBILIDADE E EMPRESTIMO
        JPanel painelAcao = new JPanel();

        painelAcao.setLayout(
                new BoxLayout(painelAcao, BoxLayout.Y_AXIS)
        );

        painelAcao.setBackground(Color.WHITE);

        JLabel status = new JLabel(
                disponivel ? "Disponível" : "Indisponível"
        );

        status.setOpaque(true);
        status.setForeground(Color.BLACK);

        status.setBackground(
                disponivel
                        ? new Color(100, 210, 120)
                        : new Color(240, 70, 70)
        );

        status.setBorder(new EmptyBorder(8, 10, 8, 10));
        status.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelAcao.add(status);

        // O BOTAO SO APARECE SE O MATERIAL ESTIVER DISPONIVEL
        if (disponivel) {

            JButton botaoEmprestar = new JButton("Emprestar");

            botaoEmprestar.setAlignmentX(Component.CENTER_ALIGNMENT);

            botaoEmprestar.addActionListener(e -> {

                int opcao = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja pegar o material \"" + titulo +
                                "\" emprestado?",
                        "Confirmar empréstimo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcao == JOptionPane.YES_OPTION) {

                    // TODO: Criar o objeto Emprestimo.
                    // TODO: Registrar o empréstimo usando EmprestimoService.
                    // TODO: Alterar a disponibilidade do material.
                    // TODO: Atualizar a interface após o empréstimo.

                    JOptionPane.showMessageDialog(
                            this,
                            "Confirmação recebida! O registro do empréstimo será implementado.",
                            "Empréstimo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            });

            painelAcao.add(Box.createVerticalStrut(8));
            painelAcao.add(botaoEmprestar);
        }

        card.add(painelAcao, BorderLayout.EAST);

        painelMateriais.add(card);
        painelMateriais.add(Box.createVerticalStrut(10));

        painelMateriais.revalidate();
        painelMateriais.repaint();
    }
}
