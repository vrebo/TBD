package org.silo.modelos.bo;

import java.util.Date;
import org.silo.utils.Validator;

public class Empleado extends Persona {

    private String idEmpleado;
    private Date horaEntrada;
    private Date horaSalida;
    private String estado;
    private String puesto;
    private double sueldo;

    public Empleado() {
    }

    public Empleado(String idEmpleado, Date horaEntrada, Date horaSalida, String estado, String puesto, double sueldo, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        this(horaEntrada, horaSalida, estado, puesto, sueldo, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        Validator.checkForContent(idEmpleado, "El id no puede estar vacío.");
        this.idEmpleado = idEmpleado;
    }
    
    public Empleado(String idEmpleado, Date horaEntrada, Date horaSalida, String estado, String puesto, double sueldo, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro) {
        this(horaEntrada, horaSalida, estado, puesto, sueldo, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro);
        Validator.checkForContent(idEmpleado, "El id no puede estar vacío.");
        this.idEmpleado = idEmpleado;
    }
    
    public Empleado(Date horaEntrada, Date horaSalida, String estado, String puesto, double sueldo, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro);
        Validator.checkForNull(horaEntrada, "La hora de entrada no puede ser null");
        Validator.checkForNull(horaSalida, "La hora de entrada no puede ser null");
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
        Validator.checkForContent(puesto, "El puesto no puede estar vacío.");
        Validator.checkForPositive(sueldo, "El sueldo no puede ser negativo");
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.estado = estado;
        this.puesto = puesto;
        this.sueldo = sueldo;
    }

    public Empleado(Date horaEntrada, Date horaSalida, String estado, String puesto, double sueldo, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        Validator.checkForNull(horaEntrada, "La hora de entrada no puede ser null");
        Validator.checkForNull(horaSalida, "La hora de entrada no puede ser null");
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
        Validator.checkForContent(puesto, "El puesto no puede estar vacío.");
        Validator.checkForPositive(sueldo, "El sueldo no puede ser negativo");
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.estado = estado;
        this.puesto = puesto;
        this.sueldo = sueldo;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        Validator.checkForContent(idEmpleado, "El id no puede estar vacío.");
        this.idEmpleado = idEmpleado;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        Validator.checkForNull(horaEntrada, "La hora de entrada no puede ser null");
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        Validator.checkForNull(horaSalida, "La hora de entrada no puede ser null");
        this.horaSalida = horaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        Validator.checkForContent(estado, "El estado no puede estar vacío.");
        this.estado = estado;
        
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        Validator.checkForContent(puesto, "El puesto no puede estar vacío.");
        this.puesto = puesto;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        Validator.checkForPositive(sueldo, "El sueldo no puede ser negativo");
        this.sueldo = sueldo;
    }

}
