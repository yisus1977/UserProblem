package com.inter;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inter.service.UserService;


public class UserName {


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
			Scanner keyboard;
			boolean keep = true;
			while(keep){
			keyboard = new Scanner(System.in);
			System.out.println("Choose a user name: ");			
			String username = keyboard.nextLine();
			hm  = usrService.checkUsername(username);
			if(hm.containsKey(true)){
				System.out.println("UserName : " + username + " is valid ");	
				keep = false;
			}else{
				System.out.println("UserName : " + username + " is NOT valid or is already taken.");
				System.out.println("The following user names are available: " + hm.get(false).toString());
			}
			}
			System.out.println("Bye!");
		

	}

}
