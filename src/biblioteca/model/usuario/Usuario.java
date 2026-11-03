package biblioteca.model.usuario;

import java.util.Random;

public abstract class Usuario {

	// atributos
	
	private final int id;
	private String nome;
	private String login;
	private String senha;
	
	// construtor
	
	public Usuario(String nome, String login, String senha) {
		this.id = new Random().nextInt();
		this.setNome(nome);
		this.setLogin(login);
		this.setSenha(senha);
	}

	// getters e setters

	private void setNome(String nome) {
		this.nome = nome;
	}

	private void setLogin(String login) {
		this.login = login;
	}

	private void setSenha(String senha) {
		this.senha = senha;
	}
	
	public int getID() {
		return this.id;
	}
	
	protected String getNome() {
		return nome;
	}

	protected String getLogin() {
		return login;
	}

	protected String getSenha() {
		return senha;
	}
	
	// metodos
	
	public abstract String exibirDados();
	
}
