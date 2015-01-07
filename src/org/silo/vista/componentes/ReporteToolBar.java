package org.silo.vista.componentes;

import javax.swing.JButton;

public class ReporteToolBar extends MyToolBar {
    
    private JButton before;
    private JButton next;
    private JButton defaultZoom;
    
    public ReporteToolBar() {
        initComponents();
    }

    public JButton getBefore() {
        return before;
    }

    public JButton getNext() {
        return next;
    }
    
    private void initComponents(){
        before = makeNavigationButton(
                "circle_back_arrow_outline_stroke.png", 
                "RetrocederPagina", 
                "Retrocede una página en el reporte", 
                "Página anterior");
        next = makeNavigationButton(
                "circle_next_arrow_disclosure_outline_stroke.png", 
                "AvanzaPagina", 
                "Avanza una página en el reporte.", 
                "Página siguiente");
        defaultZoom = makeNavigationButton(
                "common_search_lookup_outline_stroke.png", 
                "RestauraZoom", 
                "Restaura el zoom de la vista del reporte.", 
                "Restaura zoom");
        add(before);
        add(next);
        add(defaultZoom);
    }

}
