/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appvehiculos;

import POJOS.Contribuyente;
import POJOS.HibernateUtil;
import POJOS.Recibos;
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
    public static boolean saveContribuyente(Contribuyente contribuyente){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(contribuyente);
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
    public static boolean saveRecibo(Recibos recibo){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(recibo);
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
