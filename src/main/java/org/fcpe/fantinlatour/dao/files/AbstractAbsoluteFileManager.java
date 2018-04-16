package org.fcpe.fantinlatour.dao.files;

import java.io.File;

public abstract class AbstractAbsoluteFileManager extends AbstractFileManager {

	public AbstractAbsoluteFileManager(FileFactory fileFactory) {
		super(fileFactory);
	}

	public boolean exists() {
		File dir = getFileFactory().create(getAbsolutePath()); 
		return exists(dir);
	}

	
	protected abstract String getAbsolutePath();

}
