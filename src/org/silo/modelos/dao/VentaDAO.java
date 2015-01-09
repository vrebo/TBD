package org.silo.modelos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.silo.modelos.bo.Cliente;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Venta;

public class VentaDAO extends GenericDAO<Venta, Long> {

    public final static String nombreTabla = propiedades.getProperty("venta-tabla");
    public final static String idVentaDAO = propiedades.getProperty("venta-id");
    public final static String fechaVentaDAO = propiedades.getProperty("venta-fecha");
    public final static String netoVentaDAO = propiedades.getProperty("venta-neto");

    @Override
    public boolean persistir(Venta e, Connection con) {
        boolean result = false;
        try {
            /**
            String statement = "SELECT MAX("
                               + idVentaDAO
                               + ") FROM "
                               + nombreTabla
                               + ";";
            long id;
            try (ResultSet rs = con.createStatement().executeQuery(statement)) {
                rs.next();
                id = rs.getLong(1) + 1;
            }
            String statement
                   = "SELECT setval('venta_venta_id_seq', " + id + ");";
            con.createStatement().execute(statement);
            */
            String statement = "INSERT INTO " + nombreTabla + " ("
                        + fechaVentaDAO + ","
                        + netoVentaDAO + ", "
                        + ClienteDAO.ID_TABLA + ","
                        + EmpleadoDAO.idEmpleadoDAO + ") "
                        + " VALUES ("
                        + " ?::date, ?, ?, ?"
                        + ");";
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setDate(1, new java.sql.Date(e.getFechaVenta().getTime()));
            ps.setDouble(2, e.getNetoVenta());
            ps.setString(3, e.getCliente().getIdCliente());
            ps.setString(4, e.getEmpleado().getIdEmpleado());
            ps.execute();
            System.out.println("todo bien...");
            statement = "SELECT setval('detalle_venta_detallevta_id_seq', "
                        + "(SELECT MAX(detallevta_id) FROM detalle_venta));";
            con.createStatement().execute(statement);
            statement = "INSERT INTO detalle_venta ("
                        + idVentaDAO + ", "
                        + CopiaPeliculaDAO.idCopiaPeliculaDAO
                        + ") VALUES ((SELECT MAX(venta_id) FROM venta), ?);";
            CopiaPeliculaDAO copiaDAO = new CopiaPeliculaDAO();
            for (CopiaPelicula copia : e.getDetalleVenta()) {
                ps = con.prepareStatement(statement);
                ps.setLong(1, copia.getIdCopiaPelicula());
                copia.setEstado("VENDIDA");
                copiaDAO.actualizar(copia, con);
                ps.execute();
            }
            ps.close();
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public boolean actualizar(Venta e, Connection con) {
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE " + nombreTabla + " SET "
                    + fechaVentaDAO + " = ?::date, "
                    + netoVentaDAO + " = ?, "
                    + ClienteDAO.NOMBRE_TABLA + " = ?, "
                    + EmpleadoDAO.idEmpleadoDAO + " = ? "
                    + "WHERE "
                    + idVentaDAO + " = ?;");
            ps.setDate(1, new java.sql.Date(e.getFechaVenta().getTime()));
            ps.setDouble(2, e.getNetoVenta());
            ps.setString(3, e.getCliente().getIdCliente());
            ps.setString(4, e.getEmpleado().getIdEmpleado());
            ps.setLong(5, e.getIdVenta());
            ps.executeUpdate();
            ps.close();
            result = true;
        } catch (SQLException ex) {
        }
        return result;
    }

    @Override
    public boolean eliminar(Venta e, Connection con) {
        boolean result = false;

        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM venta WHERE " + idVentaDAO + " = ?;")) {
            ps.setLong(1, e.getIdVenta());
            ps.execute();

            result = true;
        } catch (SQLException ex) {
        }
        return result;
    }

    @Override
    public List<Venta> buscarTodos(Connection con) {
        ArrayList<Venta> lista = new ArrayList<>();
        String statement = "SELECT * FROM venta;";

        try (PreparedStatement ps = con.prepareStatement(statement); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long idVenta = rs.getLong(idVentaDAO);
                String idCliente = rs.getString(ClienteDAO.NOMBRE_TABLA);
                String idEmpleado = rs.getString(EmpleadoDAO.idEmpleadoDAO);
                Date fechaVenta = rs.getDate(fechaVentaDAO);
                double netoVenta = rs.getDouble(netoVentaDAO);

                ClienteDAO clienteDAO = new ClienteDAO();
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                Cliente cliente = clienteDAO.buscarPorId(idCliente, con);
                Empleado empleado = empleadoDAO.buscarPorId(idEmpleado, con);
                lista.add(new Venta(idVenta, cliente, empleado, fechaVenta, netoVenta));
            }
        } catch (SQLException ex) {
        }
        return lista;
    }

    @Override
    public Venta buscarPorId(Long id, Connection con) {
        Venta e = null;
        String statement
               = "SELECT * FROM venta WHERE "
                 + idVentaDAO + " = ? ;";

        try (PreparedStatement ps = con.prepareStatement(statement)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long idVenta = rs.getLong(idVentaDAO);
            String idCliente = rs.getString(ClienteDAO.ID_TABLA);
            String idEmpleado = rs.getString(EmpleadoDAO.idEmpleadoDAO);
            Date fechaVenta = rs.getDate(fechaVentaDAO);
            double netoVenta = rs.getDouble(netoVentaDAO);
            ClienteDAO clienteDAO = new ClienteDAO();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Cliente cliente = clienteDAO.buscarPorId(idCliente, con);
            Empleado empleado = empleadoDAO.buscarPorId(idEmpleado, con);

            e = new Venta(idVenta, cliente, empleado, fechaVenta, netoVenta);
        } catch (SQLException ex) {
        }
        return e;
    }

    @Override
    public PreparedStatement setArgumentos(Venta e, PreparedStatement ps
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venta extraeResultado(ResultSet rs
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
