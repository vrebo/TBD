package org.silo.vista.catalogos;

import java.awt.event.ActionEvent;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.servicios.ServiciosSILO;
import org.silo.vista.catalogos.forms.Form;
import org.silo.vista.catalogos.forms.FormularioEmpleado;
import org.silo.vista.componentes.EmpleadoTableModel;

public class CatalogoEmpleado extends Catalogo {

    public CatalogoEmpleado() {
        super(new FormularioEmpleado(), new EmpleadoTableModel());
        installListeners();
        searchToolBar.getUpdateCatalog().doClick();
    }

    private void installListeners() {
        searchToolBar.getSaveEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().actualizaEmpleado((Empleado) ((Form) form).getData());
            } else {
                ServiciosSILO.getServicios().altaEmpleado((Empleado) ((Form) form).getData());
                setEditando(true);
            }
            searchToolBar.getUpdateCatalog().doClick();
        });
        searchToolBar.getDeleteEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().eliminaEmpleado((Empleado) ((Form) form).getData());
                setEditando(false);
                searchToolBar.getUpdateCatalog().doClick();
            }
        });
        searchToolBar.getUpdateCatalog().addActionListener((ActionEvent e) -> {
            modelo.setData(ServiciosSILO.getServicios().catalogoEmpleados());
            searchToolBar.getNewEntity().doClick();
        });
    }

}
