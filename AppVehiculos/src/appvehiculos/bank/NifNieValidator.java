package appvehiculos.bank;

import POJOS.Contribuyente;
import appvehiculos.data.ExcelManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Jose Antonio Lopez Perez
 *
 * Esta clase es la encargada de validar los diferentes Nif o Nie
 *
 * El funcionamiento es usar el metodo checkNifNie con el valor del trabajador
 * y la clase devuelve los siguientes strings:
 *      - "correct" el input tiene un valor de nif o nie correcto
 *      - "broken" el input tiene una estructura valida pero el digito de control no es correcto
 *      - "NotValid" el input no tiene una estructura correcta
 *
 * La clase tambien incluye una funcion getFixedNifNie que arregla los valores si estan clasificados como broken
 * calculando el nif nie correcto y lo devuelve
 */
public class NifNieValidator {

    /*
    Retorna false si no tiene un formato valido/reparable y por tanto se debe generar un xml.
    */
    public static boolean check(ArrayList<String> nifsList,String text, ExcelManager excelVehiculos, int row, int nifnieColumn, Contribuyente cont) {
        boolean valid = true;
        if(!Character.isDigit(text.charAt(0)) && text.charAt(0)!='X'&& text.charAt(0)!='Y'&& text.charAt(0)!='Z'){
            return false;
        }
        if(text.length() == 9){
            //check number positions
            for (int i = 1; i < 8; i++) {
                if(!Character.isDigit(text.charAt(i))){
                    return false;
                }
            }
            //check has control letter
            if(!Character.isLetter(text.charAt(8))){
                return false;
            }
        }else if(text.length() == 8){
            for (int i = 1; i < 8; i++) {
                if(!Character.isDigit(text.charAt(i))){
                    return false;
                }
            }
        }else{
            return false;
        }
        //Formato valido
        if(Character.isDigit(text.charAt(0))){
            return checkNif( nifsList,text, excelVehiculos, row, nifnieColumn, cont);
        }else{
            return checkNie( nifsList,text, excelVehiculos, row, nifnieColumn, cont);
        }
    }
    public static String getFixedNifNie_NoExcelChange_NoCheckFormat(String text){
        if(Character.isDigit(text.charAt(0))){
            return getFixedNif_NoExcelChange_NoCheckFormat( text);
        }else{
            return getFixedNie_NoExcelChange_NoCheckFormat( text);
        }
    }
    private static String getFixedNif_NoExcelChange_NoCheckFormat(String text) {
        String digits = text.substring(0,8);
        char controlLetter = getControlLetter(digits);
        if(text.length() == 9){
            if(text.charAt(8) != controlLetter){
                return digits+controlLetter;
            }
            return text;
        }else{
            return digits+controlLetter;
        }

    }
    private static String getFixedNie_NoExcelChange_NoCheckFormat(String text) {
        String NIEchars = "XYZ";
        String digits = NIEchars.indexOf(text.charAt(0)) + text.substring(1,8);
        if(text.length() == 9){
            if(text.charAt(8) != getControlLetter(digits)){

                char controlLetter = getControlLetter(digits);
                return NIEchars.charAt(digits.charAt(0)) + digits.substring(1)+ controlLetter;

            }
            return text;
        }else{//falta digito de control
            char controlLetter = getControlLetter(digits);
            return NIEchars.charAt(digits.charAt(0)) + digits.substring(1)+ controlLetter;
        }

    }
    private static boolean checkNie(ArrayList<String> nifsList,String text, ExcelManager excelVehiculos, int row, int nifnieColumn, Contribuyente cont) {
        String NIEchars = "XYZ";
        String digits = NIEchars.indexOf(text.charAt(0)) + text.substring(1,8);
        if(text.length() == 9){
            if(text.charAt(8) != getControlLetter(digits)){
                //Fix nie sin avisar.
                return fixNie(nifsList,digits, excelVehiculos, row, nifnieColumn, cont);
            }
        }else{//falta digito de control
            return fixNie(nifsList,digits, excelVehiculos, row, nifnieColumn, cont);
        }

        return true;

    }
    private static boolean fixNie(ArrayList<String> nifsList,String digits, ExcelManager excelVehiculos, int row, int nifnieColumn, Contribuyente cont) {
        char controlLetter = getControlLetter(digits);
        String NIEchars = "XYZ";
        String correctedNie = NIEchars.charAt(digits.charAt(0)) + digits.substring(1)+ controlLetter;
        try{
            if(nifsList.contains(correctedNie)){
                return false;
            }
            excelVehiculos.setCellValue(0, row, nifnieColumn, correctedNie);
            cont.setNifnie(correctedNie);

        }catch (Exception e){
            System.out.println("No se ha podido arreglar el nie de la fila-columna "+row+"-"+nifnieColumn);
            return false;
        }
        return true;
    }

