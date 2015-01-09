package org.silo.vista.componentes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.silo.controlador.Accion;

public class BarraMenu extends JMenuBar {

    private JMenu jMenuSesion;
    private JMenu jMenuCatalogos;
    private JMenu jMenuProcesos;
    private JMenu jMenuReportes;
    private JMenu jMenuUtilerias;

    private final JMenuItem[] itemsMenuSesion = {
        //        new JMenuItem("Full Screen"),
        new JMenuItem("Salir")
    };

    private final JMenuItem[] itemsMenuCatalogo = {
        new JMenuItem("Clientes"),
        new JMenuItem("Empleados"),
        new JMenuItem("Películas"),
        new JMenuItem("Copias de películas"),
        new JMenuItem("Genero")
//        new JMenuItem("Venta")
    };

    private final JMenuItem[] itemsMenuProcesos = {
        new JMenuItem("Registro de venta")        
    };    

    private final JMenuItem[] itemsMenuReporte = {
        new JMenuItem("Ventas"),
        new JMenuItem("Clientes"),
        new JMenuItem("Empleados"),
        new JMenuItem("Copias película"),
        new JMenuItem("Películas")
//        new JMenuItem("Generos")
    };
    
    private final JMenuItem[] itemsMenuUtileria = {
        new JMenuItem("Respaldo"),
        new JMenuItem("Restauración"),
        new JMenuItem("Bitacoras")
//        new JMenuItem("Alta de usuarios en SILO")
    };

    private final String[] accionesItemsSesion = {
        //        "FullScreen",
        "CierraSesion"
    };

    private final String[] accionesItemsCatalogo = {
        "MuestraCatClientes",
        "MuestraCatEmpleados",
        "MuestraCatPeliculas",
        "MuestraCatCopias",
        "MuestraCatGeneros",
        "MuestraCatVentas"
    };
    
    private final String[] accionesItemsProceso = {
        "MuestraVentaFrame",
        "MuestraCambioFrame"
    };
    
    private final String[] accionesItemsReporte = {
        "MuestraReporteVentas",
        "MuestraReporteClientes",
        "MuestraReporteEmpleados",
        "MuestraReporteCopias",
        "MuestraReportePeliculas"
//        "ReporteGenero"
    };
    
        private final String[] accionesItemsUtileria = {
        "MuestraRespaldo",
        "MuestraRestauracion",
        "MuestraBitacoras",
        "MuestraAltaUsuarios",
        "MuestraReporteCopias",
        "MuestraReportePeliculas"
//        "ReporteGenero"
    };

    public BarraMenu(Container componente) {
        System.out.println("Barra de menú creada");
        addComponentes();
        addEventos(componente);
    }

    private void addComponentes() {
        jMenuSesion = new JMenu("Sesión");
        jMenuCatalogos = new JMenu("Catálogos");
        jMenuProcesos = new JMenu("Procesos");
        jMenuReportes = new JMenu("Reportes");
        jMenuUtilerias = new JMenu("Utilerías");
        
        add(jMenuSesion);
        add(jMenuCatalogos);
        add(jMenuProcesos);
        add(jMenuReportes);
        add(jMenuUtilerias);
        setVisible(false);
        
        //Agregación de los items al menú Sesión
        addComponentes(jMenuSesion, itemsMenuSesion, accionesItemsSesion);
        
        //Agregación de los items al menú Catálogo        
        addComponentes(jMenuCatalogos, itemsMenuCatalogo, accionesItemsCatalogo);
        
        //Agregación de los items al menú Procesos
        addComponentes(jMenuProcesos, itemsMenuProcesos, accionesItemsProceso);
               
        //Agregación de los items al menú Reportes
        addComponentes(jMenuReportes, itemsMenuReporte, accionesItemsReporte);

        addComponentes(jMenuUtilerias, itemsMenuUtileria, accionesItemsUtileria);
    }
    
    private void addComponentes(JMenu menu, JMenuItem[] items, String[] acciones){
        for (int i = 0; i < items.length; i++) {
            menu.add(items[i]);
            items[i].setName(acciones[i]);
        }
    }

    private void addEventos(Container contenedor) {
        System.out.println("Eventos agregados a items de la barra de menú");
        addEventos(itemsMenuCatalogo, contenedor);
        addEventos(itemsMenuSesion, contenedor);        
        addEventos(itemsMenuProcesos, contenedor);
        addEventos(itemsMenuReporte, contenedor);
        addEventos(itemsMenuUtileria, contenedor);
    }
    
    private void addEventos(JMenuItem[] items, Container contenedor){
        for (JMenuItem jMenuItem : items) {
            jMenuItem.addActionListener((ActionEvent e) -> {
                String tipo = ((Container) e.getSource()).getName();
                System.out.println(tipo);
                Accion accion = Accion.getAccion(tipo);
                accion.ejecutar(contenedor);
            });
        }
    }

}
