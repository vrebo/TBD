package org.silo.vista.catalogos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Pelicula;
import org.silo.modelos.servicios.ServiciosSILO;
import org.silo.vista.catalogos.forms.Form;
import org.silo.vista.catalogos.forms.FormularioPelicula;
import org.silo.vista.componentes.PeliculaTableModel;

public class CatalogoPelicula extends Catalogo {

    public CatalogoPelicula() {
        super(new FormularioPelicula(), new PeliculaTableModel());
        installListeners();
        searchToolBar.getUpdateCatalog().doClick();
    }

    private void installListeners() {
        searchToolBar.getSaveEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().actualizaPelicula((Pelicula) ((Form) form).getData());
            } else {
                System.out.println("alta");
                ServiciosSILO.getServicios().altaPelicula((Pelicula) ((Form) form).getData());
            }
            searchToolBar.getUpdateCatalog().doClick();
        });
        searchToolBar.getDeleteEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().eliminaPelicula((Pelicula) ((Form) form).getData());
                setEditando(false);
                searchToolBar.getUpdateCatalog().doClick();
            }
        });
        searchToolBar.getUpdateCatalog().addActionListener((ActionEvent e) -> {
            ((FormularioPelicula) form)
                    .getGenero().setModel(
                            new DefaultComboBoxModel(
                                    ServiciosSILO.getServicios().catalogoGeneros().toArray()));
            modelo.setData(ServiciosSILO.getServicios().catalogoPeliculas());
            searchToolBar.getNewEntity().doClick();
        });

    }

}
