/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silo.vista.reportes;

/**
 *
 * @author VREBO
 */
public class GeneradorReportePeliculas extends GeneradorReporte {

    public GeneradorReportePeliculas() {
        super(new PanelPeliculas(), "resources/pdf/reporte-peliculas-en-inventario-por-anio-estreno.pdf");
    }
}
