package appvehiculos.recibos;
import POJOS.*;
import static appvehiculos.data.DatabaseController.saveRecibo;
import static appvehiculos.data.DatabaseController.saveVehiculo;
import static appvehiculos.recibos.CalculadoraImporte.getTrimestres;
import static appvehiculos.recibos.CalculadoraImporte.getUnidadValorString;
import static appvehiculos.utilities.IvtmManager.subirVehiculosBBDD;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GeneradorRecibo {
    private ArrayList<Ordenanza> listadoOrdenanzas;
    private ArrayList<Vehiculos> listadoVehiculos;
    private ArrayList<Contribuyente> listadoContribuyentesValidos;

    public GeneradorRecibo(ArrayList<Ordenanza> listadoOrdenanzas, ArrayList<Vehiculos> listadoVehiculos, ArrayList<Contribuyente> listadoContribuyentesValidos) {
        this.listadoOrdenanzas = listadoOrdenanzas;
        this.listadoVehiculos = listadoVehiculos;
        this.listadoContribuyentesValidos = listadoContribuyentesValidos;
    }

    public void generarRecibos(String periodo, ArrayList<Recibos> nuevosRecibos) throws IOException, ParseException {
        StringBuilder sbRecibos = new StringBuilder();
        StringBuilder sbErrores = new StringBuilder();
        int contadorRecibos = 0;
        float totalRecibos = 0f;
        for (int i = 0; i < this.listadoVehiculos.size(); i++) {
            Vehiculos vehiculo = this.listadoVehiculos.get(i);
            if(vehiculo.getTipo().toLowerCase().equals("automovil")){
                continue;
            }
            //Obtenemos los errores del vehiculo
            ArrayList<String> listadoErrores = new ArrayList<>();
            obtenerErroresVehiculo(periodo, vehiculo, listadoErrores);

            if(listadoErrores.isEmpty()) {
                //Generar recibo
                int trimestres = getTrimestres(vehiculo, periodo);
                if(trimestres == 0){
                    continue;
                }
                float importes[] = CalculadoraImporte.getImporte(vehiculo, this.listadoOrdenanzas, periodo);
                float totalRecibo = importes[1] ;
                //float totalRecibo = 90f;
                totalRecibos += totalRecibo;
                sbRecibos.append(GeneradorRecibosXML.getXMLRecibo(vehiculo, totalRecibo, (contadorRecibos+1)));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                LocalDate fechaHoy = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaFormateada = fechaHoy.format(formato);
                Recibos nuevoRecibo = new Recibos(
                        (contadorRecibos+1), 
                        vehiculo.getContribuyente(), 
                        vehiculo, 
                        sdf.parse("1/1/"+periodo), 
                        sdf.parse(fechaFormateada),
                        vehiculo.getContribuyente().getNifnie(),
                        (vehiculo.getContribuyente().getDireccion() + " " + vehiculo.getContribuyente().getNumero()),
                        vehiculo.getTipo(),
                        (vehiculo.getMarca() + " " + vehiculo.getModelo()),
                        getUnidadValorString(vehiculo)[0],
                        Double.valueOf(getUnidadValorString(vehiculo)[1]),
                        totalRecibo, 
                        vehiculo.getContribuyente().getAyuntamiento()     
                );
                nuevoRecibo.setIban(nuevoRecibo.getContribuyente().getIban());
                nuevoRecibo.setBonificacion(nuevoRecibo.getContribuyente().getBonificacion());
                nuevoRecibo.setExencion(nuevoRecibo.getVehiculos().getExencion());
                nuevoRecibo.setEmail(nuevoRecibo.getContribuyente().getEmail());
                nuevosRecibos.add(nuevoRecibo);

                saveVehiculo(vehiculo);
                saveRecibo(nuevoRecibo);
                ReciboPDF.generarReciboPDF(vehiculo, importes, nuevoRecibo.getNumRecibo(), periodo, this.listadoOrdenanzas);
                
                contadorRecibos += 1;
            }else{
                //Generar xml error
                StringBuilder textoError = new StringBuilder();
                for (String error : listadoErrores) {
                    textoError.append(error+". ");
                }
                sbErrores.append(GeneradorRecibosXML.getXMLErrorVehiculo(vehiculo, textoError.toString()));
            }

        }
        GeneradorRecibosXML.saveXMLErrorVehiculo(sbErrores.toString());
        GeneradorRecibosXML.saveXMLRecibo(sbRecibos.toString(), periodo, totalRecibos, contadorRecibos);
        ResumenPDF.generarResumen(contadorRecibos, totalRecibos, periodo);

    }
    private ArrayList<String> obtenerErroresVehiculo(String periodo, Vehiculos vehiculo, ArrayList<String> listadoErrores){
        if(!fechasValidas(vehiculo, periodo)){
            listadoErrores.add("Fechas incoherentes");
        }
        if(!matriculaValida(vehiculo)){
            String tipoAnterior = vehiculo.getTipo();
            vehiculo.setTipo("historico");
            if(!matriculaValida(vehiculo)){
                listadoErrores.add("Matricula Erronea");
            }
            vehiculo.setTipo(tipoAnterior);
        }
        if(!propietarioValido(vehiculo)){ //Aqui se asocia a cada vehiculo su propietario
            if(!tienePropietario(vehiculo)){
                 listadoErrores.add("Vehiculo sin propietario");
            }else{
                 listadoErrores.add("Vehiculo con propietario erróneo");
            }   
        }
        
        
        return listadoErrores;

    }

    private boolean propietarioValido(Vehiculos vehiculo) {
        for (int i = 0; i < listadoContribuyentesValidos.size(); i++) {
            if(listadoContribuyentesValidos.get(i).getNifnie().equals(vehiculo.getContribuyente().getNifnie())){
                vehiculo.setContribuyente(listadoContribuyentesValidos.get(i));
                return true;
            }
        }
        
        
        return false;
    }

    private boolean tienePropietario(Vehiculos vehiculo) {
        if( vehiculo.getContribuyente().getNifnie() == null || vehiculo.getContribuyente().getNifnie().equals("EMPTYCELL")){
            return false;
        }
        return true;
    }

    private boolean matriculaValida(Vehiculos vehiculo) {
        String tipo = vehiculo.getTipo().toLowerCase();
        String matricula = vehiculo.getMatricula();
        ArrayList<String> codigosMatricula = new ArrayList<>(Arrays.asList(
                "A",    // Alicante
                "AB",   // Albacete
                "AL",   // Almería
                "AV",   // Ávila
                "B",    // Barcelona
                "BA",   // Badajoz
                "BI",   // Vizcaya
                "BU",   // Burgos
                "C",    // A Coruña
                "CA",   // Cádiz
                "CC",   // Cáceres
                "CE",   // Ceuta
                "CO",   // Córdoba
                "CR",   // Ciudad Real
                "CS",   // Castellón
                "CU",   // Cuenca
                "GC",   // Las Palmas (Gran Canaria)
                "GI",   // Girona
                "GR",   // Granada
                "GU",   // Guadalajara
                "H",    // Huelva
                "HU",   // Huesca
                "J",    // Jaén
                "L",    // Lleida
                "LE",   // León
                "LO",   // La Rioja (Logroño)
                "LU",   // Lugo
                "M",    // Madrid
                "MA",   // Málaga
                "ML",   // Melilla
                "MU",   // Murcia
                "NA",   // Navarra
                "O",    // Asturias (Oviedo)
                "OR",   // Ourense
                "P",    // Palencia
                "PM",   // Baleares (Palma de Mallorca)
                "PO",   // Pontevedra
                "S",    // Cantabria (Santander)
                "SA",   // Salamanca
                "SE",   // Sevilla
                "SG",   // Segovia
                "SO",   // Soria
                "SS",   // Gipuzkoa (San Sebastián)
                "T",    // Tarragona
                "TE",   // Teruel
                "TF",   // Santa Cruz de Tenerife
                "TO",   // Toledo
                "V",    // Valencia
                "VA",   // Valladolid
                "VI",   // Álava (Vitoria)
                "Z",    // Zaragoza
                "ZA"    // Zamora
        ));
        if(matricula.length() > 9) { return false; }
        switch (tipo) {
            case "ciclomotor":
                if(matricula.length() != 8 || matricula.charAt(0) != 'C' || !matricula.substring(1,5).matches("\\d+") || !matricula.substring(5).matches("[A-Z]+")){
                    return false;
                }
                return true;
            case "historico":
                if(matricula.length() != 8 || matricula.charAt(0) != 'H' || !matricula.substring(1,5).matches("\\d+") || !matricula.substring(5).matches("[A-Z]+")){
                    return false;
                }
                return true;
            case "remolque":
                if(Character.isDigit(matricula.charAt(matricula.length()-1))){ // CIUDAD 00...
                    if(matricula.length() > 8 || matricula.length() < 2) { return false; }
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    matricula = matricula.substring(iniciales.length());
                    return codigosMatricula.contains(iniciales) && matricula.matches("\\d+") ;
                } else if (!Character.isDigit(matricula.charAt(matricula.length()-3))) { // R 0000 LLL
                    if(matricula.length() != 8) {return false; }
                    if(matricula.charAt(0) != 'R') {return false; }
                    return matricula.substring(1,5).matches("\\d+") && matricula.substring(5).matches("[A-Z]+");
                }else{// CIUDAD 00000 VE
                    if(matricula.length() < 8) { return false; }
                    if(!matricula.substring(matricula.length()-2).equals("VE")) {return false; }
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    matricula = matricula.substring(iniciales.length(), matricula.length()-2);
                    return codigosMatricula.contains(iniciales) && matricula.matches("\\d+") ;
                }
            case "tractor":
                if(Character.isDigit(matricula.charAt(matricula.length()-1))){ // CIUDAD 00...
                    if(matricula.length() > 8 || matricula.length() < 2) { return false; }
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    matricula = matricula.substring(iniciales.length());
                    return codigosMatricula.contains(iniciales) && matricula.matches("\\d+") ;
                } else if (!Character.isDigit(matricula.charAt(matricula.length()-3))) { // R 0000 LLL
                    if(matricula.length() != 8) {return false; }
                    if(matricula.charAt(0) != 'E') {return false; }
                    return matricula.substring(1,5).matches("\\d+") && matricula.substring(5).matches("[A-Z]+");
                }else{// CIUDAD 00000 VE
                    if(matricula.length() < 8) { return false; }
                    if(!matricula.substring(matricula.length()-2).equals("VE")) {return false; }
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    matricula = matricula.substring(iniciales.length(), matricula.length()-2);
                    return codigosMatricula.contains(iniciales) && matricula.matches("\\d+") && matricula.length() == 5 ;
                }
            case "turismo":
            case "autobus":
            case "camion":
            case "motocicleta":
                if(Character.isDigit(matricula.charAt(0))){ //0000 LLL
                    if(matricula.length() != 7) {return false; }
                    return matricula.substring(0,4).matches("\\d+") && matricula.substring(4).matches("[A-Z]+");
                } else if (!Character.isDigit(matricula.charAt(matricula.length()-1))) { // CIUDAD 0000 L o LL
                    if(matricula.length() < 6) {return false; }
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    String finales = (!Character.isDigit(matricula.charAt(matricula.length()-2)))?
                            matricula.substring(matricula.length()-2) :
                            matricula.charAt(matricula.length()-1)+"";
                    matricula = matricula.substring(iniciales.length(),matricula.length()-finales.length());
                    return codigosMatricula.contains(iniciales) && matricula.matches("\\d+") && matricula.length() ==4;
                }else{
                    String iniciales = (!Character.isDigit(matricula.charAt(1)))? matricula.substring(0,2) : matricula.charAt(0)+"";
                    return codigosMatricula.contains(iniciales) && matricula.substring(iniciales.length()).matches("\\d+") && matricula.substring(iniciales.length()).length()<= 6 ;
                }
            default:
                return false;
        }
    }

    private boolean fechasValidas(Vehiculos vehiculo, String periodo) {
        //TODO hay casos null sin comprobar
        if(vehiculo.getFechaAlta() == null){
            //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son validas por no tener fecha de alta");
            return false;
        }
        if(vehiculo.getFechaMatriculacion() == null){
            //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son validas por no tener fecha de matriculacion");
            return false;
        }
        if(vehiculo.getFechaBaja() == null && vehiculo.getFechaBajaTemporal()== null) {
            if(vehiculo.getFechaAlta().before(vehiculo.getFechaMatriculacion())){ 
             //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son  validas porque la fecha de alta es anterior a la de matriculacion");
             return false;
            }
            return true;
        }
            if(vehiculo.getFechaBaja() != null){
                if(!vehiculo.getFechaAlta().before(vehiculo.getFechaBaja())){
                    //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son  validas porque la fecha de alta es posterior a la baja");
                    return false; 
                }
            }
        
        if(vehiculo.getFechaAlta().before(vehiculo.getFechaMatriculacion())){ 
            //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son  validas porque la fecha de alta es anterior a la de matriculacion");
            return false;
        }
        
        if(vehiculo.getFechaBajaTemporal() != null && vehiculo.getFechaBaja() != null){
            if(vehiculo.getFechaBajaTemporal().after(vehiculo.getFechaBaja())){
                //System.out.println("Vehiculo con id "+ vehiculo.getIdVehiculo()+ " las fechas no son  validas porque la fecha de baja temporal es posterior a la fecha de baja");
                return false;
            }
        }

        return true;
    }


}
class GeneradorRecibosXML{
    /*
     * METODOS PARA LA GENERACIÓN DEL XML DE RECIBOS
     * */
    public static String getXMLRecibo(Vehiculos vehiculo, float totalRecibo, int idRecibo) {
        StringBuilder sb = new StringBuilder();

        sb.append("  <Recibo idRecibo=\"").append(idRecibo).append("\">\n");
        sb.append("    <Exencion>").append(vehiculo.getExencion()).append("</Exencion>\n");
        sb.append("    <idFilaExcelVehiculo>").append(vehiculo.getIdVehiculo()+1).append("</idFilaExcelVehiculo>\n");
        sb.append("    <nombre>").append(vehiculo.getContribuyente().getNombre()).append("</nombre>\n");
        sb.append("    <primerApellido>").append(vehiculo.getContribuyente().getApellido1()).append("</primerApellido>\n");
        sb.append("    <segundoApellido>").append(vehiculo.getContribuyente().getApellido2()).append("</segundoApellido>\n");
        sb.append("    <NIF>").append(vehiculo.getContribuyente().getNifnie()).append("</NIF>\n");
        sb.append("    <IBAN>").append(vehiculo.getContribuyente().getIban()).append("</IBAN>\n");
        sb.append("    <tipoVehiculo>").append(vehiculo.getTipo()).append("</tipoVehiculo>\n");
        sb.append("    <marcaModelo>").append(vehiculo.getMarca()).append(" ").append(vehiculo.getModelo()).append("</marcaModelo>\n");
        sb.append("    <matricula>").append(vehiculo.getMatricula()).append("</matricula>\n");
        sb.append("    <totalRecibo>").append(totalRecibo).append("</totalRecibo>\n");
        sb.append("  </Recibo>\n");

        return sb.toString();
    }
    public static boolean saveXMLRecibo(String xmlData, String periodo, float totalRecibos, int contadorRecibos) {
        try (FileWriter writer = new FileWriter("resources/Recibos.xml", false)) {

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Recibos fechaPadron=\"" + "IVTM de " + periodo + "\" totalPadron=\"" + String.format("%.2f", totalRecibos) + "\" numeroTotalRecibos=\"" + contadorRecibos + "\">\n");
            writer.write(xmlData);
            writer.write("</Recibos>\n");

        } catch (Exception e) {
            System.out.println("No se ha podido guardar el xml de recibos");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getXMLErrorVehiculo(Vehiculos vehiculo, String error) {
        StringBuilder sb = new StringBuilder();

        sb.append("  <Vehiculo id=\"").append(vehiculo.getIdVehiculo()+1).append("\">\n");
        sb.append("    <Marca>").append(vehiculo.getMarca()).append("</Marca>\n");
        sb.append("    <Modelo>").append(vehiculo.getModelo()).append("</Modelo>\n");
        sb.append("    <Error>").append(error.substring(0, error.length()-1)).append("</Error>\n");
        sb.append("  </Vehiculo>\n");

        return sb.toString();
    }
    public static boolean saveXMLErrorVehiculo(String xmlData) {
        try (FileWriter writer = new FileWriter("resources/ErroresVehiculos.xml", false)) {

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Vehiculos>\n");
            writer.write(xmlData);
            writer.write("</Vehiculos>\n");
        } catch (Exception e) {
            System.out.println("No se ha podido guardar el xml de erres de vehiculos");
            e.printStackTrace();
            return false;
        }
        return true;
    }



}

class CalculadoraImporte{

