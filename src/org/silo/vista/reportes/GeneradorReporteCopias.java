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
public class GeneradorReporteCopias extends GeneradorReporte{
    
    public GeneradorReporteCopias() {
        super(new PanelCopias(), "resources/pdf/reporte-copias-por-estado.pdf");
    }
}
