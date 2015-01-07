package org.silo.modelos.bo;

import java.util.Date;
import org.silo.utils.Validator;

public class Cliente extends Persona {

    private String idCliente;

    public Cliente() {
    }

    public Cliente(String idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, Date fechaRegistro, Imagen imagen) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, imagen);
        Validator.checkForContent(idCliente, "El id no puede estar vacío.");
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        Validator.checkForContent(idCliente, "El id no puede estar vacío.");
        this.idCliente = idCliente;
    }
}
