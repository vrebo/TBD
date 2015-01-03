package org.silo.vista.catalogos.forms;


public interface Form<E> {
        
    E getData();
    
    void setData(E data);
    
    void updateData();
    
    void cleanData();
}
