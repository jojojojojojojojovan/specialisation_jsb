package com.tmsapp.tms.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Util.HibernateUtil;

@Repository
public class ApplicationRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    static Session session;

    public Map<String, Object> createApplication(Application application){
        Map<String, Object> response = new HashMap<>();
        Transaction transaction = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.save(application);
            
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
                
            }
            response.put("success", false);
            response.put("message", "application exists");
            return response;
        }finally{
            if(session != null){
                session.close();
            }
        }
        response.put("success", true);
        return response;
    }

    public Application getApplication(String appName){
        Transaction transaction = null;
        Application result = null;
        System.out.println("appname " + appName);
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            // String hql = "FROM Application WHERE App_Acronym=:acronym";

            // Query<Application> query = session.createQuery(hql, Application.class);
            // System.out.println(" query " + query);
            // query.setParameter("acronym", appName);
            // System.out.println(appName);
            // appName = query.getSingleResult();


            result = session.get(Application.class, appName);
            System.out.println("result " + result);
            
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
System.out.println("result after " + result);
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
