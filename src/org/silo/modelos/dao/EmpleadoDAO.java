package org.silo.modelos.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Imagen;

public class EmpleadoDAO extends GenericDAO<Empleado, String> {

    public final static String nombreTabla = propiedades.getProperty("empleado-tabla");
    public final static String idEmpleadoDAO = propiedades.getProperty("empleado-id");
    public final static String nombreDAO = propiedades.getProperty("empleado-nombre");
    public final static String apellidoPaternoDAO = propiedades.getProperty("empleado-appater");
    public final static String apellidoMaternoDAO = propiedades.getProperty("empleado-apmater");
    public final static String horaEntradaDAO = propiedades.getProperty("empleado-horaentrada");
    public final static String horaSalidaDAO = propiedades.getProperty("empleado-horasalida");
    public final static String fechaNacimientoDAO = propiedades.getProperty("empleado-fechanacimiento");
    public final static String fechaRegistroDAO = propiedades.getProperty("empleado-fecharegistro");
    public final static String estadoDAO = propiedades.getProperty("empleado-estado");
    public final static String puestoDAO = propiedades.getProperty("empleado-puesto");

    @Override
    public PreparedStatement setArgumentos(Empleado e, PreparedStatement ps) {
        try {
            ps.setString(1,
                         e.getNombre());
            ps.setString(2, e.getApellidoPaterno());
            ps.setString(3, e.getApellidoMaterno());
            ps.setTime(4, new Time(e.getHoraEntrada().getTime()));
            ps.setTime(5, new Time(e.getHoraSalida().getTime()));
            ps.setDate(6, new Date(e.getFechaNacimiento().getTime()));
            ps.setDate(7, new Date(e.getFechaRegistro().getTime()));
            ps.setString(8, e.getEstado());
            ps.setString(9, e.getPuesto());
            ps.setDouble(10, e.getSueldo());

            File portada = e.getImagen().getArchivo();
            FileInputStream fis = new FileInputStream(portada);
            ps.setBinaryStream(11, fis, (int) portada.length());
            ps.setString(12, portada.getName());
            ps.setString(13, e.getIdEmpleado());

            return ps;
        } catch (SQLException | IOException ex) {
            String nombreClase = EmpleadoDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }

    @Override
    public Empleado extraeResultado(ResultSet rs) {
        try {
            String idEmpleado = rs.getString(1);
            String nombre = rs.getString(2);
            String apellidoPaterno = rs.getString(3);
            String apellidoMaterno = rs.getString(4);
            java.util.Date horaEntrada = rs.getTime(5);
            java.util.Date horaSalida = rs.getTime(6);
            Date fechaNacimiento = rs.getDate(7);
            Date fechaRegistro = rs.getDate(8);
            String estado = rs.getString(9);
            String puesto = rs.getString(10);
            double sueldo = rs.getDouble("empleado_sueldo");
            String nombreImagen = rs.getString("empleado_imagen_nombre");
            FileOutputStream fos = new FileOutputStream("temp/" + nombreImagen);
            byte[] bytes = rs.getBytes("empleado_imagen");
            File archivo = new File("temp/" + nombreImagen);
            fos.write(bytes);
            ImageIcon imageIcon = new ImageIcon(bytes);
            Imagen imagen = new Imagen(archivo, imageIcon);
            return new Empleado(idEmpleado, horaEntrada, horaSalida, estado, puesto, sueldo, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        } catch (SQLException | IOException ex) {
            String nombreClase = EmpleadoDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }

    @Override
    public boolean persistir(Empleado e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + nombreTabla + " ("
                    + nombreDAO + ", "
                    + apellidoPaternoDAO + ", "
                    + apellidoMaternoDAO + ", "
                    + horaEntradaDAO + ", "
                    + horaSalidaDAO + ", "
                    + fechaNacimientoDAO + ", "
                    + fechaRegistroDAO + ", "
                    + estadoDAO + ", "
                    + puestoDAO + ","
                    + "empleado_sueldo,"
                    + "empleado_imagen,"
                    + "empleado_imagen_nombre,"
                    + idEmpleadoDAO
                    + " )"
                    + " VALUES (?, ?, ?, ?::time, ?::time, ?::timestamp, "
                    + "? , ?::empleado_estado, ?::empleado_puesto, ?, ?, ?, ?);");

            ps = setArgumentos(e, ps);

            ps.execute();
            ps.close();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Empleado e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + nombreTabla + " SET "
                    + nombreDAO + " = ?, "
                    + apellidoPaternoDAO + " = ?, "
                    + apellidoMaternoDAO + " = ?, "
                    + horaEntradaDAO + " = ?::time, "
                    + horaSalidaDAO + " = ?::time, "
                    + fechaNacimientoDAO + " = ?::timestamp, "
                    + fechaRegistroDAO + " = ?::timestamp, "
                    + estadoDAO + " = ?::empleado_estado, "
                    + puestoDAO + " = ?::empleado_puesto,"
                    + "empleado_sueldo = ?,"
                    + "empleado_imagen = ?,"
                    + "empleado_imagen_nombre = ?"
                    + " WHERE "
                    + idEmpleadoDAO + " = ?;");

            ps = setArgumentos(e, ps);

            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public boolean eliminar(Empleado e, Connection con) {
        boolean result = false;

        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM " + nombreTabla + " WHERE "
                + idEmpleadoDAO
                + " = ?;")) {
            ps.setString(1, e.getIdEmpleado());
            ps.execute();

            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al eliminar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public List<Empleado> buscarTodos(Connection con) {
        ArrayList<Empleado> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + nombreTabla + ";";
        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = extraeResultado(rs);
                lista.add(e);
            }

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
    }

    @Override
    public Empleado buscarPorId(String id, Connection con) {
        Empleado e = null;
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE "
                 + idEmpleadoDAO + " = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            e = extraeResultado(rs);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return e;
    }

}
