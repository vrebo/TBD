package org.silo.vista.componentes;

import org.silo.modelos.bo.CopiaPelicula;

public class DetalleVentaTableModel extends MyTableModel<CopiaPelicula>{

    public DetalleVentaTableModel() {
    columnNames = new String[]{
            "Código",
            "Título",
            "Formato",
            "Precio"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getIdCopiaPelicula();
            case 1:
                return data.get(rowIndex).getPelicula().getTitulo();
            case 2:
                return data.get(rowIndex).getFormato();
            default:
                return data.get(rowIndex).getPrecio();

        }
    }
}
