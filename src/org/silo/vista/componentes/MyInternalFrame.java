package org.silo.vista.componentes;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JInternalFrame;

public class MyInternalFrame extends JInternalFrame {

    public MyInternalFrame(String titulo){
        super(titulo, true, true, true, true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    public MyInternalFrame(String titulo, Component component){
        this(titulo);
        add(component);
        pack();
    }
    
    @Override
    public void show() {
        Dimension deskopSize = getDesktopPane().getSize();
        Dimension catalogoSize = getSize();
        int x = deskopSize.width / 2 - catalogoSize.width / 2;
        int y = deskopSize.height / 2 - catalogoSize.height / 2;
        setLocation(x, y);
        super.show();
    }
}
