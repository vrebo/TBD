package org.silo.modelos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.bo.Conexion;
import org.silo.modelos.bo.Usuario;


public class DataBaseHelper {

    private static Conexion conexion;
    private static Usuario usuarioLogeado;

    public static Connection getConexion() {
        return getConexion(conexion);
    }

    public static Connection getConexion(Conexion conexion) {
        Connection connection = null;
        try {
            Class.forName(conexion.getDriver()).newInstance();
            connection = DriverManager.getConnection(
                    conexion.getUrl() + conexion.getHost() + ":"
                    + conexion.getPort() + "/" + conexion.getBaseDatos(),
                    conexion.getUser(), conexion.getPassword());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println("Problema con la conexion" + ex.getMessage());
            ex.printStackTrace();
        }
        return connection;
    }

    public static boolean testConexion(Conexion conexion) {
        Connection connection = null;
        try {
            Class.forName(conexion.getDriver()).newInstance();
            connection = DriverManager.getConnection(
                    conexion.getUrl() + conexion.getHost() + ":"
                    + conexion.getPort() + "/" + conexion.getBaseDatos(),
                    conexion.getUser(), conexion.getPassword());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        boolean result;
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public static void setConexion(Conexion con) {
        conexion = con;
    }

    public static Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public static void setUsuarioLogeado(Usuario usuarioLogeado) {
        DataBaseHelper.usuarioLogeado = usuarioLogeado;
    }
    
}
