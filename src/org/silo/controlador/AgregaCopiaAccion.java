package org.silo.controlador;

import java.awt.Container;
import java.util.List;
import javax.swing.JOptionPane;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.vista.componentes.MyTableModel;

public class AgregaCopiaAccion extends Accion {

    @Override
    public void tarea(Container contenedor, Object... args) {
//        VentaPanel ventaPanel = (VentaPanel) contenedor;
//        String texto = ventaPanel.getjTextFieldCodigoCopia().getText();
//        if(texto.equals("")){
//            return;
//        }
//        Long id = new Long(texto);
//        CopiaPelicula copia = ServiciosLOIS.getServicios().buscaCopiaPorID(id);
//        if (copia == null) {
//            JOptionPane.showMessageDialog(
//                    ventaPanel,
//                    "No se encontró la copia con el código " + id,
//                    "Advertencia",
//                    JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            if (copia.isAvailable()) {
//                MyTableModel<CopiaPelicula> modelo = ((MyTableModel) ventaPanel.getjTable1().getModel());
//                List<CopiaPelicula> data = modelo.getData();
//                boolean estaEn = false;
//                for (CopiaPelicula copiaPelicula : data) {
//                    if (copiaPelicula.getIdCopiaPelicula() == copia.getIdCopiaPelicula()) {
//                        estaEn = true;
//                        break;
//                    }
//                }
//                if (!estaEn) {
//                    data.add(copia);
//                    modelo.actualizaDatos();
//                    Accion.getAccion("ActualizaTotalVenta").ejecutar(ventaPanel, ventaPanel.getVenta());
//                }
//            }
//        }
    }
}
