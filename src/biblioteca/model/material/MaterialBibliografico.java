package biblioteca.model.material;

import java.util.Random;

public abstract class MaterialBibliografico {

	// atributos
	
	private final int id;
	private String titulo;
	private int anoPublicacao;

	private String editora;
	private boolean disponivel;
	
	// construtor
	
	public MaterialBibliografico(String titulo, int anoPublicacao, String editora) {
		this.id = new Random().nextInt();
		this.setTitulo(titulo.trim());
		this.setAnoPublicacao(anoPublicacao);
		this.setDisponivel(true);
		this.setEditora(editora);
	}

	public MaterialBibliografico(String titulo, int anoPublicacao, String editora, int id) {
		this.id = id;
		this.setTitulo(titulo.trim());
		this.setAnoPublicacao(anoPublicacao);
		setEditora(editora);
		this.setDisponivel(true);
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

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public boolean estaDisponivel() {
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

	public void marcarComoDisponivel() {
		this.setDisponivel(true);
	}

	public void marcarComoIndisponivel() {
		this.setDisponivel(false);
	}
}
