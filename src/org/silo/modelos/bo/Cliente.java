package org.silo.modelos.bo;

import java.util.Date;

public class Cliente extends Persona {

    private String idCliente;

    public Cliente() {
    }

    public Cliente(String idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        validaStrings(idCliente, "El id del cliente está vacio.");
        this.idCliente = idCliente;
    }
}
