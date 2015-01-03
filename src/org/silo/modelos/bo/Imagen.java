
package org.silo.modelos.bo;

import java.io.File;
import javax.swing.ImageIcon;

public class Imagen {
    
    private File archivo;
    private ImageIcon imagen;

    public Imagen(File archivo, ImageIcon imagen) {
        this.archivo = archivo;
        this.imagen = imagen;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

}
