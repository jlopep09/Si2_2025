package appvehiculos;

//Si el iban no son all digitos o si no tiene el nim correcto no se arregla, se marca como irreparable

import POJOS.Contribuyente;
import POJOS.HibernateUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


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

        arregloDeNifs(excelVehiculos);

        //---------------------------------------------------------------

        excelOrdenanzas.closeExcel();
        excelVehiculos.closeExcel();
        HibernateUtil.shutdown();
        System.out.println("Ejecución terminada");

    }
    public static void arregloDeNifs(ExcelManager excelVehiculos) throws IOException {

        int numRows = excelVehiculos.getRowsCount(0);
        System.out.println("Numero de rows encontrado: " + numRows);

        StringBuilder sb = new StringBuilder();
        ArrayList<String> nifsList = new ArrayList<>();
        for (int i = 1; i < numRows; i++) {
            String nifnie = excelVehiculos.getCellValue(0, i, 0);
            if(nifnie.equals(ExcelManager.cellType.EMPTYROW.toString())){
                System.out.println("Fila vacia: "+(i+1));
                continue;}
            boolean isCorrect = NifNieValidator.check(nifnie, excelVehiculos, i, 0);

            if(isCorrect && nifsList.contains(nifnie)){
                System.out.println("Se ha detectado un nif duplicado en la fila "+i);
                Contribuyente cont = new Contribuyente();
                cont.setNifnie(nifnie);
                cont.setIdContribuyente(i+1);
                cont.setApellido1(excelVehiculos.getCellValue(0, i, 1));
                cont.setApellido2(excelVehiculos.getCellValue(0, i, 2));
                cont.setNombre(excelVehiculos.getCellValue(0, i, 3));
                String error = "NIF DUPLICADO";
                sb.append(NifNieValidator.getXML(cont, error));
            }else if(isCorrect && !nifsList.contains(nifnie)){
                nifsList.add(nifnie);
            }

            if(!isCorrect){
                System.out.print("Se ha detectado un nif irreparable en la fila "+i);
                Contribuyente cont = new Contribuyente();
                cont.setNifnie(nifnie);
                cont.setIdContribuyente(i+1);
                cont.setApellido1(excelVehiculos.getCellValue(0, i, 1));
                cont.setApellido2(excelVehiculos.getCellValue(0, i, 2));
                cont.setNombre(excelVehiculos.getCellValue(0, i, 3));
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
        NifNieValidator.saveXML(sb.toString());

    }

}
