package biblioteca.model.material;

public class Livro extends MaterialBibliografico{

	// atributos
	
	private String autor;
	private String isbn;
	
	// construtor
	
	public Livro(String titulo, int anoPublicacao, String autor, String isbn, String editora) {
		super(titulo, anoPublicacao, editora);
		this.setAutor(autor);
		this.setIsbn(isbn);
	}
	
	// getters e setters

	public String getAutor() {
		return autor;
	}

	private void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	private void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	// metodos
	
	@Override
	public String exibirDetalhes() {
		return "Titulo do livro: %s - Autor: %s - Ano de publicação: %s - ISBN: %s - Editora: %s".formatted(this.getTitulo(), this.getAutor(), this.getAnoPublicacao(), this.getIsbn(), this.getEditora());
	}

	@Override
	public boolean podeSerEmprestado() {
		return this.isDisponivel();
	}
	
	

}
