package org.silo.modelos.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.bo.Conexion;


public class DataBaseHelper {

    private static Conexion conexion;

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

    public boolean insertarImagen(String ruta) throws IOException {
        FileInputStream fis;
        try {
            Connection conexion = DataBaseHelper.getConexion();
            File file = new File(ruta);
            fis = new FileInputStream(file);
            PreparedStatement pstm = conexion.prepareStatement("INSERT INTO pelicula(pelicula_portada)VALUES (?);");
            pstm.setBinaryStream(1, fis, (int) file.length());
            pstm.execute();
            fis.close();
            conexion.close();
            return true;
        } catch (FileNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String select(String consultaSQL, boolean e) throws SQLException {
        String r = "";
        Connection conexion = DataBaseHelper.getConexion();
        Statement statement = conexion.createStatement();
        ResultSet rs = statement.executeQuery(consultaSQL);
        while (rs.next()) {
            if (e) {
                r += rs.getString(1);
            } else {
                r += rs.getString(1) + "#";
            }
        }
        conexion.close();
        return r;
    }

    public String[] seleccionarClientes() {
        ArrayList<String> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_cliente;");
            while (rs.next()) {
                String cliente = "";
                cliente += rs.getString("cliente_id") + "&";
                cliente += rs.getString("cliente_nombre") + "&";
                cliente += rs.getString("cliente_appater") + "&";
                cliente += rs.getString("cliente_apmater") + "&";
                cliente += rs.getString("cliente_fecharegistro") + "&";
                cliente += rs.getString("cliente_fechanacimiento");
                array.add(cliente);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array.toArray(new String[0]);
    }

    public String[] seleccionarCopiasPelicula() {
        ArrayList<String> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_copia_pelicula;");
            while (rs.next()) {
                String copiaPelicula = "";
                copiaPelicula += rs.getString("copia_id") + "&";
                copiaPelicula += rs.getString("copia_codigo") + "&";
                copiaPelicula += rs.getString("copia_fmto") + "&";
                copiaPelicula += rs.getString("copia_fechaadquisicion") + "&";
                copiaPelicula += rs.getString("copia_precio") + "&";
                copiaPelicula += rs.getString("copia_edo") + "&";
                copiaPelicula += rs.getString("pelicula_id");
                array.add(copiaPelicula);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array.toArray(new String[0]);
    }

    public String[] seleccionarEmpleado() {
        ArrayList<String> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_empleado;");
            while (rs.next()) {
                String empleado = "";
                empleado += rs.getString("empleado_id") + "&";
                empleado += rs.getString("empleado_nombre") + "&";
                empleado += rs.getString("empleado_appater") + "&";
                empleado += rs.getString("empleado_apmater") + "&";
                empleado += rs.getString("empleado_horaentrada") + "&";
                empleado += rs.getString("empleado_horasalida") + "&";
                empleado += rs.getString("empleado_fechanacimiento") + "&";
                empleado += rs.getString("empleado_fecharegistro") + "&";
                empleado += rs.getString("empleado_edo") + "&";
                empleado += rs.getString("empleado_puesto") + "&";
                empleado += rs.getString("empleado_sueldo");
                array.add(empleado);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array.toArray(new String[0]);
    }

    public String[] seleccionarGenero() {
        ArrayList<String> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_genero;");
            while (rs.next()) {
                String genero = "";
                genero += rs.getInt("genero_id") + "&";
                genero += rs.getString("genero_nombre") + "&";
                genero += rs.getString("genero_descripcion");
                array.add(genero);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array.toArray(new String[0]);
    }

    public String[] seleccionarPelicula() {
        ArrayList<String> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_pelicula;");
            while (rs.next()) {
                String pelicula = "";
                pelicula += rs.getString("pelicula_id") + "&";
                pelicula += rs.getString("pelicula_titulo") + "&";
                pelicula += rs.getString("pelicula_anioestreno") + "&";
                pelicula += rs.getString("pelicula_director") + "&";
                pelicula += rs.getString("pelicula_estelares") + "&";
                pelicula += rs.getString("pelicula_duracion") + "&";
                pelicula += rs.getString("pelicula_clasif") + "&";
                pelicula += rs.getString("genero_id");
                array.add(pelicula);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array.toArray(new String[0]);
    }

    public ArrayList<byte[]> obtenerPortadasPelicula() {
        ArrayList<byte[]> array = new ArrayList();
        try {
            Connection conexion = DataBaseHelper.getConexion();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from vista_portada_pelicula;");
            while (rs.next()) {
                byte[] arreglo = rs.getBytes("pelicula_portada");
                if (arreglo == null) {
                    FileInputStream fis = new FileInputStream(new File("C:\\Users\\VREBO\\Documents\\NetBeansProjects\\LOSI\\resources\\imagenes\\logo-lois.png"));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    try {
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                            System.out.println("read " + readNum + " bytes,");
                        }
                    } catch (IOException ex) {
                        
                    }

                    //bytes is the ByteArray we need
                    arreglo = bos.toByteArray();
                } 
                array.add(arreglo);
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataBaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
}
