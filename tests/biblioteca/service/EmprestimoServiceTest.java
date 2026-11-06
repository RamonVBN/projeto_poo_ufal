package biblioteca.service;

import biblioteca.exception.LimiteEmprestimosException;
import biblioteca.exception.MaterialIndisponivelException;
import biblioteca.model.Emprestimo;
import biblioteca.model.material.Livro;
import biblioteca.model.material.MaterialBibliografico;
import biblioteca.model.usuario.Usuario;
import biblioteca.model.usuario.UsuarioComum;
import biblioteca.repository.EmprestimoRepositorio;
import biblioteca.repository.MaterialRepositorio;
import biblioteca.repository.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoServiceTest {

    private EmprestimoService service;
    private EmprestimoRepositorio emprestimoRepositorio;
    private MaterialRepositorio materialRepositorio;
    private UsuarioRepositorio usuarioRepositorio;

    private Usuario usuario;
    private MaterialBibliografico material;

    @BeforeEach
    void setUp() {
        emprestimoRepositorio = new EmprestimoRepositorio();
        materialRepositorio = new MaterialRepositorio();
        usuarioRepositorio = new UsuarioRepositorio();

        service = new EmprestimoService(
                emprestimoRepositorio,
                materialRepositorio,
                usuarioRepositorio
        );

        usuario = new UsuarioComum(
                "João Silva",
                "joao",
                "123456"
        );

        material = new Livro(
                "Clean Code",
                2008,
                "Robert C. Martin",
                "9780132350884",
                "Prentice Hall"
        );

        usuarioRepositorio.adicionar(usuario);
        materialRepositorio.adicionar(material);
    }

    @Test
    void deveRegistrarEmprestimo() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        List<Emprestimo> emprestimos = emprestimoRepositorio.listarTodos();

        assertEquals(1, emprestimos.size());
        assertTrue(emprestimos.get(0).estaAtivo());
    }

    @Test
    void deveMarcarMaterialComoIndisponivelAposEmprestimo() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        MaterialBibliografico encontrado = materialRepositorio.buscarPorId(material.getId());

        assertFalse(encontrado.podeSerEmprestado());
    }

    @Test
    void deveImpedirEmprestimoDeMaterialIndisponivel() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        assertThrows(
                MaterialIndisponivelException.class,
                () -> service.registrarEmprestimo(
                        usuario.getID(),
                        material.getId()
                )
        );
    }

    @Test
    void deveRegistrarDevolucao() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        Emprestimo emprestimo = emprestimoRepositorio.listarTodos().get(0);

        service.registrarDevolucao(emprestimo.getId());

        assertFalse(emprestimo.estaAtivo());
    }

    @Test
    void deveMarcarMaterialComoDisponivelAposDevolucao() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        Emprestimo emprestimo =
                emprestimoRepositorio.listarTodos().get(0);

        service.registrarDevolucao(emprestimo.getId());

        MaterialBibliografico encontrado = materialRepositorio.buscarPorId(material.getId());

        assertTrue(encontrado.podeSerEmprestado());
    }

    @Test
    void deveListarEmprestimosDeUmUsuario() {
        service.registrarEmprestimo(usuario.getID(), material.getId());

        List<Emprestimo> emprestimos = service.listarEmprestimosPorUsuario(usuario.getID());

        assertEquals(1, emprestimos.size());
    }

    @Test
    void deveImpedirMaisDeTresEmprestimosAtivos() {
        MaterialBibliografico livro2 = new Livro(
                "Clean Architecture",
                2017,
                "Robert C. Martin",
                "9780134494166",
                "Prentice Hall"
        );

        MaterialBibliografico livro3 = new Livro(
                "Domain-Driven Design",
                2003,
                "Eric Evans",
                "9780321125217",
                "Addison-Wesley"
        );

        MaterialBibliografico livro4 = new Livro(
                "Refactoring",
                1999,
                "Martin Fowler",
                "9780201485677",
                "Addison-Wesley"
        );

        materialRepositorio.adicionar(livro2);
        materialRepositorio.adicionar(livro3);
        materialRepositorio.adicionar(livro4);

        service.registrarEmprestimo(usuario.getID(), material.getId());
        service.registrarEmprestimo(usuario.getID(), livro2.getId());
        service.registrarEmprestimo(usuario.getID(), livro3.getId());

        assertThrows(
                LimiteEmprestimosException.class,
                () -> service.registrarEmprestimo(
                        usuario.getID(),
                        livro4.getId()
                )
        );
    }
}
