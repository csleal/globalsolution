package br.com.fiap.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.fiap.model.Visitor;

public class VisitorDao {
	
	@Inject
	@RequestScoped
	private EntityManager manager;
	
	public void create(Visitor visitor) {
		manager.getTransaction().begin();
		manager.persist(visitor);
		manager.getTransaction().commit();
		
		manager.clear();
		manager.close();

	}
	
	public List<Visitor> listAll() {
//		TypedQuery<Setup> query = manager.createQuery("SELECT s FROM Setup s", Setup.class);
//		return query.getResultList();
		
		return manager.createQuery("SELECT s FROM Visitor s", Visitor.class).getResultList();
	}
	
	public void remove(Visitor visitor) {
		manager.getTransaction().begin();
		manager.remove(visitor);
		manager.getTransaction().commit();
		
		manager.close();
	}

	public void update(Visitor visitor) {
		manager.getTransaction().begin();
		manager.merge(visitor);
		manager.getTransaction().commit();
		
		manager.close();
	}
}
