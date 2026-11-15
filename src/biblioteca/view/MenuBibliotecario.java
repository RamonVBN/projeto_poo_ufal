package biblioteca.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.MaterialIndisponivelException;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.material.Revista;
import biblioteca.model.usuario.Usuario;
import biblioteca.service.CatalogoService;

public class MenuBibliotecario extends JPanel{
	
	// atributos
	
	private CatalogoService catalogo;
	
	private JPanel painelListaMateriais;

	// construtor
	
	public MenuBibliotecario(CatalogoService catalogo) {
		this.catalogo = catalogo;
		configurarPainel();
        criarComponentes();
    }
	
	// metodos

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
        // REMOVER OBRA
        // =================================

        JLabel tituloRemover = new JLabel("Remover obra");
        tituloRemover.setFont(new Font("Arial", Font.PLAIN, 16));
        tituloRemover.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelPrincipal.add(tituloRemover);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Campo de pesquisa e botão
        JPanel painelPesquisa = new JPanel(new BorderLayout(28, 0));
        painelPesquisa.setBackground(new Color(220, 220, 220));
        painelPesquisa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        painelPesquisa.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField campoPesquisa = new JTextField();
        JButton botaoBuscar = new JButton("Buscar obra");

        botaoBuscar.setBackground(Color.WHITE);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setBorderPainted(false);

        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(botaoBuscar, BorderLayout.EAST);

        botaoBuscar.addActionListener(e -> {
            String pesquisa = campoPesquisa.getText();

            List<MaterialBibliografico> materiais = this.catalogo.listarMateriais(pesquisa);
            this.criarMateriais(materiais);
        });

        painelPrincipal.add(painelPesquisa);
        painelPrincipal.add(Box.createVerticalStrut(22));
        
        // Painel que recebera os materiais
        painelListaMateriais = new JPanel();

        painelListaMateriais.setLayout(new BoxLayout(painelListaMateriais, BoxLayout.Y_AXIS));

        painelListaMateriais.setBackground(new Color(220, 220, 220));

        painelPrincipal.add(painelListaMateriais);
        painelPrincipal.add(Box.createVerticalStrut(35));

        // =================================
        // CADASTRAR OBRA
        // =================================

        JLabel tituloCadastro = new JLabel("Cadastrar obra");
        tituloCadastro.setFont(new Font("Arial", Font.PLAIN, 16));
        tituloCadastro.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelPrincipal.add(tituloCadastro);
        painelPrincipal.add(Box.createVerticalStrut(5));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(new Color(220, 220, 220));
        formulario.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Campos do formulário
        JTextField campoTitulo = new JTextField();
        JTextField campoEditora = new JTextField();
        JTextField campoAno = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoISBN = new JTextField();
        JTextField campoDataPublicacao = new JTextField();
        JTextField campoNumeroEdicao = new JTextField();

        JComboBox<String> comboTipo = new JComboBox<String>(new String[]{ "Livro", "Revista", "Jornal"});

        // Título
        adicionarCampo(formulario, gbc, 0, "Título", campoTitulo);

        // Editora
        adicionarCampo(formulario, gbc, 1, "Editora", campoEditora);

        // Ano de publicação e tipo
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formulario.add(new JLabel("Ano de publicação"), gbc);

        JPanel painelAnoTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelAnoTipo.setBackground(new Color(220, 220, 220));

        campoAno.setPreferredSize(new Dimension(80, 25));
        comboTipo.setPreferredSize(new Dimension(100, 25));

        painelAnoTipo.add(campoAno);
        painelAnoTipo.add(comboTipo);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulario.add(painelAnoTipo, gbc);

        // Autor
        adicionarCampo(formulario, gbc, 3, "Autor", campoAutor);

        // ISBN
        adicionarCampo(formulario, gbc, 4, "ISBN", campoISBN);
        
        // Data de publicacao
        adicionarCampo(formulario, gbc, 5, "Data de publicação", campoDataPublicacao);
        
        // Numero de edição
        adicionarCampo(formulario, gbc, 6, "Número de edição", campoNumeroEdicao);
        
