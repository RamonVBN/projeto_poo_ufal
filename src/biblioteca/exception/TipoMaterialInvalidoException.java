package biblioteca.exception;

import biblioteca.model.material.MaterialBibliografico;

public class TipoMaterialInvalidoException extends Exception{

    public TipoMaterialInvalidoException(MaterialBibliografico material) {
        super("Tipo de material inválido para esta operação: " + material.getClass().getSimpleName());
    }
}
