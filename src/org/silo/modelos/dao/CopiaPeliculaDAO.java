package org.silo.modelos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Pelicula;

public class CopiaPeliculaDAO extends GenericDAO<CopiaPelicula, Long> {

    public final static String nombreTabla = propiedades.getProperty("copia-tabla");
    public final static String idCopiaPeliculaDAO = propiedades.getProperty("copia-id");
    public final static String codigoDAO = propiedades.getProperty("copia-codigo");
    public final static String formatoDAO = propiedades.getProperty("copia-formato");
    public final static String fechaAdquisicionDAO = propiedades.getProperty("copia-fecha-adquisicion");
    public final static String precioDAO = propiedades.getProperty("copia-precio");
    public final static String estadoDAO = propiedades.getProperty("copia-estado");

    @Override
    public boolean persistir(CopiaPelicula e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + nombreTabla + " ("
                    + formatoDAO + ", "
                    + fechaAdquisicionDAO + ", "
                    + precioDAO + ", "
                    + estadoDAO + ", "
                    + PeliculaDAO.idPeliculaDAO + " "
                    + ") VALUES (?::copia_formato, ?::date, ?, ?::copia_estado, ?);");

            ps = setArgumentos(e, ps);

            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
        } catch (Exception ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(CopiaPelicula e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + nombreTabla + " SET "
                    + formatoDAO + " = ?::copia_formato, "
                    + fechaAdquisicionDAO + " = ?::date, "
                    + precioDAO + " = ?, "
                    + estadoDAO + " = ?::copia_estado, "
                    + PeliculaDAO.idPeliculaDAO + " = ? "
                    + " WHERE "
                    + idCopiaPeliculaDAO + " = ? ;");

            ps = setArgumentos(e, ps);
            ps.setLong(6, e.getIdCopiaPelicula());

            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
        } catch (Exception ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean eliminar(CopiaPelicula e, Connection con) {
        boolean result = false;

        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM " + nombreTabla + " WHERE "
                + idCopiaPeliculaDAO + " = ?;")) {
            ps.setLong(1, e.getIdCopiaPelicula());
            ps.execute();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return result;
    }

    @Override
    public List<CopiaPelicula> buscarTodos(Connection con) {
        ArrayList<CopiaPelicula> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + nombreTabla + ";";

        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                CopiaPelicula copia = extraeResultado(rs);
                PeliculaDAO peliculaDAO = new PeliculaDAO();
                Pelicula pelicula = peliculaDAO.buscarPorId(copia.getPelicula().getIdPelicula(), con);
                copia.setPelicula(pelicula);
                lista.add(copia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return lista;
    }

    public List<CopiaPelicula> buscarTodosDisponibles() {
        Connection con = DataBaseHelper.getConexion();
        ArrayList<CopiaPelicula> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE " + estadoDAO + " = 'EN-STOCK';";

        try (PreparedStatement ps = con.prepareStatement(statement)) {
            con.setAutoCommit(false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                CopiaPelicula copia = extraeResultado(rs);
                PeliculaDAO peliculaDAO = new PeliculaDAO();
                Pelicula pelicula = peliculaDAO.buscarPorId(copia.getPelicula().getIdPelicula(), con);
                copia.setPelicula(pelicula);
                lista.add(copia);
            }
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return lista;
    }

    @Override
    public CopiaPelicula buscarPorId(Long id, Connection con) {
        CopiaPelicula e = null;
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE "
                 + idCopiaPeliculaDAO + " = ? ;";

        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                e = extraeResultado(rs);
            }
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            Pelicula pelicula = peliculaDAO.buscarPorId(e.getPelicula().getIdPelicula(), con);
            e.setPelicula(pelicula);
        } catch (SQLException ex) {
            Logger.getLogger(CopiaPeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }

        return e;
    }

    @Override
    public PreparedStatement setArgumentos(CopiaPelicula e, PreparedStatement ps) {
        try {
            ps.setString(1, e.getFormato());
            ps.setDate(2, new java.sql.Date(e.getFechaAdquisicion().getTime()));
            ps.setDouble(3, e.getPrecio());
            ps.setString(4, e.getEstado());
            ps.setLong(5, e.getPelicula().getIdPelicula());

            return ps;
        } catch (SQLException ex) {
            String nombreClase = CopiaPelicula.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }

    @Override
    public CopiaPelicula extraeResultado(ResultSet rs) {
        try {
            long idCopiaPelicula = rs.getLong(idCopiaPeliculaDAO);
            String formato = rs.getString(formatoDAO);
            Date fechaAdquisicion = rs.getDate(fechaAdquisicionDAO);
            String estado = rs.getString(estadoDAO);
            double precio = rs.getDouble(precioDAO);
            long idPelicula = rs.getLong(PeliculaDAO.idPeliculaDAO);
            Pelicula pelicula = new Pelicula();
            pelicula.setIdPelicula(idPelicula);
            return new CopiaPelicula(idCopiaPelicula, pelicula, formato, fechaAdquisicion, precio, estado);
        } catch (SQLException ex) {
            Logger.getLogger(CopiaPelicula.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
    }
}
