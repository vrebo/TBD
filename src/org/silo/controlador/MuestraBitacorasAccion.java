/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.controlador;

import java.awt.Container;
import org.silo.vista.VistaPrincipal;
import org.silo.vista.componentes.MyInternalFrame;

/**
 *
 * @author VREBO
 */
public class MuestraBitacorasAccion extends Accion{

    @Override
    public void tarea(Container contenedor, Object... args) {
        System.out.println("Mostrando bitácoras");
        MyInternalFrame internalFrame = ((VistaPrincipal)contenedor).getBitacora();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }
    
}
