package biblioteca.model;

import java.time.LocalDate;
import java.util.Random;

import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Usuario;

public class Emprestimo {

	// atributos
	
	private final int id;
	private final LocalDate dataEmprestimo;
	private LocalDate dataPrevistaDevolucao;
	private LocalDate dataDevolucao;
	private StatusEmprestimo status;
	
	private MaterialBibliografico material;
	private Usuario usuario;
	
	// construtor
	
	public Emprestimo() {
		this.id = new Random().nextInt();
		this.dataEmprestimo = LocalDate.now();
		this.setStatus(StatusEmprestimo.ATIVO);
	}
	
	// getters e setters

	public LocalDate getDataPrevistaDevolucao() {
		return dataPrevistaDevolucao;
	}

	private void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	private void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public StatusEmprestimo getStatus() {
		return status;
	}

	private void setStatus(StatusEmprestimo status) {
		this.status = status;
	}

	private void setMaterial(MaterialBibliografico material) {
		this.material = material;
	}

	private void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private int getId() {
		return id;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}
	
	// metodos
	
	public void registrarDevolucao() {
		this.setStatus(StatusEmprestimo.DEVOLVIDO);
	}
	
	public boolean estaAtivo() {
		return this.getStatus() == StatusEmprestimo.ATIVO;
	}
	
	
}
