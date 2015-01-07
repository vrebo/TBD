package org.silo.controlador;

import java.awt.Container;
import org.silo.vista.VistaPrincipal;
import org.silo.vista.componentes.MyInternalFrame;

public class MuestraCatGenerosAccion extends Accion {

    @Override
    public void tarea(Container contenedor, Object... args) {
        MyInternalFrame internalFrame = ((VistaPrincipal) contenedor).getGeneroCatalogo();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }

}
