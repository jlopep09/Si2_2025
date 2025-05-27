package appvehiculos.recibos;

import POJOS.Contribuyente;
import POJOS.Ordenanza;
import POJOS.Vehiculos;
import static appvehiculos.recibos.CalculadoraImporte.getUnidadValorString;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReciboPDF {
        private static ArrayList<Ordenanza> getOrdenanzas(Vehiculos vehiculo,ArrayList<Ordenanza> ordenanzas) {
        ArrayList<Ordenanza> listadoOrdenanzasResultado = new ArrayList<>();
        for (int i = 0; i < ordenanzas.size(); i++) {
            if(ordenanzas.get(i).getAyuntamiento().equals(vehiculo.getContribuyente().getAyuntamiento()) && vehiculo.getTipo().equals(ordenanzas.get(i).getTipoVehiculo())){
                listadoOrdenanzasResultado.add(ordenanzas.get(i));
            }
        }
        if(listadoOrdenanzasResultado.isEmpty()){throw new RuntimeException("Se esta intentado calcular el recibo de un vehiculo pero no se encontraron ordenanzas asociadas.");}
        return listadoOrdenanzasResultado;
    }
    public  static void generarReciboPDF(Vehiculos vehiculo, float importes[], int contadorRecibo, String periodo, ArrayList<Ordenanza> listadoOrdenanzas){
        ArrayList<Ordenanza> ordenanzasAsociadas = getOrdenanzas(vehiculo, listadoOrdenanzas);
        Contribuyente cont =  vehiculo.getContribuyente();
        String dniDestinatario =cont.getNifnie();
        String NombreSinEspacios =  cont.getNombre()+ cont.getApellido1()+cont.getApellido2();
        String fileName = dniDestinatario+NombreSinEspacios+vehiculo.getMatricula()+periodo+".pdf";
        try{
        PdfWriter writer = new PdfWriter("resources/recibos/"+ fileName);

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.LETTER);
        generarDatosAyuntamiento(doc, cont.getAyuntamiento() ,  vehiculo,  cont, contadorRecibo);
        generarDatosDestinatario(doc, cont);
        generarDatosVehiculo(doc, vehiculo);
        generarTituloRecibo(doc, importes, periodo, vehiculo);
        generarTablaConceptos(doc, vehiculo, importes, cont);
        generarResumenTabla(doc, importes);

        doc.close();
        }catch(Exception e){
            System.out.println("Se ha producido un error al intentar generar el PDF del recibo "+fileName);
            e.printStackTrace();
        }

    }

    private static void generarDatosAyuntamiento(Document doc, String ayuntamiento,Vehiculos vehiculo, Contribuyente cont, int contadorRecibo) throws FileNotFoundException {
        String cifAyuntamiento = "P24001017F";
        String direccion = "Calle de la Iglesia, 13";
        String CodigoPostal = "24280 " +  ayuntamiento + " León"; 
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaHoy.format(formato);

        Table tabla1 = new Table(2);
        tabla1.setWidth(500);

        Paragraph nom = new Paragraph(ayuntamiento);
        Paragraph cif = new Paragraph(cifAyuntamiento);
        Paragraph dir1 = new Paragraph(direccion);
        Paragraph dir2 = new Paragraph(CodigoPostal);
        nom.setMarginTop(4);
        Cell cell1 = new Cell();
        cell1.setBorder(new SolidBorder(1));
        cell1.setWidth(250);
        cell1.setTextAlignment(TextAlignment.CENTER);

        cell1.add(nom);
        cell1.add(cif);
        cell1.add(dir1);
        cell1.add(dir2);
        tabla1.addCell(cell1);

        Cell cell2 = new Cell();
        cell2.setBorder(Border.NO_BORDER);
        cell2.setPadding(10);
        cell2.setTextAlignment(TextAlignment.RIGHT);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        cell2.add(new Paragraph("Número recibo: "+ contadorRecibo));
        cell2.add(new Paragraph("IBAN: "+cont.getIban()));
        cell2.add(new Paragraph("Fecha recibo: "+(fechaFormateada)));
        cell2.add(new Paragraph("Fecha matriculación: "+ sdf.format(vehiculo.getFechaMatriculacion())));
        cell2.add(new Paragraph("Fecha de alta: "+sdf.format(vehiculo.getFechaAlta())));
        
        tabla1.addCell(cell2);
        doc.add(tabla1);
    }

    private static void generarDatosDestinatario(Document doc, Contribuyente cont) throws MalformedURLException, FileNotFoundException, IOException {

        Table tabla2 = new Table(2);
        tabla2.setWidth(500);
        String imagen = "resources/logo.jpg";
        Image img = new Image(ImageDataFactory.create(imagen));
        img.setBorder(Border.NO_BORDER);
        img.setPadding(10);

        img.setAutoScale(true);
        Cell cell3 = new Cell();
        cell3.add(img);
        cell3.setBorder(Border.NO_BORDER);
        cell3.setPaddingLeft(23);
        cell3.setPaddingTop(20);
        cell3.setWidth(250);
        cell3.setHeight(100);

        Paragraph nomDest = new Paragraph(cont.getNombre()+" "+ cont.getApellido1()+" "+cont.getApellido2() );
        Paragraph cifDest = new Paragraph("DNI: "+ cont.getNifnie());
        Paragraph dir1Dest = new Paragraph(cont.getDireccion()+" "+cont.getNumero());
        Paragraph dir2Dest = new Paragraph(cont.getAyuntamiento());

        Cell cell4 = new Cell();
        cell4.setBorder(new SolidBorder(1));
        cell4.setWidth(250);
        cell4.setPaddingTop(10);
        cell4.setTextAlignment(TextAlignment.RIGHT);
        PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLDOBLIQUE);

        cell4.add(new Paragraph("Destinatario:").setTextAlignment(TextAlignment.LEFT).setPaddingLeft(10f).setFont(boldFont));
        cell4.add(nomDest);
        cell4.add(cifDest);
        cell4.add(dir1Dest);
        cell4.add(dir2Dest);

        tabla2.addCell(cell3);
        tabla2.addCell(cell4);
        doc.add(tabla2);
    }

    private static void generarDatosVehiculo(Document doc, Vehiculos vehiculo) throws FileNotFoundException {

        Table tabla3 = new Table(3);
        tabla3.setWidth(500);
        tabla3.setBorder(new SolidBorder(1));
        tabla3.setMarginTop(20);

        Paragraph tipo = new Paragraph("Tipo: "+vehiculo.getTipo());
        Paragraph marcaModelo = new Paragraph(vehiculo.getMarca() + " " + vehiculo.getModelo());
        Paragraph matricula = new Paragraph("Matricula: "+vehiculo.getMatricula());

        Cell cell5 = new Cell();
        cell5.setBorder(Border.NO_BORDER);
        cell5.setWidth(150);
        cell5.setTextAlignment(TextAlignment.CENTER);
        cell5.add(tipo);

        Cell cell6 = new Cell();
        cell6.setBorder(Border.NO_BORDER);
        cell6.setWidth(150);
        cell6.setTextAlignment(TextAlignment.CENTER);
        cell6.add(marcaModelo);

        Cell cell7 = new Cell();
        cell7.setBorder(Border.NO_BORDER);
        cell7.setWidth(200);
        cell7.setTextAlignment(TextAlignment.CENTER);
        cell7.add(matricula);

        tabla3.addCell(cell5);
        tabla3.addCell(cell6);
        tabla3.addCell(cell7);
        doc.add(tabla3);
        // Bastidor
        Table tabla1 = new Table(1);
        tabla1.setWidth(500);
        tabla1.setBorder(new SolidBorder(1));
        tabla1.setMarginTop(1);

        Paragraph bastidor = new Paragraph("Bastidor: "+vehiculo.getNumeroBastidor());

        Cell cell1 = new Cell();
        cell1.setBorder(Border.NO_BORDER);
        cell1.setWidth(500);
        cell1.setTextAlignment(TextAlignment.CENTER);
        cell1.add(bastidor);

        tabla1.addCell(cell1);
        doc.add(tabla1);
    }

    private static void generarTituloRecibo(Document doc,  float importes[], String periodo, Vehiculos vehiculo) throws IOException {
        Table tabla4 = new Table(1);
        tabla4.setWidth(500);
        tabla4.setBorder(Border.NO_BORDER);
        tabla4.setMarginTop(20);
        PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLDOBLIQUE);

        Paragraph titulo = new Paragraph("Recibo vehiculo:  Ejercicio "+ periodo +". Numero de trimestres: "+ (int)importes[2]);
        Cell cell8 = new Cell();
        cell8.setBorder(Border.NO_BORDER);
        cell8.setWidth(500);
        cell8.setTextAlignment(TextAlignment.CENTER);
        cell8.add(titulo.setFont(boldFont));

        tabla4.addCell(cell8);
        doc.add(tabla4);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean deBaja = (vehiculo.getFechaBaja()!= null || vehiculo.getFechaBajaTemporal() != null);
        if(deBaja){
                Table tabla1 = new Table(1);
                tabla1.setWidth(500);
                tabla1.setBorder(new SolidBorder(ColorConstants.BLACK, 2));
                tabla1.setMarginTop(1);
                String texto = ( vehiculo.getFechaBajaTemporal() != null)? ("Vehiculo en baja provisional con fecha: " + sdf.format(vehiculo.getFechaBajaTemporal())):("Vehiculo baja definitiva con fecha: "+sdf.format(vehiculo.getFechaBaja()));
                Paragraph baja = new Paragraph(texto);
                Cell cell1 = new Cell();
                cell1.setBorder(Border.NO_BORDER);
                cell1.setWidth(500);
                cell1.setTextAlignment(TextAlignment.CENTER);
                cell1.add(baja);

                tabla1.addCell(cell1);
                doc.add(tabla1);
        }

    }

    private static void generarTablaConceptos(Document doc, Vehiculos vehiculo, float importes[], Contribuyente cont) {
        float bonificacion = vehiculo.getContribuyente().getBonificacion().floatValue();
        int columnas = (bonificacion > 0f)? 7 : 6;
        Table tabla5 = new Table(columnas);
        tabla5.setWidth(500);
        tabla5.setBorder(Border.NO_BORDER);
        tabla5.setMarginTop(20);


        Paragraph titulos[]= new Paragraph[columnas];
        titulos[0] = new Paragraph("Tipo");
        titulos[1] = new Paragraph("Marca");
        titulos[2] = new Paragraph("Modelo");
        titulos[3] = new Paragraph("Unidad");
        titulos[4] = new Paragraph("Valor unidad");
        titulos[5] = new Paragraph("Importe");
        if(columnas == 7){
            titulos[6] = new Paragraph("Descuento");
        }

        //Creamos las celdas de la fila superior
        for (int i = 0; i < columnas; i++) {
            Cell cellTemp = new Cell();
            cellTemp.setBorder(Border.NO_BORDER);
            cellTemp.setBorderTop(new SolidBorder(2));
            cellTemp.setBorderBottom(new SolidBorder(2));
            cellTemp.setWidth(70);
            cellTemp.setTextAlignment(TextAlignment.CENTER);
            cellTemp.add(titulos[i].setFontSize(10));
            tabla5.addCell(cellTemp);
        }
        //Creamos fila de valores
        Cell cell1 = new Cell();
        cell1.setBorder(Border.NO_BORDER);
        cell1.setWidth(70);
        cell1.setTextAlignment(TextAlignment.CENTER);
        cell1.add(new Paragraph(vehiculo.getTipo()).setFontSize(8));
        cell1.setPadding(4);
        tabla5.addCell(cell1);
        
        Cell cell2 = new Cell();
        cell2.setBorder(Border.NO_BORDER);
        cell2.setWidth(70);
        cell2.setTextAlignment(TextAlignment.CENTER);
        cell2.add(new Paragraph(vehiculo.getMarca()).setFontSize(8));
        cell2.setPadding(4);
        tabla5.addCell(cell2);
        
        Cell cell3 = new Cell();
        cell3.setBorder(Border.NO_BORDER);
        cell3.setWidth(70);
        cell3.setTextAlignment(TextAlignment.CENTER);
        cell3.add(new Paragraph(vehiculo.getModelo()).setFontSize(8));
        cell3.setPadding(4);
        tabla5.addCell(cell3);
        String unidadValor[] = getUnidadValorString(vehiculo);
        Cell cell4 = new Cell();
        cell4.setBorder(Border.NO_BORDER);
        cell4.setWidth(70);
        cell4.setTextAlignment(TextAlignment.CENTER);
        cell4.add(new Paragraph(unidadValor[0]).setFontSize(8));
        cell4.setPadding(4);
        tabla5.addCell(cell4);
        
        Cell cell5 = new Cell();
        cell5.setBorder(Border.NO_BORDER);
        cell5.setWidth(70);
        cell5.setTextAlignment(TextAlignment.CENTER);
        cell5.add(new Paragraph(unidadValor[1]).setFontSize(8));
        cell5.setPadding(4);
        tabla5.addCell(cell5);
        
        Cell cell6 = new Cell();
        cell6.setBorder(Border.NO_BORDER);
        cell6.setWidth(70);
        cell6.setTextAlignment(TextAlignment.CENTER);
        cell6.add(new Paragraph(importes[0]+"").setFontSize(8));
        cell6.setPadding(4);
        tabla5.addCell(cell6);
        
        if(columnas == 7){
            Cell cell7 = new Cell();
            cell7.setBorder(Border.NO_BORDER);
            cell7.setWidth(70);
            cell7.setTextAlignment(TextAlignment.CENTER);
            cell7.add(new Paragraph(cont.getBonificacion()+"%").setFontSize(8));
            cell7.setPadding(4);
            tabla5.addCell(cell7);
        }

        //Creamos linea de celdas vacias pero con borde inferior
        for (int i = 0; i < columnas; i++) {
            Cell cellTemp = new Cell();
            cellTemp.setBorder(Border.NO_BORDER);
            
            cellTemp.setWidth(70);
            cellTemp.setTextAlignment(TextAlignment.CENTER);
            cellTemp.add(new Paragraph(" "));
            cellTemp.setPadding(4);
            tabla5.addCell(cellTemp);
        }
        doc.add(tabla5);
    }

    private static void generarResumenTabla(Document doc, float importes[]) {
        Table tabla6 = new Table(2);
        tabla6.setWidth(500);
        tabla6.setBorder(Border.NO_BORDER);
        tabla6.setMarginTop(0);
        Cell celdaTabla6_1 = new Cell();
        Cell celdaTabla6_2 = new Cell();

        celdaTabla6_1.setBorder(Border.NO_BORDER);
        celdaTabla6_1.setWidth(250);
        celdaTabla6_1.setTextAlignment(TextAlignment.LEFT);
        celdaTabla6_1.setPaddingBottom(30);
        celdaTabla6_1.add(new Paragraph("TOTAL BASE IMPONIBLE......................................").setFontSize(8));
        tabla6.addCell(celdaTabla6_1);
        
        celdaTabla6_2.setBorder(Border.NO_BORDER);
        celdaTabla6_2.setWidth(250);
        celdaTabla6_2.setTextAlignment(TextAlignment.RIGHT);
        celdaTabla6_2.add(new Paragraph(importes[1]+"").setFontSize(8));
        celdaTabla6_2.setPaddingBottom(30);
        tabla6.addCell(celdaTabla6_2);

        Cell celdaTabla6_4 = new Cell();
        Cell t6c5 = new Cell();

        celdaTabla6_4.setBorder(Border.NO_BORDER);
        celdaTabla6_4.setWidth(250);
        celdaTabla6_4.setTextAlignment(TextAlignment.LEFT);
        celdaTabla6_4.add(new Paragraph("TOTAL RECIBO...........................................................").setFontSize(8));
        celdaTabla6_4.setBorderTop(new SolidBorder(2));
        tabla6.addCell(celdaTabla6_4);

        t6c5.setBorder(Border.NO_BORDER);
        t6c5.setWidth(250);
        t6c5.setTextAlignment(TextAlignment.RIGHT);
        t6c5.add(new Paragraph(importes[1]+"").setFontSize(8));
        t6c5.setBorderTop(new SolidBorder(2));
        tabla6.addCell(t6c5);

        doc.add(tabla6);
    }

}
