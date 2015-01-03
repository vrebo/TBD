/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.silo.vista.componentes;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
 
/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView {
    ImageIcon jpgIcon = Utils.createImageIcon("/images/filetype-jpg-icon.png");
    ImageIcon gifIcon = Utils.createImageIcon("/images/Image-GIF-icon.png");
    ImageIcon tiffIcon = Utils.createImageIcon("/images/Image-TIFF-icon.png");
    ImageIcon pngIcon = Utils.createImageIcon("/images/Image-PNG-icon.png");
 
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
        String extension = Utils.getExtension(f);
        String type = null;
 
        if (extension != null) {
            switch (extension) {
                case Utils.jpeg:
                case Utils.jpg:
                    type = "JPEG Image";
                    break;
                case Utils.gif:
                    type = "GIF Image";
                    break;
                case Utils.tiff:
                case Utils.tif:
                    type = "TIFF Image";
                    break;
                case Utils.png:
                    type = "PNG Image";
                    break;
            }
        }
        return type;
    }
 
    @Override
    public Icon getIcon(File f) {
        String extension = Utils.getExtension(f);
        Icon icon = null;
 
        if (extension != null) {
            switch (extension) {
                case Utils.jpeg:
                case Utils.jpg:
                    icon = jpgIcon;
                    break;
                case Utils.gif:
                    icon = gifIcon;
                    break;
                case Utils.tiff:
                case Utils.tif:
                    icon = tiffIcon;
                    break;
                case Utils.png:
                    icon = pngIcon;
                    break;
            }
        }
        return icon;
    }
}