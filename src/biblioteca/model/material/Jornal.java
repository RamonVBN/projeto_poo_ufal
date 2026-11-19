package biblioteca.model.material;

public class Jornal extends MaterialBibliografico{

	// atributos

	private int numeroEdicao;
	private String dataPublicacao;
	
	// construtor
	
	public Jornal(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao) {
		super(titulo, anoPublicacao, editora);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}

	public Jornal(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, int id) {
		super(titulo, anoPublicacao, editora, id);
		this.setEditora(editora.trim());
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao.trim());
	}
	
	// getters e setters

	public int getNumeroEdicao() {
		return numeroEdicao;
	}

	public void setNumeroEdicao(int numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	// metodos
	
	@Override
	public String exibirDetalhes() {
		return "Titulo do jornal: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
	}
	
}
