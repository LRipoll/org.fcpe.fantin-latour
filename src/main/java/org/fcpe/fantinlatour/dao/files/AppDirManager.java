package org.fcpe.fantinlatour.dao.files;

import java.io.File;

public class AppDirManager extends AbstractAbsoluteFileManager {

	private String dirName;

	public AppDirManager(String userDir, String appDir, FileFactory fileFactory) {
		super(fileFactory);
		dirName = FileUtils.getAbsolutePath(userDir, appDir);

	}
	
	public boolean create() {
		boolean result = true;
		File dir = getFileFactory().create(dirName);
		if (!exists(dir)) {
			result = dir.mkdirs();
		}
		return result;
	}

	@Override
	protected String getAbsolutePath() {
		return dirName;
	}

}

