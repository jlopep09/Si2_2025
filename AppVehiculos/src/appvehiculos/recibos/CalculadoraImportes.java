package appvehiculos.recibos;
import POJOS.Contribuyente;
import POJOS.Ordenanza;
import Utilities.ConfigVariables;
import Utilities.ExcelManager;
import Utilities.water.Concepto;
import proyectonetbeans.Utilities.ddbb.ManagerDDBB;

import javax.sound.midi.Soundbank;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

import static Utilities.ConfigVariables.*;

public class CalculadoraImportes {

    ArrayList<Concepto> conceptos;
    public ArrayList<Ordenanza> listaOrdenanzasDDBB;

    public CalculadoraImportes(){
        conceptos = new ArrayList<>();
        listaOrdenanzasDDBB = new ArrayList<>();
        try {
            setUpImportes();
            imprimeInfoConceptos();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpImportes() throws FileNotFoundException {
        //CREAR CONCEPTOS Y AGREGARLOS A LA LISTA
        ArrayList<Integer> listaID = new ArrayList<>();
        for (int i = 1; i < ExcelManager.getRowsCount(ordenanzaSheetNumber); i++) {
            if(!listaID.contains((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaID)))){
                Concepto temp = new Concepto((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaID)));
                listaID.add(temp.id);
                conceptos.add(temp);
            }
            int id  = i;
            int idOrdenanza = ((int) Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaID)));
            String concepto = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaConcepto);
            String subconcepto = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaSubconcepto);
            String descripcion = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaDescripcion);
            String acumulable = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaAcumulable);
            Double iva = Double.parseDouble(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaIVA));
            String pueblo = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaPueblo);
            String tipoCalculo = ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaTipoCalculo);

            Integer precioFijo;
            Integer m3incluidos; 
            Integer conceptoRelacionado;
            try{
                precioFijo = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaPrecioFijo));
            }catch(Exception e){
                precioFijo = null;
            }
            try{
                m3incluidos = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaM3incluidos));
            }catch(Exception e){
                m3incluidos = null;
            }
            try{
                conceptoRelacionado = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaIdOtro));
            }catch(Exception e){
                conceptoRelacionado = null;
            }

            Double preciom3;
            Double porcentaje;
            try{
                preciom3 = Double.parseDouble(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaPrecioM3));
            }catch(Exception e){
                preciom3 = null;
            }
            try{
                porcentaje = Double.parseDouble(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaPorcentajeOtro));
            }catch(Exception e){
                porcentaje = null;
            }

            Ordenanza oo = new Ordenanza( id,  idOrdenanza,  concepto,  subconcepto,  descripcion,  acumulable,  precioFijo,  m3incluidos,  preciom3,  porcentaje,  conceptoRelacionado,  iva,  pueblo,  tipoCalculo, null);         
            ManagerDDBB.updateOrdenanza(oo);
            listaOrdenanzasDDBB.add(oo); 
            
        }
    }

    private void imprimeInfoConceptos() {
        System.out.println("_______INFORMACION SOBRE LOS TIPOS DE CONCEPTOS DETECTADOS_______");
        for (int i = 0; i < conceptos.size(); i++) {
            System.out.println("_______Concepto "+conceptos.get(i).id+"_______");
            System.out.println(" -> tipoCalculo: "+conceptos.get(i).conceptoType);
            System.out.println(" -> Dependiente: "+conceptos.get(i).isRelativeToOther());
            System.out.println(" -> Nlineas: "+conceptos.get(i).getLineasConcepto().size());
            System.out.println(" -> iva: "+conceptos.get(i).porcentajeIva);
        }
    }

    public float getImporte(int id, int row){
        try{
            float descuento = (100f-Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaBonificacion)))/100;
            for (int i = 0; i < conceptos.size(); i++) {
                if(conceptos.get(i).id == id){
                    //Calculo si depende de otro concepto
                    if(conceptos.get(i).isRelativeToOther()){
                        for (Concepto concepto : conceptos) {
                            if (concepto.id == conceptos.get(i).idOtroConcepto){

                                float result = getImporte(concepto.id,row) * conceptos.get(i).porcentajeSobreOtro;
                                return result;
                            }

                        }
                    }
                    //Calculo normal

                    float result2 = conceptos.get(i).calcularImporte(row);
                    return result2*descuento;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        return 0;
    }
    public float getIva(int id, int row){
        try{
            float descuento = (100f-Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaBonificacion)))/100;
            for (int i = 0; i < conceptos.size(); i++) {
                if(conceptos.get(i).id == id){
                    //Calculo si depende de otro concepto
                    if(conceptos.get(i).isRelativeToOther()){
                        for (Concepto concepto : conceptos) {
                            if (concepto.id == conceptos.get(i).idOtroConcepto){
                                return getImporte( conceptos.get(i).id,  row) * conceptos.get(i).porcentajeIva;
                            }
                        }
                    }
                    //Calculo normal
                    return getImporte( conceptos.get(i).id,  row) * conceptos.get(i).porcentajeIva;
                }
            }
        }catch(Exception e){e.printStackTrace();}

        return 0;
    }
    public Concepto getConcepto(int id){
        for (int i = 0; i < conceptos.size(); i++) {
            if(conceptos.get(i).id == id){
                return conceptos.get(i);
            }
        }
        return null;
    }
}
