package org.silo.modelos.bo;

public class Genero {

    private long idGenero;
    private String nombre;
    private String descripcion;

    public Genero() {
    }

    public Genero(long idGenero, String nombre, String descripcion) {
        this(nombre, descripcion);
        this.idGenero = idGenero;
    }

    public Genero(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(long idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
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
}
