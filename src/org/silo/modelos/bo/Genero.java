package org.silo.modelos.bo;

import org.silo.utils.Validator;

public class Genero {

    private long idGenero;
    private String nombre;
    private String descripcion;

    public Genero() {
    }

    public Genero(long idGenero, String nombre, String descripcion) {
        this(nombre, descripcion);
        Validator.checkForRange(Long.MIN_VALUE, Long.MAX_VALUE, idGenero, nombre);
        this.idGenero = idGenero;
    }

    public Genero(String nombre, String descripcion) {
        Validator.checkForContent(nombre, "El nombre no puede estár vacío");
        Validator.checkForContent(descripcion, "La descripción no puede estar vacía.");
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(long idGenero) {
        Validator.checkForRange(Long.MIN_VALUE, Long.MAX_VALUE, idGenero, nombre);
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Validator.checkForContent(nombre, "El nombre no puede estár vacío");
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        Validator.checkForContent(descripcion, "La descripción no puede estar vacía.");
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Genero) {
            return idGenero == ((Genero) o).getIdGenero();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (this.idGenero ^ (this.idGenero >>> 32));
        return hash;
    }
}
