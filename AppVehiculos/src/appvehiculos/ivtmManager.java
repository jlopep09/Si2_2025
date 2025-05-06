/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appvehiculos;

import POJOS.Contribuyente;
import POJOS.Vehiculos;
import static appvehiculos.AppVehiculos.getEmail;
import appvehiculos.bank.BankValidator;
import static appvehiculos.bank.BankValidator.getXML;
import appvehiculos.bank.NifNieValidator;
import appvehiculos.data.ExcelManager;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Jose Antonio Lopez Perez
 */
public class ivtmManager {
    public static void cargarContribuyentes(ArrayList<Contribuyente> listadoContribuyentes, ExcelManager excelVehiculos) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        int numRows = excelVehiculos.getRowsCount(sheet);
                //------------- Iteramos Excel -------------
        for (int i = 1; i < numRows; i++) {
            String nifnie = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NIFNIE.getValor());

            //------------- Fila vacia -------------
            if(nifnie.equals(ExcelManager.cellType.EMPTYROW.toString())){
                System.out.println("Fila vacia: "+(i+1));
                continue;
            }
            //------------- Creamos objeto Contribuyente y lo agregamos al listado -------------
            Contribuyente cont = new Contribuyente();
            cont.setIdContribuyente(i);
            cont.setNombre(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NOMBRE.getValor()));
            cont.setApellido1(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO1.getValor()));
            cont.setApellido2(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.APELLIDO2.getValor()));
            cont.setNifnie(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NIFNIE.getValor()));
            cont.setDireccion(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.DIRECCION.getValor()));
            cont.setNumero(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NUMERO.getValor()));
            cont.setPaisCcc(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.PAISCCC.getValor()));
            cont.setCcc(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.CCC.getValor()));
           cont.setBonificacion(Double.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.BONIFICACION.getValor())));
           cont.setAyuntamiento(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.AYUNTAMIENTO.getValor()));
           
           listadoContribuyentes.add(cont);
        }
    }
    public static void cargarVehiculos(ArrayList<Vehiculos> listadoVehiculos, ExcelManager excelVehiculos) throws IOException {
        int sheet = Constantes.ColumnasVehiculos.SHEET.getValor();
        int numRows = excelVehiculos.getRowsCount(sheet);
                //------------- Iteramos Excel -------------
        for (int i = 1; i < numRows; i++) {
            String matricula = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.MATRICULA.getValor());

            //------------- Fila vacia -------------
            if(matricula.equals(ExcelManager.cellType.EMPTYROW.toString())){
                System.out.println("Fila vacia: "+(i+1));
                continue;
            }
            //------------- Creamos objeto Vehiculo y lo agregamos al listado -------------
            Vehiculos vehiculo = new Vehiculos();
            vehiculo.setIdVehiculo(i);
            vehiculo.setTipo(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.TIPO.getValor()));
           vehiculo.setMarca(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.MARCA.getValor()));
           vehiculo.setModelo(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.MODELO.getValor()));
           vehiculo.setMatricula(matricula);
           vehiculo.setNumeroBastidor(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.BASTIDOR.getValor()));
           vehiculo.setCaballosFiscales(Double.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.CABALLOS.getValor())));
           vehiculo.setPlazas(Double.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.PLAZAS.getValor())));
           vehiculo.setCentimetroscubicos(Double.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.CC.getValor())));
           vehiculo.setKgcarga(Double.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.KG.getValor())));
           vehiculo.setExencion(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.EXENCION.getValor()).charAt(0));
           vehiculo.setFechaMatriculacion(Date.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHAMATRICULACION.getValor())));
           vehiculo.setFechaAlta(Date.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHAALTA.getValor())));
           vehiculo.setFechaBaja(Date.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHABAJA.getValor())));
           vehiculo.setFechaBajaTemporal(Date.valueOf(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHABAJATEMPORAL.getValor())));
           
           listadoVehiculos.add(vehiculo);
        }
    }
    public static void gestionarNifNies(ArrayList<Contribuyente> listadoContribuyentes, ArrayList<Contribuyente> listadoContribuyentesNifNieValidos, ExcelManager excelVehiculos ) throws IOException {
        ArrayList<String> nifsList = new ArrayList<>();
        StringBuilder sb_XMLnif = new StringBuilder();
        for (int j = 0; j < listadoContribuyentes.size(); j++) {
            Contribuyente cont = listadoContribuyentes.get(j);
             //------------- Comprobamos validez del nif -------------
            boolean isCorrect = NifNieValidator.check(nifsList, cont.getNifnie(), excelVehiculos, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.NIFNIE.getValor());

            //------------- Gestionamos duplicados -------------
            if(isCorrect && nifsList.contains(cont.getNifnie())){
                gestionarUsuarioDuplicado(excelVehiculos, sb_XMLnif, cont.getIdContribuyente(), cont.getNifnie());

            //------------- Nif válidos y/o arreglados -------------
            }else if(isCorrect && !nifsList.contains(cont.getNifnie())){
                nifsList.add(cont.getNifnie());
                listadoContribuyentesNifNieValidos.add(cont);
            }
            //------------- Nif irreparable o blanco -------------
            if(!isCorrect){
                gestionarUsuarioErroneo(excelVehiculos, sb_XMLnif, cont.getIdContribuyente(), cont.getNifnie());
            }
        }
        NifNieValidator.saveXML(sb_XMLnif.toString());
    }
    public static void gestionarCCCs(ArrayList<Contribuyente> listadoContribuyentes, ArrayList<Contribuyente> listadoContribuyentesCCCValidos, ExcelManager excelVehiculos ) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        StringBuilder sb_XMLccc = new StringBuilder();
        for (int j = 0; j < listadoContribuyentes.size(); j++) {
            Contribuyente cont = listadoContribuyentes.get(j);
            
             //------------- Comprobamos validez del ccc -------------
             try{
                boolean isCorrect = BankValidator.checkCCC(cont.getCcc());
                if(!isCorrect){
                    //reparable
                    String fixed = BankValidator.getFixedCCC(cont.getCcc());
                    cont.setCcc(fixed);
                    cont.setIban(BankValidator.getIban(cont.getCcc(), cont.getPaisCcc()));
                    listadoContribuyentesCCCValidos.add(cont);
                    sb_XMLccc.append(getXML(cont, ""));
                    excelVehiculos.setCellValue(sheet, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.CCC.getValor(), cont.getCcc());
                    
                }else{
                    //correcto
                    listadoContribuyentesCCCValidos.add(cont);
                }
            }catch(Exception e){
                //Irreparable
                sb_XMLccc.append(getXML(cont, "IMPOSIBLE GENERAR IBAN"));
                
            }
            
        }
        BankValidator.saveXML(sb_XMLccc.toString());
    }
    public static void gestionarIbanEmail(ArrayList<Contribuyente> listadoContribuyentesValidos, ExcelManager excelVehiculos ) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        ArrayList<String> emailList = new ArrayList<>();
        for (int i = 0; i < listadoContribuyentesValidos.size() ; i++) {
            Contribuyente cont = listadoContribuyentesValidos.get(i);
            
            cont.setIban(BankValidator.getIban(cont.getCcc(), cont.getPaisCcc()));
            excelVehiculos.setCellValue(sheet, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.IBAN.getValor(), cont.getIban());
            
            cont.setEmail( getEmail(emailList, cont));
            excelVehiculos.setCellValue(sheet, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.EMAIL.getValor(), cont.getEmail());
     
        }
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
}
