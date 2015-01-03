package org.silo.modelos.bo;

import java.util.Date;

public class CopiaPelicula {

    private long idCopiaPelicula;
    private Pelicula pelicula;
    private String formato;
    private Date fechaAdquisicion;
    private double precio;
    private String estado;
    
    public CopiaPelicula(){}

    public CopiaPelicula(long idCopiaPelicula, Pelicula pelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        this(pelicula, formato, fechaAdquisicion, precio, estado);
        this.idCopiaPelicula = idCopiaPelicula;
    }

    public CopiaPelicula(Pelicula pelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        this.pelicula = pelicula;
        this.formato = formato;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precio = precio;
        this.estado = estado;
    }

    public CopiaPelicula(long idCopiaPelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        this.idCopiaPelicula = idCopiaPelicula;
        this.formato = formato;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precio = precio;
        this.estado = estado;
    }
    
    

    public long getIdCopiaPelicula() {
        return idCopiaPelicula;
    }

    public void setIdCopiaPelicula(long idCopiaPelicula) {
        this.idCopiaPelicula = idCopiaPelicula;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public boolean isAvailable(){
        return estado.equals("EN-STOCK");
    }
}
