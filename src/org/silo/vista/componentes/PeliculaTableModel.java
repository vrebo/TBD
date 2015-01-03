package org.silo.vista.componentes;

import org.silo.modelos.bo.Pelicula;

public class PeliculaTableModel extends MyTableModel<Pelicula> {

    public PeliculaTableModel() {
        columnNames = new String[]{
            "ID",
            "Título",
            "Año de estreno",
            "Director",
            "Estelares",
            "Duración",
            "Clasificación",
            "Genero"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getIdPelicula();
            case 1:
                return data.get(rowIndex).getTitulo();
            case 2:
                return data.get(rowIndex).getAnioEstreno().getYear() + 1900;
            case 3:
                return data.get(rowIndex).getDirector();
            case 4:
                return data.get(rowIndex).getEstelares();
            case 5:
                return data.get(rowIndex).getDuracion();
            case 6:
                return data.get(rowIndex).getClasificacion();
            default:
                return data.get(rowIndex).getGenero().getNombre();
        }
    }

}
