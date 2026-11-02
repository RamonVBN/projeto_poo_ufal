package biblioteca.model.usuario;

public class Administrador extends Usuario{

	public Administrador(String nome, String login, String senha) {
		super(nome, login, senha);
	}

	@Override
	public String exibirDados() {
		return "Administrador: %s - ID: %s - Login: %s".formatted(this.getNome(), this.getID(), this.getLogin());
	}
}
