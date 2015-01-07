package org.silo.vista;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import org.silo.vista.catalogos.CatalogoCliente;
import org.silo.vista.catalogos.CatalogoCopia;
import org.silo.vista.catalogos.CatalogoEmpleado;
import org.silo.vista.catalogos.CatalogoGenero;
import org.silo.vista.catalogos.CatalogoPelicula;
import org.silo.vista.componentes.BarraMenu;
import org.silo.vista.componentes.MyInternalFrame;
import org.silo.vista.login.LoggingForm;

public class VistaPrincipal extends JFrame {

    //Ventanas de Catálogos
    private MyInternalFrame clienteCatalogo;
    private MyInternalFrame empleadoCatalogo;
    private MyInternalFrame generoCatalogo;
    private MyInternalFrame peliculaCatalogo;
    private MyInternalFrame copiaCatalogo;

    //Ventanas de Procesos
//    private VentaCatalogo ventaCatalogo;
//    private VentaFrame ventaFrame;
//    private AltaClienteFrame altaClienteFrame;
//    private AltaEmpleadoFrame altaEmpleadoFrame;
//    private AltaPeliculaFrame altaPeliculaFrame;
//    private AltaGeneroFrame altaGeneroFrame;
    private JDesktopPane jDesktopPane1;
    private LoggingForm loggingForm;

    public VistaPrincipal() {
        addComponentes();
        System.out.println("vista principal creada");
    }

    public final void addComponentes() {
        loggingForm = new LoggingForm(this);
        jDesktopPane1 = new javax.swing.JDesktopPane();

//        ventaCatalogo = new VentaCatalogo();
//        ventaFrame = new VentaFrame();
//        altaClienteFrame = new AltaClienteFrame();
//        altaEmpleadoFrame = new AltaEmpleadoFrame();
//        altaPeliculaFrame = new AltaPeliculaFrame();
//        altaGeneroFrame = new AltaGeneroFrame();
        setJMenuBar(new BarraMenu(this));
        setContentPane(loggingForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Información Lord Ordo");

        String url = "/images/Silo2T.png";
        setIconImage(new ImageIcon(getClass().getResource(url)).getImage());
//        setUndecorated(true);//Verdadero FullScreen HD
//        jDesktopPane1.add(ventaCatalogo);
//        jDesktopPane1.add(ventaFrame);
//        jDesktopPane1.add(altaClienteFrame);
//        jDesktopPane1.add(altaEmpleadoFrame);
//        jDesktopPane1.add(altaPeliculaFrame);
//        jDesktopPane1.add(altaGeneroFrame);

        setMinimumSize(new Dimension());
        pack();
        setLocationRelativeTo(null);//Centra el JFrame en la pantalla
    }

    public void initCatalogos() {
        jDesktopPane1.add(
                clienteCatalogo = new MyInternalFrame("Catálogo de clientes", new CatalogoCliente()));
        jDesktopPane1.add(
                empleadoCatalogo = new MyInternalFrame("Catálogo de empleados", new CatalogoEmpleado()));
        jDesktopPane1.add(
                generoCatalogo = new MyInternalFrame("Catálogo de géneros", new CatalogoGenero()));
        jDesktopPane1.add(
                peliculaCatalogo = new MyInternalFrame("Catálogo de películas", new CatalogoPelicula()));
        jDesktopPane1.add(
                copiaCatalogo = new MyInternalFrame("Catálogo de copias", new CatalogoCopia()));
    }

    public LoggingForm getLoggingForm() {
        return loggingForm;
    }

    public JDesktopPane getjDesktopPane1() {
        return jDesktopPane1;
    }

    public MyInternalFrame getClienteCatalogo() {
        return clienteCatalogo;
    }

    public MyInternalFrame getEmpleadoCatalogo() {
        return empleadoCatalogo;
    }

    public MyInternalFrame getGeneroCatalogo() {
        return generoCatalogo;
    }

    public MyInternalFrame getPeliculaCatalogo() {
        return peliculaCatalogo;
    }

    public MyInternalFrame getCopiaCatalogo() {
        return copiaCatalogo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info);
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//        }
        java.awt.EventQueue.invokeLater(() -> {
            new VistaPrincipal().setVisible(true);
        });
    }
}
