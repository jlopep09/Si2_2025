package appvehiculos.recibos;
import Utilities.ExcelManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static Utilities.ConfigVariables.*;

public class Concepto {

    public int id;

    public String Pueblo;
    public String TipoCalculo;
    public String Concepto;
    public String Subconcepto;
    public String Descripcion;

    public int fijo;
    public ArrayList<Integer> m3Incluidos;
    public ArrayList<Float> precioM3;
    public float porcentajeIva;

    public float porcentajeSobreOtro;
    public int idOtroConcepto;

    public String calcularDatosImporteTramo(int row, int numeroTramo) throws FileNotFoundException {
        float descuento = (100f-Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaBonificacion)))/100;
        if(numeroTramo == 0){
            return fijo*descuento+"";
        }else{
            int metrosSinPagar = (int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaLecturaActual))-(int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaLecturaAnterior));
            switch (conceptoType){
                case TRAMOS_N:
                    ArrayList<Integer> m3IncluidosClon = (ArrayList<Integer>) m3Incluidos.clone();
                    ArrayList<Float> precioM3Clon = (ArrayList<Float>) precioM3.clone();
                    float resultN = 0f+fijo;
                    int numIters = precioM3.size();
                    for (int i = 0; i < numIters; i++) {

                        if(metrosSinPagar >= m3Incluidos.get(0)){
                            if(i == numeroTramo){
                                resultN =  m3Incluidos.get(0) * precioM3.get(0);
                                m3Incluidos = (ArrayList<Integer>) m3IncluidosClon.clone();
                                precioM3 = (ArrayList<Float>) precioM3Clon.clone();
                                return resultN*descuento+"";
                            }
                            metrosSinPagar = metrosSinPagar - m3Incluidos.get(0);
                            resultN += m3Incluidos.get(0) * precioM3.get(0);
                            m3Incluidos.remove(0);
                            precioM3.remove(0);
                        }else{
                            if(i == numeroTramo){
                                resultN = metrosSinPagar * precioM3.get(0);
                                m3Incluidos = (ArrayList<Integer>) m3IncluidosClon.clone();
                                precioM3 = (ArrayList<Float>) precioM3Clon.clone();
                                return resultN*descuento+"";
                            }
                            resultN += metrosSinPagar * precioM3.get(0);
                            metrosSinPagar = 0;
                        }
                    }
                    m3Incluidos = (ArrayList<Integer>) m3IncluidosClon.clone();
                    precioM3 = (ArrayList<Float>) precioM3Clon.clone();
                case TRAMOS_S:
                    ArrayList<Integer> m3IncluidosClon2 = (ArrayList<Integer>) m3Incluidos.clone();
                    ArrayList<Float> precioM3Clon2 = (ArrayList<Float>) precioM3.clone();
                    float resultS = 0f+fijo;
                    int metrosTemp = metrosSinPagar;
                    numIters = precioM3.size();
                    int tramoBuscado = 0;
                    for (int i = 0; i < numIters; i++) {
                        if(metrosTemp > m3Incluidos.get(0)){
                            tramoBuscado++;
                            if(i == numeroTramo){
                                m3Incluidos = (ArrayList<Integer>) m3IncluidosClon2.clone();
                                precioM3 = (ArrayList<Float>) precioM3Clon2.clone();
                                return 0+"";
                            }
                            metrosTemp = metrosTemp - m3Incluidos.get(0);
                            m3Incluidos.remove(0);
                            precioM3.remove(0);

                        }

                    }
                    if(tramoBuscado < numeroTramo){
                        m3Incluidos = (ArrayList<Integer>) m3IncluidosClon2.clone();
                        precioM3 = (ArrayList<Float>) precioM3Clon2.clone();
                        return 0+"";
                    }
                    resultS = metrosSinPagar*precioM3.get(0);
                    m3Incluidos = (ArrayList<Integer>) m3IncluidosClon2.clone();
                    precioM3 = (ArrayList<Float>) precioM3Clon2.clone();
                    return resultS*descuento+"";
                default:
                    throw new RuntimeException("Estas intentando calcular el importe de un tramo pero el concepto no es por tramos");
            }
        }
    }

    public enum TYPE{
        DEPENDIENTE,
        SOLO_FIJO,
        TODO1,
        TRAMOS_S,
        TRAMOS_N
    }
    public TYPE conceptoType;
    public Concepto(int id) {
        this.id = id;
        try {
            setTYPE();
            loadData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTYPE() throws FileNotFoundException {
        //                      si            Independiente?       no
        //                     _---------------------|--------------_
        //               Solo 1 linea?                             Dependiente
        //             _--------|-------_
        //     Solo fijo?         Tramos acumulables?
        // _--------|-------_    _--------|-------_
        // FIJO          TODO1  TAcum           TnoAcum
        if(isRelativeToOther()){ conceptoType = TYPE.DEPENDIENTE; return; }

        ArrayList<Integer> numerosLineasConcepto = getLineasConcepto();
        int cantidadLineas = numerosLineasConcepto.size();

        if(cantidadLineas<1){
            throw new RuntimeException("Cant find the id ordenanza, check excel sheet number "+ ordenanzaSheetNumber);
        }else if(cantidadLineas==1){
            if(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioM3).isEmpty()){
                conceptoType = TYPE.SOLO_FIJO;
            }else conceptoType = TYPE.TODO1;
        }else {
            if(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(1), columnaAcumulable).equals("S")){
                conceptoType = TYPE.TRAMOS_S;
            }else if (ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(1), columnaAcumulable).equals("N")){
                conceptoType = TYPE.TRAMOS_N;
            }else throw new RuntimeException("No se puede determinar si el calculo de agua por tramos es acumulable o no lo es.");
        }
    }
    private void loadData() throws FileNotFoundException {
        ArrayList<Integer> numerosLineasConcepto = getLineasConcepto();
        Pueblo = ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPueblo);
        TipoCalculo = ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaTipoCalculo);
        Concepto = ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaConcepto);
        Subconcepto = ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaSubconcepto);
        Descripcion = ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaDescripcion);
        porcentajeIva = Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaIVA))/100;
        m3Incluidos = new ArrayList<>();
        precioM3 = new ArrayList<>();
        switch (conceptoType){
            case SOLO_FIJO:
                if(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioFijo).isEmpty()){
                    System.out.println("Va a petar con el id "+this.id);
                }
                try{
                    fijo = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioFijo));

                }catch (Exception n){
                    System.out.println("Ha petado que flipas con el id "+this.id);
                }
                fijo = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioFijo));
                break;
            case TRAMOS_N:
            case TRAMOS_S:
                fijo = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioFijo));
                m3Incluidos.add((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaM3incluidos)));
                precioM3.add(0f);
                for (int i = 1; i < numerosLineasConcepto.size(); i++) {
                    m3Incluidos.add((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(i), columnaM3incluidos)));
                    precioM3.add(Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(i), columnaPrecioM3)));
                }
                break;
            case TODO1:
                fijo = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioFijo));
                m3Incluidos.add((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaM3incluidos)));
                precioM3.add(Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPrecioM3)));
                break;
            case DEPENDIENTE:
                porcentajeSobreOtro = Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPorcentajeOtro))/100;
                idOtroConcepto = (int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaIdOtro));
                break;
            default:
                throw new RuntimeException("Estas intentando cargar datos de un concepto que no tiene definido el tipo correctamente");
        }
    }

    public float calcularImporte(int row) {
        try{
            if(isRelativeToOther())throw new RuntimeException("No puedes usar la funcion de calculo de importe ya que depende de otro concepto");

            int metrosSinPagar = (int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaLecturaActual))-(int)Float.parseFloat(ExcelManager.getCellValue(userSheetNumber, row, columnaLecturaAnterior));
            switch (conceptoType){
                case SOLO_FIJO:
                    return fijo;
                case TRAMOS_N:
                    ArrayList<Integer> m3IncluidosClon = (ArrayList<Integer>) m3Incluidos.clone();
                    ArrayList<Float> precioM3Clon = (ArrayList<Float>) precioM3.clone();
                    float resultN = 0f+fijo;
                    int numIters = precioM3.size();
                    for (int i = 0; i < numIters; i++) {

                        if(metrosSinPagar >= m3Incluidos.get(0)){
                            metrosSinPagar = metrosSinPagar - m3Incluidos.get(0);
                            resultN += m3Incluidos.get(0) * precioM3.get(0);
                            m3Incluidos.remove(0);
                            precioM3.remove(0);
                        }else{

                            resultN += metrosSinPagar * precioM3.get(0);
                            metrosSinPagar = 0;
                        }
                    }

                    m3Incluidos = (ArrayList<Integer>) m3IncluidosClon.clone();
                    precioM3 = (ArrayList<Float>) precioM3Clon.clone();
                    return resultN;
                case TRAMOS_S:
                    ArrayList<Integer> m3IncluidosClon2 = (ArrayList<Integer>) m3Incluidos.clone();
                    ArrayList<Float> precioM3Clon2 = (ArrayList<Float>) precioM3.clone();
                    float resultS = 0f+fijo;
                    int metrosTemp = metrosSinPagar;
                    numIters = precioM3.size();
                    for (int i = 0; i < numIters; i++) {
                        if(metrosTemp > m3Incluidos.get(0)){
                            metrosTemp = metrosTemp - m3Incluidos.get(0);
                            m3Incluidos.remove(0);
                            precioM3.remove(0);
                        }
                    }
                    resultS += metrosSinPagar*precioM3.get(0);
                    m3Incluidos = (ArrayList<Integer>) m3IncluidosClon2.clone();
                    precioM3 = (ArrayList<Float>) precioM3Clon2.clone();
                    return resultS;
                case TODO1:
                    return fijo+(metrosSinPagar-m3Incluidos.get(0))*precioM3.get(0);
                case DEPENDIENTE:
                    throw new RuntimeException("Estas intentando calcular el importe de un concepto que depende de otro y no se conoce su valor");
                default:
                    throw new RuntimeException("Estas intentando calcular el importe de un concepto que no tiene definido el tipo correctamente");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public float calcularIva(int row) {
        if(isRelativeToOther())throw new RuntimeException("No puedes usar la funcion de calculo de importe ya que depende de otro concepto");
        return calcularImporte(row)*porcentajeIva;
    }

    public boolean isRelativeToOther(){
        try{
            ArrayList<Integer> numerosLineasConcepto = getLineasConcepto();
            return !ExcelManager.getCellValue(ordenanzaSheetNumber, numerosLineasConcepto.get(0), columnaPorcentajeOtro).isEmpty();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Integer> getLineasConcepto() {
        ArrayList<Integer> numerosLineasConcepto = new ArrayList<Integer>();
        try{

            for (int i = 1; i < ExcelManager.getRowsCount(ordenanzaSheetNumber); i++) {
                if((int)Float.parseFloat(ExcelManager.getCellValue(ordenanzaSheetNumber, i, columnaID)) == this.id){
                    numerosLineasConcepto.add(i);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return numerosLineasConcepto;
    }
}
