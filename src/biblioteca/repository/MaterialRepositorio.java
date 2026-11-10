package biblioteca.repository;

import java.util.ArrayList;
import java.util.List;

import biblioteca.model.material.MaterialBibliografico;

public class MaterialRepositorio extends Repositorio<MaterialBibliografico> {

	private ArrayList<MaterialBibliografico> materiais = new ArrayList<>();
	
	@Override
    public void adicionar(MaterialBibliografico material) {

        for (MaterialBibliografico m : materiais) {
            if (m.getId() == material.getId()) {
                throw new IllegalArgumentException(
                    "Já existe um material com esse ID."
                );
            }
        }

        materiais.add(material);
    }
	
	@Override
    public MaterialBibliografico buscarPorId(int id) {

        for (MaterialBibliografico material : materiais) {
            if (material.getId() == id) {
                return material;
            }
        }

        throw new IllegalArgumentException(
            "Material não encontrado."
        );
    }
	
	@Override
    public List<MaterialBibliografico> listarTodos() {
        return new ArrayList<>(materiais);
    }

    @Override
    public void atualizar(MaterialBibliografico material) {

        for (int i = 0; i < materiais.size(); i++) {

            if (materiais.get(i).getId() == material.getId()) {
                materiais.set(i, material);
                return;
            }
        }

        throw new IllegalArgumentException(
            "Material não encontrado."
        );
    }
    
    @Override
    public void remover(int id) {

        MaterialBibliografico material = buscarPorId(id);
        materiais.remove(material);
    }
}
