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
public class MuestraReporteClientesAccion extends Accion{

    @Override
    public void tarea(Container contenedor, Object... args) {
        MyInternalFrame internalFrame = ((VistaPrincipal)contenedor).getReporteClientes();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }
    
}
