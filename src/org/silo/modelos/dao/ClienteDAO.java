package org.silo.modelos.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public final static String nombreTabla = propiedades.getProperty("cliente-tabla");
    public final static String idClienteDAO = propiedades.getProperty("cliente-id");
    public final static String nombreDAO = propiedades.getProperty("cliente-nombre");
    public final static String apellidoPaternoDAO = propiedades.getProperty("cliente-apellido-paterno");
    public final static String apellidoMaternoDAO = propiedades.getProperty("cliente-apellido-materno");
    public final static String fechaNacimientoDAO = propiedades.getProperty("cliente-fecha-nacimiento");
    public final static String fechaRegistroDAO = propiedades.getProperty("cliente-fecha-registro");

    @Override
    public boolean persistir(Cliente e, Connection con) {
        boolean result = false;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + nombreTabla + " ("
                    + nombreDAO + ", "
                    + apellidoPaternoDAO + ", "
                    + apellidoMaternoDAO + ", "
                    + fechaNacimientoDAO + ", "
                    + fechaRegistroDAO + ", "
                    + "cliente_imagen, "
                    + "cliente_imagen_nombre,"
                    + idClienteDAO + " "
                    + ") VALUES (?, ?, ?, ?::date, ?::timestamp , ?, ?, ?);");
            ps = setArgumentos(e, ps);
            ps.execute();
            ps.close();
            result = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Cliente e, Connection con) {
        boolean result = false;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + nombreTabla + " SET "
                    + nombreDAO + " = ?, "
                    + apellidoPaternoDAO + " = ?, "
                    + apellidoMaternoDAO + " = ?, "
                    + fechaNacimientoDAO + " = ?::timestamp, "
                    + fechaRegistroDAO + " = ?::timestamp, "
                    + "cliente_imagen = ?, "
                    + "cliente_imagen_nombre = ?"
                    + " WHERE "
                    + idClienteDAO + " = ?;");
            ps = setArgumentos(e, ps);
            ps.executeUpdate();
            result = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean eliminar(Cliente e, Connection con) {
        boolean result = false;
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM " + nombreTabla + " WHERE "
                    + idClienteDAO + " = ?;")) {
                ps.setString(1, e.getIdCliente());
                ps.execute();
            }
            result = true;
        } catch (SQLException ex) {
        }
        return result;
    }

    @Override
    public List<Cliente> buscarTodos(Connection con) {
        ArrayList<Cliente> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + nombreTabla + ";";
        try (PreparedStatement ps = con.prepareStatement(statement); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = extraeResultado(rs);
                lista.add(cliente);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public Cliente buscarPorId(String id, Connection con) {
        Cliente e = null;
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE "
                 + idClienteDAO + " = ? ;";
        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            e = extraeResultado(rs);

            rs.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    @Override
    public PreparedStatement setArgumentos(Cliente e, PreparedStatement ps) throws Exception {
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
    }

    @Override
    public Cliente extraeResultado(ResultSet rs) throws Exception {
        String idCliente = rs.getString(idClienteDAO);
        String nombre = rs.getString(nombreDAO);
        String apellidoPaterno = rs.getString(apellidoPaternoDAO);
        String apellidoMaterno = rs.getString(apellidoMaternoDAO);
        java.util.Date fechaNacimiento = rs.getDate(fechaNacimientoDAO);
        java.util.Date fechaRegistro = rs.getDate(fechaRegistroDAO);

        String nombreImagen = rs.getString("cliente_imagen_nombre");
        FileOutputStream fos = new FileOutputStream("temp/" + nombreImagen);
        byte[] bytes = rs.getBytes("cliente_imagen");
        fos.write(bytes);
        File archivo = new File("temp/" + nombreImagen);
        ImageIcon imageIcon = new ImageIcon(bytes);
        Imagen imagen = new Imagen(archivo, imageIcon);

        return new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
    }
}
