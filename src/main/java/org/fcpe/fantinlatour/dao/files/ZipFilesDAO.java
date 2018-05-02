package org.fcpe.fantinlatour.dao.files;

import java.io.File;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.service.SpringFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

public class ZipFilesDAO {

	private FileFactory fileFactory;
	private String zipDirname;
	private ZipFileFactory zipFileFactory;

	public ZipFilesDAO(FileFactory fileFactory, ZipFileFactory zipFileFactory, String zipDirname) {
		super();
		this.fileFactory = fileFactory;
		this.zipFileFactory = zipFileFactory;
		this.zipDirname = zipDirname;

	}

	public File pack(String zippedFilename, String zipFilename, String password) throws DataException {

		File result = null;

		File zippedFile = fileFactory.create(zippedFilename);
		

		result = fileFactory.create(zipFilename);
		if (result.exists()) {
			result.deleteOnExit();
		} else {
			result.getParentFile().mkdirs();
		}
		try {
			ZipFile zipFile = zipFileFactory.create(zipFilename);

			ZipParameters parameters = zipFileFactory.createZipParameters();

			parameters.setPassword(password);

			zipFile.addFile(zippedFile, parameters);
		} catch (ZipException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.pack.failed"),
					e);
		}

		return result;

	}

	public String getZipFileName(String filename) {
		return String.format("%s%s.zip", String.format("%s%s", zipDirname, File.separator), filename);
	}

}
