package biblioteca.model.usuario;

public class UsuarioComum extends Usuario{

	public UsuarioComum(String nome, String login, String senha) {
		super(nome, login, senha);
	}

	@Override
	public String exibirDados() {
		return "Usuario: %s - ID: %s - Login: %s".formatted(this.getNome(), this.getID(), this.getLogin());
	}

}
