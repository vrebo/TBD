package org.silo.controlador;

import java.awt.Container;
import org.silo.vista.VistaPrincipal;
import org.silo.vista.componentes.MyInternalFrame;

public class MuestraCatEmpleadosAccion extends Accion{

    @Override
    public void tarea(Container contenedor, Object... args) {
        MyInternalFrame internalFrame = ((VistaPrincipal)contenedor).getEmpleadoCatalogo();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }
    
}
