package org.fcpe.fantinlatour.dao.files;

import java.io.File;

import org.fcpe.fantinlatour.dao.DataException;
import org.zeroturnaround.zip.ZipUtil;

public class ZipFilesDAO  {

	private FileFactory fileFactory;
	
	public ZipFilesDAO(FileFactory fileFactory) {
		super();
		this.fileFactory = fileFactory;
	}

	public File pack(String zippedFilename, String zipFilename) throws DataException {
		
		File result = null;

		File zippedFile = fileFactory.create(zippedFilename);
		File[] filesToBePacked = new File[] { zippedFile };

		result = fileFactory.create(zipFilename);
		if (result.exists()) {
			result.deleteOnExit();
		} else {
			result.getParentFile().mkdirs();
		}
		ZipUtil.packEntries(filesToBePacked, result);
		
		return result;

	}

}
