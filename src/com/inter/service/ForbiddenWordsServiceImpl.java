package com.inter.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * @author Jesus
 *
 */
public class ForbiddenWordsServiceImpl implements ForbiddenWordsService{
	protected static final Logger logger = Logger.getLogger(ForbiddenWordsServiceImpl.class);
	private InputStream inputStream;
	private String propFileName ="forbiddenwords.properties";
	private Properties prop = new Properties();
	
	/**
	 * Constructor
	 * @throws IOException
	 */
	ForbiddenWordsServiceImpl() throws IOException{
	try {
		//Lectura de archivo properties
    	
    	
		//Lectura de archivo properties
		inputStream = ForbiddenWordsServiceImpl.class.getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			logger.error("property file '" + propFileName + "' not found");
			throw new FileNotFoundException("property file '" + propFileName + "' not found");
		}
	} catch (IOException e) {
		logger.error("Error reading property file '" + propFileName + "'", e.getCause());
		throw new IOException("Error reading property file '" + propFileName + "'");
	}	
	
	}
	
	/* (non-Javadoc)
	 * @see com.inter.service.ForbiddenWordsService#getForbidenWords()
	 */
	@Override
	public Collection<Object> getForbidenWords(){		
		return this.prop.values();
	}
	
	/* (non-Javadoc)
	 * @see com.inter.service.ForbiddenWordsService#isForbiddenWord(java.lang.String)
	 */
	@Override
	public boolean isForbiddenWord(String str){
		return prop.contains(str);
	}



}
