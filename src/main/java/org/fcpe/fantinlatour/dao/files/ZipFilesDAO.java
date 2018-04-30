package org.fcpe.fantinlatour.dao.files;

import java.io.File;

import org.fcpe.fantinlatour.dao.DataException;
import org.zeroturnaround.zip.ZipUtil;

public class ZipFilesDAO  {

	private FileFactory fileFactory;
	private String zipDirname;
	
	public ZipFilesDAO(FileFactory fileFactory,String zipDirname) {
		super();
		this.fileFactory = fileFactory;
		this.zipDirname = zipDirname;
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

	public String getZipFileName(String filename) {
		return String.format("%s%s.zip", String.format("%s%s", zipDirname,File.separator) , filename);
		//return String.format("%s%s.zip", String.format("%s%s%s%s", System.getProperty("user.home"),File.separator,"Downloads",File.separator) , filename);
	}

}
