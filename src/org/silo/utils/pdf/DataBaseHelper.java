/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.utils.pdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import org.silo.modelos.bo.Cliente;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Genero;
import org.silo.modelos.bo.Log;
import org.silo.modelos.bo.Pelicula;
import org.silo.modelos.bo.Usuario;
import org.silo.modelos.bo.Venta;

/**
 *
 * @author Adrián
 */
public class DataBaseHelper {

    private Connection connection;
    private Statement statement;

    public DataBaseHelper() {
        connection = null;
        statement = null;
    }

    public boolean crearConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/SILO", "postgres", "1");
            statement = connection.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public String select(String consultaSQL, boolean e) throws SQLException {
        String r = "";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(consultaSQL);
        while (rs.next()) {
            if (e) {
                r += rs.getString(1);
            } else {
                r += rs.getString(1) + "#";
            }
        }
        return r;
    }

    public String selectDatos(String consultaSQL) {
        String r = "";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(consultaSQL);
            while (rs.next()) {
                r += rs.getString(1) + "#";
                r += rs.getString(2) + "#";
                r += rs.getString(3);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return r;
    }

    public String[] seleccionarDatosCopiasPeliculas(String estado) {
        ArrayList<String> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT copia_id, pelicula_titulo, copia_fmto, copia_fechaadquisicion, copia_precio\n"
                                                  + "FROM copia_pelicula, pelicula\n"
                                                  + "where copia_pelicula.pelicula_id = pelicula.pelicula_id\n"
                                                  + "and copia_edo = '" + estado + "'\n"
                                                  + "ORDER BY copia_id;");

            while (rs.next()) {
                String copiaPelicula = "";
                copiaPelicula += rs.getString("copia_id") + "&";
                copiaPelicula += rs.getString("pelicula_titulo") + "&";
                copiaPelicula += rs.getString("copia_fmto") + "&";
                copiaPelicula += rs.getString("copia_fechaadquisicion") + "&";
                copiaPelicula += rs.getString("copia_precio");
                array.add(copiaPelicula);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return array.toArray(new String[0]);
    }

    public String[] seleccionarDatosEmpleadoMensualEstado(
            String estado, Date fechaInicio, Date fechaFin) {
        ArrayList<String> array = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT empleado_id, "
                    + "coalesce(empleado_nombre||' '||empleado_appater||' '"
                    + "||empleado_apmater), \n"
                    + "       coalesce('Entrada: "
                    + "'||extract(hour from empleado_horaentrada)||':'"
                    + "||extract(minute from empleado_horaentrada) ||'\n'||"
                    + "'Salida'||empleado_horasalida),\n"
                    + "       empleado_fecharegistro, empleado_sueldo\n"
                    + "  FROM empleado\n"
                    + "  WHERE empleado_edo = ?"
                    + "AND empleado_fecharegistro >= ?\n"
                    + "AND empleado_fecharegistro <= ?;");
            ps.setString(1, estado);
            ps.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(3, new java.sql.Date(fechaFin.getTime()));
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT empleado_id, "
                    + "coalesce(empleado_nombre||' '||empleado_appater||' '"
                    + "||empleado_apmater), \n"
                    + "       coalesce('Entrada: "
                    + "'||extract(hour from empleado_horaentrada)||':'"
                    + "||extract(minute from empleado_horaentrada) ||'\n'||"
                    + "'Salida'||empleado_horasalida),\n"
                    + "       empleado_fecharegistro, empleado_sueldo\n"
                    + "  FROM empleado\n"
                    + "  WHERE empleado_edo = '" + estado + "';");
            while (rs.next()) {
                String datosEmpleado = "";
                datosEmpleado += rs.getString(1) + "&";
                datosEmpleado += rs.getString(2) + "&";
                datosEmpleado += rs.getString(3) + "&";
                datosEmpleado += rs.getString(4) + "&";
                datosEmpleado += rs.getString(5);
                array.add(datosEmpleado);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return array.toArray(new String[0]);
    }

