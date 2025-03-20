package appvehiculos;

import POJOS.Contribuyente;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    public static boolean check(String text){
        boolean valid = true;
        //Check if is not valid
        if(text.length() == 9){
            //check number positions
            for (int i = 1; i < 8; i++) {
                if(!Character.isDigit(text.charAt(i))){
                    valid = false;
                }
            }
            //check has control letter
            if(!Character.isLetter(text.charAt(8))){
                valid = false;
            }
        }else{
            //TODO ¿Que pasa con los casos en elos que llegan los digitos correctos sin letra?
            valid = false;
        }
        if(valid){
            if(Character.isDigit(text.charAt(0))){
                return checkNif(text);
            }else{
                return checkNie(text);
            }
        }else{
            return false;
        }
    }
    
    private static boolean checkNie(String text) {
        if(text.charAt(0) != 'X' && text.charAt(0) != 'Y' && text.charAt(0) != 'Z'){
            return false;
        }
        String NIEchars = "XYZ";
        String digits = NIEchars.indexOf(text.charAt(0)) + text.substring(1,8);

        if(text.charAt(8) != getControlLetter(digits)){
            //Fix nie sin avisar.
            return fixNie(digits, text);
        }
        return true;

    }
    private static boolean fixNie(String digits, String brokenNie) {
        char controlLetter = getControlLetter(digits);
        String NIEchars = "XYZ";
        String correctedNie = NIEchars.charAt(digits.charAt(0)) + digits.substring(1)+ controlLetter;
        //TODO actualizar en el excel el nuevo valor del nif
        
        return true;
    }

    private static boolean checkNif(String text) {

        String digits = text.substring(0,8);
        char controlLetter = getControlLetter(digits);

        if(text.charAt(8) != controlLetter){
            return fixNif(digits, text);
        }
        return true;

    }
    private static boolean fixNif(String digits, String brokenNie) {
        char controlLetter = getControlLetter(digits);
        //TODO actualizar en el excel el nuevo nif
        
        String correctedNif = digits+controlLetter;
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
        sb.append("    <NIF_NIE>").append(cont.getNifnie()).append("</NIF_NIE>\n");
        sb.append("    <Nombre>").append(cont.getNombre()).append("</Nombre>\n");
        sb.append("    <PrimerApellido>").append(cont.getApellido1()).append("</PrimerApellido>\n");
        sb.append("    <SegundoApellido>").append(cont.getApellido2()).append("</SegundoApellido>\n");
        sb.append("    <TipoDeError>").append(ErrorType).append("</TipoDeError>\n");
        sb.append("  </Contribuyente>\n");

        return sb.toString();
    }
}
