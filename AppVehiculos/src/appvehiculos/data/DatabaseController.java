/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appvehiculos.data;

import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import POJOS.Ordenanza;
import POJOS.Recibos;
import POJOS.Vehiculos;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author José Antonio López Pérez
 */
public class DatabaseController {

    public static List getContribuyentesByNif(String nif){
        Session session = null;
        List<Contribuyente> contribuyentes = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT cont FROM Contribuyente cont WHERE cont.nifnie = ?");
            query.setParameter(0, nif);
            contribuyentes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return contribuyentes;
    }
    public static List getRecibos(){
        Session session = null;
        List<Recibos> recibos = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT recib FROM Recibos recib");
            recibos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return recibos;
    }
    public static List getRecibos(Contribuyente cont){
        Session session = null;
        List<Recibos> recibos = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT recib FROM Recibos recib WHERE recib.contribuyente = ?");
            query.setParameter(0, cont);
            recibos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return recibos;
    }
    
public static boolean saveContribuyente(Contribuyente contribuyente) {
    Session session = null;
    Transaction tx = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        // 1) busca por nifnie
        Contribuyente existente = session.createQuery(
            "FROM Contribuyente WHERE nifnie = :nifnie", Contribuyente.class)
            .setParameter("nifnie", contribuyente.getNifnie())
            .uniqueResult();

        if (existente != null) {
            contribuyente.setIdContribuyente(existente.getIdContribuyente());
        } else {
            contribuyente.setIdContribuyente(0);
        }

        // 2) mergea y recupera la instancia gestionada
        Contribuyente gestionado = (Contribuyente) session.merge(contribuyente);

        // 3) COPIA EL ID EN EL OBJETO ORIGINAL
        contribuyente.setIdContribuyente(gestionado.getIdContribuyente());
        if (contribuyente.getIdContribuyente() == 0) {
                    Contribuyente guardado = (Contribuyente) session.createQuery(
                        "FROM Contribuyente WHERE nifnie = :nif")
                        .setParameter("nif", contribuyente.getNifnie())
                        .uniqueResult();
                    if (guardado != null) {
                        contribuyente.setIdContribuyente(guardado.getIdContribuyente());
                    }
                }
        tx.commit();
        return true;
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
    } finally {
        if (session != null) session.close();
    }
}
public static boolean saveOrdenanza(Ordenanza ordenanza) {
    Session session = null;
    Transaction tx = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        // 1) Consultar si ya existe
        Query<Ordenanza> query = session.createQuery(
            "FROM Ordenanza WHERE ayuntamiento = :ayuntamiento " +
            "AND tipoVehiculo = :tipoVehiculo " +
            "AND unidad = :unidad " +
            "AND minimo_rango = :minimoRango " +
            "AND maximo_rango = :maximoRango",
            Ordenanza.class);
        query.setParameter("ayuntamiento", ordenanza.getAyuntamiento());
        query.setParameter("tipoVehiculo", ordenanza.getTipoVehiculo());
        query.setParameter("unidad", ordenanza.getUnidad());
        query.setParameter("minimoRango", ordenanza.getMinimoRango());
        query.setParameter("maximoRango", ordenanza.getMaximoRango());

        Ordenanza existente = query.uniqueResult();
        if (existente != null) {
            ordenanza.setId(existente.getId());
        } else {
            ordenanza.setId(0);
        }

        // 2) Merge para insertar o actualizar
        session.merge(ordenanza);
        tx.commit();
        session.close();  // cerramos la sesión para evitar conflictos

        // 3) Si sigue en 0, volvemos a abrir otra sesión y recargamos
        if (ordenanza.getId() == 0) {
            Session session2 = HibernateUtil.getSessionFactory().openSession();
            Query<Ordenanza> query2 = session2.createQuery(
                "FROM Ordenanza WHERE ayuntamiento = :ayuntamiento " +
                "AND tipoVehiculo = :tipoVehiculo " +
                "AND unidad = :unidad " +
                "AND minimo_rango = :minimoRango " +
                "AND maximo_rango = :maximoRango",
                Ordenanza.class);
            query2.setParameter("ayuntamiento", ordenanza.getAyuntamiento());
            query2.setParameter("tipoVehiculo", ordenanza.getTipoVehiculo());
            query2.setParameter("unidad", ordenanza.getUnidad());
            query2.setParameter("minimoRango", ordenanza.getMinimoRango());
            query2.setParameter("maximoRango", ordenanza.getMaximoRango());

            Ordenanza guardado = query2.uniqueResult();
            if (guardado != null) {
                ordenanza.setId(guardado.getId());
            }
            session2.close();
        }

        return true;
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
    } finally {
        // Aseguramos cerrar en caso de excepción
        if (session != null && session.isOpen()) session.close();
    }
}

