/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.catalogos;

import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Pelicula;
import org.silo.modelos.servicios.ServiciosSILO;
import org.silo.utils.ImageUtils;
import org.silo.vista.catalogos.forms.Form;
import org.silo.vista.catalogos.forms.FormularioCopia;
import org.silo.vista.componentes.CopiaPeliculaTableModel;

/**
 *
 * @author VREBO
 */
public class CatalogoCopia extends Catalogo {

    public CatalogoCopia() {
        super(new FormularioCopia(), new CopiaPeliculaTableModel());
        installListeners();
        searchToolBar.getUpdateCatalog().doClick();
    }

    private void installListeners() {
        searchToolBar.getSaveEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().actualizaCopiaPelicula((CopiaPelicula) ((Form) form).getData());
            } else {
                ServiciosSILO.getServicios().altaCopiaPelicula((CopiaPelicula) ((Form) form).getData());
                setEditando(true);
            }
            searchToolBar.getUpdateCatalog().doClick();
        });
        searchToolBar.getDeleteEntity().addActionListener((ActionEvent e) -> {
            if (isEditando()) {
                ServiciosSILO.getServicios().eliminaCopia((CopiaPelicula) ((Form) form).getData());
                setEditando(false);
                searchToolBar.getUpdateCatalog().doClick();
            }
        });
        searchToolBar.getUpdateCatalog().addActionListener((ActionEvent e) -> {
            ((FormularioCopia) form)
                    .getTitulo().setModel(
                            new DefaultComboBoxModel(
                                    ServiciosSILO
                                    .getServicios().catalogoPeliculas().toArray()
                            ));
            modelo.setData(ServiciosSILO.getServicios().catalogoCopias());
            searchToolBar.getNewEntity().doClick();
        });

        JComboBox titulo = ((FormularioCopia) form).getTitulo();

        titulo.addActionListener((ActionEvent e) -> {
            System.out.println("item cambiado");
            Object o = titulo.getSelectedItem();
            if (o instanceof Pelicula) {
                Pelicula pelicula = (Pelicula) o;
                ((FormularioCopia) form)
                        .getjXImageView1().setImage(ImageUtils.minimizeImage(pelicula.getImagen().getImagen()));

            }
        });
    }

}
