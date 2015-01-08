package org.silo.controlador;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.silo.modelos.bo.Conexion;
import org.silo.modelos.dao.DataBaseHelper;
import org.silo.vista.VistaPrincipal;

public class IniciaSesionAccion extends Accion {
    
    @Override
    public void tarea(Container contenedor, Object... args) {
        String user = (String) args[0];
        String pasword = (String) args[1];
//        String user = "postgres";
//        String pasword = "1";
        Conexion conexion = new Conexion(user, pasword);
        try {
            if (DataBaseHelper.testConexion(conexion)) {
                DataBaseHelper.setConexion(conexion);
                VistaPrincipal vista = (VistaPrincipal) contenedor;
                vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
                vista.getJMenuBar().setVisible(true);
                vista.setContentPane(vista.getjDesktopPane1());
                vista.setMinimumSize(new Dimension(700, 600));
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(contenedor, ex.getCause(), "Problema al accesar al sistema", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
