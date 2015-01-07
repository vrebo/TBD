/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.silo.modelos.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


/**
 *
 * @author Adrián
 */
public class ListenerPDF extends PdfPageEventHelper {
    
//    private PDFHelper pdf;
    
    public ListenerPDF () {
        
    }
    
//    @Override
//    public void onStartPage(PdfWriter writer, Document document) {
//        try {
//            pdf.setDatos(document, pdf.getTituloPDF());
//        } catch (DocumentException | IOException ex) {
//            System.out.println(ex + "\n" + ex.getMessage());
//        }
//    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        System.out.println("Holi");
    }
    
}
