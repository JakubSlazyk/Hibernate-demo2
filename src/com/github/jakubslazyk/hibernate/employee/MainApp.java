package com.github.jakubslazyk.hibernate.employee;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration()
										.configure("hibernate.cfg.xml")
										.addAnnotatedClass(Employee.class)
										.buildSessionFactory();
		try{
			Session mySession = sessionFactory.getCurrentSession();
			//saving object
			
			String firstName="Jakub",lastName="Slazyk",company="Company2";
			
			addEmployee(mySession, firstName, lastName, company);
			
			//retrieving object
			int id=2;
			
			searchEmployee(sessionFactory, id);
			
			//query objects
			
			String tempCompany="Company2";
			searchByCompany(sessionFactory, tempCompany);
			
			//deleting objects
			
			deleteEmployee(sessionFactory, id);
			
			
		}finally{
			sessionFactory.close();
		}
	
	
	}

	private static void deleteEmployee(SessionFactory sessionFactory, int id) {
		Session mySession;
		mySession= sessionFactory.getCurrentSession();
		mySession.getTransaction();
		mySession.createQuery("delete Employee where id="+id).executeUpdate();
		mySession.getTransaction().commit();
	}

	private static void searchByCompany(SessionFactory sessionFactory, String tempCompany) {
		Session mySession;
		mySession=sessionFactory.getCurrentSession();
		mySession.beginTransaction();
		List <Employee>myEmployees=mySession.createQuery("from Employee e where e.company like '"+tempCompany+"'").getResultList();
		for(Employee employ:myEmployees)
			System.out.println(employ);
	}

	private static void searchEmployee(SessionFactory sessionFactory, int id) {
		Session mySession;
		mySession = sessionFactory.getCurrentSession();
		mySession.beginTransaction();
		Employee tempEmployee=(Employee) mySession.createQuery("from Employee where id="+id).getSingleResult();
		System.out.println(tempEmployee);
		mySession.getTransaction().commit();
	}

	private static void addEmployee(Session mySession, String firstName, String lastName, String company) {
		
		mySession.beginTransaction();
		Employee myEmployee = new Employee(firstName,lastName,company);
		mySession.save(myEmployee);
		mySession.getTransaction().commit();
	}
	
}
