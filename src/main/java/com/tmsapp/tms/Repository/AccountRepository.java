package com.tmsapp.tms.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.AccountDTO;
import com.tmsapp.tms.Util.HibernateUtil;

@Repository
public class AccountRepository {

    // private HibernateUtil hibernateUtil = new HibernateUtil();
    private HibernateUtil hibernateUtil;

    @Autowired
    public AccountRepository(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    static Session session;

    public boolean createAccount(Account account) {
        Transaction transaction = null;
        Boolean result = false;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(account);

            result = true;
            transaction.commit();
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

    public boolean updateAccount(Account account) {
        Transaction transaction = null;
        Boolean result = false;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(account);
            result = true;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public Account getAccountByUsername(String username) {
        Transaction transaction = null;
        Account account = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Account WHERE username=:un";
            Query<Account> query = session.createQuery(hql, Account.class);
            query.setParameter("un", username);
            System.out.println(username);
            account = query.getSingleResult();
            System.out.println("in here getAccountByUsername ");
            if (account != null) {
                Hibernate.initialize(account.getAccgroups());
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof org.hibernate.TransactionException) {
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
        //  catch (NoResultException e) {
        //     account = null;
        //     e.printStackTrace();
        // } 
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

        return account;
    }

    public String getEmail(String username) {
        Transaction transaction = null;
        String email = null;

        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            String hql = "SELECT email FROM Account WHERE username=:un";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("un", username);

            email = query.getSingleResult();

            transaction.commit();
        } catch (NoResultException e) {
            email = null;
            e.printStackTrace();
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

        return email;
    }

    public List<Accgroup> getGroupsByUsername(String username) {
        Transaction transaction = null;
        List<Accgroup> groups = null;
        Account account = null;
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "SELECT ag FROM Accgroup ag JOIN ag.accounts acc WHERE acc.username = :un";
            Query<Accgroup> query = session.createQuery(hql, Accgroup.class);
            query.setParameter("un", username);

            groups = query.getResultList();
            // if(account != null){
            // // Hibernate.initialize(account.getAccgroups());
            // groups = account.getAccgroups();
            // }
            transaction.commit();
        } catch (NoResultException e) {
            account = null;
            e.printStackTrace();
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

        return groups;
    }

    public List<AccountDTO> getAllAccounts() {
        Transaction transaction = null;
        List<Account> accounts = null;
        List<AccountDTO> returnAccounts = new ArrayList<>();
        try {
            session = hibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "FROM com.tmsapp.tms.Entity.Account";
            TypedQuery<Account> query = session.createQuery(hql, Account.class);
            accounts = query.getResultList();

            for (Account acc : accounts) {
                String hql2 = "SELECT ag FROM Accgroup ag JOIN ag.accounts acc WHERE acc.username = :un";
                TypedQuery<Accgroup> query2 = session.createQuery(hql2, Accgroup.class);
                query2.setParameter("un", acc.getUsername());
                List<Accgroup> tempaccgroup = query2.getResultList();
                System.out.println(tempaccgroup);
                AccountDTO tempAcc = new AccountDTO(acc.getUsername(), acc.getPassword(), acc.getEmail(),
                        acc.getStatus(), tempaccgroup);
                returnAccounts.add(tempAcc);
            }
            transaction.commit();
        } catch (NoResultException e) {
            e.printStackTrace();
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

        return returnAccounts;
    }

}
