package biblioteca.model.material;

public class Jornal extends MaterialBibliografico{

	// atributos

	private int numeroEdicao;
	private String dataPublicacao;
	
	// construtor
	
	public Jornal(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao) {
		super(titulo, anoPublicacao, editora);
		this.setNumeroEdicao(numeroEdicao);
		this.setDataPublicacao(dataPublicacao);
	}
	
	// getters e setters

	public int getNumeroEdicao() {
		return numeroEdicao;
	}

	private void setNumeroEdicao(int numeroEdicao) {
		this.numeroEdicao = numeroEdicao;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	private void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	// metodos
	
	@Override
	public String exibirDetalhes() {
		return "Titulo do jornal: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
	}

	@Override
	public boolean podeSerEmprestado() {
		return this.isDisponivel();
	}
	
}
