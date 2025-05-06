package appvehiculos.recibos;
import POJOS.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GeneradorRecibo {
    private CalculadoraImportes calculadora;


    public GeneradorRecibo(){
        calculadora = new CalculadoraImportes();
    }

    public void generarRecibos(String periodo) throws IOException {

        //Objetenemos un listado de columnas de los usuarios que no tienen errores de nif ni banco
        ArrayList<String> listadoValidos = BankController.getValidUserRows();
        float contadorBaseImponible = 0;
        float contadorIva = 0;
        int contadorRecibos = numeroPrimerRecibo + 1;
        ArrayList<Ordenanza> listaOrdenanzas = calculadora.listaOrdenanzasDDBB;
        //Obtener los valores de bImponible e iva y los suma al contador global
        for (int i = 1; i < ExcelManager.getRowsCount(userSheetNumber); i++) {
            if(!listadoValidos.contains(i+"")) continue;
            if(!isRowInPeriod(periodo, i)) continue;
            String cellValue = ExcelManager.getCellValue(userSheetNumber, i, 3);
            if (cellValue.equals("EMPTYROW") ) continue;
            String[] idImportes = ExcelManager.getCellValue(userSheetNumber,i,16).split(" ");

            float baseImp = 0;
            for (int j = 0; j < idImportes.length; j++) {
                baseImp+= calculadora.getImporte(Integer.parseInt(idImportes[j]),i);
            }
            float ivaCalculado = 0;

            for (int j = 0; j < idImportes.length; j++) {
                ivaCalculado+= calculadora.getIva(Integer.parseInt(idImportes[j]),i);
            }
            try{
                if(ExcelManager.getCellValue(userSheetNumber, i, columnaExcencion).equals(exencion)){
                    baseImp = 0f;
                    ivaCalculado = 0f;
                }
            }catch(Exception e){e.printStackTrace();}
            contadorBaseImponible +=baseImp;
            contadorIva += ivaCalculado;
            //Obtener valores necesarios para la generacion del recibo xml
            String excencion = ExcelManager.getCellValue(userSheetNumber,i,10);
            String idFilaExcel = (i+1)+"";
            String nombre = ExcelManager.getCellValue(userSheetNumber,i,0);
            String primerApellido = ExcelManager.getCellValue(userSheetNumber,i,1);
            String segundoApellido = ExcelManager.getCellValue(userSheetNumber,i,2);
            String NIF = ExcelManager.getCellValue(userSheetNumber,i,3);
            String IBAN = ExcelManager.getCellValue(userSheetNumber,i,8);
            String lecturaActual = ExcelManager.getCellValue(userSheetNumber,i,13);
            String lecturaAnterior = ExcelManager.getCellValue(userSheetNumber,i,12);
            float metros = Float.parseFloat(ExcelManager.getCellValue(userSheetNumber,i, 13)) - Float.parseFloat(ExcelManager.getCellValue(0,i, 12));
            String consumo = metros+"";
            String baseImponibleRecibo = baseImp+"";
            String ivaRecibo = ivaCalculado+"";
            String totalRecibo = (baseImp+ivaCalculado)+"";
            //Genero la entrada en el xml
            generarXMLRecibo(((contadorRecibos)+""), excencion, idFilaExcel,nombre,primerApellido, segundoApellido,NIF,IBAN,lecturaActual,lecturaAnterior,consumo,baseImponibleRecibo,ivaRecibo,totalRecibo, contadorBaseImponible+"", contadorIva+"", (contadorBaseImponible+contadorIva)+"",periodo);
            generarReciboPDF( i,  periodo,  calculadora,  Float.parseFloat(baseImponibleRecibo),  Float.parseFloat(ivaRecibo));
            contadorRecibos++;
            Date fechaAltaDate;
            Date fechaBajaDate;
            try{
                 //System.out.println(getDateInDateFormat(convertExcelDateToString(ExcelManager.getCellValue(userSheetNumber, i , columnaFechaAlta))));
                 fechaAltaDate = Date.valueOf(getDateInDateFormat(convertExcelDateToString(ExcelManager.getCellValue(userSheetNumber, i , columnaFechaAlta))));
            }catch(Exception e){
                e.printStackTrace();
                fechaAltaDate = new Date(1999,1,1);
            }
            try{
                fechaBajaDate = Date.valueOf(getDateInDateFormat(convertExcelDateToString(ExcelManager.getCellValue(userSheetNumber, i , columnaFechaBaja))));
            }catch(Exception e){
                
                fechaBajaDate = null;
            }
            Date fechaPadron;
            try{
                fechaPadron = Date.valueOf(getDateInDateFormat(calculaFechasPeriodo(periodo)[1]));
            }catch(Exception e){
                e.printStackTrace();
                fechaPadron = new Date(1999,1,1);
            }
            Contribuyente cc = new Contribuyente((int)i, nombre, primerApellido, segundoApellido, NIF, ExcelManager.getCellValue(userSheetNumber, i , columnaDireccion), ExcelManager.getCellValue(userSheetNumber, i , columnaNumero), ExcelManager.getCellValue(userSheetNumber, i , columnaPaisCCC),ExcelManager.getCellValue(userSheetNumber, i , columnaCCC), IBAN, ExcelManager.getCellValue(userSheetNumber, i , columnaEmail), ExcelManager.getCellValue(userSheetNumber, i , columnaExcencion).charAt(0), Double.parseDouble(ExcelManager.getCellValue(userSheetNumber, i , columnaBonificacion)), fechaAltaDate,fechaBajaDate, null, null, null  );
            ManagerDDBB.updateContribuyente(cc);
            Lecturas lectura = new Lecturas(0,cc,periodo.substring(3), periodo.substring(0,2),(int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber,i, columnaLecturaAnterior)),(int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber,i, columnaLecturaActual)));//1T 2024
            ManagerDDBB.updateLecturas(lectura);
            Recibos recibo = new Recibos(contadorRecibos-1, cc, cc.getNifnie(),  cc.getDireccion(),  cc.getNombre(),  cc.getApellido1()+" "+cc.getApellido2(),   Date.valueOf(LocalDate.now()),  (int)Float.parseFloat(lecturaAnterior) , (int)Float.parseFloat(lecturaActual) ,  (int)Float.parseFloat(consumo),  fechaPadron,  Double.parseDouble(baseImponibleRecibo) ,  Double.parseDouble(ivaRecibo),  Double.parseDouble(totalRecibo),  IBAN, cc.getEemail(),  excencion,  null);//1T 2024
            Recibos reciboTrasGuardado = ManagerDDBB.updateRecibos(recibo);

            for (int j = 0; j < idImportes.length; j++) {
                for (int k = 0; k < listaOrdenanzas.size(); k++) {
                    if(listaOrdenanzas.get(k).getIdOrdenanza()==(int)Float.parseFloat(idImportes[j])){
                        RelContribuyenteOrdenanza rco = new RelContribuyenteOrdenanza(0, cc, listaOrdenanzas.get(k));
                        ManagerDDBB.updateRelContribuyenteOrdenanza(rco);
                        break;
                    }
                }
            }
            ArrayList<Lineasrecibo> listadoLineasRecibo = ReciboPDF.generarLineasRecibo(i, calculadora, reciboTrasGuardado);
            for (int j = 0; j < listadoLineasRecibo.size(); j++) {
                ManagerDDBB.updateLineasRecibo(listadoLineasRecibo.get(j));
            }

        }
        cerrarXMLRecibo(contadorBaseImponible+"", contadorIva+"", (contadorBaseImponible+contadorIva)+"",periodo);
        ResumenPDF.generarResumen(contadorBaseImponible, contadorIva, periodo);
        ConfigVariables.numeroPrimerRecibo = contadorRecibos-1;
    }
    private String getDateInDateFormat(String fecha){//  10/12/2023
        if(fecha.isEmpty())return "";
        return (fecha.substring(6)+"-"+fecha.substring(3,5)+"-"+fecha.substring(0,2));
    }
    /*
    * METODOS PARA LA GENERACIÓN DEL XML DE RECIBOS
    * */
    private void generarXMLRecibo(String idContador, String Excencion, String idFilaExcel, String nombre, String apellido1, String apellido2, String nif, String iban, String lecturaActual, String lecturaAnterior, String consumo, String baseImponible, String ivaRecibo, String totalRecibo, String TotalBaseImponible, String TotalIva, String totalImporte, String periodo) throws IOException {
        String nombreXML = "/recibos"+periodo.substring(0,2)+"_"+periodo.substring(3)+".xml";
        boolean existeArchivo = new File(ruteXML_RECIBOS+nombreXML).exists();
        try (FileWriter writer = new FileWriter(ruteXML_RECIBOS+nombreXML, true)) {
            if (!existeArchivo) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.write("<Recibos fechaPadron=\"" + periodo.substring(0,2)+" de "+periodo.substring(3)+ "\" totalBaseImponible=\"" +TotalBaseImponible+"\" totalIva=\""+TotalIva+"\" totalRecibos=\""+totalImporte+"\">\n");
            }
            writer.write("  <Recibo idRecibo=\"" + idContador + "\">\n");
            writer.write("    <Exencion>" + Excencion + "</Exencion>\n");
            writer.write("    <idFilaExcel>" + idFilaExcel + "</idFilaExcel>\n");
            writer.write("    <nombre>" + nombre + "</nombre>\n");
            writer.write("    <primerApellido>" + apellido1 + "</primerApellido>\n");
            writer.write("    <segundoApellido>" + apellido2 + "</segundoApellido>\n");
            writer.write("    <NIF>" + nif + "</NIF>\n");
            writer.write("    <IBAN>" + iban + "</IBAN>\n");
            writer.write("    <lecturaActual>" + lecturaActual + "</lecturaActual>\n");
            writer.write("    <lecturaAnterior>" + lecturaAnterior + "</lecturaAnterior>\n");
            writer.write("    <consumo>" + consumo + "</consumo>\n");
            writer.write("    <baseImponibleRecibo>" + baseImponible + "</baseImponibleRecibo>\n");
            writer.write("    <ivaRecibo>" + ivaRecibo + "</ivaRecibo>\n");
            writer.write("    <totalRecibo>" + totalRecibo + "</totalRecibo>\n");
            writer.write("  </Recibo>\n");
        }
    }
    private void cerrarXMLRecibo(String TotalBaseImponible, String TotalIva, String totalImporte, String periodo) throws IOException {
        // Leer todas las líneas del archivo
        String nombreXML = "/recibos"+periodo.substring(0,2)+"_"+periodo.substring(3)+".xml";
        List<String> lines = Files.readAllLines(Paths.get(ruteXML_RECIBOS+nombreXML));

        // Modificar la segunda línea si el archivo contiene al menos dos líneas
        if (lines.size() > 1) {
            String segundaLineaOriginal = lines.get(1);
            // Ejemplo de cómo sería la nueva segunda línea. Modificar según sea necesario.
            String nuevaSegundaLinea = "<Recibos fechaPadron=\"" + periodo.substring(0,2)+" de "+periodo.substring(3)+ "\" totalBaseImponible=\"" +TotalBaseImponible+"\" totalIva=\""+TotalIva+"\" totalRecibos=\""+totalImporte+"\">";
            lines.set(1, nuevaSegundaLinea);
        }

        // Añadir la línea de cierre del XML
        lines.add("</Recibos>");

        // Escribir todas las líneas de nuevo al archivo
        Files.write(Paths.get(ruteXML_RECIBOS+nombreXML), lines);
    }
    private static String convertExcelDateToString(String excelDateStr, DateTimeFormatter formatter) {
        double excelDate = Double.parseDouble(excelDateStr);
        LocalDate date = LocalDate.of(1899, 12, 30).plusDays((long) excelDate);
        return date.format(formatter);
    }
    private static String convertExcelDateToString(String excelDateStr) {
        if(excelDateStr.isEmpty()) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double excelDate = Double.parseDouble(excelDateStr);
        LocalDate date = LocalDate.of(1899, 12, 30).plusDays((long) excelDate);
        return date.format(formatter);
    }

    private boolean isRowInPeriod(String periodo, int rowNum) throws FileNotFoundException {

        String fechaAlta = ExcelManager.getCellValue(userSheetNumber, rowNum, columnaFechaAlta);
        String fechaBaja = ExcelManager.getCellValue(userSheetNumber, rowNum, columnaFechaBaja);
        // Define el formato del parseo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(fechaBaja.equals("EMPTYROW")||fechaBaja.isEmpty()){
            fechaBaja = "01/01/2099";
        }else{
            fechaBaja = convertExcelDateToString(fechaBaja, formatter);
        }
        if(fechaAlta.equals("EMPTYROW")||fechaAlta.isEmpty()){
            fechaAlta = "01/01/1800";
        }else{
            fechaAlta = convertExcelDateToString(fechaAlta, formatter);
        }
        // Parseo de las fechas
        LocalDate dateAlta = LocalDate.parse(fechaAlta, formatter);
        LocalDate dateBaja = LocalDate.parse(fechaBaja, formatter);


        String fechaInicioTrimestre = calculaFechasPeriodo(periodo)[0];
        String fechaFinTrimestre =  calculaFechasPeriodo(periodo)[1];

        LocalDate dateInicioTrimestre = LocalDate.parse(fechaInicioTrimestre, formatter);
        LocalDate dateFinTrimestre = LocalDate.parse(fechaFinTrimestre, formatter);
        if(dateBaja.isAfter(dateInicioTrimestre)&&dateAlta.isBefore(dateFinTrimestre)){
            return true;
        }
        return false;
    }
    private String[] calculaFechasPeriodo(String periodo){
        String[] result = new String[2];
        int numeroTrimestre = Integer.parseInt(periodo.substring(0,1));
        String year = periodo.substring(3);
        String fechaInicioTrimestre = "";
        String fechaFinTrimestre = "";
        switch (numeroTrimestre){
            case 1:
                fechaInicioTrimestre = "01/01/"+year;
                fechaFinTrimestre = "31/03/"+year;
                break;
            case 2:
                fechaInicioTrimestre = "01/04/"+year;
                fechaFinTrimestre = "30/06/"+year;
                break;
            case 3:
                fechaInicioTrimestre = "01/07/"+year;
                fechaFinTrimestre = "30/09/"+year;
                break;
            case 4:
                fechaInicioTrimestre = "01/10/"+year;
                fechaFinTrimestre = "31/12/"+year;
                break;
            default:
                throw new RuntimeException("Numero de trimestre no valido");
        }
        result[0] = fechaInicioTrimestre;
        result[1] = fechaFinTrimestre;
        return result;
    }
}
