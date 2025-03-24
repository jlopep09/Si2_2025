package appvehiculos;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Si el iban no son todo digitos o si no tiene el nim correcto no se arregla, se marca como irreparable

import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import java.io.IOException;


/**
 *
 * @author José Antonio López Pérez
 */
public class AppVehiculos {

    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando aplicación de gestion IVTM");
        ExcelManager excelOrdenanzas = new ExcelManager("resources/", "SistemasOrdenanzas", "TrasEjecución");
        ExcelManager excelVehiculos = new ExcelManager("resources/", "SistemasVehiculos", "TrasEjecución");
        //---------------------------------------------------------------
        int numRows = excelVehiculos.getRowsCount(0);
        StringBuilder sb = new StringBuilder();
        
        for (int i = 1; i < numRows; i++) {
            String nifnie = excelVehiculos.getCellValue(0, i, 0);
            if(nifnie!=null && nifnie.equals("EMPTYROW")){
                //System.out.println("NIFVACIO");
                continue;}
            //System.out.println("Comprobando el nif "+ nifnie + "de la fila columna "+ i +"-"+0);
            boolean isCorrect = NifNieValidator.check(nifnie);

            if(!isCorrect){
                //System.out.println("Se ha detectado un nif irreparable");
                Contribuyente cont = new Contribuyente();
                cont.setNifnie(nifnie);
                cont.setIdContribuyente(i);
                cont.setApellido1(excelVehiculos.getCellValue(0, i, 1));
                cont.setApellido2(excelVehiculos.getCellValue(0, i, 2));
                cont.setNombre(excelVehiculos.getCellValue(0, i, 3));
                String error ="";
                if(nifnie.equals("")){
                    error = "NIF BLANCO";
                }else{
                    error = "NIF ERRONEO";
                }
                sb.append(NifNieValidator.getXML(cont, error));   
            }
        }
        NifNieValidator.saveXML(sb.toString());

        //---------------------------------------------------------------
        HibernateUtil.shutdown();
        excelOrdenanzas.closeExcel();
        excelVehiculos.closeExcel();
        System.out.println("Ejecución terminada");

    }
}
