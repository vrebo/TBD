/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.modelos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Usuario;

/**
 *
 * @author VREBO
 */
public class UsuarioDAO extends GenericDAO<Usuario, Long> {

    @Override
    public boolean persistir(Usuario e, Connection con) {
        boolean result = false;
        try {
            String statement = "INSERT INTO usuario (usename, privilegios) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setString(1, e.getEmpleado().getIdEmpleado());
            ps.setLong(2, e.getPrivilegios());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean actualizar(Usuario e, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(Usuario e, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> buscarTodos(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscarPorId(Long id, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PreparedStatement setArgumentos(Usuario e, PreparedStatement ps) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario extraeResultado(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Usuario buscarPorNombre(String nombre) {
        Connection con = DataBaseHelper.getConexion();
        Usuario e = new Usuario();
        try {
            String statement = "SELECT * FROM usuario WHERE usename = ?;";
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            rs.next();
            e.setIdUsuario(rs.getLong("usuario_id"));
            e.setPrivilegios(rs.getLong("privilegios"));
            rs.close();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Empleado empleado = empleadoDAO.buscarPorId(nombre, con);
            e.setEmpleado(empleado);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

}
