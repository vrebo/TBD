package org.silo.modelos.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.silo.modelos.bo.Genero;
import org.silo.modelos.bo.Imagen;
import org.silo.modelos.bo.Pelicula;

public class PeliculaDAO extends GenericDAO<Pelicula, Long> {

    public final static String nombreTabla = propiedades.getProperty("pelicula-tabla");
    public final static String idPeliculaDAO = propiedades.getProperty("pelicula-id");
    public final static String estelaresDAO = propiedades.getProperty("pelicula-estelares");
    public final static String anioEstrenoDAO = propiedades.getProperty("pelicula-anio");
    public final static String directorDAO = propiedades.getProperty("pelicula-director");
    public final static String duracionDAO = propiedades.getProperty("pelicula-duracion");
    public final static String clasificacionDAO = propiedades.getProperty("pelicula-clasificacion");
    public final static String tituloDAO = propiedades.getProperty("pelicula-titulo");

    @Override
    public boolean persistir(Pelicula e, Connection con) {
        boolean result = false;
        try {
//            String statement = "SELECT MAX("
//                    + idPeliculaDAO
//                    + ") FROM "
//                    + nombreTabla
//                    + ";";
//            long id;
//            try (ResultSet rs = con.createStatement().executeQuery(statement)) {
//                rs.next();
//                id = rs.getLong(1);
//            }
//            statement
//                    = "SELECT setval('pelicula_pelicula_id_seq', " + id + ");";
//            con.createStatement().execute(statement);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO " + nombreTabla + " ("
                    + tituloDAO + ", "
                    + directorDAO + ", "
                    + estelaresDAO + ", "
                    + anioEstrenoDAO + ", "
                    + duracionDAO + ", "
                    + clasificacionDAO + ", "
                    + GeneroDAO.idGeneroDAO + ", "
                    + "pelicula_portada,"
                    + "pelicula_portada_nombre "
                    + ") VALUES (?, ?, ?, ?::interval, ?::time, ?::pelicula_clasificacion, ?, ?, ?);");

            ps = setArgumentos(e, ps);
            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Pelicula e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + nombreTabla + " SET "
                    + tituloDAO + " = ?, "
                    + directorDAO + " = ?, "
                    + estelaresDAO + " = ?, "
                    + anioEstrenoDAO + " = ?::interval, "
                    + duracionDAO + " = ?::time, "
                    + clasificacionDAO + " = ?::pelicula_clasificacion, "
                    + GeneroDAO.idGeneroDAO + " = ?,"
                    + "pelicula_portada = ?,"
                    + "pelicula_portada_nombre = ? "
                    + "WHERE "
                    + idPeliculaDAO + " = ?;");
            ps = setArgumentos(e, ps);
            ps.setLong(10, e.getIdPelicula());
            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return result;
    }

    @Override
    public boolean eliminar(Pelicula e, Connection con) {
        boolean result = false;
        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM " + nombreTabla + " WHERE "
                + idPeliculaDAO + " = ?;")) {
            ps.setLong(1, e.getIdPelicula());
            ps.execute();

            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al elimar datos de la base de datos.", ex);
        }
        return result;
    }

    @Override
    public List<Pelicula> buscarTodos(Connection con) {
        ArrayList<Pelicula> lista = null;
        String statement
               = "SELECT * FROM " + nombreTabla + ";";
        try (PreparedStatement ps = con.prepareStatement(statement); ResultSet rs = ps.executeQuery()) {
            lista = new ArrayList<>();
            while (rs.next()) {
                Pelicula pelicula = extraeResultado(rs);
                long idGenero = rs.getLong(GeneroDAO.idGeneroDAO);
                GeneroDAO generoDAO = new GeneroDAO();
                Genero genero = generoDAO.buscarPorId(idGenero, con);
                pelicula.setGenero(genero);
                lista.add(pelicula);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return lista;
    }

    @Override
    public Pelicula buscarPorId(Long id, Connection con) {
        Pelicula e = null;
        String statement
               = "SELECT * FROM " + nombreTabla + " WHERE "
                 + idPeliculaDAO + " = ? ;";

        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ps.setLong(1, id);
            long idGenero;
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                e = extraeResultado(rs);
                idGenero = rs.getLong(GeneroDAO.idGeneroDAO);
            }
            Genero genero = new GeneroDAO().buscarPorId(idGenero, con);
            e.setGenero(genero);

        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al extraer datos de la base de datos.", ex);
        }
        return e;
    }

    @Override
    public PreparedStatement setArgumentos(Pelicula e, PreparedStatement ps) {
        try {
            ps.setString(1, e.getTitulo());
            ps.setString(2, e.getDirector());
            ps.setString(3, e.getEstelares());
            ps.setString(4, "'" + e.getAnioEstreno().getYear() + " years'");
            ps.setTime(5, new Time(e.getDuracion().getTime()));
            ps.setString(6, e.getClasificacion());
            ps.setLong(7, e.getGenero().getIdGenero());
            File archivo = e.getImagen().getArchivo();
            FileInputStream fis = new FileInputStream(archivo);
            ps.setBinaryStream(8, fis, (int) archivo.length());
            ps.setString(9, archivo.getName());
            return ps;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al almacenar datos en la base de datos.", ex);
        }
    }

    @Override
    public Pelicula extraeResultado(ResultSet rs) {
        try {
            long idPelicula = rs.getLong(idPeliculaDAO);
            String titulo = rs.getString(tituloDAO);
            String estelares = rs.getString(estelaresDAO);
            Date anioEstreno = new Date();
            anioEstreno.setYear(Integer.parseInt(
                    rs.getString(anioEstrenoDAO).split(" ")[0]));
            String director = rs.getString(directorDAO);
            String clasificacion = rs.getString(clasificacionDAO);
            Time duracion = rs.getTime(duracionDAO);

            String nombreImagen = rs.getString("pelicula_portada_nombre");
            FileOutputStream fos = new FileOutputStream("temp/" + nombreImagen);
            byte[] bytes = rs.getBytes("pelicula_portada");
            fos.write(bytes);
            File archivo = new File("temp/" + nombreImagen);
            ImageIcon imageIcon = new ImageIcon(bytes);
            Imagen imagen = new Imagen(archivo, imageIcon);
            return new Pelicula(idPelicula, estelares, titulo, anioEstreno,
                                director, clasificacion, duracion, imagen);
        } catch (SQLException | IOException ex) {
            String nombreClase = PeliculaDAO.class.getName();
            Logger.getLogger(
                    nombreClase).log(Level.SEVERE, null, ex);
            throw new RuntimeException(nombreClase
                                       + "Problema al extraer los datos de la base de datos.", ex);
        }
    }
}