public static boolean saveVehiculo(Vehiculos vehiculo) {
    Session session = null;
    Transaction tx = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        // A) Mergea al contribuyente asociado y actualiza el id
        Contribuyente cont = vehiculo.getContribuyente();
        Contribuyente gestionadoCont = (Contribuyente) session.merge(cont);
        cont.setIdContribuyente(gestionadoCont.getIdContribuyente());
        vehiculo.setContribuyente(cont);

        // B) Busca si ya existe ese vehículo
        Vehiculos existente = session.createQuery(
            "FROM Vehiculos WHERE matricula = :matricula", Vehiculos.class)
            .setParameter("matricula", vehiculo.getMatricula())
            .uniqueResult();

        if (existente != null) {
            vehiculo.setIdVehiculo(existente.getIdVehiculo());
        } else {
            vehiculo.setIdVehiculo(0);
        }

        // C) Mergea el vehículo y copia el id en el original
        Vehiculos gestionadoVeh = (Vehiculos) session.merge(vehiculo);
        vehiculo.setIdVehiculo(gestionadoVeh.getIdVehiculo());
        
        if (vehiculo.getIdVehiculo() == 0) {
                Vehiculos guardado = (Vehiculos) session.createQuery(
                     "FROM Vehiculos WHERE matricula = :matricula")
                    .setParameter("matricula", vehiculo.getMatricula())
                    .uniqueResult();
                if (guardado != null) {
                    vehiculo.setIdVehiculo(guardado.getIdVehiculo());
                }
            }

        
        tx.commit();
        return true;
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
    } finally {
        if (session != null) session.close();
    }
}


public static boolean saveRecibo(Recibos recibo) {
    Session session = null;
    Transaction tx = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

        // Mergea primero al contribuyente y al vehículo
        Contribuyente cont = (Contribuyente)session.merge(recibo.getContribuyente());
        recibo.getContribuyente().setIdContribuyente(cont.getIdContribuyente());

        Vehiculos veh = (Vehiculos)session.merge(recibo.getVehiculos());
        recibo.setVehiculos(veh);
        recibo.setNifContribuyente(cont.getNifnie());

        // Busca si ya existe
        Recibos existente = session.createQuery(
            "FROM Recibos r WHERE r.nifContribuyente = :nif AND r.fechaPadron = :fecha AND r.vehiculos.matricula = :mat",
            Recibos.class)
            .setParameter("nif", recibo.getNifContribuyente())
            .setParameter("fecha", recibo.getFechaPadron())
            .setParameter("mat", recibo.getVehiculos().getMatricula())
            .uniqueResult();

        if (existente != null) {
            recibo.setNumRecibo(existente.getNumRecibo());
        } else {
            recibo.setNumRecibo(0);
        }

        // Mergea el recibo y copia su id si hace falta
        Recibos gestionadoRec = (Recibos) session.merge(recibo);
        recibo.setNumRecibo(gestionadoRec.getNumRecibo());
        
        if (recibo.getNumRecibo() == 0) {
                Recibos guardado = (Recibos) session.createQuery(
                     "FROM Recibos r WHERE r.nifContribuyente = :nif AND r.fechaPadron = :fecha AND r.vehiculos.matricula = :mat")
                    .setParameter("nif", recibo.getContribuyente().getNifnie())
                    .setParameter("fecha", recibo.getFechaPadron())
                    .setParameter("mat", recibo.getVehiculos().getMatricula())
                        
                    .uniqueResult();
                if (guardado != null) {
                    recibo.setNumRecibo(guardado.getNumRecibo());
                }
            }
        tx.commit();
        return true;
    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
    } finally {
        if (session != null) session.close();
    }
}

    
    public static boolean deleteRecibo(Recibos recibo){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(recibo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }
}
