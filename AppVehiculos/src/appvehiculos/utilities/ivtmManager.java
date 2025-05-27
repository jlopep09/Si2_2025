/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appvehiculos.utilities;

import appvehiculos.utilities.Constantes;
import POJOS.Contribuyente;
import POJOS.Ordenanza;
import POJOS.Recibos;
import POJOS.Vehiculos;
import static appvehiculos.utilities.Utilities.getEmail;
import appvehiculos.bank.BankValidator;
import static appvehiculos.bank.BankValidator.getXML;
import appvehiculos.bank.NifNieValidator;
import static appvehiculos.data.DatabaseController.saveContribuyente;
import static appvehiculos.data.DatabaseController.saveOrdenanza;
import static appvehiculos.data.DatabaseController.saveRecibo;
import static appvehiculos.data.DatabaseController.saveVehiculo;
import appvehiculos.data.ExcelManager;
import static appvehiculos.utilities.Utilities.getDateOrNull;
import static appvehiculos.utilities.Utilities.getDoubleOrNull;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jose Antonio Lopez Perez
 */
public class IvtmManager {
    public static void cargarContribuyentes(ArrayList<Contribuyente> listadoContribuyentes, ExcelManager excelVehiculos) throws IOException {
        int sheet = Constantes.ColumnasContribuyentes.SHEET.getValor();
        int numRows = excelVehiculos.getRowsCount(sheet);
                //------------- Iteramos Excel -------------
        for (int i = 1; i < numRows; i++) {
            String nifnie = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasContribuyentes.NIFNIE.getValor());

            //------------- Fila vacia -------------
            if(nifnie.equals(ExcelManager.cellType.EMPTYROW.toString())){
                //System.out.println("Fila vacia: "+(i+1));
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
                //System.out.println("Fila vacia: "+(i+1));
                continue;
            }
            //------------- Creamos objeto Vehiculo y lo agregamos al listado -------------
            Vehiculos vehiculo = new Vehiculos();
            vehiculo.setIdVehiculo(i);
            vehiculo.setTipo(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.TIPO.getValor()));
           vehiculo.setMarca(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.MARCA.getValor()));
           String modelo = excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.MODELO.getValor());
           if(modelo.length() > 3 && modelo.charAt(modelo.length()-2) == '.' && modelo.charAt(modelo.length()-1) == '0' && Character.isDigit(modelo.charAt(0))){
               modelo = modelo.substring(0, modelo.length()-2);
           }
           vehiculo.setModelo(modelo);
           vehiculo.setMatricula(matricula);
           vehiculo.setNumeroBastidor(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.BASTIDOR.getValor()));
           
           vehiculo.setCaballosFiscales(getDoubleOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.CABALLOS.getValor())));
            vehiculo.setPlazas(getDoubleOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.PLAZAS.getValor())));
            vehiculo.setCentimetroscubicos(getDoubleOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.CC.getValor())));
            vehiculo.setKgcarga(getDoubleOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.KG.getValor())));

           
           vehiculo.setExencion(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.EXENCION.getValor()).charAt(0));
           
            vehiculo.setFechaMatriculacion(getDateOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHAMATRICULACION.getValor())));
            vehiculo.setFechaAlta(getDateOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHAALTA.getValor())));
            vehiculo.setFechaBaja(getDateOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHABAJA.getValor())));
            vehiculo.setFechaBajaTemporal(getDateOrNull(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.FECHABAJATEMPORAL.getValor())));
           
           vehiculo.setContribuyente(new Contribuyente());
           vehiculo.getContribuyente().setNifnie(excelVehiculos.getCellValue(sheet, i, Constantes.ColumnasVehiculos.NIFPROPIETARIO.getValor()));
           //System.out.println(" Se ha creado el vehiculo con id " + vehiculo.getIdVehiculo() + " y fecha de matriculacion " + vehiculo.getFechaMatriculacion());
           listadoVehiculos.add(vehiculo);
        }
    }
    public static void cargarOrdenanzas(ArrayList<Ordenanza> listadoOrdenanzas, ExcelManager excelOrdenanzas) throws IOException {
        int sheet = Constantes.ColumnasOrdenanzas.SHEET.getValor();
        int numRows = excelOrdenanzas.getRowsCount(sheet);
        //------------- Iteramos Excel -------------
        for (int i = 1; i < numRows; i++) {
            String ayuntamiento = excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.AYUNTAMIENTO.getValor());

            //------------- Fila vacia -------------
            if(ayuntamiento.equals(ExcelManager.cellType.EMPTYROW.toString())){
                //System.out.println("Fila vacia: "+(i+1));
                continue;
            }
            //------------- Creamos objeto Ordenanza y lo agregamos al listado -------------
            Ordenanza ordenanza = new Ordenanza();
            String tipoVehiculo = excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.TIPOVEHICULO.getValor());
            String unidad = excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.UNIDAD.getValor());
            double minimoRango = Double.parseDouble(excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.MINIMO.getValor()));
            double maximoRango = Double.parseDouble(excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.MAXIMO.getValor()));
            double importe = Double.parseDouble(excelOrdenanzas.getCellValue(sheet, i, Constantes.ColumnasOrdenanzas.IMPORTE.getValor()));

            ordenanza.setId(i);
            ordenanza.setAyuntamiento(ayuntamiento);
            ordenanza.setTipoVehiculo(tipoVehiculo);
            ordenanza.setUnidad(unidad);
            ordenanza.setMinimoRango(minimoRango);
            ordenanza.setMaximoRango(maximoRango);
            ordenanza.setImporte(importe);

            listadoOrdenanzas.add(ordenanza);
        }
    }
    
    public static void gestionarNifNies(ArrayList<Contribuyente> listadoContribuyentes, ArrayList<Contribuyente> listadoContribuyentesNifNieValidos, ExcelManager excelVehiculos ) throws IOException {
        ArrayList<String> nifsList = new ArrayList<>();
        StringBuilder sb_XMLnif = new StringBuilder();
        for (int j = 0; j < listadoContribuyentes.size(); j++) {
            Contribuyente cont = listadoContribuyentes.get(j);
             //------------- Comprobamos validez del nif -------------
            boolean isCorrect = NifNieValidator.check(nifsList, cont.getNifnie(), excelVehiculos, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.NIFNIE.getValor(), cont);

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
                    String wrongCCC = cont.getCcc();
                    cont.setCcc(fixed);
                    cont.setIban(BankValidator.getIban(cont.getCcc(), cont.getPaisCcc()));
                    listadoContribuyentesCCCValidos.add(cont);
                    sb_XMLccc.append(getXML(cont, "",wrongCCC ));
                    excelVehiculos.setCellValue(sheet, cont.getIdContribuyente(), Constantes.ColumnasContribuyentes.CCC.getValor(), cont.getCcc());
                    
                }else{
                    //correcto
                    listadoContribuyentesCCCValidos.add(cont);
                }
            }catch(Exception e){
                //Irreparable
                sb_XMLccc.append(getXML(cont, "IMPOSIBLE GENERAR IBAN", cont.getCcc()));
                
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
        //System.out.println("Se ha detectado un nif duplicado en la fila "+i);
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
        //System.out.print("Se ha detectado un nif irreparable en la fila "+i);
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
        //System.out.println(" con error: " + error);
        sb.append(NifNieValidator.getXML(cont, error));
    }
    public static void subirRecibosBBDD(ArrayList<Recibos> nuevosRecibos){
        try{
            for (int i = 0; i < nuevosRecibos.size(); i++) {
                //Comprobar que el contribuyente existe
                //Comprobar que el vehiculo existe
                //Comprobar si existe el recibo en bbdd (igual nif, fehcha del padron y matricula)
                //Actualizar o crear recibo en bbdd
                saveRecibo(nuevosRecibos.get(i));
            }
        }catch(Exception e){
            System.out.println("No se ha podido subir los recibos a la bbdd.");
            e.printStackTrace();
        }
    
    }
        public static void subirContribuyentesOrdenanzasBBDD(ArrayList<Contribuyente> conts,ArrayList<Ordenanza> ords ){
        try{
            //Subir contribuyentes
            for (int i = 0; i < conts.size(); i++) {
                Contribuyente cont = conts.get(i);
                if(!saveContribuyente(cont)){
                    System.out.println("No se ha podido guardar en bbdd el contribuyente "+cont.getNifnie() +cont.getNombre());
                }
            }
            //Subir ordenanzas
            for (int i = 0; i < ords.size(); i++) {
                Ordenanza ordenanza = ords.get(i);
                if(!saveOrdenanza(ordenanza)){
                    System.out.println("No se ha podido guardar en bbdd la ordenanza "+ordenanza.getAyuntamiento() + ordenanza.getTipoVehiculo());
                }
            }

        }catch(Exception e){
            System.out.println("No se ha podido subir los contribuyentes, ordenanzas y vehiculos.");
            e.printStackTrace();
        }
    
    }
    public static void subirVehiculosBBDD(ArrayList<Vehiculos> vehiculos ){
        try{
            //Subir vehiculos
            for (int i = 0; i < vehiculos.size(); i++) {
                Vehiculos vehiculo = vehiculos.get(i);
                if(vehiculo.getContribuyente().getIdContribuyente() == 0){continue;}
                if(!saveVehiculo(vehiculo)){
                    System.out.println("No se ha podido guardar en bbdd el vehiculo "+ vehiculo.getMatricula()+" contribuyente con id "+vehiculo.getContribuyente().getIdContribuyente()    );
                }
            }
        }catch(Exception e){
            System.out.println("No se ha podido subir los contribuyentes, ordenanzas y vehiculos.");
            e.printStackTrace();
        }
    
    }
}
