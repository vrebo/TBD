/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.componentes;

import org.silo.modelos.bo.Cliente;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Genero;
import org.silo.modelos.bo.Log;
import org.silo.modelos.bo.Pelicula;
import org.silo.modelos.bo.Venta;

/**
 *
 * @author VREBO
 */
public class ModelosDeTabla {

    public static class LogClienteTableModel extends MyTableModel<Log<Cliente>> {

        public LogClienteTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID cliente",
                "Nombre","Apellido Paterno","Apellido materno","Fecha nacimiento",
                "Fecha registro"
            };
        }
        
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdCliente();
                case 4: return data.get(rowIndex).getEntidad().getNombre();
                case 5: return data.get(rowIndex).getEntidad().getApellidoPaterno();
                case 6: return data.get(rowIndex).getEntidad().getApellidoMaterno();
                case 7: return data.get(rowIndex).getEntidad().getFechaNacimiento();
                default: return data.get(rowIndex).getEntidad().getFechaRegistro();
            }
        }

    }

    public static class LogEmpleadoTableModel extends MyTableModel<Log<Empleado>> {

        public LogEmpleadoTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID empleado","Nombre",
                "Apellido Paterno","Apellido materno","Fecha nacimiento",
                "Fecha registro","Hora de entrada", "Hora de salida", "Estado", 
                "Puesto","Sueldo"
            };
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdEmpleado();
                case 4: return data.get(rowIndex).getEntidad().getNombre();
                case 5: return data.get(rowIndex).getEntidad().getApellidoPaterno();
                case 6: return data.get(rowIndex).getEntidad().getApellidoMaterno();
                case 7: return data.get(rowIndex).getEntidad().getFechaNacimiento();
                case 8: return data.get(rowIndex).getEntidad().getFechaRegistro();
                case 9: return data.get(rowIndex).getEntidad().getHoraEntrada();
                case 10: return data.get(rowIndex).getEntidad().getHoraSalida();
                case 11: return data.get(rowIndex).getEntidad().getEstado();
                case 12: return data.get(rowIndex).getEntidad().getPuesto();
                default: return data.get(rowIndex).getEntidad().getSueldo();
            }
        }

    }

    public static class LogPeliculaTableModel extends MyTableModel<Log<Pelicula>> {

        public LogPeliculaTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID película","Título",
                "Director","Estelares","Duración",
                "Año de estreno","ID Género", "Clasificación"
            };
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdPelicula();
                case 4: return data.get(rowIndex).getEntidad().getTitulo();
                case 5: return data.get(rowIndex).getEntidad().getDirector();
                case 6: return data.get(rowIndex).getEntidad().getEstelares();
                case 7: return data.get(rowIndex).getEntidad().getDuracion();
                case 8: return data.get(rowIndex).getEntidad().getAnioEstreno();
                case 9: return data.get(rowIndex).getEntidad().getGenero().getIdGenero();
                default: return data.get(rowIndex).getEntidad().getClasificacion();
            }
        }

    }

    public static class LogCopiaTableModel extends MyTableModel<Log<CopiaPelicula>> {

        public LogCopiaTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID película","Título",
                "Director","Estelares","Duración",
                "Año de estreno","ID Género", "Clasificación"
            };
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdCopiaPelicula();
                case 4: return data.get(rowIndex).getEntidad().getPelicula().getIdPelicula();
                case 5: return data.get(rowIndex).getEntidad().getFormato();
                case 6: return data.get(rowIndex).getEntidad().getEstado();
                case 7: return data.get(rowIndex).getEntidad().getFechaAdquisicion();
                default: return data.get(rowIndex).getEntidad().getPrecio();
            }
        }

    }

    public static class LogGeneroTableModel extends MyTableModel<Log<Genero>> {

        public LogGeneroTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID género","Nombre",
                "Descripción"
            };
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdGenero();
                case 4: return data.get(rowIndex).getEntidad().getNombre();
                default: return data.get(rowIndex).getEntidad().getDescripcion();                
            }
        }

    }

    public static class LogVentaTableModel extends MyTableModel<Log<Venta>> {

        public LogVentaTableModel(){
            columnNames = new String[]{
                "Usuario", "Fecha operación", "Operación","ID venta","Fecha de venta",
                "Neto venta", "ID cliente","ID empleado"
            };
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return data.get(rowIndex).getId();
                case 1: return data.get(rowIndex).getFechaOperacion();
                case 2: return data.get(rowIndex).getOperacion();
                case 3: return data.get(rowIndex).getEntidad().getIdVenta();
                case 4: return data.get(rowIndex).getEntidad().getFechaVenta();
                case 5: return data.get(rowIndex).getEntidad().getNetoVenta();
                case 6: return data.get(rowIndex).getEntidad().getCliente().getIdCliente();
                default: return data.get(rowIndex).getEntidad().getEmpleado().getIdEmpleado();
            }
        }

    }
}
