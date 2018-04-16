package org.fcpe.fantinlatour.dao;

public interface UserPreferencesDAO {

	public static final String ID = "userPreferencesDAO";
	
	boolean isLoad();

	boolean exists();

	boolean load() throws DataException;

	boolean store() throws DataException;

	void setDefaultConseilLocalName(String name) throws DataException;
	
	String getDefaultConseilLocalName() throws DataException;

}