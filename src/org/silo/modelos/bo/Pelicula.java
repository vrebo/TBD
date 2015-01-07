package org.silo.modelos.bo;

import java.util.Date;
import org.silo.utils.Validator;

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

    public Pelicula(
            long idPelicula, String estelares, String titulo, Date anioEstreno,
            String director, String clasificacion, Date duracion, Imagen imagen) {
        Validator.checkForRange(Long.MIN_VALUE, Long.MAX_VALUE, idPelicula,
                                "El id está fuera del rango permitido min "
                                + Long.MIN_VALUE + " max " + Long.MAX_VALUE);
        Validator.checkForContent(estelares, "Los estelares no pueden estar vacío.");
        Validator.checkForContent(titulo, "El título no puede estar vacío.");
        Validator.checkForNull(anioEstreno, "El año de estreno no puede ser null.");
        Validator.checkForContent(director, "El director no puede estar vacío.");
        Validator.checkForContent(clasificacion, "La clasificación no puede estar vacía.");
        Validator.checkForNull(duracion, "La duración no puede ser null.");
        Validator.checkForNull(imagen, "La imagen no puede ser null.");
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
        Validator.checkForNull(genero, "El genero no puede ser null.");
        this.genero = genero;
    }

    public long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(long idPelicula) {
        Validator.checkForRange(Long.MIN_VALUE, Long.MAX_VALUE, idPelicula,
                                "El id está fuera del rango permitido min "
                                + Long.MIN_VALUE + " max " + Long.MAX_VALUE);
        this.idPelicula = idPelicula;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        Validator.checkForNull(genero, "El genero no puede ser null.");
        this.genero = genero;
    }

    public String getEstelares() {
        return estelares;
    }

    public void setEstelares(String estelares) {
        Validator.checkForContent(estelares, "Los estelares no pueden estar vacío.");
        this.estelares = estelares;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        Validator.checkForContent(titulo, "El título no puede estar vacío.");
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        Validator.checkForContent(director, "El director no puede estar vacío.");
        this.director = director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        Validator.checkForContent(clasificacion, "La clasificación no puede estar vacía.");
        this.clasificacion = clasificacion;
    }

    public Date getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(Date anioEstreno) {
        Validator.checkForNull(anioEstreno, "El año de estreno no puede ser null.");
        this.anioEstreno = anioEstreno;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        Validator.checkForNull(duracion, "La duración no puede ser null.");
        this.duracion = duracion;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        Validator.checkForNull(imagen, "La imagen no puede ser null.");
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return titulo + " - " + director;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pelicula) {
            return hashCode() == ((Pelicula) o).hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.idPelicula ^ (this.idPelicula >>> 32));
        return hash;
    }

}
