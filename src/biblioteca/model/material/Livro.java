package biblioteca.model.material;

public class Livro extends MaterialBibliografico{

	// atributos
	
	private String autor;
	private String isbn;
	private String editora;
	
	// construtor
	
	public Livro(String titulo, int anoPublicacao, String autor, String isbn, String editora) {
		super(titulo, anoPublicacao);
		this.setAutor(autor);
		this.setIsbn(isbn);
		this.setEditora(editora);
	}

	public Livro(String titulo, int anoPublicacao, String autor, String isbn, String editora, int id) {
		super(titulo, anoPublicacao, id);
		this.setAutor(autor);
		this.setIsbn(isbn);
		this.setEditora(editora);
	}
	
	// getters e setters

	private String getAutor() {
		return autor;
	}

	private void setAutor(String autor) {
		this.autor = autor;
	}

	private String getIsbn() {
		return isbn;
	}

	private void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	private String getEditora() {
		return editora;
	}

	private void setEditora(String editora) {
		this.editora = editora;
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
