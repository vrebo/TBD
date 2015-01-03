package org.silo.vista.componentes;

import org.silo.modelos.bo.Empleado;

public class EmpleadoTableModel extends MyTableModel<Empleado> {

    public EmpleadoTableModel() {
        columnNames = new String[]{
            "ID",
            "Nombre completo",
            "Horario de trabajo",
            "Estado",
            "Puesto",
            "Fecha de nacimiento",
            "Fecha de registro"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getIdEmpleado();
            case 1:
                return data.get(rowIndex).getNombreCompleto();
            case 2:
                return data.get(rowIndex).getHoraEntrada() + " - "  + data.get(rowIndex).getHoraSalida();
            case 3:
                return data.get(rowIndex).getPuesto();
            case 4:
                return data.get(rowIndex).getEstado();
            case 5:
                return data.get(rowIndex).getFechaNacimiento();
            default:
                return data.get(rowIndex).getFechaRegistro();
        }
    }

}