package com.inter.test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inter.service.UserService;



public class Test {
	
	private static UserService usrService;
	private static ApplicationContext appContext;


	public static void main(String[] args) throws Exception {
		
		HashMap<Boolean, List<String>> hm;
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
		
		
		appContext = new ClassPathXmlApplicationContext(new String[] {	
				"applicationContext.xml", 
				"datasourceContext.xml" });
		
		usrService = (UserService) appContext.getBean("userService");
		
		
		//Inserting mock data
		usrService.insertMockData();
		
		//Printing all users for testing porpuses
		System.out.println("Users in DB : " + usrService.getAllUsers());		

		String[] users = new String[] {"John01","John02","JohnJJ","JohnDrunk","cannabis"};
		
		
		for (int i = 0; i < users.length; i++) {
			hm  = usrService.checkUsername(users[i]);
			System.out.println("checkUserName : " + users[i] + ", result: " + hm.toString());
		}
		
		
		

	}
	
	

}
