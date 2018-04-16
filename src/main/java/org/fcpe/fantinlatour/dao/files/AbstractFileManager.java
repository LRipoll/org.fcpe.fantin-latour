package org.fcpe.fantinlatour.dao.files;

import java.io.File;

public abstract class AbstractFileManager {

	private FileFactory fileFactory;

	public AbstractFileManager(FileFactory fileFactory) {
		super();
		this.fileFactory = fileFactory;
		
	}

	 
	
	/**
	 * @param file
	 * @return
	 */
	protected boolean exists(File file) {
		return file.exists();
		
	}
	/**
	 * @return the fileFactory
	 */
	protected final FileFactory getFileFactory() {
		return fileFactory;
	}

	

}