package com.iticbcn.aitormontejo.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.iticbcn.aitormontejo.model.Aeropuerto;
import com.iticbcn.aitormontejo.model.Avion;
import com.iticbcn.aitormontejo.model.Piloto;
import com.iticbcn.aitormontejo.model.Vuelo;

public abstract class GenDAOImpl<T> implements GenDAO<T> {
    // propiedades
    private SessionFactory factory;
    private Class<T> classe;

    // constructor
    public GenDAOImpl(SessionFactory factory, Class<T> classe) {
        this.factory = factory;
        this.classe = classe;
    }

    // metodos
    @Override
    public void save(T entity) {
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            if (entity instanceof Vuelo vuelo) {
                vuelo.setAvion(session.get(Avion.class, vuelo.getAvion().getIdAvion()));
                vuelo.setPiloto(session.get(Piloto.class, vuelo.getPiloto().getId()));
                vuelo.setOrigen(session.get(Aeropuerto.class, vuelo.getOrigen().getId()));
                vuelo.setDestino(session.get(Aeropuerto.class, vuelo.getDestino().getId()));
            }

            session.persist(entity);

            session.getTransaction().commit();

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(T entity) {
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            session.merge(entity);

            session.getTransaction().commit();

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(T entity) {
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            session.remove(entity);

            session.getTransaction().commit();

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public T get(int id) {
        Session session = factory.openSession();

        try {

            T entity = session.find(classe, id);

            if(entity!=null) return entity;

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return null;
    }

    @Override
    public List<T> getAll() {
        List<T> entitys = null;
        Session session = factory.openSession();

        try {

            String query = "FROM " + classe.getSimpleName();
            entitys = session.createQuery(query, classe).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return entitys;
    }
}
