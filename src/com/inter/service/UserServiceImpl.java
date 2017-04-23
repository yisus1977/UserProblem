package com.inter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.inter.model.User;
import com.inter.persistance.UserDAO;

/**
 * @author Jesus
 *
 */
public class UserServiceImpl implements UserService{
	protected static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private UserDAO userdao;
	private ForbiddenWordsService forbiddenWords;
	
	
	/**
	 * Setter for User DAO
	 * @param userdao
	 */
	public void setUserdao(UserDAO userdao) {
		this.userdao = userdao;
	}

	
	/**
	 * Setter for Forbidden Words service
	 * @param forbiddenWords
	 */
	public void setForbiddenWords(ForbiddenWordsServiceImpl forbiddenWords) {
		this.forbiddenWords = forbiddenWords;
	}
	
	/* (non-Javadoc)
	 * @see com.inter.service.UserService#isUser(java.lang.String)
	 */
	@Override
	public boolean isUser(String username) throws Exception{
		if(userdao.findByUserName(username).getUsername()==null){
			return false;
		}else {
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.inter.service.UserService#getAllUsers()
	 */
	@Override
	public List<User> getAllUsers() throws Exception{
		return userdao.findAll();	
	}

	/* (non-Javadoc)
	 * @see com.inter.service.UserService#insertMockData()
	 */
	@Override
	public void insertMockData() throws Exception{
		userdao.insertMockData();	
	}
	
	/**
	 * @param username
	 * @return A generated list of Hints based on the given String 
	 */
	private List<String> generateHints(String username){
		List<String> ls = new ArrayList<String>();
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = null;
		
		for(int i =0; i< 14;i++){
		Random rnd = new Random();
		salt = new StringBuilder();
		if(i % 2 == 0){
			while (salt.length() < 2) { 
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
		}else{
    		while (salt.length() < 4) { 
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
        }
		ls.add(username +salt);
		}
		
				
		return ls;
	}
	
	/**
	 *
	 * @param username
	 * @return A hashmap with key true is the given String contains a forbidden word, and value a cleaned username 
	 */
	private HashMap<Boolean, String> containsForbidddenWord(String username){
		HashMap<Boolean, String> hm = new HashMap<Boolean, String>();
		Collection<Object> fw = this.forbiddenWords.getForbidenWords();
		boolean fwwasfound = false;
		for (Iterator<Object> iterator = fw.iterator(); iterator.hasNext();) {
			String fword = (String) iterator.next();		
			if(username.toUpperCase().contains(fword.toUpperCase())){
				
				fwwasfound = true;
				char fc = fword.charAt(0);
				username = username.toUpperCase().replaceAll(fword.toUpperCase(),String.copyValueOf(new char[]{fc,fc,fc,fc})  );
			}	
		}
		if(fwwasfound){
			hm.put(true, username);
			return hm;
		}else{
			hm.put(false, "");
			return hm;	
		}
			
	}
	
	/**
	 * @param username
	 * @return A Set with username hints from generate hints method not containing usernames already existing in DB
	 */
	private Set<String> hints(String username){
		Set<String> hs = new HashSet<>();
		List<String> alike;
		int alikesize;
		try {			
			alike = userdao.findAlikeUserName(username);
			alikesize = alike.size();
		} catch (Exception e) {
			logger.error("userdao.findAlikeUserName has failed", e);
			return hs;
		}
		hs.addAll(alike);
		
		for (int i = 0; i < 3; i++) {
			hs.addAll(generateHints(username));
			if(hs.size()>=(alikesize + 14)){
				break;
			}
		}
		
		hs.removeAll(alike);
		return hs;
	}

	/* (non-Javadoc)
	 * @see com.inter.service.UserService#checkUsername(java.lang.String)
	 */
	@Override
	public HashMap<Boolean, List<String>> checkUsername(String username) throws Exception {
		if(username.length()<6)
			throw  new Exception("Username must be at least 6 characters long");
		
		HashMap<Boolean, List<String>> rsp = new HashMap<Boolean, List<String>>();
		
		HashMap<Boolean, String> cfw = this.containsForbidddenWord(username);
		
		if(cfw.containsKey(true)){
			username = cfw.get(true);
			List<String> hintslist =new ArrayList<String>(this.hints(username));
			Collections.sort(hintslist);
			rsp.put(false, hintslist);
			return rsp;
		}
		
		try {
			if(this.isUser(username)){
				List<String> hintslist =new ArrayList<String>(this.hints(username));
				Collections.sort(hintslist);
				rsp.put(false, hintslist);
			} else {
				rsp.put(true, new ArrayList<String>());							
			}	
		}catch(Exception e){	
			logger.error("error invoking this.isUser(username)", e);
		}
		return rsp;
	}

}
