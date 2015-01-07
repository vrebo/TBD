/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.componentes;

import org.silo.utils.ImageUtils;
import java.io.File;
import javax.swing.filechooser.*;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = ImageUtils.getExtension(f);
        if (extension != null) {
            return extension.equals(ImageUtils.tiff)
                    || extension.equals(ImageUtils.tif)
                    || extension.equals(ImageUtils.gif)
                    || extension.equals(ImageUtils.jpeg)
                    || extension.equals(ImageUtils.jpg)
                    || extension.equals(ImageUtils.png);
        }
        return false;
    }

    //The description of this filter
    @Override
    public String getDescription() {
        return "Solo imágenes";
    }
}
