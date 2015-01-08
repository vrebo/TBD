package org.silo.vista;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.silo.vista.reportes.GeneradorReporteClientes;
import org.silo.vista.reportes.GeneradorReporteCopias;
import org.silo.vista.reportes.GeneradorReporteEmpleados;
import org.silo.vista.reportes.GeneradorReportePeliculas;
import org.silo.vista.reportes.GeneradorReporteVentas;

public class VistaPrincipal extends JFrame {

    //Ventanas de Catálogos
    private MyInternalFrame catalogoCliente;
    private MyInternalFrame catalogoEmpleado;
    private MyInternalFrame catalogoGenero;
    private MyInternalFrame catalogoPelicula;
    private MyInternalFrame catalogoCopia;

    //Ventanas de Reportes
    private MyInternalFrame reporteClientes;
    private MyInternalFrame reporteEmpleados;
    private MyInternalFrame reporteVentas;
    private MyInternalFrame reportePeliculas;
    private MyInternalFrame reporteCopias;

    //Ventanas de Procesos
//    private VentaCatalogo ventaCatalogo;
//    private VentaFrame ventaFrame;
    
    private JDesktopPane jDesktopPane;
    private LoggingForm loggingForm;

    public VistaPrincipal() {
        addComponentes();
    }

    public final void addComponentes() {
        loggingForm = new LoggingForm(this);
        jDesktopPane = new javax.swing.JDesktopPane();

//        ventaCatalogo = new VentaCatalogo();
//        ventaFrame = new VentaFrame();
        
        setJMenuBar(new BarraMenu(this));
        setContentPane(loggingForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Información Lord Ordo");

        String url = "/images/Silo3-ico2.png";
        setIconImage(new ImageIcon(getClass().getResource(url)).getImage());
//        setUndecorated(true);//Verdadero FullScreen HD

        setMinimumSize(new Dimension());
        pack();
        setLocationRelativeTo(null);//Centra el JFrame en la pantalla
    }

    public void initCatalogos() {
        jDesktopPane.add(catalogoCliente
                          = new MyInternalFrame("Catálogo de clientes", new CatalogoCliente()));
        jDesktopPane.add(catalogoEmpleado
                          = new MyInternalFrame("Catálogo de empleados", new CatalogoEmpleado()));
        jDesktopPane.add(catalogoGenero
                          = new MyInternalFrame("Catálogo de géneros", new CatalogoGenero()));
        jDesktopPane.add(catalogoPelicula
                          = new MyInternalFrame("Catálogo de películas", new CatalogoPelicula()));
        jDesktopPane.add(catalogoCopia
                          = new MyInternalFrame("Catálogo de copias", new CatalogoCopia()));
    }

    public void initReportes() {
        jDesktopPane.add(reporteClientes = new MyInternalFrame(
                "Generador de reportes de clientes", new GeneradorReporteClientes()));
        jDesktopPane.add(reporteEmpleados = new MyInternalFrame(
                "Generador de reportes de empleados", new GeneradorReporteEmpleados()));
        jDesktopPane.add(reporteVentas = new MyInternalFrame(
                "Generador de reportes de ventas", new GeneradorReporteVentas()));
        jDesktopPane.add(reportePeliculas = new MyInternalFrame(
                "Generador de reportes de películas", new GeneradorReportePeliculas()));
        jDesktopPane.add(reporteCopias = new MyInternalFrame(
                "Generador de reportes de copias", new GeneradorReporteCopias()));
    }

    public LoggingForm getLoggingForm() {
        return loggingForm;
    }

    public JDesktopPane getjDesktopPane1() {
        return jDesktopPane;
    }

    public MyInternalFrame getClienteCatalogo() {
        return catalogoCliente;
    }

    public MyInternalFrame getEmpleadoCatalogo() {
        return catalogoEmpleado;
    }

    public MyInternalFrame getGeneroCatalogo() {
        return catalogoGenero;
    }

    public MyInternalFrame getPeliculaCatalogo() {
        return catalogoPelicula;
    }

    public MyInternalFrame getCopiaCatalogo() {
        return catalogoCopia;
    }

    public MyInternalFrame getReporteClientes() {
        return reporteClientes;
    }

    public MyInternalFrame getReporteEmpleados() {
        return reporteEmpleados;
    }

    public MyInternalFrame getReporteVentas() {
        return reporteVentas;
    }

    public MyInternalFrame getReportePeliculas() {
        return reportePeliculas;
    }

    public MyInternalFrame getReporteCopias() {
        return reporteCopias;
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new VistaPrincipal().setVisible(true);
        });
    }
}
