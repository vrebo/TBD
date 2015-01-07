package org.silo.vista.componentes;

import javax.swing.JButton;


public class GeneradorToolBar extends MyToolBar{
    
    private JButton saveReport;
    private JButton createReport;

    public GeneradorToolBar() {
        initComponents();
    }
    
    public JButton getSaveReport() {
        return saveReport;
    }

    public JButton getCreateReport() {
        return createReport;
    }
    
    private void initComponents(){
        createReport = makeNavigationButton(
                "editor_document_file_add_outline_stroke.png", 
                "CrearReporte", 
                "Crea un nuevo reporte", 
                "Crear reporte");
        saveReport = makeNavigationButton(
                "editor_floopy-dish_save_outline_stroke.png", 
                "GuardaReporte", 
                "Guarda el reporte mostrado.", 
                "Guardar ");
        add(createReport);
        add(saveReport);
    }

}
