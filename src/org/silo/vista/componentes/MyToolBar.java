package org.silo.vista.componentes;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.silo.utils.ImageUtils;

public class MyToolBar extends JToolBar{
    
    protected JButton makeNavigationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = "/images/icons/"
                + imageName;

        ImageIcon icon = ImageUtils.createImageIcon(imgLocation);
        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
//        button.addActionListener(this);

        if (icon != null) {                      //image found
            button.setIcon(icon);
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }
}
