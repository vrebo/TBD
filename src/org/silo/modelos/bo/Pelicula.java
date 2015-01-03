package org.silo.modelos.bo;

import java.util.Date;

public class Pelicula {

    private long idPelicula;
    private Genero genero;
    private String estelares;
    private String titulo;
    private Date anioEstreno;
    private String director;
    private String clasificacion;
    private Date duracion;
    private Imagen imagen;

    public Pelicula() {
    }

    public Pelicula(long idPelicula, String estelares, String titulo, Date anioEstreno, String director, String clasificacion, Date duracion, Imagen imagen) {
        this.idPelicula = idPelicula;
        this.estelares = estelares;
        this.titulo = titulo;
        this.anioEstreno = anioEstreno;
        this.director = director;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.imagen = imagen;
    }

    public Pelicula(long idPelicula, Genero genero, String estelares, String titulo, Date anioEstreno, String director, String clasificacion, Date duracion, Imagen imagen) {
        this(idPelicula, estelares, titulo, anioEstreno, director, clasificacion, duracion, imagen);
        this.genero = genero;
    }

    public long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEstelares() {
        return estelares;
    }

    public void setEstelares(String estelares) {
        this.estelares = estelares;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Date getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(Date anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return titulo + " - " + director;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pelicula) {
            return idPelicula == ((Pelicula) o).getIdPelicula();
        }
        return false;
    }

}