    public ResultSet seleccionarDatosPeliculasGeneralInventario() {
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT distinct(pelicula.pelicula_id), "
                                        + "pelicula_titulo, extract(year from pelicula_anioestreno),\n"
                                        + "       pelicula_estelares, pelicula_director, pelicula_clasif, "
                                        + "genero_nombre, pelicula_duracion, "
                                        + "(select count(copia_pelicula.pelicula_id) from copia_pelicula "
                                        + "where copia_pelicula.pelicula_id = pelicula.pelicula_id "
                                        + "and copia_pelicula.copia_edo = 'EN-STOCK'), pelicula_portada\n"
                                        + "  FROM pelicula, copia_pelicula, genero\n"
                                        + "  WHERE genero.genero_id = pelicula.genero_id;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet seleccionarDatosPeliculasGeneralInventario(String anio) {
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT distinct(pelicula.pelicula_id), "
                                        + "pelicula_titulo, extract(year from pelicula_anioestreno),\n"
                                        + "       pelicula_estelares, pelicula_director, pelicula_clasif, "
                                        + "genero_nombre, pelicula_duracion, (select "
                                        + "count(copia_pelicula.pelicula_id) from copia_pelicula "
                                        + "where copia_pelicula.pelicula_id = pelicula.pelicula_id "
                                        + "and copia_pelicula.copia_edo = 'EN-STOCK'), pelicula_portada\n"
                                        + "  FROM pelicula, copia_pelicula, genero\n"
                                        + "  WHERE genero.genero_id = pelicula.genero_id"
                                        + "  AND extract (year from pelicula.pelicula_anioestreno) = " + anio + ";");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet seleccionarDatosPeliculasGeneralInventarioClasificaciones(String clasif) {
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT distinct(pelicula.pelicula_id), "
                                        + "pelicula_titulo, extract(year from pelicula_anioestreno), "
                                        + "pelicula_estelares, pelicula_director, pelicula_clasif, "
                                        + "genero_nombre, pelicula_duracion, (select count(copia_pelicula.pelicula_id) "
                                        + "from copia_pelicula where copia_pelicula.pelicula_id = pelicula.pelicula_id "
                                        + "and copia_pelicula.copia_edo = 'EN-STOCK'), pelicula_portada\n"
                                        + "FROM pelicula, copia_pelicula, genero\n"
                                        + "WHERE genero.genero_id = pelicula.genero_id\n"
                                        + "AND pelicula.pelicula_clasif = '" + clasif + "';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet seleccionarDatosPeliculasGeneralInventarioGenero(String genero) {
        ResultSet rs = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT distinct(pelicula.pelicula_id), "
                                        + "pelicula_titulo, extract(year from pelicula_anioestreno), "
                                        + "pelicula_estelares, pelicula_director, pelicula_clasif, "
                                        + "genero_nombre, pelicula_duracion, (select "
                                        + "count(copia_pelicula.pelicula_id) from copia_pelicula "
                                        + "where copia_pelicula.pelicula_id = pelicula.pelicula_id "
                                        + "and copia_pelicula.copia_edo = 'EN-STOCK'), pelicula_portada\n"
                                        + "FROM pelicula, copia_pelicula, genero\n"
                                        + "WHERE genero.genero_id = pelicula.genero_id\n"
                                        + "AND genero.genero_nombre = '" + genero + "'\n"
                                        + "AND copia_pelicula.copia_edo = 'EN-STOCK';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet seleccionarDatosRegistroMensualClientes(Date fechaInicio, Date fechaFin) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT cliente_id, "
                    + "coalesce (cliente_nombre || ' ' || cliente_appater "
                    + "|| ' ' || cliente_apmater), extract (year from age(now(),"
                    + "cliente_fechanacimiento)), to_char(cliente_fecharegistro,"
                    + " 'dd/mm/yyyy'), cliente_imagen\n"
                    + "FROM cliente\n"
                    + "WHERE cliente_fecharegistro >= ?\n"
                    + "AND cliente_fecharegistro <= ?;", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));
            rs = ps.executeQuery();
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);
//            rs = statement.executeQuery("SELECT cliente_id, "
//                    + "coalesce (cliente_nombre || ' ' || cliente_appater "
//                    + "|| ' ' || cliente_apmater), extract (year from age(now(),"
//                    + "cliente_fechanacimiento)), to_char(cliente_fecharegistro,"
//                    + " 'dd/mm/yyyy'), cliente_imagen\n"
//                    + "FROM cliente\n"
//                    + "WHERE cliente_fecharegistro >= '" + fechaInicio + "'\n"
//                    + "AND cliente_fecharegistro <= '" + fechaFin + "';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet seleccionarDatosRegistroMensualEmpleados(Date fechaInicio, Date fechaFin) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT empleado_id, "
                    + "coalesce (empleado_nombre || ' ' || empleado_appater || "
                    + "' ' || empleado_apmater), extract (year from (age(now(), "
                    + "empleado_fechanacimiento))),\n"
                    + " to_char(empleado_fecharegistro, 'dd/mm/yyyy'), "
                    + "coalesce ((coalesce(extract (hour from empleado_horaentrada) "
                    + "|| ':'\n"
                    + "  || extract (minute from empleado_horaentrada))) || "
                    + "' - ' || (coalesce(extract (hour from empleado_horasalida)) "
                    + "|| ':' || extract(minute from empleado_horasalida))), "
                    + "empleado_sueldo, empleado_imagen\n"
                    + "FROM empleado\n"
                    + "WHERE empleado_fecharegistro >= ?\n"
                    + "AND empleado_fecharegistro <= ?;", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));
            rs = ps.executeQuery();
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                                                   ResultSet.CONCUR_READ_ONLY);
//            rs = statement.executeQuery(
//                    "SELECT empleado_id, "
//                    + "coalesce (empleado_nombre || ' ' || empleado_appater || "
//                    + "' ' || empleado_apmater), extract (year from (age(now(), "
//                    + "empleado_fechanacimiento))),\n"
//                    + " to_char(empleado_fecharegistro, 'dd/mm/yyyy'), "
//                    + "coalesce ((coalesce(extract (hour from empleado_horaentrada) "
//                    + "|| ':'\n"
//                    + "  || extract (minute from empleado_horaentrada))) || "
//                    + "' - ' || (coalesce(extract (hour from empleado_horasalida)) "
//                    + "|| ':' || extract(minute from empleado_horasalida))), "
//                    + "empleado_sueldo, empleado_imagen\n"
//                    + "FROM empleado\n"
//                    + "WHERE empleado_fecharegistro >= '" + fechaInicio + "'\n"
//                    + "AND empleado_fecharegistro <= '" + fechaFin + "';");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public String[] seleccionarDatosVentasMensual(String fechaInicio, String fechaFin) {
        ArrayList<String> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT venta.venta_id, "
                                                  + "to_char(venta.venta_fecha, 'dd/mm/yyyy'), coalesce "
                                                  + "(empleado.empleado_id || ' ' || empleado.empleado_nombre "
                                                  + "|| ' ' || empleado.empleado_appater),\n"
                                                  + "coalesce (cliente.cliente_id || ' ' || \n"
                                                  + "cliente.cliente_nombre || ' ' || cliente.cliente_appater || \n"
                                                  + "' ' || cliente.cliente_apmater), venta.venta_neto\n"
                                                  + "FROM venta, empleado, cliente\n"
                                                  + "WHERE venta.empleado_id = empleado.empleado_id\n"
                                                  + "AND venta.cliente_id = cliente.cliente_id\n"
                                                  + "AND venta.venta_fecha >= '" + fechaInicio + "'\n"
                                                  + "AND venta.venta_fecha <= '" + fechaFin + "' ORDER BY venta.venta_id;");

            while (rs.next()) {
                String datosVenta = "";
                datosVenta += rs.getString(1) + "&";
                datosVenta += rs.getString(2) + "&";
                datosVenta += rs.getString(3) + "&";
                datosVenta += rs.getString(4) + "&";
                datosVenta += rs.getString(5);
                array.add(datosVenta);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return array.toArray(new String[0]);
    }

    public String seleccionarDatosVentasCantidadVentas(String fechaInicio, String fechaFin) {
        String result = "";
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("select count(venta.venta_id), "
                                                  + "coalesce(empleado.empleado_id||' '||empleado.empleado_nombre||"
                                                  + "' '||empleado.empleado_appater||' '||empleado.empleado_apmater), "
                                                  + "sum(venta.venta_neto)\n"
                                                  + "from venta, empleado\n"
                                                  + "where venta.empleado_id = empleado.empleado_id\n"
                                                  + "and venta.venta_fecha >= '" + fechaInicio + "'\n"
                                                  + "and venta.venta_fecha <= '" + fechaFin + "'\n"
                                                  + "group by empleado.empleado_id\n"
                                                  + "order by count(venta.venta_id) desc;");
            rs.next();
            result += rs.getString(1) + "&";
            result += rs.getString(2) + "&";
            result += rs.getString(3);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + "puto1");
            ex.printStackTrace();
        }
        return result;
    }

    public String seleccionarDatosVentasCantidadNeta(String fechaInicio, String fechaFin) {
        String result = "";
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("select sum(venta.venta_neto), "
                                                  + "coalesce(empleado.empleado_id||' '||empleado.empleado_nombre||"
                                                  + "' '||empleado.empleado_appater||' '||empleado.empleado_apmater), "
                                                  + "count(venta.venta_id)\n"
                                                  + "from venta, empleado\n"
                                                  + "where venta.empleado_id = empleado.empleado_id\n"
                                                  + "and venta.venta_fecha >= '" + fechaInicio + "'\n"
                                                  + "and venta.venta_fecha <= '" + fechaFin + "'\n"
                                                  + "group by empleado.empleado_id\n"
                                                  + "order by sum(venta.venta_neto) desc;");
            rs.next();
            result += rs.getString(1) + "&";
            result += rs.getString(2) + "&";
            result += rs.getString(3);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + "****");
            ex.printStackTrace();
        }
        return result;
    }

    public String[] seleccionarDatosEmpleadoVentas(String fecha1, String fecha2) {
        ArrayList<String> array = new ArrayList();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("");
            while (rs.next()) {
                String datos = "";
                datos += rs.getString(1) + "&";
                datos += rs.getString(2) + "&";
                datos += rs.getString(3) + "&";
                datos += rs.getString(4);
                array.add(datos);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return array.toArray(new String[0]);
    }

    public void cerrarConexion() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public ArrayList<Log<Cliente>> getLogClienteData() {
        ArrayList<Log<Cliente>> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT cliente_log_operation, "
                                                  + "cliente_log_stamp, cliente_log_userid, \n"
                                                  + "       cliente_log_cliente_id, cliente_log_cliente_nombre, "
                                                  + "cliente_log_cliente_appater, \n"
                                                  + "       cliente_log_cliente_apmater, "
                                                  + "cliente_log_cliente_fecharegistro, \n"
                                                  + "       cliente_log_cliente_fechanacimiento\n"
                                                  + "  FROM cliente_log;");
            while (rs.next()) {
                Log<Cliente> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Cliente cliente = new Cliente(rs.getString(4),
                                              rs.getString(5), rs.getString(6), rs.getString(7),
                                              rs.getDate(8), rs.getDate(9));
                log.setEntidad(cliente);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public ArrayList<Log<CopiaPelicula>> getLogCopiaPeliculaData() {
        ArrayList<Log<CopiaPelicula>> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT copia_pelicula_log_operation, "
                                                  + "copia_pelicula_log_stamp, coipa_pelicula_log_userid, \n"
                                                  + "       coipa_pelicula_log_copia_id, coipa_pelicula_log_copia_fmto, "
                                                  + "coipa_pelicula_log_copia_fechaadquisicion, \n"
                                                  + "       coipa_pelicula_log_copia_precio, coipa_pelicula_log_copia_edo, \n"
                                                  + "       coipa_pelicula_log_pelicula_id\n"
                                                  + "  FROM copia_pelicula_log;");
            while (rs.next()) {
                Log<CopiaPelicula> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Pelicula pelicula = new Pelicula();
                pelicula.setIdPelicula(rs.getLong("coipa_pelicula_log_pelicula_id"));
                CopiaPelicula copia = new CopiaPelicula(rs.getLong(4),pelicula,
                                                        rs.getString(5), rs.getDate(6), rs.getDouble(7), rs.getString(8));
                log.setEntidad(copia);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public ArrayList<Log<Empleado>> getLogEmpleadoData() {
        ArrayList<Log<Empleado>> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT empleado_log_operation, "
                                                  + "empleado_log_stamp, empleado_log_userid, \n"
                                                  + "       empleado_log_empleado_id,"
                                                  + " empleado_log_empleado_nombre, "
                                                  + "empleado_log_empleado_appater, \n"
                                                  + "       empleado_log_empleado_apmater, "
                                                  + "empleado_log_empleado_horaentrada, \n"
                                                  + "       empleado_log_empleado_horasalida, "
                                                  + "empleado_log_empleado_fechanacimiento, \n"
                                                  + "       empleado_log_empleado_fecharegistro,"
                                                  + " empleado_log_empleado_edo, \n"
                                                  + "       empleado_log_empleado_puesto,"
                                                  + " empleado_log_empleado_sueldo\n"
                                                  + "  FROM empleado_log;");
            while (rs.next()) {
                Log<Empleado> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Empleado empleado = new Empleado(rs.getString(4), rs.getDate(8),
                                                 rs.getDate(9), rs.getString(12), rs.getString(13), rs.getDouble(14),
                                                 rs.getString(5), rs.getString(6), rs.getString(7),
                                                 rs.getDate(10), rs.getDate(11));
                log.setEntidad(empleado);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public ArrayList<Log<Genero>> getLogGeneroData() {
        ArrayList<Log<Genero>> array = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT genero_log_operation, "
                                                  + "genero_log_stamp, genero_log_userid, genero_log_genero_id, \n"
                                                  + "       genero_log_genero_nombre, genero_log_genero_descripcion\n"
                                                  + "  FROM genero_log;");
            while (rs.next()) {
                Log<Genero> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Genero genero = new Genero(rs.getLong(4), rs.getString(5), rs.getString(6));
                log.setEntidad(genero);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public ArrayList<Log<Pelicula>> getLogPeliculaData() {
        ArrayList<Log<Pelicula>> array = new ArrayList();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT pelicula_log_operation, "
                    + "pelicula_log_stamp, pelicula_log_userid, \n"
                    + "       pelicula_log_pelicula_id, pelicula_log_pelicula_titulo, "
                    + "pelicula_log_pelicula_anioestreno, \n"
                    + "       pelicula_log_pelicula_director, "
                    + "pelicula_log_pelicula_estelares, \n"
                    + "       pelicula_log_pelicula_duracion, "
                    + "pelicula_log_pelicula_clasif, \n"
                    + "       pelicula_log_genero_id\n"
                    + "  FROM pelicula_log;");
            while (rs.next()) {
                Log<Pelicula> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Genero genero = new Genero();
                genero.setIdGenero(rs.getLong("pelicula_log_genero_id"));
                String anio = rs.getString("pelicula_log_pelicula_anioestreno");
                Date anioEstreno = new Date();
                anioEstreno.setYear(Integer.parseInt(anio.split(" ")[0]));
                Pelicula pelicula = new Pelicula(
                        rs.getLong("pelicula_log_pelicula_id"),
                        genero,
                        rs.getString("pelicula_log_pelicula_estelares"),
                        rs.getString("pelicula_log_pelicula_titulo"),
                        anioEstreno,
                        rs.getString("pelicula_log_pelicula_director"),
                        rs.getString("pelicula_log_pelicula_clasif"),
                        rs.getDate("pelicula_log_pelicula_duracion"));
                log.setEntidad(pelicula);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

//    public ArrayList<Log<Usuario>> getLogUsuarioData() {
//        ArrayList<Log<Usuario>> array = new ArrayList<>();
//        try {
//            statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT usuario_log_operation, "
//                                                  + "usuario_log_stamp, usuario_log_userid, \n"
//                                                  + "       usuario_log_usuario_id, usuario_log_usename, usuario_log_privilegios\n"
//                                                  + "  FROM usuario_log;");
//            while (rs.next()) {
//                Log<Usuario> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
//                Empleado empleado = new Empleado();
//                empleado.setIdEmpleado(rs.getString(5));
//                Usuario usuario = new Usuario(rs.getLong(4), empleado, rs.getLong(6));
//                log.setEntidad(usuario);
//                array.add(log);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return array;
//    }

    public ArrayList<Log<Venta>> getLogVentaData() {
        ArrayList<Log<Venta>> array = new ArrayList();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT venta_log_operation, "
                                                  + "venta_log_stamp, venta_log_userid, venta_log_venta_id, \n"
                                                  + "       venta_log_venta_fecha, venta_log_venta_neto, "
                                                  + "venta_log_empleado_id, \n"
                                                  + "       venta_log_cliente_id\n"
                                                  + "  FROM venta_log;");
            while (rs.next()) {
                Log<Venta> log = new Log(rs.getString(1), rs.getDate(2), rs.getString(3));
                Cliente cliente = new Cliente();
                Empleado empleado = new Empleado();
                cliente.setIdCliente(rs.getString(7));
                empleado.setIdEmpleado(rs.getString(8));
                Venta venta = new Venta(rs.getLong(4), cliente, empleado, rs.getDate(5), rs.getDouble(6));
                log.setEntidad(venta);
                array.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }
}
