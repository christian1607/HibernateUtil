package com.celmam.siswisp.daoImpl;

import com.celmam.siswisp.dao.EntityDao;
import com.celmam.siswisp.util.FactorySession;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Christian
 * @param <T>
 */
public class EntityDaoImpl<T> implements EntityDao {

    @Override
    public <T> void createT(T entity) {
        Session session = FactorySession.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public <T> List<T> listT(Class c) {
        Session session = FactorySession.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        List<T> list = new ArrayList<>();
        try {
            tx.begin();
                Query query=session.createQuery("select a from "+ c.getName()+" a");
                list=query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public <T> void updateT(T entity) {
        Session session = FactorySession.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public <T> void deleteT(T entity,int id) {
        Session session = FactorySession.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        
        try {
            tx.begin();
            session.load(entity, id);
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public <T> T findById(Class entity, int id) {
        Session session = FactorySession.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        T t=null;
        Object object = new Object();
        try {
            tx.begin();
            t=(T) session.get(entity.getName(), id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        
        }
        return t;
        
    }
    
    

}
