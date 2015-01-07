/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.componentes;

import org.silo.utils.ImageUtils;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author VREBO
 */
public class JXDatePicker extends org.jdesktop.swingx.JXDatePicker{
    
    public JXDatePicker(){
        editButtonIcon();
    }
    
    private void editButtonIcon(){
        JButton boton = (JButton) getComponent(1);
        ImageIcon icon = new ImageIcon(
                ImageUtils.createImageIcon("/images/icons/common_calendar_month_outline_stroke.png").getImage().getScaledInstance(-1, 20, Image.SCALE_DEFAULT));
        boton.setIcon(icon);
    }
}
