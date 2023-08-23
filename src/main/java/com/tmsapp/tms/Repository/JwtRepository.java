package com.tmsapp.tms.Repository;

import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Util.HibernateUtil;

import io.jsonwebtoken.Jwt;

@Repository
@Transactional
public class JwtRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    private Session session;

    public boolean InvalidateJWT(JwtInvalidation jwtInvalidation){
        // Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            // transaction =session.beginTransaction();
            session.save(jwtInvalidation);
            
            result = true;
            // transaction.commit();
        }catch(Exception e){
            // if(transaction != null){
            //     transaction.rollback();

            // }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return result;
    }

    public JwtInvalidation checkInvalidateJwt(String jwt){
        // Transaction transaction = null;
        JwtInvalidation result = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            // transaction =session.beginTransaction();
            String hql = "FROM com.tmsapp.tms.Entity.JwtInvalidation WHERE jwtToken=:jwt";
            Query<JwtInvalidation> query = session.createQuery(hql, JwtInvalidation.class);
            query.setParameter("jwt", jwt);
            result = query.getSingleResult();
            
            // transaction.commit();
        }
        catch(NoResultException e){
            result = null;
        }
        catch(GenericJDBCException e) {
            result = null;
        }
        catch(Exception e){
            // if(transaction != null){
            //     transaction.rollback();

            // }
            e.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }

        return result;
    }
}
