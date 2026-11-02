package biblioteca.model.usuario;

public class Bibliotecario extends Usuario{

	public Bibliotecario(String nome, String login, String senha) {
		super(nome, login, senha);
	}

	@Override
	public String exibirDados() {
		return "Bibliotecario: %s - ID: %s - Login: %s".formatted(this.getNome(), this.getID(), this.getLogin());
	}
}
