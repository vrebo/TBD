package org.silo.vista.componentes;

import org.silo.modelos.bo.Cliente;

public class ClienteTableModel extends MyTableModel<Cliente> {

    public ClienteTableModel() {
        columnNames = new String[]{
            "ID",
            "Nombre completo",
            "Fecha de nacimiento",
            "Fecha de registro"};
    }    

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return data.get(row).getIdCliente();
            case 1:
                return data.get(row).getNombreCompleto();
            case 4:
                return data.get(row).getFechaNacimiento();
            default:
                return data.get(row).getFechaRegistro();
        }
    }
    
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        if(data.isEmpty()){
            return Object.class;
        }
        switch (c) {
            case 0:
                return data.get(0).getIdCliente().getClass();
            case 1:
                return data.get(0).getNombre().getClass();
            case 2:
                return data.get(0).getApellidoPaterno().getClass();
            case 3:
                return data.get(0).getApellidoMaterno().getClass();
            case 4:
                return data.get(0).getFechaNacimiento().getClass();
            default:
                return data.get(0).getFechaRegistro().getClass();
        }
    }
}
