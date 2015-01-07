package org.silo.controlador;

import java.awt.Container;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.vista.componentes.MyInternalFrame;

public class MuestraInternalFrameAccion extends Accion{

    @Override
    public void tarea(Container contenedor, Object... args) {
        MyInternalFrame frame = (MyInternalFrame)args[0];
        if(!frame.isVisible()){
            frame.show();
            System.out.println("cerrado");
        }else{
            frame.toFront();
            try {
                frame.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MuestraInternalFrameAccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
