package appvehiculos.recibos;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

public class ResumenPDF {
    public static void generarResumen(int numeroRecibos, float totalBaseImponible, String periodo) {
        try{
            String fileName = "54285240L_resumen.pdf";
            PdfWriter writer = new PdfWriter("resources/recibos/"+ fileName);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.LETTER);


            Table tabla = new Table(1);
            tabla.setWidth(500);
            tabla.setBorder(new SolidBorder(1));
            tabla.setMarginTop(4);
            tabla.setMarginLeft(14);

            Cell cell1 = new Cell();
            cell1.setBorder(Border.NO_BORDER);
            cell1.setWidth(500);
            cell1.setTextAlignment(TextAlignment.LEFT);
            cell1.add(new Paragraph("RESUMEN IVTM  Ejercicio "+periodo+"."));
            tabla.addCell(cell1);

            Cell cell2 = new Cell();
            cell2.setBorder(Border.NO_BORDER);
            cell2.setWidth(500);
            cell2.setTextAlignment(TextAlignment.LEFT);
            cell2.add(new Paragraph("TOTAL BASE IMPONIBLE.................. "+ totalBaseImponible + " EUROS."));
            tabla.addCell(cell2);

            Cell cell3 = new Cell();
            cell3.setBorder(Border.NO_BORDER);
            cell3.setWidth(500);
            cell3.setTextAlignment(TextAlignment.LEFT);
            cell3.add(new Paragraph("NUMERO TOTAL DE RECIBOS................ " + numeroRecibos +" RECIBOS."));
            tabla.addCell(cell3);

            doc.add(tabla);

            doc.close();
        }catch( Exception e){
            System.out.println("No se ha podido generar el resumen en pdf de los recibos.");
            e.printStackTrace();
        }
    }
}
