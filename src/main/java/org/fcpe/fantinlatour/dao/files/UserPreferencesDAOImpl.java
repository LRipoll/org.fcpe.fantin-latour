package org.fcpe.fantinlatour.dao.files;

import java.io.File;
import java.util.Properties;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.UserPreferencesDAO;

public class UserPreferencesDAOImpl extends AbstractAbsoluteFileManager implements UserPreferencesDAO {
	private static final String DEFAULT_CONSEIL_LOCAL = "default.conseil.local";

	private AppDirManager appDirManager;

	private PersistentPropertiesManager persistentPropertiesManager;
	private Properties properties;
	private String filename;
	private boolean isLoad;

	public UserPreferencesDAOImpl(AppDirManager appDirManager, String filename, FileFactory fileFactory,
			PersistentPropertiesManager persistentPropertiesManager) {
		super(fileFactory);
		this.appDirManager = appDirManager;
		this.persistentPropertiesManager = persistentPropertiesManager;
		this.filename = filename;
		isLoad = false;
	}

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.files.UserPreferencesDAO#isLoad()
	 */
	@Override
	public boolean isLoad() {
		return isLoad;
	}

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.files.UserPreferencesDAO#exists()
	 */
	@Override
	public boolean exists() {

		boolean result = super.exists();
		if (!result) {
			isLoad = true;
		}
		return result;
	}

	protected void setProperty(String key, String value) throws DataException {
		if (properties == null) {
			if (exists()) {
				load();
			} else {
				properties = persistentPropertiesManager.create();
			}
		}
		if (value==null)  {
			properties.remove(key);
		}
		else  {
			properties.setProperty(key, value);
		}
		
	}

	protected String getProperty(String key) throws DataException {
		String result = null;
		if (properties != null || load()) {
			result = properties.getProperty(key);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.files.UserPreferencesDAO#load()
	 */
	@Override
	public boolean load() throws DataException {
		boolean result = false;
		isLoad = true;

		String absolutePath = getAbsolutePath();
		File file = getFileFactory().create(absolutePath);

		if (exists()) {
			properties = persistentPropertiesManager.load(file);
			result = !properties.isEmpty();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.files.UserPreferencesDAO#store()
	 */
	@Override
	public boolean store() throws DataException {
		boolean result = true;
		String absolutePath = getAbsolutePath();
		File file = getFileFactory().create(absolutePath);

		if (!exists()) {
			result = appDirManager.create();
		}
		result = result && persistentPropertiesManager.store(file, properties, filename);

		return result;
	}

	/**
	 * @return
	 */
	@Override
	protected String getAbsolutePath() {
		return FileUtils.getAbsolutePath(appDirManager.getAbsolutePath(), filename);
	}

	@Override
	public void setDefaultConseilLocalName(String name) throws DataException {
		setProperty(DEFAULT_CONSEIL_LOCAL,name);
		
	}

	@Override
	public String getDefaultConseilLocalName() throws DataException {
		
		return getProperty(DEFAULT_CONSEIL_LOCAL);
	}
	
	
	

}