/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.modelos.bo;

/**
 *
 * @author VREBO
 */
public class Usuario {

    private long idUsuario;
    private Empleado empleado;
    private long privilegios;
    
    public Usuario(){ }

    public Usuario(long idUsuario, Empleado empleado, long privilegios) {
        this.idUsuario = idUsuario;
        this.empleado = empleado;
        this.privilegios = privilegios;
    }
    
    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public long getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(long privilegios) {
        this.privilegios = privilegios;
    }
    
    
}
