package appvehiculos.bank;
import POJOS.Contribuyente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class BankValidator {

    public static boolean checkCCC(String code){
        if(code.length()!=20) throw new IllegalArgumentException("CCC length not correct");

        String entidad = code.substring(0,4);
        String oficina = code.substring(4,8);
        String control = code.substring(8,10);
        String cuenta = code.substring(10);
        if(!(entidad + oficina + cuenta).matches("\\d+")) throw new IllegalArgumentException("CCC contiene letras");
        String digitControl1 = CCCalgorithm("00"+entidad+oficina);
        String digitControl2 = CCCalgorithm(cuenta);
        String generatedControl = digitControl1+digitControl2;

        return control.equals(generatedControl);
    }
    public static String getFixedCCC(String code){

        if(code.length()!=20) throw new IllegalArgumentException("CCC length not correct");
        String entidad = code.substring(0,4);
        String oficina = code.substring(4,8);
        String cuenta = code.substring(10);

        String digitControl1 = CCCalgorithm("00"+entidad+oficina);
        String digitControl2 = CCCalgorithm(cuenta);
        String generatedControl = digitControl1+digitControl2;

        return entidad+oficina+generatedControl+cuenta;
    }
    public static String getIban(String codeCCC, String codigoPais){
        if(codigoPais.length()!=2) throw new IllegalArgumentException("Codigo de pais de longitud distinta a 2");
        if(codeCCC.length()!=20) throw new IllegalArgumentException("CCC length not correct, cant obtain iban code");
        try{
            String CountryCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String codigoPaisNumerico = (CountryCode.indexOf(codigoPais.charAt(0))+10)+""+(CountryCode.indexOf(codigoPais.charAt(1))+10);
            String tempValue = codeCCC + codigoPaisNumerico + "00";
            BigInteger ibanNumerico = new BigInteger(tempValue);
            long resto = ibanNumerico.mod(new BigInteger("97")).longValue();
            String digitosControl = ((int) (98-resto))+"";
            if(digitosControl.length() ==1) digitosControl = "0" + digitosControl;
            return codigoPais+digitosControl+codeCCC;
        }catch (Exception e){
            throw new IllegalArgumentException("Error calculando el IBAN "+e.getMessage());
        }

    }
    private static String CCCalgorithm(String code){
        int[] factores = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int contador = 0;
        for (int i = 0; i < 10; i++) {
            contador += Character.getNumericValue(code.charAt(i)) * factores[i];
        }
        contador = contador%11;
        contador = 11-contador;

        if(contador<10) return contador + "";
        else if (contador == 10) return 1 + "";
        else return 0 + "";
    }
    public static String getXML(Contribuyente cont,String error) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("  <Cuenta id=\"").append(cont.getIdContribuyente()).append("\">\n");
        sb.append("    <Nombre>").append(cont.getNombre()).append("</Nombre>\n");
        sb.append("    <Apellidos>").append(cont.getApellido1()).append(" ").append(cont.getApellido2()).append("</Apellidos>\n");
        sb.append("    <NIFNIE>").append(cont.getNifnie()).append("</NIFNIE>\n");
        sb.append("    <CCCErroneo>").append(cont.getCcc()).append("</CCCErroneo>\n");
        if(error.isEmpty()){
            sb.append("    <IBANCorrecto>").append(cont.getIban()).append("</IBANCorrecto>\n");
        }else{
            sb.append("    <TipoError>").append(error).append("</TipoError>\n");
        }
        sb.append("  </Cuenta>\n");
        return sb.toString();
    }
    public static boolean saveXML(String xmlData) {
        try (FileWriter writer = new FileWriter("resources/ErroresCCC.xml", false)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Cuentas>\n");
            writer.write(xmlData);
            writer.write("</Cuentas>\n");
        }catch(Exception e){
            System.out.println("No se ha podido guardar el xml de errores CCC");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
