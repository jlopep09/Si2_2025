package appvehiculos;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import POJOS.Recibos;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author José Antonio López Pérez
 */
public class AppVehiculos {

    public static void main(String[] args) {
        System.out.println("Iniciando aplicación de gestion IVTM");
        
        practica1(askUserForNif());
        
        HibernateUtil.shutdown();
        System.out.println("Ejecución terminada");

    }
    public static String askUserForNif(){
        System.out.println("Por favor, introduzca el nif de un contribuyente: ");
        Scanner sc = new Scanner(System.in);
        String nif = sc.nextLine(); //Ejemplo de nif 09703447T
        System.out.println("Se ha introducido el nif: " + nif);
        return nif;
    }
    public static void practica1(String nif){
         // TODO code application logic here

        List<Contribuyente> contribuyentes = DatabaseController.getContribuyentesByNif(nif);
        
        if(contribuyentes.isEmpty()){
            System.out.println("No se ha encontrado ningun contribuyente con nif "+nif);
        }else{
            Contribuyente contribuyente = contribuyentes.get(0);
            System.out.println("Datos del contribuyente");
            System.out.println(" - Nombre: "+contribuyente.getNombre());
            System.out.println(" - Apellidos: " + contribuyente.getApellido1() + " " + contribuyente.getApellido2());
            System.out.println(" - Nif: "+ contribuyente.getNifnie());
            System.out.println(" - Dirección: "+ contribuyente.getDireccion() + " nº " +contribuyente.getNumero());
            //-------------------
            System.out.println("Cambiando el importe de sus recibos a 115 euros");
            List<Recibos> recibos = DatabaseController.getRecibos(contribuyente);
            for(Recibos r : recibos){
                System.out.print(" - Cambiado valor de recibo, anterior valor: "+r.getTotalRecibo() + ", nuevo valor: ");
                r.setTotalRecibo(115);
                System.out.println(r.getTotalRecibo());
                DatabaseController.saveRecibo(r);
            }
            //-------------------
            System.out.println("Guardando datos en bbdd...");
            DatabaseController.saveContribuyente(contribuyente);
            System.out.println("Datos guardados correctamente");
            //-------------------
            System.out.println("Eliminando recibos del contribuyente cuyo importe sea menor a la media global de recibos en bbdd");
            List <Recibos> listadoRecibosTotales = DatabaseController.getRecibos();
            double valorMedioRecibos = 0;
            for(Recibos r : listadoRecibosTotales){
                valorMedioRecibos += r.getTotalRecibo();
            }
            valorMedioRecibos = valorMedioRecibos / listadoRecibosTotales.size();
            System.out.println("Importe medio de recibos en bbdd: " + valorMedioRecibos);
            System.out.println("Listado actual de recibos del contribuyente");
            recibos = DatabaseController.getRecibos(contribuyente);
            for(Recibos r : recibos){
                System.out.println(" - Valor del recibo "+r.getNumRecibo() + ": "+r.getTotalRecibo());
            }
            System.out.println("Listado tras eliminar los recibos");
            
            for(Recibos r : recibos){
                if(r.getTotalRecibo()<valorMedioRecibos){
                    DatabaseController.deleteRecibo(r);
                }
            }
            //Actualizamos los listados a su version mas reciente de bbdd
            recibos = DatabaseController.getRecibos(contribuyente);
            for(Recibos r : recibos){
                System.out.println(" - Valor del recibo "+r.getNumRecibo() + ": "+r.getTotalRecibo());
            }
        }
    }
    
}
