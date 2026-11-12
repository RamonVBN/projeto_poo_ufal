package biblioteca.model.material;

import java.util.Random;

public abstract class MaterialBibliografico {

	// atributos
	
	private final int id;
	private String titulo;
	private int anoPublicacao;
	private boolean disponivel;
	
	// construtor
	
	public MaterialBibliografico(String titulo, int anoPublicacao) {
		this.id = new Random().nextInt();
		this.setTitulo(titulo);
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
	}

	public MaterialBibliografico(String titulo, int anoPublicacao, int id) {
		this.id = id;
		this.setTitulo(titulo);
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
	}
	
	// getters e setters

	protected String getTitulo() {
		return titulo;
	}

	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	protected int getAnoPublicacao() {
		return anoPublicacao;
	}

	private void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	protected boolean isDisponivel() {
		return disponivel;
	}

	private void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public int getId() {
		return id;
	}
	
	// metodos
	
	public abstract String exibirDetalhes();
	
	public abstract boolean podeSerEmprestado();
	
	public void alterarDisponibilidade() {
		this.setDisponivel(!this.isDisponivel());
	}
	
}
