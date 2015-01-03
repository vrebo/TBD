package org.silo.modelos.bo;

import java.util.Date;
import javax.swing.ImageIcon;

public abstract class Persona {

    protected String nombre;
    protected String apellidoPaterno;
    protected String apellidoMaterno;
    protected Date fechaNacimiento;
    protected Date fechaRegistro;
    protected Imagen imagen;

    public Persona() {
    }

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.imagen = imagen;
    }

    public void validaStrings(String string, String msjError) {
        if (string.isEmpty()) {
            throw new IllegalArgumentException(msjError);
        }
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validaStrings(nombre, "El nombre es necesario.");
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        validaStrings(apellidoPaterno, "El apellido paterno es necesario.");
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        validaStrings(apellidoMaterno, "El apellido paterno es necesario.");
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
