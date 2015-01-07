package org.silo.vista.componentes;

import org.silo.utils.ImageUtils;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import org.jdesktop.swingx.JXSearchField;

public class SearchToolBar extends JToolBar {

    private JXSearchField searchField;
    private JButton newEntity;
    private JButton saveEntity;
    private JButton deleteEntity;
    private JButton updateCatalog;

    public SearchToolBar() {
        initComponents();
    }

    public JXSearchField getSearchField() {
        return searchField;
    }

    public JButton getNewEntity() {
        return newEntity;
    }

    public JButton getSaveEntity() {
        return saveEntity;
    }

    public JButton getDeleteEntity() {
        return deleteEntity;
    }

    public JButton getUpdateCatalog() {
        return updateCatalog;
    }
    
    private void initComponents() {

        searchField = new JXSearchField("Buscar");
        searchField.setSearchMode(JXSearchField.SearchMode.INSTANT);
        searchField.setInstantSearchDelay(100);
        searchField.setColumns(10);
              
        newEntity = makeNavigationButton(
                "editor_document_file_add_outline_stroke.png", 
                "NuevaEntidad", 
                "Limpia el formulario para registrar una nueva entidad", 
                "Nuevo");
        saveEntity = makeNavigationButton(
                "editor_floopy-dish_save_outline_stroke.png", 
                "GuardaEntidad", 
                "Guarda los cambios de la entidad en el formulario.", 
                "Guardar");
        deleteEntity = makeNavigationButton(
                "editor_trash_delete_recycle_bin_outline_stroke.png", 
                "EliminaEntidad", 
                "Elimina la entidad seleccionada de ser posible.", 
                "Eliminar");
        updateCatalog = makeNavigationButton(
                "circle_sync_backup_2_outline_stroke.png", 
                "ActualizaCatalogo", 
                "Actualiza las entidades del catálogo.", 
                "Actualizar");

        add(searchField);
        add(new JSeparator(JSeparator.VERTICAL));
        add(newEntity);
        add(saveEntity);
        add(deleteEntity);
        add(updateCatalog);
    }

    protected JButton makeNavigationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = "/images/icons/"
                + imageName;

        ImageIcon icon = ImageUtils.createImageIcon(imgLocation);
        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
//        button.addActionListener(this);

        if (icon != null) {                      //image found
            button.setIcon(icon);
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

}
