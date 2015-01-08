package org.silo.vista.catalogos;

import java.awt.event.ActionEvent;
import org.silo.modelos.bo.Cliente;
import org.silo.modelos.servicios.ServiciosSILO;
import org.silo.vista.catalogos.forms.Form;
import org.silo.vista.catalogos.forms.FormularioCliente;
import org.silo.vista.componentes.ClienteTableModel;

public class CatalogoCliente extends Catalogo {

    public CatalogoCliente() {
        super(new FormularioCliente(), new ClienteTableModel());
        installListeners();
        searchToolBar.getUpdateCatalog().doClick();
    }

    private void installListeners() {
        searchToolBar.getSaveEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().actualizaCliente((Cliente) ((Form) form).getData());
            } else {
                ServiciosSILO.getServicios().altaCliente((Cliente) ((Form) form).getData());
                setEditando(true);
            }
            searchToolBar.getUpdateCatalog().doClick();
        });
        searchToolBar.getDeleteEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().eliminaCliente((Cliente) ((Form) form).getData());
                setEditando(false);
                searchToolBar.getUpdateCatalog().doClick();
            }
        });
        searchToolBar.getUpdateCatalog().addActionListener((ActionEvent e) -> {
            modelo.setData(ServiciosSILO.getServicios().catalogoClientes());
            searchToolBar.getNewEntity().doClick();
        });
        
    }
}
