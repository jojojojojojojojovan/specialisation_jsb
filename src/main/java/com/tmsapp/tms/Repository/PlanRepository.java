package com.tmsapp.tms.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Plan;
import com.tmsapp.tms.Entity.PlanDTO;
import com.tmsapp.tms.Util.HibernateUtil;

@Repository
public class PlanRepository {
    
    @Autowired
    private HibernateUtil hibernateUtil;
    static Session session;
    
    public Map<String, Object> createPlan(Plan plan){
        Transaction transaction = null;
        Map<String, Object> result = new HashMap<>();
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.save(plan);
            
            transaction.commit();
            result.put("success", true);
        }catch(PersistenceException e) {
            result.put("success", false);
            result.put("message", "Plan name already exists");
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
        return result;
    }

     public List<Plan> getAllPlan(){
        Transaction transaction = null;
        List<Plan> plans = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            String hql = "from com.tmsapp.tms.Entity.Plan";
            Query<Plan> query = session.createQuery(hql, Plan.class);
            plans = query.getResultList();
            

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

        return plans;
    }

    public Plan getPlansByPlanName(String planName){
        Transaction transaction = null;
        Plan result = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            String hql = "from com.tmsapp.tms.Entity.Plan where Plan_MVP_name=:pn";
            Query<Plan> query = session.createQuery(hql, Plan.class);
            query.setParameter("pn", planName);
            result = query.getSingleResult();
            

            transaction.commit();
        }        catch (PersistenceException e) {
            if (e.getCause() instanceof GenericJDBCException) {
                // Handle the specific exception for duplicate key violation
                // result = false;
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                // result = false;
            }
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

        return result;
    }

    @Transactional
    public List<Plan> getPlansByAppAcronym(String appAcronym) {
        List<Plan> plans = new ArrayList<>();
        Transaction transaction = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            String hql = "SELECT new Plan(p.Plan_MVP_name, p.Plan_startDate, p.Plan_endDate, p.Colour) FROM Plan p INNER JOIN p.application a WHERE a.App_Acronym=:appAcronym";
            Query<Plan> query = session.createQuery(hql, Plan.class);
            query.setParameter("appAcronym", appAcronym);
            plans = query.getResultList();
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
        return plans;
    }
}
