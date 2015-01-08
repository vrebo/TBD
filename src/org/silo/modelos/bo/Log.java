/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.modelos.bo;

import java.util.Date;

/**
 *
 * @author VREBO
 * @param <E>
 */
public class Log<E> {
    
    private String operacion;
    private Date fechaOperacion;
    private String id;
    private E entidad;

    public Log(String operacion, Date fechaOperacion, String id) {
        this.operacion = operacion;
        this.fechaOperacion = fechaOperacion;
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public E getEntidad() {
        return entidad;
    }

    public void setEntidad(E entidad) {
        this.entidad = entidad;
    }
        
}
