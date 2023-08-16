package com.tmsapp.tms.Util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Entity.Plan;
import com.tmsapp.tms.Entity.Task;

@Component
public class HibernateUtil {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;



    private static SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				System.out.println(dbUrl);
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, dbUrl);
				settings.put(Environment.USER, dbUsername);
				settings.put(Environment.PASS, dbPassword);
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL55Dialect");
				settings.setProperty("hibernate.dialect.storage_engine", "innodb");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(settings);

				configuration.addAnnotatedClass(Account.class);
				configuration.addAnnotatedClass(Accgroup.class);
				configuration.addAnnotatedClass(Application.class);
				configuration.addAnnotatedClass(Plan.class);
				configuration.addAnnotatedClass(Task.class);
				configuration.addAnnotatedClass(JwtInvalidation.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				//System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;
			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