        // Botao adicionar
        JButton botaoAdicionar = new JButton("Adicionar material");
        botaoAdicionar.setBackground(new Color(220, 20, 30));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.setBorderPainted(false);
        
        botaoAdicionar.addActionListener(e -> {
        	String tipo = (String) comboTipo.getSelectedItem();
        	switch (tipo) {
	        	case "Livro": {
	        		try {
	        			this.catalogo.cadastrarLivro(campoTitulo.getText(), Integer.parseInt(campoAno.getText()), campoAutor.getText(), campoISBN.getText(), campoEditora.getText());
	        		} catch (ItemDuplicadoException ide) {
	        			JOptionPane.showMessageDialog(this, "Esta obra já existe!", "Obra duplicada", ERROR);
	        		} catch (CredenciaisInvalidasException cie) {
	        			JOptionPane.showMessageDialog(this, "Algum dado digitado é inválido!", "Dados inválidos", ERROR);
	        		}
	        	} case "Revista", "Jornal": {
	        		try {
	        			this.catalogo.cadastrarJornalRevista(
	        					campoTitulo.getText(),
	        					Integer.parseInt(campoAno.getText()),
	        					campoEditora.getText(),
	        					campoNumeroEdicao.getText(),
	        					campoDataPublicacao.getText());
	        		} catch (ItemDuplicadoException ide) {
	        			JOptionPane.showMessageDialog(this, "Esta obra já existe!", "Obra duplicada", ERROR);
	        		} catch (CredenciaisInvalidasException cie) {
	        			JOptionPane.showMessageDialog(this, "Algum dado digitado é inválido!", "Dados inválidos", ERROR);
	        		}
	        	}
        	}
        });

        painelPrincipal.add(formulario);

        add(painelPrincipal, BorderLayout.NORTH);
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
    
    private void criarMateriais(List<MaterialBibliografico> materiais) {

        painelListaMateriais.removeAll();

        for (MaterialBibliografico material : materiais) {

            JPanel painelObra = new JPanel(new BorderLayout(15, 0));

            painelObra.setBackground(Color.WHITE);
            painelObra.setBorder(new EmptyBorder(12, 15, 12, 15));
            painelObra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
            painelObra.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Informações do material
            JPanel informacoes = new JPanel(new GridLayout(2, 3, 20, 8));
            informacoes.setBackground(Color.WHITE);

            informacoes.add(new JLabel(material.getTitulo()));

            if (material instanceof Livro) {
            	Livro material_temp = (Livro) material;
            	informacoes.add(new JLabel(material_temp.getAutor()));
            }
            informacoes.add(new JLabel(material.getEditora()));
            informacoes.add(new JLabel(String.valueOf(material.getAnoPublicacao())));
            if (!(material instanceof Livro)) {
            	Revista material_temp = (Revista) material;
            	informacoes.add(new JLabel(String.valueOf(material_temp.getNumeroEdicao())));
            }

            // Botão remover
            JButton botaoRemover = new JButton("Remover");

            botaoRemover.setBackground(new Color(220, 20, 30));
            botaoRemover.setForeground(Color.WHITE);
            botaoRemover.setFocusPainted(false);
            botaoRemover.setBorderPainted(false);

            botaoRemover.addActionListener(e -> {
            	int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente remover esse material?", "Remover material", JOptionPane.YES_NO_OPTION);
            	if (opcao == JOptionPane.YES_OPTION) {
            		try {
            			this.catalogo.removerMaterial(material.getId());
            			this.criarMateriais(materiais);
            		} catch (MaterialIndisponivelException mie) {
            			JOptionPane.showMessageDialog(this, "Este material está emprestado, não foi possível remover!", "Erro ao remover", ERROR);
            		}
            	}
            });

            painelObra.add(informacoes, BorderLayout.CENTER);
            painelObra.add(botaoRemover, BorderLayout.EAST);

            painelListaMateriais.add(painelObra);
            painelListaMateriais.add(Box.createVerticalStrut(15));
        }

        painelListaMateriais.revalidate();
        painelListaMateriais.repaint();
    }
}
