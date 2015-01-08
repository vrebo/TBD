/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.componentes;

import java.util.List;
import javax.swing.AbstractListModel;
import org.silo.modelos.bo.CopiaPelicula;

/**
 *
 * @author VREBO
 */
public class CopiaListModel extends AbstractListModel<CopiaPelicula> {

    private List<CopiaPelicula> data = new java.util.ArrayList<>();

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public CopiaPelicula getElementAt(int index) {
        return data.get(index);
    }
    
    public void setData(List<CopiaPelicula> data){
        this.data = data;
//        fire
    }

}
