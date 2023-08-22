package com.tmsapp.tms.Repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Util.HibernateUtil;

@Repository
public class ApplicationRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    static Session session;

    public boolean createApplication(Application application){
        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.save(application);
            
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

    public Application getApplication(String appName){
        Transaction transaction = null;
        Application result = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            result = session.get(Application.class, appName);
            
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

    public List<Application> getAllApplication(){
        Transaction transaction = null;
        List<Application> result = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            String hql = "FROM Application";
            Query<Application> query = session.createQuery(hql, Application.class);
            result = query.getResultList();
            
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

    public Boolean updateApplication(Application application){
        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.update(application);
            
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

    public Integer getApplicationRNumber(String appAcronym){
        Transaction transaction = null;
         Integer appRnumber = 0;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            Query<Integer> query = session.createQuery(
            "SELECT a.App_Rnumber FROM Application a WHERE a.App_Acronym = :appAcronym", Integer.class
            );
            query.setParameter("appAcronym", appAcronym);
            appRnumber = query.uniqueResult();
            System.out.println("appRNumber: " + appRnumber);
            transaction.commit();
        }catch(Exception e){
            System.err.println(e);
            if(transaction != null){
                transaction.rollback();

            }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return appRnumber;
    }
}
