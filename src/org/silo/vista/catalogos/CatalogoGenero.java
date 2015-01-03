
package org.silo.vista.catalogos;

import java.awt.event.ActionEvent;
import org.silo.modelos.bo.Genero;
import org.silo.modelos.servicios.ServiciosSILO;
import org.silo.vista.catalogos.forms.Form;
import org.silo.vista.catalogos.forms.FormularioGenero;
import org.silo.vista.componentes.GeneroTableModel;

public class CatalogoGenero extends Catalogo{

    public CatalogoGenero() {
        super(new FormularioGenero(), new GeneroTableModel());
        installListeners();
        searchToolBar.getUpdateCatalog().doClick();
    }

    private void installListeners() {
        searchToolBar.getSaveEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().actualizaGenero((Genero) ((Form) form).getData());
            } else {
                ServiciosSILO.getServicios().altaGenero((Genero) ((Form) form).getData());
                setEditando(true);
            }
            searchToolBar.getUpdateCatalog().doClick();
        });
        searchToolBar.getDeleteEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().eliminaGenero((Genero) ((Form) form).getData());
                setEditando(false);
                searchToolBar.getNewEntity().doClick();
            }
        });
        searchToolBar.getUpdateCatalog().addActionListener((ActionEvent e) -> {
            modelo.setData(ServiciosSILO.getServicios().catalogoGeneros());
        });
        
    }

}