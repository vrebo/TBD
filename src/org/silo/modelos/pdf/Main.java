/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.silo.modelos.pdf;

/**
 *
 * @author Adrián
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Panel p = new Panel();
        Listener l = new Listener(p);
        p.addListener(l);
        Frame f = new Frame(p);
        f.iniciarFrame();
        
    }
    
}
