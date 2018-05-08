package org.fcpe.fantinlatour.dao.files;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.service.SpringFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.exception.ZipExceptionConstants;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

public class ZipFilesDAO {

	private FileFactory fileFactory;
	private String exportZipDirname;
	private String importZipDirname;
	private ZipFileFactory zipFileFactory;

	public ZipFilesDAO(FileFactory fileFactory, ZipFileFactory zipFileFactory, String exportZipDirname, String importZipDirname) {
		super();
		this.fileFactory = fileFactory;
		this.zipFileFactory = zipFileFactory;
		this.exportZipDirname = exportZipDirname;
		this.importZipDirname = importZipDirname;

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

	public String unpack(String archiveFilename, String password, String archiveHeaderFilename) throws DataException {
		String result = getImportZipDirname(FilenameUtils.getBaseName(archiveFilename));
		ZipFile zfile;
		try {
			zfile = new ZipFile(archiveFilename);
			if (!zfile.isValidZipFile()) {
				throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.pack.failed"), null);
			}
			File file = new File(result);
			if (file.isDirectory() && !file.exists()) {
				file.mkdirs();
			}
			if (zfile.isEncrypted()) {
				zfile.setPassword(password.toCharArray());
			}
			FileHeader fileHeader = zfile.getFileHeader(archiveHeaderFilename);
			zfile.extractFile(fileHeader, result);
			zfile.extractAll(result);
		} catch (ZipException e) {
			if (e.getCode()==ZipExceptionConstants.WRONG_PASSWORD) {
				throw new PasswordException(SpringFactory.getMessage(
						"org.fcpe.fantinlatour.dao.files.ZipFilesDAO.unpack.password.failed"),null);
			}
			throw new DataException("",e);
		}
		
		return result;

	}
	
	private String getImportZipDirname(String zipFilename) {
		return String.format("%s%s", String.format("%s%s%s", importZipDirname, File.separator,zipFilename));
	}
	public String getExportZipFilename(String etablissement) {
		return String.format("%s%s.zip", String.format("%s%s", exportZipDirname, File.separator), etablissement);
	}
	
	public String getNameFromArchiveFilename(String archiveFilename) {
		return FilenameUtils.getBaseName(archiveFilename);
	}

	public boolean exportZipFilenameAlreadyExists(String etablissement) {
		String filename = getExportZipFilename(etablissement);
		return fileFactory.create(filename).exists();
	}

	

}
