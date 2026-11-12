package biblioteca.model.material;

import java.util.Random;

public abstract class MaterialBibliografico {

	// atributos
	
	private final int id;
	private String titulo;
	private int anoPublicacao;
	private boolean disponivel;
	private String editora;
	
	// construtor
	
	public MaterialBibliografico(String titulo, int anoPublicacao, String editora) {
		this.id = new Random().nextInt();
		this.setTitulo(titulo);
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
		this.setEditora(editora);
	}
	
	// getters e setters

	public String getTitulo() {
		return titulo;
	}

	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	private void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public boolean isDisponivel() {
		return this.disponivel;
	}

	private void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public int getId() {
		return id;
	}
	
	public String getEditora() {
		return editora;
	}

	private void setEditora(String editora) {
		this.editora = editora;
	}
	
	// metodos

	public abstract String exibirDetalhes();
	
	public abstract boolean podeSerEmprestado();
	
	public void alterarDisponibilidade() {
		this.setDisponivel(!this.isDisponivel());
	}
	
}
