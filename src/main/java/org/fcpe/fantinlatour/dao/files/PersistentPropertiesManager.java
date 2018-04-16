package org.fcpe.fantinlatour.dao.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.springframework.util.DefaultPropertiesPersister;

public class PersistentPropertiesManager {

	private FileFactory fileFactory;
	private DefaultPropertiesPersister propertiesPersister;

	private PropertiesFactory propertiesFactory;

	public PersistentPropertiesManager(FileFactory fileFactory, DefaultPropertiesPersister propertiesPersister,
			PropertiesFactory propertiesFactory) {
		this.fileFactory = fileFactory;
		this.propertiesPersister = propertiesPersister;
		this.propertiesFactory = propertiesFactory;
	}

	public Properties create() {
		return propertiesFactory.create();
	}

	public Properties load(File file) throws DataException {
		Properties result = null;
		InputStream inputStream = fileFactory.createInputStream(file);
		if (inputStream != null) {
			try {
				Properties properties = create();
				propertiesPersister.load(properties, inputStream);
				result = properties;
			} catch (IOException e) {
				throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.persistentPropertiesManager.load.fileNotFoundException"), e);
			} finally {
				fileFactory.close(inputStream);
			}
		}
		return result;
	}

	public boolean store(File file, Properties properties, String header) throws DataException {
		boolean result = false;
		OutputStream outputStream = fileFactory.createOutputStream(file);

		try {
			propertiesPersister.store(properties, outputStream, header);
			result = true;
		} catch (IOException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.persistentPropertiesManager.store.ioException"), e);
		} finally {
			fileFactory.close(outputStream);
		}

		return result;

	}

}
