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

    private List<CopiaPelicula> data;

    public CopiaListModel() {
        data = new java.util.ArrayList<>();
    }

    public CopiaListModel(List<CopiaPelicula> data) {
        this.data = data;
    }

    @Override
    public int getSize() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public CopiaPelicula getElementAt(int index) {
        return data.get(index);
    }

    public void setData(List<CopiaPelicula> data) {
        this.data = data;
        fireContentsChanged(this, 0, data.size());
    }

    public void addElements(List<CopiaPelicula> data) {
        data.stream().forEach((data1) -> {
            addElement(data1);
        });
    }

    public void addElement(CopiaPelicula e) {
        data.add(e);
        fireContentsChanged(this, 0, data.size());
    }
    
    public void remove(CopiaPelicula e){
        data.remove(e);
        fireContentsChanged(this, 0, data.size());
    }

}
