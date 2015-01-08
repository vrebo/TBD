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
public class GeneradorReporteVentas extends GeneradorReporte{
    
    public GeneradorReporteVentas(){
        super(new PanelVentas(),"resources/pdf/reporte-ventas-mensual.pdf");
    }
    
}
