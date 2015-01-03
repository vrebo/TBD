package org.silo.vista.componentes;

import org.silo.modelos.bo.CopiaPelicula;

public class CopiaPeliculaTableModel extends MyTableModel<CopiaPelicula> {

    public CopiaPeliculaTableModel() {
        columnNames = new String[]{
            "ID",
            "Título",
            "Fecha de adquisición",
            "Precio",
            "Estado",
            "Formato"
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
                return data.get(rowIndex).getFechaAdquisicion();
            case 3:
                return data.get(rowIndex).getPrecio();
            case 4:
                return data.get(rowIndex).getEstado();
            default:
                return data.get(rowIndex).getFormato();         

        }
    }
}