    public static float[] getImporte(Vehiculos vehiculo,  ArrayList<Ordenanza> ordenanzas, String periodo) {

        //Obtenemos ordenanzas relacionadas al tipo de vehiculo y municipio
        ArrayList<Ordenanza> ordenzasAsociadas = getOrdenanzas(vehiculo, ordenanzas);

        //Calculamos el numero de trimestres a pagar
        int trimestres = getTrimestres(vehiculo, periodo);

        //Gesionamos forma de calcular ese vehiculo con esa ordenanza
        float importeInicial = (float)calculaImporte(ordenzasAsociadas, trimestres, vehiculo);
        //Aplicamos exenciones y bonificaciones

        if((vehiculo.getExencion() + "").equals("S")){
            return new float[]{importeInicial, 0f, trimestres};
        }
        float bonificacion = vehiculo.getContribuyente().getBonificacion().floatValue();
        //retornamos importe final
        float importeFinalAnual  = importeInicial * (1-bonificacion/100) ;
        toStringRecibo(vehiculo,  periodo, importeInicial, importeFinalAnual);
        return new float[]{importeInicial, importeFinalAnual, trimestres}; //aplicamos la bonificacion
    }
    private static void toStringRecibo(Vehiculos vehiculo,  String periodo, float  importeTotal, float importeFinal){
        Contribuyente cont = vehiculo.getContribuyente();
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaHoy.format(formato);
        
        System.out.println("___________________________Recibo Generado_____________________________");
        System.out.println("| "+ cont.getNombre() + " " + cont.getApellido1() + " " + cont.getApellido2() + " " + cont.getNifnie()+ " "+ cont.getDireccion() + " IBAN: "+ cont.getIban() + " Bonificación:" + cont.getBonificacion() + " |");
        System.out.println("| Fecha de recibo: "+fechaFormateada +" Periodo seleccionado: 01/01/" + periodo+ " |" );
        System.out.println("| Tipo: "+ vehiculo.getTipo() + " Marca-Modelo: " + vehiculo.getMarca() + "-" + vehiculo.getModelo() + " Matrícula: " + vehiculo.getMatricula()+ " Bastidor: "+ vehiculo.getNumeroBastidor() + " "+ getUnidadValorString(vehiculo)[0] + ": "+ getUnidadValorString(vehiculo) [1] + " Importe anual" + importeTotal + " Exencion " + vehiculo.getExencion()+ " Importe final "+ importeFinal + " |");
        System.out.println("____________________________________________________________________________");
    }
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
    public static int getTrimestres(Vehiculos vehiculo, String periodo) {
        Date fechaAlta = vehiculo.getFechaAlta();
        if (fechaAlta == null) {throw new RuntimeException("Se está intentando calcular trimestres de un vehículo sin fecha de alta");}
        Date fechaBaja = vehiculo.getFechaBaja();
        Date fechaBajaTemporal = vehiculo.getFechaBajaTemporal();

        // 1) Creamos el límite superior del rango: 31/12 del año 'periodo'
        int yearPeriod = Integer.parseInt(periodo);
        Calendar datePeriodo = Calendar.getInstance();
        datePeriodo.clear();
        datePeriodo.set(yearPeriod, Calendar.DECEMBER, 31);

        // 2) Seleccionamos fechaFinRango (bajaTemporal > baja > ficticia 31/12/2100)
        Calendar fin = Calendar.getInstance();
        if (fechaBajaTemporal != null) {
            fin.setTime(fechaBajaTemporal);
        } else if (fechaBaja != null) {
            fin.setTime(fechaBaja);
        } else {
            fin.clear();
            fin.set(2100, Calendar.DECEMBER, 31);
        }

        // 3) Rellenamos el Calendar de alta
        Calendar alta = Calendar.getInstance();
        alta.setTime(fechaAlta);

        int startYear  = alta.get(Calendar.YEAR);
        int endYear    = fin.get(Calendar.YEAR);

        // 4) Caso: alta antes de X y baja después de X → 4 trimestres
        if (startYear < yearPeriod && endYear > yearPeriod) {
            return 4;
        }

        // 5) Caso: baja antes de X, o alta después de X → 0 trimestres
        if (endYear < yearPeriod || startYear > yearPeriod) {
                    
            return 0;
        }

        // 6) Ahora, parte de la vida cae dentro de X
        int startMonth = (startYear == yearPeriod
                ? alta.get(Calendar.MONTH)
                : Calendar.JANUARY);
        int endMonth   = (endYear   == yearPeriod
                ? fin.get(Calendar.MONTH)
                : Calendar.DECEMBER);

        // 7) Calculamos cuántos trimestres quedan desde cada mes hasta final de año
        int desdeStart = calculaTrimestresPorRango(startMonth);
        int desdeEnd   = calculaTrimestresPorRango(endMonth);
        
        // 8) Trimestres = diferencia + 1
        return desdeStart - desdeEnd + 1;
    }
    private static int calculaTrimestresPorRango(int month){
        switch(month){
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return 4;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                return 3;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return 2;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return 1;
        }
        throw new RuntimeException("No se puede calcular trimestres a partir de un mes con valor Calendar:"+month);
    }
    private static double calculaImporte(ArrayList<Ordenanza> ordenanzas, int trimestres, Vehiculos vehiculo) {
        double result = 0;
        Double cantidadEvaluada = (double) 0;
        switch (vehiculo.getTipo().toLowerCase()) {
            case "ciclomotor":
                cantidadEvaluada = vehiculo.getCentimetroscubicos();
                break;
            case "historico":
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "remolque":
                cantidadEvaluada = vehiculo.getKgcarga();
                break;
            case "tractor":
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "turismo":
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "autobus":
                cantidadEvaluada = vehiculo.getPlazas();
                break;
            case "camion":
                cantidadEvaluada = vehiculo.getKgcarga();
                break;
            case "motocicleta":
                cantidadEvaluada = vehiculo.getCentimetroscubicos();
                break;

            default:
                throw new RuntimeException("Error al calcular el importe del vehiculo con tipo "+vehiculo.getTipo());
        }
        for (int i = 0; i < ordenanzas.size(); i++) {
            if (ordenanzas.get(i).getMinimoRango() <= cantidadEvaluada && ordenanzas.get(i).getMaximoRango() >= cantidadEvaluada) {
                result = ordenanzas.get(i).getImporte();
                vehiculo.setOrdenanza(ordenanzas.get(i));
                
            }
        }
        result =  result * (trimestres/4f);
        BigDecimal bd = new BigDecimal(result).setScale(6, RoundingMode.CEILING);
        return bd.doubleValue(); 
    }
        public static String[] getUnidadValorString(Vehiculos vehiculo) {
        String result = "";
        Double cantidadEvaluada = (double) 0;
        switch (vehiculo.getTipo().toLowerCase()) {
            case "ciclomotor":
                result = "Centimetros cúbicos";
                cantidadEvaluada = vehiculo.getCentimetroscubicos();
                break;
            case "historico":
                result = "Caballos";
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "remolque":
                result = "Kg";
                cantidadEvaluada = vehiculo.getKgcarga();
                break;
            case "tractor":
                result = "Caballos";
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "turismo":
                result = "Caballos";
                cantidadEvaluada = vehiculo.getCaballosFiscales();
                break;
            case "autobus":
                result = "Plazas";
                cantidadEvaluada = vehiculo.getPlazas();
                break;
            case "camion":
                result = "Kg";
                cantidadEvaluada = vehiculo.getKgcarga();
                break;
            case "motocicleta":
                result = "Centimetros cúbicos";
                cantidadEvaluada = vehiculo.getCentimetroscubicos();
                break;

            default:
                throw new RuntimeException("Error al calcular el importe del vehiculo con tipo "+vehiculo.getTipo());
        }
        return new String[]{result, cantidadEvaluada+"" };
    }

}
