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
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author José Antonio López Pérez
 */
public class AppVehiculos {
    
    public static void dia1(){
         // TODO code application logic here

        System.out.println("Iniciando aplicación de gestion IVTM");
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        System.out.println("Por favor, introduzca el nif de un contribuyente: ");
        Scanner sc = new Scanner(System.in);
        String nif = sc.nextLine(); //Ejemplo de nif 09703447T
        System.out.println("Se ha introducido el nif: " + nif);
        
        Query query = sesion.createQuery("SELECT cont FROM Contribuyente cont WHERE cont.nifnie = ?");
        query.setParameter(0, nif);
        List <Contribuyente> contribuyentes = query.list();
        if(contribuyentes.isEmpty()){
            System.out.println("No se ha encontrado ningun contribuyente con nif "+nif);
        }else{
            Contribuyente contribuyente = contribuyentes.get(0);
            System.out.println("Datos del contribuyente");
            System.out.println(" - Nombre: "+contribuyente.getNombre());
            System.out.println(" - Apellidos: " + contribuyente.getApellido1() + " " + contribuyente.getApellido2());
            System.out.println(" - Nif: "+ contribuyente.getNifnie());
            System.out.println(" - Dirección: "+ contribuyente.getDireccion() + " nº " +contribuyente.getNumero());
            
            System.out.println("Cambiando el importe de sus recibos a 115 euros");
            Set<Recibos> recibos = contribuyente.getReciboses();
            for(Recibos r : recibos){
                System.out.print(" - Cambiado valor de recibo, anterior valor: "+r.getTotalRecibo() + ", nuevo valor: ");
                r.setTotalRecibo(115);
                System.out.println(r.getTotalRecibo());
            }
            System.out.println("Guardando datos en bbdd...");
            Transaction tx = sesion.beginTransaction();
                sesion.saveOrUpdate(contribuyente);
            tx.commit();
            System.out.println("Datos guardados correctamente");
            System.out.println("Eliminando recibos del contribuyente cuyo importe sea menor a la media global de recibos en bbdd");
            query = sesion.createQuery("SELECT recib FROM Recibos recib");
            List <Recibos> listadoRecibosTotales = query.list();
            double valorMedioRecibos = 0;
            for(Recibos r : listadoRecibosTotales){
                valorMedioRecibos += r.getTotalRecibo();
            }
            valorMedioRecibos = valorMedioRecibos / listadoRecibosTotales.size();
            System.out.println("Importe medio de recibos en bbdd: " + valorMedioRecibos);
            System.out.println("Listado actual de recibos del contribuyente");
            for(Recibos r : recibos){
                System.out.println(" - Valor del recibo "+r.getNumRecibo() + ": "+r.getTotalRecibo());
            }
            System.out.println("Listado tras eliminar los recibos");
            for(Recibos r : recibos){
                if(r.getTotalRecibo()<valorMedioRecibos){
                    tx = sesion.beginTransaction();
                    String HQLborrado = "DELETE Recibos r WHERE r.numRecibo=:param1";
                    sesion.createQuery(HQLborrado).setParameter("param1", r.getNumRecibo()).executeUpdate();
                    tx.commit();
                }
            }
            sesion.refresh(contribuyente);
            recibos = contribuyente.getReciboses();
            for(Recibos r : recibos){
                System.out.println(" - Valor del recibo "+r.getNumRecibo() + ": "+r.getTotalRecibo());
            }
        }

        
        sesion.close();
        HibernateUtil.shutdown();
        System.out.println("Ejecución terminada");
    
    }

    public static void main(String[] args) {
        //dia1();
        //EL ORDEN DE LAS COLUMNAS NO VAN A CAMBAIR, LOS TIOS DE DATOS DE LAS COLUMNAS NO VAN A CAMBIAR.
        //eN LAS ORDENANZAS IGUAL, LOS TRAMOS PARA LOS TURISMOS SON IGUALES EN TODA ESPAÑA, NO DEBERIAN CAMBIAR
    }
    
}
