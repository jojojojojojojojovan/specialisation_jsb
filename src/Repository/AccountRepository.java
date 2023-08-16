package com.tmsapp.tms.Repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Util.HibernateUtil;

import javassist.NotFoundException;

@Repository
public class AccountRepository {


    // private HibernateUtil hibernateUtil = new HibernateUtil();
    private HibernateUtil hibernateUtil;

    @Autowired
    public AccountRepository(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    static Session session;

    public boolean createAccount(Account account){
        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.save(account);
            
            result = true;
            transaction.commit();
        }
        catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            result = false;
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return result;
    }

    public boolean updateAccount(Account account){
        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.update(account);
            
            result = true;
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();

            }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return result;
    }

    public Account getAccountByUsername(String username){
        Transaction transaction = null;
        Account account = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            String hql = "FROM com.tmsapp.tms.Entity.Account WHERE username=:un";
            Query<Account> query = session.createQuery(hql, Account.class);
            query.setParameter("un", username);
            account = query.getSingleResult();

            transaction.commit();
        }
        catch(NoResultException e){
            account = null;
            e.printStackTrace();
        }
        catch(Exception e){
            if(transaction != null){
                transaction.rollback();

            }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return account;
    }

    public List<Accgroup> getGroupsByUsername(String username){
        Transaction transaction = null;
        List<Accgroup> groups = null;
        Account account = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            String hql = "FROM com.tmsapp.tms.Entity.Account WHERE username=:un";
            Query<Account> query = session.createQuery(hql, Account.class);
            query.setParameter("un", username);
            account = query.getSingleResult();
            if(account != null){
                Hibernate.initialize(account.getAccgroups());
                groups = account.getAccgroups();
            }
            transaction.commit();
        }
        catch(NoResultException e){
            account = null;
            e.printStackTrace();
        }
        catch(Exception e){
            if(transaction != null){
                transaction.rollback();

            }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return groups;
    }

}
