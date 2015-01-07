/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.silo.vista.componentes;

import org.silo.utils.ImageUtils;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
 
/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView {
    ImageIcon jpgIcon = ImageUtils.createImageIcon("/images/filetype-jpg-icon.png");
    ImageIcon gifIcon = ImageUtils.createImageIcon("/images/Image-GIF-icon.png");
    ImageIcon tiffIcon = ImageUtils.createImageIcon("/images/Image-TIFF-icon.png");
    ImageIcon pngIcon = ImageUtils.createImageIcon("/images/Image-PNG-icon.png");
 
    @Override
    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    @Override
    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    @Override
    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    @Override
    public String getTypeDescription(File f) {
        String extension = ImageUtils.getExtension(f);
        String type = null;
 
        if (extension != null) {
            switch (extension) {
                case ImageUtils.jpeg:
                case ImageUtils.jpg:
                    type = "JPEG Image";
                    break;
                case ImageUtils.gif:
                    type = "GIF Image";
                    break;
                case ImageUtils.tiff:
                case ImageUtils.tif:
                    type = "TIFF Image";
                    break;
                case ImageUtils.png:
                    type = "PNG Image";
                    break;
            }
        }
        return type;
    }
 
    @Override
    public Icon getIcon(File f) {
        String extension = ImageUtils.getExtension(f);
        Icon icon = null;
 
        if (extension != null) {
            switch (extension) {
                case ImageUtils.jpeg:
                case ImageUtils.jpg:
                    icon = jpgIcon;
                    break;
                case ImageUtils.gif:
                    icon = gifIcon;
                    break;
                case ImageUtils.tiff:
                case ImageUtils.tif:
                    icon = tiffIcon;
                    break;
                case ImageUtils.png:
                    icon = pngIcon;
                    break;
            }
        }
        return icon;
    }
}