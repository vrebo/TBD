package org.silo.modelos.bo;

import java.util.Date;

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
        this.idEmpleado = idEmpleado;
    }

    public Empleado(Date horaEntrada, Date horaSalida, String estado, String puesto, double sueldo, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
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
        this.idEmpleado = idEmpleado;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

}
