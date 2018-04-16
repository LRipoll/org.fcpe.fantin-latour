package org.fcpe.fantinlatour.dao.files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.fcpe.fantinlatour.dao.ConseilLocalEtablissementDAO;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissementFactory;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.fcpe.fantinlatour.service.SpringFactory;

public class ConseilLocalEtablissementDAOImpl extends AbstractFileManager implements ConseilLocalEtablissementDAO {

	private String appFileExt;
	private AppDirManager appDirManager;
	private ConseilLocalEtablissementFactory conseilLocalEtablissementFactory;
	private XMLFileManager xmlFileManager;

	public ConseilLocalEtablissementDAOImpl(ConseilLocalEtablissementFactory conseilLocalEtablissementFactory,
			AppDirManager appDirManager, String appFileExt, FileFactory fileFactory,
			XMLFileManager xmlFileManager) {
		super(fileFactory);

		this.appDirManager = appDirManager;
		this.appFileExt = appFileExt;
		this.conseilLocalEtablissementFactory = conseilLocalEtablissementFactory;
		this.xmlFileManager = xmlFileManager;
		

	}

	@Override
	public boolean exists(String filename) {
		File file = getFile(filename);
		return exists(file);
	}

	@Override
	public boolean isValidName(String filename) {
		boolean result = (filename != null) && filename.trim().length() > 0;

		if (result) {
			File file = getFile(filename);
			result = !exists(file);
			if (result) {
				try {
					file.getCanonicalPath();
					result = true;
				} catch (IOException e) {
					result = false;
				}
			}
		}

		return result;

	}

	@Override
	public ConseilLocalEtablissement create(String name, TypeEtablissement typeEtablissement) throws DataException {

		appDirManager.create();
		File file = getFile(name);
		
		ConseilLocalEtablissement result = conseilLocalEtablissementFactory.create(name, typeEtablissement);

		xmlFileManager.store(result, file);

		return result;
	}

	private File getFile(String filename) {
		String absolutePath = getFilename(filename);

		File file = getFileFactory().create(absolutePath);
		return file;
	}

	private String getFilename(String filename) {
		return FileUtils.getAbsolutePath(appDirManager.getAbsolutePath(), String.format("%s.%s", filename, appFileExt));
	}

	@Override
	public ConseilLocalEtablissement load(String name) throws DataException {
		File file = getFile(name);
		return (ConseilLocalEtablissement)xmlFileManager.load(file);
	}

	@Override
	public List<String> getExistingConseilEtablissements() throws DataException {
		List<String> result = new ArrayList<String>();
		if (appDirManager.exists()) {
			
			File appDir = getFileFactory().create(appDirManager.getAbsolutePath());
			
			FilenameFilter filter =getFileFactory().createExtentionFilenameFilter(appFileExt);
			
			File[] files = appDir.listFiles( filter);
			for (int i=0;i<files.length;i++) {
				result.add(FilenameUtils.getBaseName( files[i].getAbsolutePath()));
			}
		}
		return result;
	}

	@Override
	public void rename(String oldName, String newName) throws DataException {
		File file = getFile(oldName);
		if (!file.renameTo(getFile(newName)))  {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ConseilLocalEtablissementDAOImpl.rename.failed"), new IOException("Rename failed"));
		}
		
	}

	@Override
	public void delete(String name) throws DataException {
		File file = getFile(name);
		if (!file.delete())  {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ConseilLocalEtablissementDAOImpl.delete.failed"), new IOException("Delete failed"));
		}
		
	}

}
