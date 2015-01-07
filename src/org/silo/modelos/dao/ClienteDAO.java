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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.silo.modelos.bo.Cliente;
import org.silo.modelos.bo.Imagen;

public class ClienteDAO extends GenericDAO<Cliente, String> {

    public final static String NOMBRE_TABLA = propiedades.getProperty("cliente-tabla");
    public final static String ID_TABLA = propiedades.getProperty("cliente-id");
    public final static String NOMBRE = propiedades.getProperty("cliente-nombre");
    public final static String APELLIDO_PAT = propiedades.getProperty("cliente-apellido-paterno");
    public final static String APELLIDO_MAT = propiedades.getProperty("cliente-apellido-materno");
    public final static String FECHA_NACIMIENTO = propiedades.getProperty("cliente-fecha-nacimiento");
    public final static String FECHA_REGISTRO = propiedades.getProperty("cliente-fecha-registro");
    public final static String NOMBRE_IMAGEN = "cliente_imagen_nombre";
    public final static String IMAGEN = "cliente_imagen";

    @Override
    public boolean persistir(Cliente e, Connection con) {
        boolean result = false;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + NOMBRE_TABLA + " ("
                    + NOMBRE + ", "
                    + APELLIDO_PAT + ", "
                    + APELLIDO_MAT + ", "
                    + FECHA_NACIMIENTO + ", "
                    + FECHA_REGISTRO + ", "
                    + IMAGEN + ","
                    + NOMBRE_IMAGEN + ","
                    + ID_TABLA + " "
                    + ") VALUES (?, ?, ?, ?::date, ?::timestamp , ?, ?, ?);");
            ps = setArgumentos(e, ps);
            ps.execute();
            ps.close();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ClienteDAO.class.getName() + " Error al almacenar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Cliente e, Connection con) {
        boolean result = false;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + NOMBRE_TABLA + " SET "
                    + NOMBRE + " = ?, "
                    + APELLIDO_PAT + " = ?, "
                    + APELLIDO_MAT + " = ?, "
                    + FECHA_NACIMIENTO + " = ?::timestamp, "
                    + FECHA_REGISTRO + " = ?::timestamp, "
                    + IMAGEN + " = ?, "
                    + NOMBRE_IMAGEN + " = ?"
                    + " WHERE "
                    + ID_TABLA + " = ?;");
            ps = setArgumentos(e, ps);
            ps.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }

        return result;
    }

    @Override
    public boolean eliminar(Cliente e, Connection con) {
        boolean result = false;

        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM " + NOMBRE_TABLA + " WHERE " + ID_TABLA + " = ?;")) {
            ps.setString(1, e.getIdCliente());
            ps.execute();

            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ClienteDAO.class.getName() + " Error al almacenar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public List<Cliente> buscarTodos(Connection con) {
        ArrayList<Cliente> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + NOMBRE_TABLA + ";";
        try (PreparedStatement ps = con.prepareStatement(statement); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = extraeResultado(rs);
                lista.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return lista;
    }

    @Override
    public Cliente buscarPorId(String id, Connection con) {
        Cliente e = null;
        String statement
               = "SELECT * FROM " + NOMBRE_TABLA + " WHERE "
                 + ID_TABLA + " = ? ;";
        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            e = extraeResultado(rs);

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return e;
    }

    @Override
    public PreparedStatement setArgumentos(Cliente e, PreparedStatement ps) {
        try {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellidoPaterno());
            ps.setString(3, e.getApellidoMaterno());
            ps.setDate(4, new Date(e.getFechaNacimiento().getTime()));
            ps.setDate(5, new Date(e.getFechaRegistro().getTime()));
            File archivo = e.getImagen().getArchivo();
            FileInputStream fis = new FileInputStream(archivo);
            ps.setBinaryStream(6, fis, (int) archivo.length());
            ps.setString(7, archivo.getName());
            ps.setString(8, e.getIdCliente());
            return ps;
        } catch (SQLException | IOException ex) {
            String nombreClase = ClienteDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }

    @Override
    public Cliente extraeResultado(ResultSet rs) {
        try {
            String idCliente = rs.getString(ID_TABLA);
            String nombre = rs.getString(NOMBRE);
            String apellidoPaterno = rs.getString(APELLIDO_PAT);
            String apellidoMaterno = rs.getString(APELLIDO_MAT);
            java.util.Date fechaNacimiento = rs.getDate(FECHA_NACIMIENTO);
            java.util.Date fechaRegistro = rs.getDate(FECHA_REGISTRO);

            String nombreImagen = rs.getString("cliente_imagen_nombre");
            FileOutputStream fos = new FileOutputStream("temp/" + nombreImagen);
            byte[] bytes = rs.getBytes("cliente_imagen");
            fos.write(bytes);
            File archivo = new File("temp/" + nombreImagen);
            ImageIcon imageIcon = new ImageIcon(bytes);
            Imagen imagen = new Imagen(archivo, imageIcon);

            return new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        } catch (SQLException | IOException ex) {
            String nombreClase = PeliculaDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }
}
