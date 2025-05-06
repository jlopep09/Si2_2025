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
import POJOS.Vehiculos;
import appvehiculos.data.ExcelManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static appvehiculos.ivtmManager.cargarContribuyentes;
import static appvehiculos.ivtmManager.cargarVehiculos;
import static appvehiculos.ivtmManager.gestionarCCCs;
import static appvehiculos.ivtmManager.gestionarIbanEmail;
import static appvehiculos.ivtmManager.gestionarNifNies;
import java.util.Scanner;

/**
 * 
 * @author José Antonio López Pérez
 */
public class AppVehiculos {

    public static void main(String[] args) throws IOException {
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 1. Cargamos en memoria todas las filas de contribuyentes 
        //                                |==> Listado Contribuyentes
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        System.out.println("Iniciando aplicación de gestion IVTM");
        ExcelManager excelVehiculos = new ExcelManager("resources/", "SistemasVehiculos", "");
        ArrayList listadoContribuyentes = new ArrayList<Contribuyente>(); //usaremos el id del contribuyente almacenando la fila del excel
        cargarContribuyentes(listadoContribuyentes, excelVehiculos);
        
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 2. Comprobamos nifnies y creamos un nuevo listado           
        //   Listado Contribuyentes |==> Listado Contribuyentes nifnie validos
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        ArrayList listadoContribuyentesNifNieValidos = new ArrayList<Contribuyente>();
        gestionarNifNies(listadoContribuyentes, listadoContribuyentesNifNieValidos, excelVehiculos);
        
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 3. Comprobamos ccc y creamos un nuevo listado           
        //   Listado Contribuyentes |==> Listado Contribuyentes ccc validos
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        ArrayList listadoContribuyentesCCCValidos = new ArrayList<Contribuyente>();
        gestionarCCCs(listadoContribuyentes, listadoContribuyentesCCCValidos, excelVehiculos);
        
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 4. Creamos nueva lista comparando las anteriores y generamos iban y correo       
        //   Listado ccc validos && Listado nifnie validos |==> Listado Contribuyentes validos
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        ArrayList listadoContribuyentesValidos = new ArrayList<Contribuyente>();
        for (int i = 0; i < listadoContribuyentesNifNieValidos.size() ; i++) {
            Contribuyente cont = (Contribuyente) listadoContribuyentesNifNieValidos.get(i);
            if(listadoContribuyentesCCCValidos.contains(cont)){
                listadoContribuyentesValidos.add(cont);
            }
        }
        gestionarIbanEmail(listadoContribuyentesValidos, excelVehiculos);
        
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 5. Cargamos en memoria listado de vehiculos y generamos recibos cuando todo sea valido
        //                                   |==> Listado Vehiculos
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        ArrayList listadoVehiculos = new ArrayList<Vehiculos>(); //usaremos el id del contribuyente almacenando la fila del excel
        cargarVehiculos(listadoVehiculos, excelVehiculos);
        
        boolean stop = false;
        while (!stop){
            GeneradorRecibo generadorRecibo= new GeneradorRecibo();
            String periodo = askPeriod();
            generadorRecibo.generarRecibos(periodo);
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
        
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        //     Paso 6. Actualizaciones en BBDD
        // ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
        
        excelVehiculos.closeExcel();
        HibernateUtil.shutdown();
        System.out.println("Ejecución terminada");
        
        /**
        ExcelManager excelOrdenanzas = new ExcelManager("resources/", "SistemasOrdenanzas", "");
        excelOrdenanzas.closeExcel();
        * */
        

    }
    private static String askPeriod(){
        System.out.println("Por favor, introduzca el año de generación de recibos. (p.e. 2024)");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
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
