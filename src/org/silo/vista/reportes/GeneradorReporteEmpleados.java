package org.silo.vista.reportes;

public class GeneradorReporteEmpleados extends GeneradorReporte {

    public GeneradorReporteEmpleados() {
        super(new PanelEmpleados(), "resources/pdf/reporte-registro-empleados-mensual.pdf");
    }
}
