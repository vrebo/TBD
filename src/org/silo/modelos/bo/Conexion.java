package org.silo.modelos.bo;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    private Properties propiedades;
    private String driver;
    private String url;
    private String host;
    private String port;
    private String baseDatos;
    private String user;
    private String password;

    public Conexion() {
        propiedades = getProperties();
        driver= propiedades.getProperty("driver");
        url = propiedades.getProperty("url");
        host = propiedades.getProperty("host");
        port = propiedades.getProperty("puerto");
        baseDatos = propiedades.getProperty("nombre-bd");
        user = propiedades.getProperty("usuario");
        password = propiedades.getProperty("password");
    }    
    
    public Conexion(String user, String password){
        propiedades = getProperties();
        driver= propiedades.getProperty("driver");
        url = propiedades.getProperty("url");
        host = propiedades.getProperty("host");
        port = propiedades.getProperty("puerto");
        baseDatos = propiedades.getProperty("nombre-bd");propiedades = getProperties();
        this.user = user;
        this.password = password;
    }
    
    private Properties getProperties() {
        Properties defaultProps = new Properties();
        Properties props = null;
        try {
            defaultProps.load(getClass().getResourceAsStream("/properties/default.properties"));
            props = new Properties(defaultProps);
            props.load(getClass().getResourceAsStream("/properties/config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;
    }

    
    public Conexion(String host, String port, String baseDatos, String user, String password) {
        this.host = host;
        this.port = port;
        this.baseDatos = baseDatos;
        this.user = user;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}