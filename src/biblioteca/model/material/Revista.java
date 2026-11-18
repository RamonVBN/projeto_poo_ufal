package biblioteca.model.material;

import java.nio.file.Path;

public class Revista extends MaterialBibliografico{
	
	// atributos
	
	private int numeroEdicao;
	private String dataPublicacao;
	
	// construtor
	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, Path caminhoCapa) {
		super(titulo, anoPublicacao, editora, caminhoCapa);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}

	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, int id, Path caminhoCapa) {
		super(titulo, anoPublicacao, editora, id, caminhoCapa);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}
	
	// getters e setters

	public int getNumeroEdicao() {
		return this.numeroEdicao;
	}

	public void setNumeroEdicao(int numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}

	public String getDataPublicacao() {
		return this.dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	// metodos
	
	@Override
	public String exibirDetalhes() {
		return "Titulo da revista: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
	}
}
