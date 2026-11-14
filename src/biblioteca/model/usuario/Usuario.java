package biblioteca.model.usuario;

import java.util.Random;

public class Usuario {

	// atributos
	
	private final int id;

	private final Cargo cargo;
	private String nome;
	private String login;
	private String senha;
	
	// construtor
	
	public Usuario(String nome, String login, String senha) {
		this.id = new Random().nextInt();
		this.cargo = Cargo.LEITOR;
		this.setNome(nome.trim());
		this.setLogin(login.trim());
		this.setSenha(senha);
	}

	public Usuario(String nome, String login, String senha, Cargo cargo) {
		this.id = new Random().nextInt();
		this.cargo = cargo;
		this.setNome(nome.trim());
		this.setLogin(login.trim());
		this.setSenha(senha);
	}

	public Usuario(String nome, String login, Usuario usuarioParaCopiar) {
		this.id = usuarioParaCopiar.getID();
		this.cargo = usuarioParaCopiar.getCargo();
		this.setSenha(usuarioParaCopiar.getSenha());
		this.setNome(nome.trim());
		this.setLogin(login.trim());
	}

	// getters e setters

	public Cargo getCargo() {
		return cargo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public int getID() {
		return this.id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}
	
	// metodos

	public String exibirDados() {
		String nomeCargo;
		switch (this.cargo) {
            case LEITOR -> {
				nomeCargo = "Leitor";
			}
            case BIBLIOTECARIO -> {
				nomeCargo = "Bibliotecario";
			}
            default -> {
				nomeCargo = "Administrador";
			}
		}
		return "%s: %s - ID: %s - Login: %s".formatted(nomeCargo, this.getNome(), this.getID(), this.getLogin());
	}
}
