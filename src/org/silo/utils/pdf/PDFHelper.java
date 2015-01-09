/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.utils.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.silo.modelos.pdf.ListenerPDF;

/**
 *
 * @author Adri�n
 */
public class PDFHelper {

    private Document doc;
    private String tituloPDF;
    private String ruta;
    private static String rutaLogo = "logo.png";
    private final Font fuenteNormal = FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK);
    private final Font fuenteTabla = FontFactory.getFont("arial", 13, Font.BOLD, BaseColor.BLACK);

    public PDFHelper() {
        doc = null;
    }

    public boolean generaReporteCopiasEstado() {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from copia_pelicula;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Se a�aden los datos de caj�n
                tituloPDF = "REPORTE DE COPIAS DE PELICULAS";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de copias por estado
                addLineaVacia(parrafo, doc);
                addParrafo(parrafo, doc, "Relaci�n de copias por estado");
                addLineaVacia(parrafo, doc);
                addLineaVacia(parrafo, doc);
                PdfPTable tabla = iniciarTablaCopiasEstado(dbh);
                tabla.setWidthPercentage(100);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                addLineaVacia(parrafo, doc);
                //Tabla de copias por formato
                addParrafo(parrafo, doc, "Relaci�n de copias por formato");
                addLineaVacia(parrafo, doc);
                addLineaVacia(parrafo, doc);
                tabla = iniciarTablaCopiasFormato(dbh);
                tabla.setWidthPercentage(100);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                //Tablas con consultas y datos
                String[] titulosColumnas = new String[]{"C�digo", "T�tulo", "Formato", "Fecha Adquisici�n", "Precio"};
                //Tabla de copias en stock
                doc.newPage();
                parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                addParrafo(parrafo, doc, "Copias en Stock");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                String[] columnas = dbh.seleccionarDatosCopiasPeliculas("EN-STOCK");
                dbh.cerrarConexion();
                addTitulosCopiasPelicula(tabla, titulosColumnas);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                //Nueva p�gina
                //Tabla de copias vendidas
                doc.newPage();
                //Datos de caj�n
                parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                addLineaVacia(parrafo, doc);
                addParrafo(parrafo, doc, "Copias vendidas");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                columnas = dbh.seleccionarDatosCopiasPeliculas("VENDIDA");
                dbh.cerrarConexion();
                addTitulosCopiasPelicula(tabla, titulosColumnas);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                //Nueva p�gina
                doc.newPage();
                //Datos de caj�n
                parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de copias da�adas
                addParrafo(parrafo, doc, "Copias da�adas");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                columnas = dbh.seleccionarDatosCopiasPeliculas("DA�ADA");
                dbh.cerrarConexion();
                addTitulosCopiasPelicula(tabla, titulosColumnas);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Registros vac�os.\nNo se puede crear el documento.");
            }
        } catch (DocumentException | IOException | SQLException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReporteEmpleadosMensualEstado(Date fechaInicio, Date fechaFin) {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from empleado;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Se a�aden los datos de caj�n
                tituloPDF = "REPORTE MENSUAL DE EMPLEADOS";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de empleados por estado
                addParrafo(parrafo, doc, "Relaci�n de empleados por estado");
                addLineaVacia(parrafo, doc);
                PdfPTable tabla = iniciarTablaEmpleadosEstado(fuenteNormal, dbh);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                //Tabla de empleados trabajando
                addParrafo(parrafo, doc, "Empleados trabajando");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                String[] columnas = dbh.seleccionarDatosEmpleadoMensualEstado("Laborando", fechaInicio, fechaFin);
                dbh.cerrarConexion();
                addTitulosEmpleado(tabla);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                //Nueva p�gina
                doc.newPage();
                //Datos de caj�n
                parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de empleados incapacitados
                addParrafo(parrafo, doc, "Empleados incapacitados");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                columnas = dbh.seleccionarDatosEmpleadoMensualEstado("Incapacitado", fechaInicio, fechaFin);
                dbh.cerrarConexion();
                addTitulosEmpleado(tabla);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                //Nueva p�gina
                doc.newPage();
                //Datos de caj�n
                parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de empleados despedidos
                addParrafo(parrafo, doc, "Empleados despedidos");
                addLineaVacia(parrafo, doc);
                tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                columnas = dbh.seleccionarDatosEmpleadoMensualEstado("Liquidado", fechaInicio, fechaFin);
                dbh.cerrarConexion();
                addTitulosEmpleado(tabla);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado exitosamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (DocumentException | IOException | SQLException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
        return false;
    }

    public boolean generarReporteGeneralPeliculasInventario() {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from pelicula;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE GENERAL DE PEL�CULAS EN INVENTARIO";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tabla de peliculas por genero
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por g�nero");
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                String[] generos = dbh.select("SELECT genero_nombre\n"
                                              + "  FROM genero\n"
                                              + "  ORDER BY genero_nombre;", false).split("#");
                dbh.cerrarConexion();
                PdfPTable tabla = iniciarTablaPeliculasGenero(dbh, generos);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                //Tabla peliculas por clasificaci�n
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por clasificaci�n");
                addLineaVacia(parrafo, doc);
                String[] clasificaciones = new String[]{"A", "B", "B15", "C", "D"};
                tabla = iniciarTablaPeliculasClasificacion(dbh, clasificaciones);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                //Tabla de peliculas por a�o
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por a�o de estreno");
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                String[] anios = dbh.select("SELECT extract(year from pelicula_anioestreno)\n"
                                            + "  FROM pelicula\n"
                                            + "  ORDER BY pelicula_anioestreno;", false).split("#");
                ArrayList<String> arrayAnio = new ArrayList();
                for (String anio : anios) {
                    if (!arrayAnio.contains(anio)) {
                        arrayAnio.add(anio);
                    }
                }
                dbh.cerrarConexion();
                anios = arrayAnio.toArray(new String[0]);
                tabla = iniciarTablaPeliculasAnioEstreno(dbh, anios);
                doc.add(tabla);
                //Tablas de registros
                dbh.crearConexion();
                ResultSet rs = dbh.seleccionarDatosPeliculasGeneralInventario();
                while (rs.next()) {
                    String[] datos = new String[9];
                    for (int i = 0; i < datos.length; i++) {
                        datos[i] = rs.getString(i + 1);
                    }
                    byte[] bytes = rs.getBytes(10);
                    doc.newPage();
                    parrafo = setDatos(doc, tituloPDF);
                    tabla = iniciarTablaPeliculasGeneral(datos, bytes);
                    parrafo.setSpacingBefore(10);
                    addLineaVacia(parrafo, doc);
                    addLineaVacia(parrafo, doc);
                    addLineaVacia(parrafo, doc);
                    addLineaVacia(parrafo, doc);
                    doc.add(tabla);
                }
                dbh.cerrarConexion();
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado exitosamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (DocumentException | IOException | SQLException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
        return false;
    }

    public boolean generarReportePeliculasInventarioAnio() {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from pelicula;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE DE PEL�CULAS EN INVENTARIO POR A�O DE ESTRENO";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                addLineaVacia(parrafo, doc);
                //Tabla de a�os
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por a�o de estreno");
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                String[] anios = dbh.select("SELECT extract(year from pelicula_anioestreno)\n"
                                            + "  FROM pelicula\n"
                                            + "  ORDER BY pelicula_anioestreno;", false).split("#");
                ArrayList<String> arrayAnio = new ArrayList();
                for (String anio : anios) {
                    if (!arrayAnio.contains(anio)) {
                        arrayAnio.add(anio);
                    }
                }
                dbh.cerrarConexion();
                anios = arrayAnio.toArray(new String[0]);
                String[] totalesAnios = new String[anios.length];
                dbh.crearConexion();
                for (int i = 0; i < anios.length; i++) {
                    totalesAnios[i] = dbh.select("SELECT count(copia_pelicula.copia_id)\n"
                                                 + "FROM copia_pelicula, pelicula\n"
                                                 + "WHERE copia_edo = 'EN-STOCK'\n"
                                                 + "AND pelicula.pelicula_id = copia_pelicula.pelicula_id\n"
                                                 + "AND extract(year from pelicula.pelicula_anioestreno) = " + anios[i] + ";", true);
                }
                dbh.cerrarConexion();
                PdfPTable tabla = iniciarTablaPeliculasAnio(anios, totalesAnios, fuenteNormal);
                addLineaVacia(parrafo, doc);
                doc.add(tabla);
                for (String anio : anios) {
                    dbh.crearConexion();
                    ResultSet rs = dbh.seleccionarDatosPeliculasGeneralInventario(anio);
                    if (rs.next()) {
                        rs.beforeFirst();
                        while (rs.next()) {
                            doc.newPage();
                            parrafo = setDatos(doc, tituloPDF);
                            parrafo.add("Pel�culas de " + anio);
                            doc.add(parrafo);
                            parrafo.clear();
                            addLineaVacia(parrafo, doc);
                            String[] datos = new String[9];
                            for (int j = 0; j < datos.length; j++) {
                                datos[j] = rs.getString(j + 1);
                            }
                            byte[] bytes = rs.getBytes(10);
                            tabla = iniciarTablaPeliculasGeneral(datos, bytes);
                            parrafo.setSpacingBefore(10);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            doc.add(tabla);
                        }
                    } else {
                        doc.newPage();
                        parrafo = setDatos(doc, tituloPDF);
                        parrafo.add("Pel�culas de " + anio);
                        doc.add(parrafo);
                        parrafo.clear();
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        parrafo.add("No existen registros");
                        doc.add(parrafo);
                        parrafo.clear();
                    }
                    dbh.cerrarConexion();
                }
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (SQLException | DocumentException | IOException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReportePeliculasInventarioClasificacion() {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from pelicula;");
            System.out.println(columna);
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE DE PEL�CULAS EN INVENTARIO POR CLASIFICACI�N";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                addLineaVacia(parrafo, doc);
                //Tabla de clasificaciones
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por clasificaci�n");
                addLineaVacia(parrafo, doc);
                String[] clasificaciones = new String[]{"A", "B", "B15", "C", "D"};
                String[] totales = new String[clasificaciones.length];
                dbh.crearConexion();
                for (int i = 0; i < clasificaciones.length; i++) {
                    totales[i] = dbh.select("select count(copia_pelicula.copia_id)\n"
                                            + "from copia_pelicula, pelicula\n"
                                            + "where copia_pelicula.pelicula_id = pelicula.pelicula_id\n"
                                            + "and copia_pelicula.copia_edo = 'EN-STOCK'\n"
                                            + "and pelicula.pelicula_clasif = '" + clasificaciones[i] + "';", true);
                }
                dbh.cerrarConexion();
                PdfPTable tabla = iniciarTablaPeliculasClasificaciones(clasificaciones, totales, fuenteNormal);
                doc.add(tabla);
                for (String clasif : clasificaciones) {
                    dbh.crearConexion();
                    ResultSet rs = dbh.seleccionarDatosPeliculasGeneralInventarioClasificaciones(clasif);
                    if (rs.next()) {
                        rs.beforeFirst();
                        while (rs.next()) {
                            doc.newPage();
                            parrafo = setDatos(doc, tituloPDF);
                            parrafo.add("Clase " + clasif);
                            doc.add(parrafo);
                            parrafo.clear();
                            addLineaVacia(parrafo, doc);
                            String[] datos = new String[9];
                            for (int j = 0; j < datos.length; j++) {
                                datos[j] = rs.getString(j + 1);
                            }
                            byte[] bytes = rs.getBytes(10);
                            tabla = iniciarTablaPeliculasGeneral(datos, bytes);
                            parrafo.setSpacingBefore(10);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            doc.add(tabla);
                        }
                    } else {
                        doc.newPage();
                        parrafo = setDatos(doc, tituloPDF);
                        parrafo.add("Clase " + clasif);
                        doc.add(parrafo);
                        parrafo.clear();
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        addParrafo(parrafo, doc, "No existen registros.");
                    }
                    dbh.cerrarConexion();
                }
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                System.out.println("No existen registros.");
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (IOException | DocumentException | SQLException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReportePeliculasInventarioGenero() {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from pelicula;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE DE PEL�CULAS EN INVENTARIO POR G�NERO";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                addLineaVacia(parrafo, doc);
                //tabla de totales
                addParrafo(parrafo, doc, "Relaci�n de pel�culas por g�nero");
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                String[] generos = dbh.select("SELECT genero.genero_nombre\n"
                                              + "FROM genero\n"
                                              + "ORDER BY genero.genero_nombre;", false).split("#");
                dbh.cerrarConexion();
                String[] totales = new String[generos.length];
                dbh.crearConexion();
                for (int i = 0; i < generos.length; i++) {
                    totales[i] = dbh.select("SELECT count(copia_pelicula.copia_id)\n"
                                            + "FROM genero, pelicula, copia_pelicula\n"
                                            + "WHERE pelicula.pelicula_id = copia_pelicula.pelicula_id\n"
                                            + "AND pelicula.genero_id = genero.genero_id\n"
                                            + "AND genero.genero_nombre = '" + generos[i] + "'\n"
                                            + "AND copia_pelicula.copia_edo = 'EN-STOCK';", true);
                }
                dbh.cerrarConexion();
                PdfPTable tabla = iniciarTablaPeliculasGeneros(generos, totales, fuenteNormal);
                doc.add(tabla);
                //Resto
                for (String genero : generos) {
                    dbh.crearConexion();
                    ResultSet rs = dbh.seleccionarDatosPeliculasGeneralInventarioGenero(genero);
                    if (rs.next()) {
                        rs.beforeFirst();
                        while (rs.next()) {
                            doc.newPage();
                            parrafo = setDatos(doc, tituloPDF);
                            parrafo.add("Pel�culas de  " + genero);
                            doc.add(parrafo);
                            parrafo.clear();
                            addLineaVacia(parrafo, doc);
                            String[] datos = new String[9];
                            for (int j = 0; j < datos.length; j++) {
                                datos[j] = rs.getString(j + 1);
                            }
                            byte[] bytes = rs.getBytes(10);
                            tabla = iniciarTablaPeliculasGeneral(datos, bytes);
                            parrafo.setSpacingBefore(10);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            addLineaVacia(parrafo, doc);
                            doc.add(tabla);
                        }
                    } else {
                        doc.newPage();
                        parrafo = setDatos(doc, tituloPDF);
                        parrafo.add("Pel�culas de " + genero);
                        doc.add(parrafo);
                        parrafo.clear();
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        parrafo.add("No existen registros");
                        doc.add(parrafo);
                        parrafo.clear();
                    }
                    dbh.cerrarConexion();
                }
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (SQLException | IOException | DocumentException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReporteRegistroMensualClientes(Date fechaInicio, Date fechaFin) {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from cliente;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE MENSUAL DE REGISTRO DE CLIENTES";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tablas
                String fecha1 = fechaInicio.getYear() + "/" + fechaInicio.getMonth() + "/" + fechaInicio.getDay();
                String fecha2 = fechaFin.getYear() + "/" + fechaFin.getMonth() + "/" + fechaFin.getDay();
                String rango = "Rango: del " + fechaInicio.getDay() + " de " + fechaInicio.getMonth()
                               + " del " + fechaInicio.getYear() + " al "
                               + fechaFin.getDay() + " de " + fechaFin.getMonth() + " del " + fechaFin.getYear();
                addParrafo(parrafo, doc, rango);
                //Tablas
                dbh.crearConexion();
                System.out.println(fecha1 + "********");
                System.out.println(fecha2 + "********");
                ResultSet rs = dbh.
                        seleccionarDatosRegistroMensualClientes(fechaInicio, fechaFin);
                PdfPTable tabla;
                int cont;
                boolean bandera;
                if (rs.next()) {
                    rs.beforeFirst();
                    cont = 0;
                    bandera = false;
                    while (rs.next()) {
                        if (cont % 3 == 0 && bandera) {
                            doc.newPage();
                            parrafo = setDatos(doc, tituloPDF);
                            parrafoNormal(parrafo);
                            addParrafo(parrafo, doc, rango);
                        }
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        String[] datos = new String[4];
                        for (int j = 0; j < datos.length; j++) {
                            datos[j] = rs.getString(j + 1);
                        }
                        byte[] bytes = rs.getBytes(5);
                        tabla = iniciarTablaMensualClientes(datos, bytes);
                        doc.add(tabla);
                        cont++;
                        bandera = true;
                    }
                } else {
                    addLineaVacia(parrafo, doc);
                    addParrafo(parrafo, doc, "No existen registros.");
                }
                dbh.cerrarConexion();
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros.\nNo se puede crear el documento.");
            }
        } catch (SQLException | IOException | DocumentException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReporteRegistroMensualEmpleados(Date fechaInicio, Date fechaFin) {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from empleado;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE MENSUAL DE REGISTRO DE EMPLEADOS";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                //Tablas
                String fecha1 = fechaInicio.getYear() + "/" + fechaInicio.getMonth() + "/" + fechaInicio.getDay();
                String fecha2 = fechaFin.getYear() + "/" + fechaFin.getMonth() + "/" + fechaFin.getDay();
                String rango = "Rango: del " + fechaInicio.getDay() + " de " + fechaInicio.getMonth()
                               + " del " + fechaInicio.getYear() + " al "
                               + fechaFin.getDay() + " de " + fechaFin.getMonth() + " del " + fechaFin.getYear();
                addParrafo(parrafo, doc, rango);
                //Tablas
                dbh.crearConexion();
                ResultSet rs = dbh.seleccionarDatosRegistroMensualEmpleados(fechaInicio, fechaFin);
                PdfPTable tabla;
                int cont;
                boolean bandera;
                if (rs.next()) {
                    rs.beforeFirst();
                    cont = 0;
                    bandera = false;
                    while (rs.next()) {
                        if (cont % 3 == 0 && bandera) {
                            doc.newPage();
                            parrafo = setDatos(doc, tituloPDF);
                            parrafoNormal(parrafo);
                            addParrafo(parrafo, doc, rango);
                        }
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        addLineaVacia(parrafo, doc);
                        String[] datos = new String[6];
                        for (int j = 0; j < datos.length; j++) {
                            datos[j] = rs.getString(j + 1);
                        }
                        byte[] bytes = rs.getBytes(7);
                        tabla = iniciarTablaMensualEmpleados(datos, bytes);
                        doc.add(tabla);
                        cont++;
                        bandera = true;
                    }
                } else {
                    addLineaVacia(parrafo, doc);
                    addParrafo(parrafo, doc, "No existen registros.");
                }
                dbh.cerrarConexion();
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros");
            }
        } catch (SQLException | IOException | DocumentException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReporteMensualVentas(Date fechaInicio, Date fechaFin) {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from venta;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE MENSUAL DE VENTAS";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                String fecha1 = (fechaInicio.getYear() + 1900) + "/"
                                + (fechaInicio.getMonth() + 1) + "/"
                                + fechaInicio.getDate();
                String fecha2 = (fechaFin.getYear() + 1900)
                                + "/" + (fechaFin.getMonth() + 1)
                                + "/" + fechaFin.getDate();
                String rango = "Rango: del "
                               + fechaInicio.getDay() + " de "
                               + (fechaInicio.getMonth() + 1)
                               + " del " + (fechaInicio.getYear() + 1900)
                               + " al " + fechaFin.getDate() + " de "
                               + (fechaFin.getMonth() + 1) + " del "
                               + (fechaFin.getYear() + 1900);
                addParrafo(parrafo, doc, rango);
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                String resultado = dbh.select("select count(venta.venta_id)\n"
                                              + "from venta\n"
                                              + "where venta.venta_fecha >= '" + fecha1 + "'\n"
                                              + "and venta.venta_fecha <= '" + fecha2 + "'", true);
                dbh.cerrarConexion();
                addParrafo(parrafo, doc, "No. total de ventas: " + resultado);
                addLineaVacia(parrafo, doc);
                dbh.crearConexion();
                resultado = dbh.select("select sum(venta_neto)\n"
                                       + "from venta\n"
                                       + "where venta_fecha >= '" + fecha1 + "'\n"
                                       + "and venta_fecha <= '" + fecha2 + "';", true);
                dbh.cerrarConexion();
                addParrafo(parrafo, doc, "Venta Neta Mensual: $" + resultado);
                addLineaVacia(parrafo, doc);
                //Tablas
                PdfPTable tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                dbh.crearConexion();
                String[] columnas = dbh.seleccionarDatosVentasMensual(fecha1, fecha2);
                dbh.cerrarConexion();
                String[] titulos = new String[]{"Clave de venta", "Fecha",
                                                "Atendi�", "Cliente", "Neto de la venta"};
                addTitulosVentasMensual(tabla, titulos);
                addRegistros(columnas, tabla);
                doc.add(tabla);
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros");
            }
        } catch (SQLException | IOException | DocumentException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean generarReporteMensualVentasEmpleado(Date fechaInicio, Date fechaFin) {
        try {
            DataBaseHelper dbh = new DataBaseHelper();
            //Solo para comprobar si hay registros
            String columna = probarRegistros(dbh, "select * from venta;");
            if (columna.length() > 0) {
                doc = inicializarDocumento();
                doc.open();
                //Datos de caj�n
                tituloPDF = "REPORTE MENSUAL DE VENTAS POR EMPLEADO";
                Paragraph parrafo = setDatos(doc, tituloPDF);
                parrafoNormal(parrafo);
                String fecha1 = fechaInicio.getYear() + "/" + fechaInicio.getMonth() + "/" + fechaInicio.getDay();
                String fecha2 = fechaFin.getYear() + "/" + fechaFin.getMonth() + "/" + fechaFin.getDay();
                String rango = "Rango: del " + fechaInicio.getDay() + " de " + fechaInicio.getMonth()
                               + " del " + fechaInicio.getYear() + " al "
                               + fechaFin.getDay() + " de " + fechaFin.getMonth() + " del " + fechaFin.getYear();
                addParrafo(parrafo, doc, rango);
                addLineaVacia(parrafo, doc);
                //Tabla datos
                String[] titulos = new String[]{" ", "Empleado", "Cantidad de ventas", "Neto vendido"};
                PdfPTable tabla = new PdfPTable(4);
                tabla.setWidthPercentage(100);
                addTitulosVentaMensualEmpleado(tabla, titulos);
                addDatosVentas(tabla, dbh, fecha1, fecha2);
                doc.add(tabla);
                addLineaVacia(parrafo, doc);
                addLineaVacia(parrafo, doc);
                addLineaVacia(parrafo, doc);
                //Tabla datos
                tabla = new PdfPTable(4);
                tabla.setWidthPercentage(100);
                titulos = new String[]{"C�digo empleado", "Nombre", "Cantidad de ventas", "Neto vendido"};
                addTitulosVentaMensualEmpleado(tabla, titulos);
                dbh.crearConexion();
                String[] ids = dbh.select("select empleado_id\n"
                                          + "from empleado\n"
                                          + "order by empleado_id;", false).split("#");
                dbh.cerrarConexion();
                for (String id : ids) {
                    dbh.crearConexion();
                    String[] datos = dbh.selectDatos("select coalesce(empleado.empleado_nombre||' '||empleado.empleado_appater||' '||empleado.empleado_apmater), count(venta.venta_id), sum(venta.venta_neto)\n"
                                                     + "from empleado, venta\n"
                                                     + "where empleado.empleado_id = '" + id + "'\n" + "and venta.empleado_id = empleado.empleado_id\n" + "group by coalesce(empleado.empleado_nombre||' '||empleado.empleado_appater||' '||empleado.empleado_apmater);").split("#");
                    try {
                        addDatos(tabla, id, datos[0], datos[1], datos[2]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        String nombre = dbh.select("select coalesce(empleado.empleado_nombre||' '||empleado.empleado_appater||' '||empleado.empleado_apmater)\n"
                                                   + "from empleado\n"
                                                   + "where empleado.empleado_id = '" + id + "';", true);
                        addDatos(tabla, id, nombre, "0", "0");
                    }
                    dbh.cerrarConexion();
                }
                doc.add(tabla);
                doc.close();
                JOptionPane.showMessageDialog(null, "Documento creado");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No existen registros");
            }
        } catch (SQLException | IOException | DocumentException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    private Document inicializarDocumento() throws DocumentException, FileNotFoundException {
        Document documento = new Document(PageSize.LETTER);
        //iz, der. arriba abajo
        documento.setMargins(100, 100, 50, 100);
        ruta = obtenerRuta();
        PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        ListenerPDF lp = new ListenerPDF();
        writer.setPageEvent(lp);
        return documento;
    }

    private void addLineaVacia(Paragraph parrafo, Document doc) throws DocumentException {
        parrafo.add(new Paragraph(""));
        doc.add(parrafo);
        parrafo.clear();
    }

    private void addParrafo(Paragraph parrafo, Document doc, String texto) {
        try {
            parrafo.add(texto);
            doc.add(parrafo);
            parrafo.clear();
        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void parrafoNormal(Paragraph parrafo) {
        parrafo.setFont(fuenteNormal);
        parrafo.setAlignment(Paragraph.ALIGN_LEFT);
        parrafo.setSpacingBefore(10);
    }

    private PdfPCell nuevaCelda() {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
        cell.setVerticalAlignment(Paragraph.ALIGN_MIDDLE);
        cell.setPadding(3);
        return cell;
    }

    private String probarRegistros(DataBaseHelper dbh, String vista) throws SQLException {
        dbh.crearConexion();
        String columna = dbh.select(vista, false);
        dbh.cerrarConexion();
        return columna;
    }

    public Paragraph setDatos(Document doc, String nombreReporte) throws
            BadElementException, DocumentException, IOException {
        //Cosillas del logo
        Image imagenLogo = Image.getInstance("resources/images/Silo2T.png");
        //ancho alto
        imagenLogo.scaleToFit(120, 150);
        imagenLogo.setAbsolutePosition(100, doc.getPageSize().getHeight() - 100);
        doc.add(imagenLogo);
        //agregar titulo
        Paragraph parrafo = new Paragraph();
        parrafo.setIndentationLeft(130);
        parrafo.setSpacingAfter(1);
        final Font fuenteTitulos = FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK);
        parrafo.setFont(fuenteTitulos);
        parrafo.add("LORD ORDO S.A. DE C.V.");
        doc.add(parrafo);
        parrafo.clear();
        parrafo.setFont(fuenteNormal);
        parrafo.add("Av. Constituyentes #42, Veracruz, ver.");
        doc.add(parrafo);
        parrafo.clear();
        parrafo.add("RFC GORO285849HV3");
        doc.add(parrafo);
        parrafo.clear();
        parrafo.setIndentationLeft(0);
        parrafo.setFont(fuenteTitulos);
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setSpacingBefore(10);
        parrafo.add(new Paragraph(""));
        doc.add(parrafo);
        doc.add(parrafo);
        parrafo.clear();
        parrafo.add(nombreReporte);
        doc.add(parrafo);
        parrafo.clear();
        parrafo.add("-------------------------------------------------------------------------------------------------------");
        doc.add(parrafo);
        parrafo.clear();
        parrafo.add(new Paragraph(""));
        doc.add(parrafo);
        return parrafo;
    }

    private void addTitulosCopiasPelicula(PdfPTable tabla, String[] titulos) {
        for (String titulo : titulos) {
            tabla.addCell(new Paragraph(titulo, fuenteNormal));
        }
    }

    private void addDatos(PdfPTable tabla, String d1, String d2, String d3, String d4) {
        PdfPCell cell = nuevaCelda();
        cell.addElement(new Paragraph(d1, fuenteNormal));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(d2, fuenteNormal));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(d3, fuenteNormal));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(d4, fuenteNormal));
        tabla.addCell(cell);
    }

    private void addTitulosVentasMensual(PdfPTable tabla, String[] titulos) {
        for (String titulo : titulos) {
            tabla.addCell(new Paragraph(titulo, fuenteNormal));
        }
    }

    private void addTitulosVentaMensualEmpleado(PdfPTable tabla, String[] titulos) {
        for (String titulo : titulos) {
            tabla.addCell(new Paragraph(titulo, fuenteTabla));
        }
    }

    private void addDatosVentas(PdfPTable tabla, DataBaseHelper dbh, String fecha1, String fecha2) {
        PdfPCell cell = nuevaCelda();
        cell.addElement(new Paragraph("Empleado con mayor # de ventas", fuenteTabla));
        tabla.addCell(cell);
        dbh.crearConexion();
        String[] resultados = dbh.seleccionarDatosVentasCantidadVentas(fecha1, fecha2).split("&");
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[1], fuenteTabla));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[0], fuenteTabla));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[2], fuenteTabla));
        tabla.addCell(cell);
        resultados = dbh.seleccionarDatosVentasCantidadNeta(fecha1, fecha2).split("&");
        dbh.cerrarConexion();
        cell = nuevaCelda();
        cell.addElement(new Paragraph("Empleado con mayor cantidad vendida", fuenteTabla));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[1], fuenteTabla));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[2], fuenteTabla));
        tabla.addCell(cell);
        cell = nuevaCelda();
        cell.addElement(new Paragraph(resultados[0], fuenteTabla));
        tabla.addCell(cell);
    }

    private void addTitulosEmpleado(PdfPTable tabla) {
        tabla.addCell(new Paragraph("C�digo Empleado", fuenteNormal));
        tabla.addCell(new Paragraph("Nombre", fuenteNormal));
        tabla.addCell(new Paragraph("Horario Trabajo", fuenteNormal));
        tabla.addCell(new Paragraph("Fecha de registro", fuenteNormal));
        tabla.addCell(new Paragraph("Sueldo", fuenteNormal));
    }

    private PdfPTable iniciarTablaCopiasFormato(DataBaseHelper dbh) throws SQLException {
        PdfPTable tabla = new PdfPTable(2);
        String resultado;
        tabla.addCell(new Paragraph("Formato de copia", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de copias", fuenteNormal));
        tabla.addCell(new Paragraph("DVD", fuenteNormal));
        dbh.crearConexion();
        resultado = dbh.select("SELECT count(copia_id)\n"
                               + "  FROM copia_pelicula\n"
                               + "  WHERE copia_fmto = 'DVD';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        tabla.addCell(new Paragraph("Blue-Ray", fuenteNormal));
        resultado = dbh.select("SELECT count(copia_id)\n"
                               + "  FROM copia_pelicula\n"
                               + "  WHERE copia_fmto = 'BLURAY';", true);
        dbh.cerrarConexion();
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        return tabla;
    }

    private PdfPTable iniciarTablaCopiasEstado(DataBaseHelper dbh) throws SQLException {
        PdfPTable tabla = new PdfPTable(2);
        String resultado;
        tabla.addCell(new Paragraph("Estado de copia", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de copias", fuenteNormal));
        tabla.addCell(new Paragraph("En stock", fuenteNormal));
        dbh.crearConexion();
        resultado = dbh.select("SELECT count(copia_id)\n"
                               + "  FROM copia_pelicula\n"
                               + "  WHERE copia_Edo = 'EN-STOCK';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        tabla.addCell(new Paragraph("Vendidas", fuenteNormal));
        resultado = dbh.select("SELECT count(copia_id)\n"
                               + "  FROM copia_pelicula\n"
                               + "  WHERE copia_Edo = 'VENDIDA';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        tabla.addCell(new Paragraph("Da�adas", fuenteNormal));
        resultado = dbh.select("SELECT count(copia_id)\n"
                               + "  FROM copia_pelicula\n"
                               + "  WHERE copia_Edo = 'DA�ADA';", true);
        dbh.cerrarConexion();
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        return tabla;
    }

    private PdfPTable iniciarTablaEmpleadosEstado(final Font fuenteNormal, DataBaseHelper dbh) throws SQLException {
        String resultado;
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.addCell(new Paragraph("Estado de empleado", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de empleados", fuenteNormal));
        tabla.addCell(new Paragraph("Trabajando", fuenteNormal));
        dbh.crearConexion();
        resultado = dbh.select("SELECT count(empleado_id)\n"
                               + "  FROM empleado\n"
                               + "  WHERE empleado_edo = 'Laborando';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        tabla.addCell(new Paragraph("Incapacitados", fuenteNormal));
        resultado = dbh.select("SELECT count(empleado_id)\n"
                               + "  FROM empleado\n"
                               + "  WHERE empleado_edo = 'Incapacitado';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        tabla.addCell(new Paragraph("Despedidos", fuenteNormal));
        resultado = dbh.select("SELECT count(empleado_id)\n"
                               + "  FROM empleado\n"
                               + "  WHERE empleado_edo = 'Liquidado';", true);
        tabla.addCell(new Paragraph(resultado, fuenteNormal));
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasGenero(DataBaseHelper dbh, String[] generos) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        String resultado;
        tabla.addCell(new Paragraph("G�nero", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        dbh.crearConexion();
        for (String genero : generos) {
            try {
                tabla.addCell(new Paragraph(genero, fuenteNormal));
                resultado = dbh.select("SELECT count(copia_id)\n"
                                       + "  FROM copia_pelicula, pelicula, genero\n"
                                       + "  WHERE copia_pelicula.pelicula_id = pelicula.pelicula_id\n"
                                       + "  AND pelicula.genero_id = genero.genero_id\n"
                                       + "AND copia_pelicula.copia_edo = 'EN-STOCK'"
                                       + "  AND genero_nombre = '" + genero + "';", true);
                tabla.addCell(new Paragraph(resultado, fuenteNormal));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        dbh.cerrarConexion();
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasClasificacion(DataBaseHelper dbh, String[] clasificaciones) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        String resultado;
        tabla.addCell(new Paragraph("Clasificaci�n", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        dbh.crearConexion();
        for (String clasificacione : clasificaciones) {
            try {
                tabla.addCell(new Paragraph(clasificacione, fuenteNormal));
                resultado = dbh.select("SELECT count(copia_id)\n"
                                       + "FROM copia_pelicula, pelicula\n"
                                       + "WHERE copia_pelicula.pelicula_id = pelicula.pelicula_id\n"
                                       + "AND copia_pelicula.copia_edo = 'EN-STOCK'"
                                       + "AND pelicula.pelicula_clasif = '" + clasificacione + "';", true);
                tabla.addCell(new Paragraph(resultado, fuenteNormal));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        dbh.cerrarConexion();
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasAnioEstreno(DataBaseHelper dbh, String[] anios) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        String resultado;
        tabla.addCell(new Paragraph("A�o de estreno", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        dbh.crearConexion();
        for (String anio : anios) {
            try {
                tabla.addCell(new Paragraph(anio, fuenteNormal));
                resultado = dbh.select("SELECT count(copia_id)\n"
                                       + "  FROM copia_pelicula, pelicula\n"
                                       + "  WHERE copia_pelicula.pelicula_id = pelicula.pelicula_id\n"
                                       + "AND copia_pelicula.copia_edo = 'EN-STOCK'"
                                       + "  AND extract(year from pelicula.pelicula_anioestreno) = " + anio + ";", true);
                tabla.addCell(new Paragraph(resultado, fuenteNormal));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        dbh.cerrarConexion();
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasGeneral(String[] datos, byte[] bytes) {
        try {
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            StringBuilder sb = new StringBuilder();
            String[] titulos = new String[]{"ID pel�cula: ", "T�tulo: ", "A�o de estreno: ",
                                            "Estelares: ", "Director: ", "Clasificaci�n: ", "G�nero: ", "Duraci�n: ", "Copias disponibles: "};
            for (int i = 0; i < datos.length; i++) {
                sb.append(titulos[i]).append(datos[i]).append("\n\n\n");
            }
            tabla.addCell(new Paragraph(sb.toString(), fuenteTabla));
            Image img = Image.getInstance(bytes);
            img.scaleToFit(300, 300);
            PdfPCell cell = new PdfPCell(img, true);
            cell.setVerticalAlignment(Image.ALIGN_MIDDLE);
            tabla.addCell(cell);
            return tabla;
        } catch (BadElementException | IOException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
        return null;
    }

    private PdfPTable iniciarTablaMensualClientes(String[] datos, byte[] bytes) {
        try {
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            StringBuilder sb = new StringBuilder();
            String[] titulos = new String[]{"C�digo cliente: ", "Nombre: ", "Edad: ", "Fecha de registro: "};
            for (int i = 0; i < datos.length; i++) {
                sb.append(titulos[i]).append(datos[i]).append("\n");
            }
            tabla.addCell(new Paragraph(sb.toString(), fuenteTabla));
            Image img = Image.getInstance(bytes);
            img.scaleToFit(100, 100);
            PdfPCell cell = new PdfPCell(img, false);
            cell.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Image.ALIGN_CENTER);
            tabla.addCell(cell);
            return tabla;
        } catch (BadElementException | IOException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
        return null;
    }

    private PdfPTable iniciarTablaMensualEmpleados(String[] datos, byte[] bytes) {
        try {
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            StringBuilder sb = new StringBuilder();
            String[] titulos = new String[]{"C�digo empleado: ", "Nombre: ", "Edad: ",
                                            "Fecha de registro: ", "Horario de trabajo: ", "Sueldo: "};
            for (int i = 0; i < datos.length; i++) {
                sb.append(titulos[i]).append(datos[i]).append("\n");
            }
            tabla.addCell(new Paragraph(sb.toString(), fuenteTabla));
            Image img = Image.getInstance(bytes);
            img.scaleToFit(100, 100);
            PdfPCell cell = new PdfPCell(img, false);
            cell.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Image.ALIGN_CENTER);
            tabla.addCell(cell);
            return tabla;
        } catch (BadElementException | IOException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
        return null;
    }

    private PdfPTable iniciarTablaPeliculasAnio(String[] anios, String[] totales, Font fuenteNormal) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.addCell(new Paragraph("A�o de estreno", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        for (int i = 0; i < anios.length; i++) {
            tabla.addCell(new Paragraph(anios[i], fuenteNormal));
            tabla.addCell(new Paragraph(totales[i], fuenteNormal));
        }
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasClasificaciones(String[] clasificaiones, String[] totales, Font fuenteNormal) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.addCell(new Paragraph("Clasificaci�n", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        for (int i = 0; i < clasificaiones.length; i++) {
            tabla.addCell(new Paragraph(clasificaiones[i], fuenteNormal));
            tabla.addCell(new Paragraph(totales[i], fuenteNormal));
        }
        return tabla;
    }

    private PdfPTable iniciarTablaPeliculasGeneros(String[] generos, String[] totales, Font fuenteNormal) {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.addCell(new Paragraph("Genero", fuenteNormal));
        tabla.addCell(new Paragraph("Cantidad de pel�culas", fuenteNormal));
        for (int i = 0; i < generos.length; i++) {
            tabla.addCell(new Paragraph(generos[i], fuenteNormal));
            tabla.addCell(new Paragraph(totales[i], fuenteNormal));
        }
        return tabla;
    }

    private void addRegistros(String[] columnas, PdfPTable tabla) {
        if (columnas.length > 0) {
            String[][] matriz = splitea(columnas);
            for (int i = 0; i < columnas.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    tabla.addCell(new Paragraph(matriz[i][j], fuenteNormal));
                }
            }
        }
    }

    private String[][] splitea(String[] columnas) {
        String[][] matriz = new String[columnas.length][columnas[0].split("[&]+").length];
        for (int i = 0; i < columnas.length; i++) {
            matriz[i] = columnas[i].trim().split("[&]+");
        }
        return matriz;
    }

    private String obtenerRuta() {
//        String url;
//        JFileChooser chooser = new JFileChooser();
//        chooser.setCurrentDirectory(new File("C:\\Users\\Adri�n\\Desktop"));
//        chooser.showSaveDialog(chooser);
//        url = chooser.getSelectedFile().getAbsolutePath();
//        return url + ".pdf";
        return "temp/" + new Date().getTime() + ".pdf";
    }

    /**
     * @return the doc
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * @return the tituloPDF
     */
    public String getTituloPDF() {
        return tituloPDF;
    }

    public String getRuta() {
        return ruta;
    }

}
