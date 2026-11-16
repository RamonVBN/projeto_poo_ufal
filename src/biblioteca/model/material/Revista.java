package biblioteca.model.material;

public class Revista extends MaterialBibliografico{
	
	// atributos
	
	private int numeroEdicao;
	private String dataPublicacao;
	
	// construtor
	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao) {
		super(titulo, anoPublicacao);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}

	public Revista(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, int id) {
		super(titulo, anoPublicacao, id);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}
	
	// getters e setters

	public String getEditora() {
		return this.editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

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
