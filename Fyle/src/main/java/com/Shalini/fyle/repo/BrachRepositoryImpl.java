package com.Shalini.fyle.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.Shalini.fyle.entities.Branch;

@Repository
public class BrachRepositoryImpl {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	public List<Branch> getBranches(String b, int limit, int offset) {
		String query="SELECT t FROM Branch t WHERE LOWER(t.branch) LIKE LOWER(CONCAT('%','"+b+"','%')) order by t.ifsc";
	    return entityManager.createQuery(query, Branch.class)
	    		.setMaxResults(limit)
	    		.setFirstResult(offset)
	    		.getResultList();
	}

	public List<Branch> getBranchesByCity(String city, int limit, int offset) {
		String query="SELECT t FROM Branch t WHERE LOWER(t.city) LIKE LOWER('"+city+"') order by t.ifsc";
	    return entityManager.createQuery(query, Branch.class)
	    		.setMaxResults(limit)
	    		.setFirstResult(offset)
	    		.getResultList();
	}

}
