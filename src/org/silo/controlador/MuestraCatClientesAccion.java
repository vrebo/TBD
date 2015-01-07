package org.silo.controlador;

import java.awt.Container;
import org.silo.vista.VistaPrincipal;
import org.silo.vista.componentes.MyInternalFrame;


public class MuestraCatClientesAccion extends Accion{

    @Override
    public void tarea(Container contenedor, Object... args) {
        System.out.println("Mostrando catalogo de clientes");
        MyInternalFrame internalFrame = ((VistaPrincipal)contenedor).getClienteCatalogo();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }
    
}
