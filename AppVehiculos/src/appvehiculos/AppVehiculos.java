package appvehiculos;
import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import POJOS.Ordenanza;
import POJOS.Recibos;
import POJOS.Vehiculos;
import appvehiculos.data.ExcelManager;
import java.util.ArrayList;
import appvehiculos.recibos.GeneradorRecibo;
import java.util.Scanner;
import appvehiculos.utilities.Utilities;
import appvehiculos.utilities.IvtmManager;
import static appvehiculos.utilities.IvtmManager.subirContribuyentesOrdenanzasBBDD;
import static appvehiculos.utilities.IvtmManager.subirRecibosBBDD;
import java.io.IOException;
import java.text.ParseException;
/**
 * 
 * @author José Antonio López Pérez
 */
public class AppVehiculos {

    
    public static void main(String[] args) {
        System.out.println("Iniciando aplicación de gestion IVTM");
        try{
            runApp();
        }catch(Exception e){
            System.out.println("Se ha producido un error fatal. Ejecución terminada.");
            e.printStackTrace();
        }
        System.out.println("Ejecución terminada");
    }
    
    
    private static void runApp() throws IOException, ParseException  {
        //   ==== ==== ==== ====  Paso 1. Cargamos en memoria todas las filas de contribuyentes  ==== ==== ==== ====
       
        ExcelManager excelVehiculos = new ExcelManager("resources/", "SistemasVehiculos", "");
        ArrayList<Contribuyente> listadoContribuyentes = new ArrayList<Contribuyente>(); //usaremos el id del contribuyente almacenando la fila del excel
        IvtmManager.cargarContribuyentes(listadoContribuyentes, excelVehiculos);
        
        //   ==== ==== ==== ====  Paso 2. Comprobamos nifnies y creamos un nuevo listado   ==== ==== ==== ====
        ArrayList<Contribuyente> listadoContribuyentesNifNieValidos = new ArrayList<Contribuyente>();
        IvtmManager.gestionarNifNies(listadoContribuyentes, listadoContribuyentesNifNieValidos, excelVehiculos);

        //   ==== ==== ==== ====  Paso 3. Comprobamos ccc y creamos un nuevo listado  ==== ==== ==== ====         
        ArrayList<Contribuyente> listadoContribuyentesCCCValidos = new ArrayList<Contribuyente>();
        IvtmManager.gestionarCCCs(listadoContribuyentes, listadoContribuyentesCCCValidos, excelVehiculos);

        //  ==== ==== ==== ====   Paso 4. Creamos nueva lista comparando las anteriores y generamos iban y correo   ==== ==== ==== ====    
        ArrayList<Contribuyente> listadoContribuyentesValidos = new ArrayList<Contribuyente>();
        for (int i = 0; i < listadoContribuyentesNifNieValidos.size() ; i++) {
            Contribuyente cont = (Contribuyente) listadoContribuyentesNifNieValidos.get(i);
            if(listadoContribuyentesCCCValidos.contains(cont)){
                listadoContribuyentesValidos.add(cont);
            }
        }
        IvtmManager.gestionarIbanEmail(listadoContribuyentesValidos, excelVehiculos);

        //  ==== ==== ==== ====   Paso 5. Cargamos en memoria listado de vehiculos y generamos recibos cuando todo sea valido   ==== ==== ==== ====
        ArrayList<Vehiculos> listadoVehiculos = new ArrayList<Vehiculos>(); //usaremos el id del contribuyente almacenando la fila del excel
        IvtmManager.cargarVehiculos(listadoVehiculos, excelVehiculos);
        ExcelManager excelOrdenanzas = new ExcelManager("resources/", "SistemasOrdenanzas", "");
        ArrayList<Ordenanza> listadoOrdenanzas = new ArrayList<Ordenanza>();
        IvtmManager.cargarOrdenanzas(listadoOrdenanzas, excelOrdenanzas);

        subirContribuyentesOrdenanzasBBDD(listadoContribuyentesValidos, listadoOrdenanzas);
        boolean stop = false;
        ArrayList<Recibos> nuevosRecibos = new ArrayList<Recibos>();
        while (!stop){
            GeneradorRecibo generadorRecibo= new GeneradorRecibo(listadoOrdenanzas, listadoVehiculos, listadoContribuyentesValidos);
            String periodo = Utilities.askPeriod();
            generadorRecibo.generarRecibos(periodo, nuevosRecibos);
            //  ==== ==== ==== ====   Paso 6. Actualizaciones en BBDD  ==== ==== ==== ====
            
            subirRecibosBBDD(nuevosRecibos);
            nuevosRecibos.clear();
            System.out.println("Generados el reporte de recibos en formato XML y PDF con resumen PDF incluido");
            System.out.println("_____________________________________________________");
            System.out.println("| Introduzca el numero de la acción deseada:        |");
            System.out.println("|     Generar recibos de otro año:      --> [1] |");
            System.out.println("|     Finalizar ejecución de la aplicación: --> [2] |");
            System.out.println("_____________________________________________________");
            Scanner sc = new Scanner(System.in);

            if(sc.nextInt() == 2){
                stop = true;
            }
        }
        excelVehiculos.closeExcel();
        HibernateUtil.shutdown();
        excelOrdenanzas.closeExcel();
    }
}