    private static boolean checkNif(ArrayList<String> nifsList,String text, ExcelManager excelVehiculos, int row, int nifnieColumn, Contribuyente cont) {

        String digits = text.substring(0,8);
        char controlLetter = getControlLetter(digits);

        if(text.length() == 9){
            if(text.charAt(8) != controlLetter){
                return fixNif(nifsList,digits, excelVehiculos, row, nifnieColumn, cont);
            }
        }else{//falta digito de control
            return fixNif(nifsList,digits, excelVehiculos, row, nifnieColumn, cont);
        }

        return true;

    }
    private static boolean fixNif(ArrayList<String> nifsList,String digits, ExcelManager excelVehiculos, int row, int nifnieColumn, Contribuyente cont) {
        char controlLetter = getControlLetter(digits);
        //TODO actualizar en el excel el nuevo nif
        
        String correctedNif = digits+controlLetter;
        try{
            if(nifsList.contains(correctedNif)){
                return false;
            }
            excelVehiculos.setCellValue(0, row, nifnieColumn, correctedNif);
            cont.setNifnie(correctedNif);
        }catch (Exception e){
            System.out.println("No se ha podido arreglar el nif de la fila-columna "+row+"-"+nifnieColumn);
            return false;
        }
        return true;
    }

    private static char getControlLetter(String digits){
        String controlString = "TRWAGMYFPDXBNJZSQVHLCKE";
        int intValue = Integer.valueOf(digits);
        int letterPos = intValue%23;
        char controlLetter = controlString.charAt(letterPos);
        return controlLetter;
    }

    /**
     Metodos dedicados a la generación del XML con el formato específico de nif y nie
     */
    public static boolean saveXML(String xmlData) throws IOException{
        try (FileWriter writer = new FileWriter("resources/ErroresNifNie.xml", false)) {
            
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Contribuyentes>\n");
            writer.write(xmlData);
            writer.write("</Contribuyentes>\n");
        }catch(Exception e){
            System.out.println("No se ha podido guardar el xml de errores NifNie");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getXML(Contribuyente cont, String ErrorType) throws IOException{
        StringBuilder sb = new StringBuilder();

        sb.append("  <Contribuyente id=\"").append(cont.getIdContribuyente()).append("\">\n");
        String closeXmlNif = (cont.getNifnie().equals(ExcelManager.cellType.EMPTYCELL.toString())) ? " />\n" : ">"+cont.getNifnie() + "</NIF_NIE>\n";
        sb.append("    <NIF_NIE").append(closeXmlNif);
        sb.append("    <Nombre>").append(cont.getNombre()).append("</Nombre>\n");
        sb.append("    <PrimerApellido>").append(cont.getApellido1()).append("</PrimerApellido>\n");
        sb.append("    <SegundoApellido>").append(cont.getApellido2()).append("</SegundoApellido>\n");
        sb.append("    <TipoDeError>").append(ErrorType).append("</TipoDeError>\n");
        sb.append("  </Contribuyente>\n");

        return sb.toString();
    }
}
