package appvehiculos;

//Si el iban no son all digitos o si no tiene el nif correcto no se arregla, se marca como irreparable
//Preguntar duda
/*
    Cuando actualizas los nifnie erroneos en el excel si ya hay uno con ese nif no lo corriges, dejas el malo aunque sea corregible ya que estaria duplicado
    Sin embargo, al generar los errores CCC si que aparece el nif corregido aunque no lo hayamos acutalizado en el excel.
    Practica 3 fila 14
*/

import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import appvehiculos.bank.BankValidator;
import appvehiculos.bank.NifNieValidator;
import appvehiculos.data.ExcelManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static appvehiculos.bank.BankValidator.*;


/**
 *
 * @author José Antonio López Pérez
 */
public class AppVehiculos {

    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando aplicación de gestion IVTM");
        ExcelManager excelOrdenanzas = new ExcelManager("resources/", "SistemasOrdenanzas", "");
        ExcelManager excelVehiculos = new ExcelManager("resources/", "SistemasVehiculos", "");

        //---------------------------------------------------------------

        procesarUsuarios(excelVehiculos);

        //---------------------------------------------------------------

        excelOrdenanzas.closeExcel();
        excelVehiculos.closeExcel();
        HibernateUtil.shutdown();
        System.out.println("Ejecución terminada");

    }
    public static void procesarUsuarios(ExcelManager excelVehiculos) throws IOException {

        //------------- Variables iniciales del documento -------------
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        int numRows = excelVehiculos.getRowsCount(sheet);
        StringBuilder sb_XMLnif = new StringBuilder();
        StringBuilder sb_XMLccc = new StringBuilder();
        ArrayList<String> nifsList = new ArrayList<>();
        ArrayList<String> nifsIbanDupeList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();

        //------------- Iteramos Excel -------------
        for (int i = 1; i < numRows; i++) {
            String nifnie = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NIFNIE.getValor());

            //------------- Fila vacia -------------
            if(nifnie.equals(ExcelManager.cellType.EMPTYROW.toString())){
                System.out.println("Fila vacia: "+(i+1));
                continue;
            }
            //------------- Comprobamos validez del nif -------------
            boolean isCorrect = NifNieValidator.check(nifsList, nifnie, excelVehiculos, i, Constantes.ColumnasContribuyentes.NIFNIE.getValor());

            //------------- Gestionamos duplicados -------------
            if(isCorrect && nifsList.contains(nifnie)){
                gestionarUsuarioDuplicado(excelVehiculos, sb_XMLnif, i, nifnie);

            //------------- Nif válidos y/o arreglados -------------
            }else if(isCorrect && !nifsList.contains(nifnie)){
                nifsList.add(nifnie);
            }
            //------------- Nif irreparable o blanco -------------
            if(!isCorrect){
                gestionarUsuarioErroneo(excelVehiculos, sb_XMLnif, i, nifnie);
            }
            //Procesamos CCC e iban
            String cccValue = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.CCC.getValor());
            try{
                //CCC correcto -> Generamos iban SOLO SIN NIF CORRECTO/SUBSANABLE
                if(BankValidator.checkCCC(cccValue)){
                    if(!nifsList.contains(nifnie)|| nifsIbanDupeList.contains(nifnie)){
                        continue;
                    }
                    String iban = BankValidator.getIban(cccValue, excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.PAISCCC.getValor()));
                    boolean doneWrite = excelVehiculos.setCellValue(sheet, i, Constantes.ColumnasContribuyentes.IBAN.getValor(), iban);
                    if(!doneWrite){
                        throw new IllegalArgumentException("No se ha guardado los cambios");
                    }
                    nifsIbanDupeList.add(nifnie);
                    Contribuyente cont = new Contribuyente();
                    cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
                    cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
                    cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
                    String email = getEmail(emailList, cont);
                    excelVehiculos.setCellValue(sheet, i, Constantes.ColumnasContribuyentes.EMAIL.getValor(), email);
                }else{
                    //CCC subsanable -> Generamos iban SOLO SIN NIF CORRECTO/SUBSANABLE + Anotamos error en xml
                    String FixedCCC = BankValidator.getFixedCCC(cccValue);


                    String iban = BankValidator.getIban(FixedCCC, excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.PAISCCC.getValor()));
                    Contribuyente cont = new Contribuyente();
                    if(!nifsList.contains(nifnie)){
                        cont.setNifnie(nifnie);
                    }else{
                        cont.setNifnie(NifNieValidator.getFixedNifNie_NoExcelChange_NoCheckFormat(nifnie));
                    }
                    cont.setIdContribuyente(i+1);
                    cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
                    cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
                    cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
                    cont.setCcc(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.CCC.getValor()));
                    cont.setIban(iban);
                    sb_XMLccc.append(getXML(cont, ""));
                    excelVehiculos.setCellValue(sheet, i, Constantes.ColumnasContribuyentes.CCC.getValor(), FixedCCC);

                    if(!nifsList.contains(nifnie) || nifsIbanDupeList.contains(nifnie)){
                        continue;
                    }
                    excelVehiculos.setCellValue(sheet, i, Constantes.ColumnasContribuyentes.IBAN.getValor(), iban);
                    nifsIbanDupeList.add(nifnie);
                    String email = getEmail(emailList, cont);
                    excelVehiculos.setCellValue(sheet, i, Constantes.ColumnasContribuyentes.EMAIL.getValor(), email);
                }
            }catch (Exception e){
                // CCC irreparable -> Anotamos error en XML
                System.out.println("NO SE GENERA IBAN DEL USUARIO "+(i+1)+": " +e.getMessage());

                Contribuyente cont = new Contribuyente();
                if(!nifsList.contains(nifnie)){
                    cont.setNifnie(nifnie);
                }else{
                    cont.setNifnie(NifNieValidator.getFixedNifNie_NoExcelChange_NoCheckFormat(nifnie));
                }
                
                cont.setIdContribuyente(i+1);
                cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
                cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
                cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
                cont.setCcc(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.CCC.getValor()));
                sb_XMLccc.append(getXML(cont, "IMPOSIBLE GENERAR IBAN"));
            }

        }
        //------------- Cerramos XMLs -------------
        NifNieValidator.saveXML(sb_XMLnif.toString());
        BankValidator.saveXML(sb_XMLccc.toString());

    }
    public static void gestionarUsuarioDuplicado(ExcelManager excelVehiculos,  StringBuilder sb, int i, String nifnie) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        System.out.println("Se ha detectado un nif duplicado en la fila "+i);
        Contribuyente cont = new Contribuyente();
        cont.setNifnie(nifnie);
        cont.setIdContribuyente(i+1);
        cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
        cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
        cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
        String error = "NIF DUPLICADO";
        sb.append(NifNieValidator.getXML(cont, error));
    }
    public static void gestionarUsuarioErroneo(ExcelManager excelVehiculos,  StringBuilder sb, int i, String nifnie) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        System.out.print("Se ha detectado un nif irreparable en la fila "+i);
        Contribuyente cont = new Contribuyente();
        cont.setNifnie(nifnie);
        cont.setIdContribuyente(i+1);
        cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
        cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
        cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
        String error = "";
        if(nifnie.equals(ExcelManager.cellType.EMPTYCELL.toString())){
            error = "NIF BLANCO";
        }else{
            error = "NIF ERRONEO";
        }
        System.out.println(" con error: " + error);
        sb.append(NifNieValidator.getXML(cont, error));
    }
    public static String getEmail(ArrayList<String> emailList, Contribuyente cont){
        String letraInicial = cont.getNombre().substring(0,1);
        String letraInicialApellido1 = cont.getApellido1().substring(0,1);
        String letraInicialApellido2 = cont.getApellido2().substring(0,1);
        String userEmail = letraInicial + letraInicialApellido1 + letraInicialApellido2;
        emailList.add(userEmail);
        int nRepeticion = Collections.frequency(emailList, userEmail) - 1;
        String nRepeticionString = (nRepeticion < 10) ? ("0"+nRepeticion) : String.valueOf(nRepeticion);
        return userEmail + nRepeticionString + "@vehiculos2025.com";
    }

}
