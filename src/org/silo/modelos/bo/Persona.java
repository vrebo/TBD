package org.silo.modelos.bo;

import java.util.Date;
import org.silo.utils.Validator;

public abstract class Persona {

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private Imagen imagen;

    public Persona() {
    }

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        Validator.checkForContent(nombre, "El nombre no puede estar vacío.");
        Validator.checkForContent(apellidoPaterno, "El apellido paterno no puede estar vacío.");
        Validator.checkForContent(apellidoMaterno, "El apellido materno no puede estar vacío.");
        Validator.checkForNull(fechaNacimiento, "La fecha de nacimiento no puede estar vacía.");
        Validator.checkForNull(fechaRegistro, "La fecha de registro no puede estar vacía.");
        Validator.checkForNull(imagen, "La imagen no puede estar vacía.");
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.imagen = imagen;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        Validator.checkForNull(imagen, "La imagen no puede estar vacía.");
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Validator.checkForContent(nombre, "El nombre no puede estar vacío.");
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        Validator.checkForContent(apellidoPaterno, "El apellido paterno no puede estar vacío.");
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        Validator.checkForContent(apellidoMaterno, "El apellido materno no puede estar vacío.");
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        Validator.checkForNull(fechaNacimiento, "La fecha de nacimiento no puede estar vacía.");
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        Validator.checkForNull(fechaRegistro, "La fecha de registro no puede estar vacía.");
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
