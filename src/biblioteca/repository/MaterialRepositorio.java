package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.exception.ItemDuplicadoException;
import biblioteca.exception.RegistroNaoEncontradoException;
import biblioteca.model.material.MaterialBibliografico;

public class MaterialRepositorio implements Repositorio<MaterialBibliografico> {

	private final ArrayList<MaterialBibliografico> materiais = new ArrayList<>();
	
	@Override
    public void adicionar(MaterialBibliografico material) throws ItemDuplicadoException {

        for (MaterialBibliografico m : materiais) {
            if (m.getId() == material.getId()) {
                throw new ItemDuplicadoException("material");
            }
        }

        materiais.add(material);
    }
	
	@Override
    public MaterialBibliografico buscarPorId(int materialId) throws RegistroNaoEncontradoException {

        for (MaterialBibliografico material : materiais) {
            if (material.getId() == materialId) {
                return material;
            }
        }

        throw new RegistroNaoEncontradoException();
    }

    public List<MaterialBibliografico> listarPorTitulo(String titulo) {

        List<MaterialBibliografico> resultado = new ArrayList<>();

        if (titulo == null || titulo.isBlank()) {
            return List.of();
        }
        for (MaterialBibliografico material : materiais) {
            if (material.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(material);
            }
        }

        return resultado;
    }
	
	@Override
    public List<MaterialBibliografico> listarTodos() {
        return new ArrayList<>(materiais);
    }

    @Override
    public void atualizar(MaterialBibliografico material) throws RegistroNaoEncontradoException {

        for (int i = 0; i < materiais.size(); i++) {

            if (materiais.get(i).getId() == material.getId()) {
                materiais.set(i, material);
                return;
            }
        }

        throw new RegistroNaoEncontradoException();
    }
    
    @Override
    public void remover(int materialId) throws RegistroNaoEncontradoException {

        MaterialBibliografico material = buscarPorId(materialId);
        materiais.remove(material);
    }
}
