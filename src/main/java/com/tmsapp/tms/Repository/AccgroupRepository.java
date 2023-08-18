package com.tmsapp.tms.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Util.HibernateUtil;
import org.hibernate.query.Query;

@Repository
public class AccgroupRepository {

    @Autowired
    private HibernateUtil hibernateUtil;
    static Session session;

    public boolean createAccgroup(Accgroup accgroup) {
        Transaction transaction = null;
        Boolean result = false;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(accgroup);

            result = true;
            transaction.commit();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                // Handle the specific exception for duplicate key violation
                result = false;
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                result = false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            result = false;
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<Accgroup> getAllAccGroup() {
        Transaction transaction = null;
        List<Accgroup> result = new ArrayList<>();
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Accgroup";
            Query<Accgroup> queryResult = session.createQuery(hql, Accgroup.class);
            result = queryResult.getResultList();
            transaction.commit();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof NoResultException) {
                // Handle the specific exception for duplicate key violation
                result.add(new Accgroup("No group created"));
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } 
        
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public boolean updateGroup(Accgroup accgroup) {
        Transaction transaction = null;
        Boolean result = false;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(accgroup);

            transaction.commit();
            result = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public Accgroup getGroupByGroupName(String groupName) {
        Transaction transaction = null;
        Accgroup result = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Accgroup WHERE groupName=:gn";
            Query<Accgroup> queryResult = session.createQuery(hql, Accgroup.class);
            queryResult.setParameter("gn", groupName);
            result = queryResult.getSingleResult();

            transaction.commit();
        } 
        catch(NoResultException e){
            result = null;
            e.printStackTrace();
        } 
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }
}
