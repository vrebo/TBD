package org.silo.controlador;

import java.awt.Container;
import org.silo.vista.VistaPrincipal;
import org.silo.vista.componentes.MyInternalFrame;
import org.silo.vista.procesos.PanelVenta;

public class MuestraVentaFrameAccion extends Accion {

    @Override
    public void tarea(Container contenedor, Object... args) {
        MyInternalFrame internalFrame = ((VistaPrincipal)contenedor).getVenta();
        PanelVenta panelVenta = (PanelVenta)internalFrame.getComponente();
        panelVenta.initListaCopias();
        panelVenta.limpiarFormulario();
        Accion.getAccion("MuestraInternalFrame").ejecutar(contenedor, internalFrame);
    }

}
