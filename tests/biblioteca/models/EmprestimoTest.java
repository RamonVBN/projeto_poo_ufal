package biblioteca.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import biblioteca.model.Emprestimo;
import biblioteca.model.StatusEmprestimo;

public class EmprestimoTest {

	@Test
    void emprestimoDeveComecarAtivo() {
        Emprestimo emprestimo = new Emprestimo();

        assertTrue(emprestimo.estaAtivo());
        assertEquals(StatusEmprestimo.ATIVO, emprestimo.getStatus());
        assertNotNull(emprestimo.getDataEmprestimo());
    }

    @Test
    void registrarDevolucaoDeveFinalizarEmprestimo() {
        Emprestimo emprestimo = new Emprestimo();

        emprestimo.registrarDevolucao();

        assertFalse(emprestimo.estaAtivo());
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimo.getStatus());
    }
}
