package com.inter.persistance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.inter.model.User;

import org.apache.log4j.Logger;

@Transactional
public class UserDAOImpl implements UserDAO{
	
	protected static final Logger logger = Logger.getLogger(UserDAO.class);
	@PersistenceContext
	protected EntityManager entityManager;	
	

	
	/* (non-Javadoc)
	 * @see com.inter.persistance.UserDAO#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String username) throws Exception {
		try {
        	Query query = entityManager.createQuery(
        			"SELECT u "
        			+ "FROM User u "
        			+ "WHERE u.username = :un");
        	User res = (User)query.setParameter("un", username.trim()).getSingleResult();
			return res; 
      
		} catch(NoResultException e) {
			return new User();
        }       
	}
	
	/* (non-Javadoc)
	 * @see com.inter.persistance.UserDAO#findAlikeUserName(java.lang.String)
	 */
	@Override
	public List<String> findAlikeUserName(String username) throws Exception {
		try {
        	Query query = entityManager.createQuery(
        			"SELECT u.username "
        			+ "FROM User u "
        			+ "WHERE u.username LIKE :un");
        	@SuppressWarnings("unchecked")
			List<String> res = (List<String>)query.setParameter("un", username.trim()+"%").getResultList();
			return res; 
      
		} catch(NoResultException e) {
			return new ArrayList<String>();
        }       
	}
	
	
	/* (non-Javadoc)
	 * @see com.inter.persistance.UserDAO#findAll()
	 */
	@Override
	public List<User> findAll() throws Exception {
		try {
        	Query query = entityManager.createQuery(
        			"FROM User");
        	@SuppressWarnings("unchecked")
			List<User> res = ((List<User>)query.getResultList());
			return res; 
      
		} catch(NoResultException e) {
			logger.warn("findAll returned No Results");
			return new ArrayList<User>();
        }       
	}
	

	
	/* (non-Javadoc)
	 * @see com.inter.persistance.UserDAO#insertMockData()
	 */
	@Override
	public void insertMockData(){
		User u1 = new User("John01");
		User u2 = new User("John2017");
		User u3 = new User("John010X"); 
		User u4 = new User("John012C"); 
		User u5 = new User("John016B");
		User u6 = new User("John016T7J"); 
		User u7 = new User("John0173SQ"); 
		User u8 = new User("John01794S"); 
		User u9 = new User("John019I");
		User u10 = new User("John01YV");
		User u11 = new User("John01DRBL"); 
		User u12 = new User("John01NZ");
		User u13 = new User("John01R95R"); 
		User u14 = new User("John01U2AO"); 
		User u15 = new User("John01UYW2");
		entityManager.persist(u1);
		entityManager.persist(u2);
		entityManager.persist(u3);
		entityManager.persist(u4);
		entityManager.persist(u5);
		entityManager.persist(u6);
		entityManager.persist(u7);
		entityManager.persist(u8);
		entityManager.persist(u9);
		entityManager.persist(u10);
		entityManager.persist(u11);
		entityManager.persist(u12);
		entityManager.persist(u13);
		entityManager.persist(u14);
		entityManager.persist(u15);
	}
	
	

}
