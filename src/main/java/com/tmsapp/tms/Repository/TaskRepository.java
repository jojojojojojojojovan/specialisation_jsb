package com.tmsapp.tms.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmsapp.tms.Entity.Plan;
import com.tmsapp.tms.Entity.Task;
import com.tmsapp.tms.Entity.TaskDTO;
import com.tmsapp.tms.Util.HibernateUtil;

@Repository
public class TaskRepository {


    // private HibernateUtil hibernateUtil = new HibernateUtil();
    @Autowired
    private HibernateUtil hibernateUtil;

    static Session session;

    public boolean createTask(Task task){

        System.out.println("application: " + task.getTaskAppAcronym());
        System.out.println("plan: " + task.getTaskPlan());
        //TODO: 

        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            session.save(task);
            
            transaction.commit();
            result = true;
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

    public TaskDTO getTaskById(String taskId) {
        Task task = null;
        Transaction transaction = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            System.out.println(taskId);
            task = session.get(Task.class, taskId);
            System.out.println(task);
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

        return new TaskDTO(task);
    }

    public List<TaskDTO> getTasksByPlan(String taskPlan) {
        Transaction transaction = null;
         List<TaskDTO> tasksDTO = new ArrayList<>();
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            System.out.println(taskPlan);
            String hql = "FROM Task WHERE task_plan = :taskPlan";
            
            Query<Task> query = session.createQuery(hql, Task.class);
            query.setParameter("taskPlan", taskPlan);
            List<Task> tasks = query.getResultList();
            tasksDTO = !tasks.isEmpty()? tasks.stream().map(task -> new TaskDTO(task)).collect(Collectors.toList()): null;
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

        return tasksDTO;
    }

    public List<TaskDTO> getTasksByApplication(String appAcronym) {
        Transaction transaction = null;
         List<TaskDTO> tasksDTO = new ArrayList<>();
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();
            System.out.println(appAcronym);
            String hql = "SELECT new Task(t.taskName, t.taskDescription, t.taskNotes, t.taskId, t.taskPlan, t.taskAppAcronym, t.taskState, t.taskCreator, t.taskOwner, t.taskCreateDate) FROM Task t INNER JOIN t.taskAppAcronym a WHERE a.App_Acronym = :appAcronym";
            Query<Task> query = session.createQuery(hql, Task.class);
            query.setParameter("appAcronym", appAcronym);
            List<Task> tasks = query.getResultList();
            for(Task task: tasks) {
                System.out.println(task.getTaskPlan());
            }

            String hql2 = "FROM Task t INNER JOIN t.taskAppAcronym a WHERE a.App_Acronym = :appAcronym";
            Query<Task> query2 = session.createQuery(hql, Task.class);
            query2.setParameter("appAcronym", appAcronym);
            List<Task> tasks2 = query2.getResultList();
            for(Task task: tasks2) {
                System.out.println(task.getTaskPlan().getPlan_MVP_name());
            }
            tasksDTO = !tasks.isEmpty()? tasks.stream().map(task -> new TaskDTO(task)).collect(Collectors.toList()): null;
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

        return tasksDTO;
    }

    public List<TaskDTO> getAllTask() {
        List <Task> tasks = new ArrayList<>();
        Transaction transaction = null;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            String hql = "FROM Task";
            tasks = session.createQuery(hql,Task.class).getResultList();       
            // for (Task tt : tasks) {
            //     String hql2 = "SELECT ta.Plan_MVP_name from task ta JOIN ta.taskPlan WHERE ta,taskId=:tid";
            //     Query<Plan> query2 = session.createQuery(hql, Plan.class);
            //     Plan tempPlan = query2.getSingleResult();
            //     System.out.println(tempPlan.getPlan_MVP_name());
            //     tt.setTaskPlan(tempPlan);
            // }     
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
        List<TaskDTO> tasksDTO = tasks != null ? tasks.stream().map(task -> new TaskDTO(task)).collect(Collectors.toList()) : null;
        return tasksDTO;
    }

    public boolean updateTask(Task task){
        Transaction transaction = null;
        Boolean result = false;
        try{
            session = hibernateUtil.getSessionFactory().openSession();
            transaction =session.beginTransaction();

            session.update(task);
            
            transaction.commit();
            result = true;
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

}
