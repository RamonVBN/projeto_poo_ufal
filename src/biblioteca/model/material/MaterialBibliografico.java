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
		this.setTitulo(titulo.trim());
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
	}

	public MaterialBibliografico(String titulo, int anoPublicacao, int id) {
		this.id = id;
		this.setTitulo(titulo.trim());
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
		this.setEditora(editora);
	}
	
	// getters e setters

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public boolean estaDisponivel() {
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

	public void marcarComoDisponivel() {
		this.setDisponivel(true);
	}

	public void marcarComoIndisponivel() {
		this.setDisponivel(false);
	}
}
