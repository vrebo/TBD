package org.silo.modelos.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDAO<E, P> {

    private static final Properties defaultProps = new Properties();
    protected static final Properties propiedades = new Properties(defaultProps);

    static {
        try {
            defaultProps.load(
                    ClassLoader.class.
                    getResourceAsStream("/properties/default.properties"));
            propiedades.load(
                    ClassLoader.class.
                    getResourceAsStream("/properties/config.properties"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract boolean persistir(E e, Connection con);

    public abstract boolean actualizar(E e, Connection con);

    public abstract boolean eliminar(E e, Connection con);

    public abstract List<E> buscarTodos(Connection con);

    public abstract E buscarPorId(P id, Connection con);

    public abstract PreparedStatement setArgumentos(E e, PreparedStatement ps) throws Exception;

    public abstract E extraeResultado(ResultSet rs) throws Exception;

    public boolean persistirCommit(E e) {
        Connection con = DataBaseHelper.getConexion();
        boolean result = false;
        try {
            con.setAutoCommit(false);
            result = persistir(e, con);
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
                con.close();
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    public boolean actualizarCommit(E e) {
        Connection con = DataBaseHelper.getConexion();
        boolean result = false;
        try {
            con.setAutoCommit(false);
            result = actualizar(e, con);
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
                con.close();
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    public boolean eliminarCommit(E e) {
        Connection con = DataBaseHelper.getConexion();
        boolean result = false;
        try {
            con.setAutoCommit(false);
            result = eliminar(e, con);
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
                con.close();
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    public List<E> buscarTodosCommit() {
        Connection con = DataBaseHelper.getConexion();
        List<E> result = null;
        try {
            con.setAutoCommit(false);
            result = buscarTodos(con);
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
                con.close();
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    public E buscarPorIdCommit(P id) {
        Connection con = DataBaseHelper.getConexion();
        E result = null;
        try {
            con.setAutoCommit(false);
            result = buscarPorId(id, con);
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback();
                con.close();
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }
}
