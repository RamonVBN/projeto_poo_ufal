package biblioteca.model.material;

public class Revista extends MaterialBibliografico{
	
	// atributos
	
	private String editora;
	private int numeroEdicao;
	private String dataPublicacao;
	
	// construtor
	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao) {
		super(titulo, anoPublicacao);
		this.setEditora(editora);
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao);
	}

	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, int id) {
		super(titulo, anoPublicacao, id);
		this.setEditora(editora);
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao);
	}
	
	// getters e setters

	private String getEditora() {
		return editora;
	}

	private void setEditora(String editora) {
		this.editora = editora;
	}

	private int getNumeroEdicao() {
		return numeroEdicao;
	}

	private void setNumeroEdicao(int numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}

	private String getDataPublicacao() {
		return dataPublicacao;
	}

	private void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	// metodos
	
	@Override
	public String exibirDetalhes() {
		return "Titulo da revista: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
	}

	@Override
	public boolean podeSerEmprestado() {
		return this.isDisponivel();
	}
}
