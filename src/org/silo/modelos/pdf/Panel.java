/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.silo.modelos.pdf;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Adrián
 */
public class Panel extends JPanel {
    
    private JComboBox jcbCombo;
    private JButton jbGenerar;
    
    public Panel () {
        addComponentes();
    }

    private void addComponentes() {
        setBorder(BorderFactory.createLineBorder(getBackground(), 15));
        jbGenerar = new JButton("Generar");
        jcbCombo = crearCombo();
        add(getJcbCombo());
        add(jbGenerar);
    }
    
    public void addListener(Listener listener) {
        jbGenerar.addActionListener(listener);
    }
    
    private JComboBox crearCombo() {
        JComboBox c = new JComboBox();
        c.addItem("Copias_Estado");
        c.addItem("Empleados_Mensual_Estado");
        c.addItem("General_Peliculas_Inventario");
        c.addItem("Peliculas_Inventario_Anio_Estreno");
        c.addItem("Peliculas_Inventario_Clasificacion");
        c.addItem("Peliculas_Inventario_Genero");
        c.addItem("Registro_Clientes_Mensual");
        c.addItem("Registro_Empleados_Mensual");
        c.addItem("Reporte_Ventas_Mensual");
        c.addItem("Reporte_Ventas_Mensual_Empleado");
        return c;
    }

    /**
     * @return the jcbCombo
     */
    public JComboBox getJcbCombo() {
        return jcbCombo;
    }
    
}
