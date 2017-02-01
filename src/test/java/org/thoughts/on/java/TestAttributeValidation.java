package org.thoughts.on.java;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.thoughts.on.java.model.Book;

public class TestAttributeValidation {

	Logger log = Logger.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}

	@Test
	public void testPersistValidation() {
		log.info("... testPersistValidation ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Book b = new Book();
		em.persist(b);
		try {
			em.getTransaction().commit();
			Assert.fail("ConstraintViolationException exptected");
		} catch (RollbackException e) {
			Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)e.getCause()).getConstraintViolations();
			for (ConstraintViolation v : violations) {
				log.info(v);
			}
		}

		em.close();
	}
	
	@Test
	public void testUpdateValiation() {
		log.info("... testUpdateValiation ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Book b = em.find(Book.class, 1000L);
		b.setTitle("This is a very long title with more than 20 characters.");
		try {
			em.getTransaction().commit();
			Assert.fail("ConstraintViolationException exptected");
		} catch (RollbackException e) {
			log.error(e);
		}

		em.close();
	}
}
