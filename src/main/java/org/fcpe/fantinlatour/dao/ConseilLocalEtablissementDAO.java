package org.fcpe.fantinlatour.dao;

import java.util.List;

import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.TypeEtablissement;

public interface ConseilLocalEtablissementDAO {

	public static final String ID = "conseilLocalEtablissementDAO";
	
	boolean exists(String name);
	boolean isValidName(String name);
	ConseilLocalEtablissement create(String name, TypeEtablissement typeEtablissement) throws DataException;
	ConseilLocalEtablissement load(String name) throws DataException;
	List<String> getExistingConseilEtablissements() throws DataException;
	ConseilLocalEtablissement rename(String oldName, String newName) throws DataException;
	void delete(String string) throws DataException;
	
	String getArchiveFilename(String name);
	String getAbsoluteArchiveFilename(String name);
	String getAbsoluteArchiveDirname();
	
}
