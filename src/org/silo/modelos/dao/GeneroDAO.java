package org.silo.modelos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.bo.Genero;

public class GeneroDAO extends GenericDAO<Genero, Long> {

    public final static String nombreTabla = propiedades.getProperty("genero-tabla");
    public final static String idGeneroDAO = propiedades.getProperty("genero-id");
    public final static String nombreDAO = propiedades.getProperty("genero-nombre");
    public final static String descripcionDAO = propiedades.getProperty("genero-descripcion");

    @Override
    public boolean persistir(Genero e, Connection con) {
        boolean result = false;
        try {
//            String statement = "SELECT MAX("
//                               + idGeneroDAO
//                               + ") FROM "
//                               + nombreTabla
//                               + ";";
//            long id;
//            try (ResultSet rs = con.createStatement().executeQuery(statement)) {
//                rs.next();
//                id = rs.getLong(1);
//            }
//            statement
//            = "SELECT setval('genero_genero_id_seq', " + id + ");";
//            con.createStatement().execute(statement);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + nombreTabla + " ("
                    + nombreDAO + ", "
                    + descripcionDAO + ")"
                    + " VALUES (?, ?);");
            ps = setArgumentos(e, ps);
            ps.execute();
            ps.close();
            result = true;
        } catch (Exception ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Genero n, Connection con) {
        boolean result = false;
        String statement
               = "UPDATE " + nombreTabla + " SET "
                 + nombreDAO + " = ?, "
                 + descripcionDAO + " = ? WHERE "
                 + idGeneroDAO
                 + " = ?";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ps = setArgumentos(n, ps);
            ps.setLong(3, n.getIdGenero());
            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
        } catch (Exception ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Genero> buscarTodos(Connection con) {
        ArrayList<Genero> lista = new ArrayList<>();
        String statement
               = "SELECT * FROM " + nombreTabla + ";";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Genero genero = extraeResultado(rs);
                lista.add(genero);
            }
            ps.close();
        } catch (SQLException ex) {
        } catch (Exception ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public boolean eliminar(Genero e, Connection con) {
        String statement
               = "DELETE FROM " + nombreTabla + " WHERE "
                 + idGeneroDAO
                 + " = ?";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setLong(1, e.getIdGenero());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
        }
        return true;
    }

    @Override
    public Genero buscarPorId(Long id, Connection con) {
        Genero e = null;
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE "
                 + idGeneroDAO + " = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            e = extraeResultado(rs);
            ps.close();
        } catch (SQLException ex) {
        } catch (Exception ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    @Override
    public PreparedStatement setArgumentos(Genero e, PreparedStatement ps) {
        try {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            return ps;
        } catch (SQLException ex) {
            String nombreClase = GeneroDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }

    @Override
    public Genero extraeResultado(ResultSet rs) {
        try {
            long idGenero = rs.getLong(idGeneroDAO);
            String nombre = rs.getString(nombreDAO);
            String descripcion = rs.getString(descripcionDAO);
            return new Genero(idGenero, nombre, descripcion);
        } catch (SQLException ex) {
            String nombreClase = PeliculaDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }
}
