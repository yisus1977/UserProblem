package com.inter.service;

import java.util.Collection;

/**
 * @author Jesus
 *Forbidden Word Service Interface
 */
public interface ForbiddenWordsService {

	/**
	 * @param str
	 * @return true if the given String is a forbidden word
	 */
	boolean isForbiddenWord(String str);

	/**
	 * @return a Collection of forbidde words read from forbiddenwords.properties on resources
	 */
	Collection<Object> getForbidenWords();
	
}
