package appvehiculos;
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
    public NifNieValidator(){

    }
    public String checkNifNie(String code){
        String type = getType(code);
        if (type == "NIF"){
            return checkNif(code);
        }
        if (type == "NIE"){
            return checkNie(code);
        }
        return "NotValid";
    }
    public String getFixedNifNie(String code){
        if(checkNifNie(code) == "NotValid"){
            throw new IllegalArgumentException("Cant be fixed");
        }
        if(checkNifNie(code) == "correct"){
            return code;
        }
        String type = getType(code);
        if (type == "NIF"){
            return fixNif(code);
        }
        if (type == "NIE"){
            return fixNie(code);
        }
        return "null";
    }

    private String fixNie(String code) {
        String digits = "";
        switch (code.charAt(0)){
            case 'X':
                digits = "0" + code.substring(1,8);
                break;
            case 'Y':
                digits = "1" + code.substring(1,8);
                break;
            case 'Z':
                digits = "2" + code.substring(1,8);
                break;
        }
        char controlLetter = getControlLetter(digits);
        return digits+controlLetter;
    }

    private String fixNif(String code) {
        String digits = code.substring(0,8);
        char controlLetter = getControlLetter(digits);
        return digits+controlLetter;
    }

    private String checkNie(String code) {
        if(code.charAt(0) != 'X' && code.charAt(0) != 'Y' && code.charAt(0) != 'Z'){
            return "NotValid";
        }
        String digits = "";
        switch (code.charAt(0)){
            case 'X':
                digits = "0" + code.substring(1,8);
                break;
            case 'Y':
                digits = "1" + code.substring(1,8);
                break;
            case 'Z':
                digits = "2" + code.substring(1,8);
                break;
        }
        char controlLetter = getControlLetter(digits);

        if(code.charAt(8) != controlLetter){
            return "broken";
        }
        return "correct";

    }

    private String checkNif(String code) {

        String digits = code.substring(0,8);
        char controlLetter = getControlLetter(digits);

        if(code.charAt(8) != controlLetter){
            return "broken";
        }
        return "correct";

    }
    private char getControlLetter(String digits){
        String controlString = "TRWAGMYFPDXBNJZSQVHLCKE";
        int intValue = Integer.valueOf(digits);
        int letterPos = intValue%23;
        char controlLetter = controlString.charAt(letterPos);
        return controlLetter;
    }

    private String getType(String code) {
        if(code.length() == 9){
            //check number positions
            for (int i = 1; i < 8; i++) {
                if(!Character.isDigit(code.charAt(i))){
                    return "NotValid";
                }
            }
            //check has control letter
            if(!Character.isLetter(code.charAt(8))){
                return "NotValid";
            }
            if(Character.isDigit(code.charAt(0))){
                return "NIF";
            }else{
                return "NIE";
            }
        }
        return "NotValid";
    }

}
