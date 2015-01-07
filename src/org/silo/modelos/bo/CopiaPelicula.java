package org.silo.modelos.bo;

import java.util.Date;
import org.silo.utils.Validator;

public class CopiaPelicula {

    private long idCopiaPelicula;
    private Pelicula pelicula;
    private String formato;
    private Date fechaAdquisicion;
    private double precio;
    private String estado;

    public CopiaPelicula() {
    }

    public CopiaPelicula(long idCopiaPelicula, Pelicula pelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        this(pelicula, formato, fechaAdquisicion, precio, estado);
        Validator.checkForRange(1L, Long.MAX_VALUE, idCopiaPelicula, "El id está fuera de rango");
        this.idCopiaPelicula = idCopiaPelicula;
    }

    public CopiaPelicula(Pelicula pelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        Validator.checkForNull(pelicula, "El atributo película no puede ser null.");
        Validator.checkForContent(formato, "El formato no debe estar vacía.");
        Validator.checkForNull(fechaAdquisicion, "La fecha de aquisición no puede ser null.");
        Validator.checkForPositive(precio, "El precio no puede ser un valor negativo.");
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
        this.pelicula = pelicula;
        this.formato = formato;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precio = precio;
        this.estado = estado;
    }

    public CopiaPelicula(long idCopiaPelicula, String formato, Date fechaAdquisicion, double precio, String estado) {
        Validator.checkForRange(1L, Long.MAX_VALUE, idCopiaPelicula, "El id está fuera de rango");
        Validator.checkForContent(formato, "El formato no debe estar vacía.");
        Validator.checkForNull(fechaAdquisicion, "La fecha de aquisición no puede ser null.");
        Validator.checkForPositive(precio, "El precio no puede ser un valor negativo.");
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
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
        Validator.checkForRange(1L, Long.MAX_VALUE, idCopiaPelicula, "El id está fuera de rango");
        this.idCopiaPelicula = idCopiaPelicula;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        Validator.checkForNull(pelicula, "El atributo película no puede ser null.");
        this.pelicula = pelicula;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        Validator.checkForContent(formato, "El formato no debe estar vacía.");
        this.formato = formato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
        this.estado = estado;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        Validator.checkForNull(fechaAdquisicion, "La fecha de aquisición no puede ser null.");
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        Validator.checkForPositive(precio, "El precio no puede ser un valor negativo.");
        this.precio = precio;
    }

    public boolean isAvailable() {
        return estado.equals("EN-STOCK");
    }
}
