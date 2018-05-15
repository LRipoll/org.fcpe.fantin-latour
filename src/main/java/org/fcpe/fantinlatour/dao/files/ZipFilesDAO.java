package org.fcpe.fantinlatour.dao.files;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.service.SpringFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

public class ZipFilesDAO {

	private static final String WRONG_PASSWORD_MSG = "Wrong Password";

	private static final Logger logger = Logger.getLogger(ZipFilesDAO.class);

	private FileFactory fileFactory;
	private String zipDirname;
	private ZipFileFactory zipFileFactory;
	private String zipPrefix;
	private String zipSuffix;

	public ZipFilesDAO(FileFactory fileFactory, ZipFileFactory zipFileFactory, String zipDirname, String zipPrefix,
			String zipSuffix) {
		super();
		this.fileFactory = fileFactory;
		this.zipFileFactory = zipFileFactory;
		this.zipDirname = zipDirname;
		this.zipPrefix = zipPrefix;
		this.zipSuffix = zipSuffix;

	}

	public String getZipDirname() {
		return zipDirname;
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
			ZipFile zipFile = createZipFile(zipFilename);

			ZipParameters parameters = zipFileFactory.createZipParameters();

			parameters.setPassword(password);

			zipFile.addFile(zippedFile, parameters);
		} catch (ZipException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.pack.failed"),
					e);
		}

		return result;

	}

	public String getExportZipAbsoluteFilename(String etablissement) {
		return FilenameUtils
				.normalize(String.format("%s%s%s", zipDirname, File.separator, getZipFilename(etablissement)));
	}

	public String getNameFromArchiveFilename(String archiveFilename) {

		String baseName = FilenameUtils.getBaseName(archiveFilename);
		return baseName.substring(zipPrefix.length());
	}

	public boolean exportZipFilenameAlreadyExists(String etablissement) {
		String filename = getExportZipAbsoluteFilename(etablissement);
		return fileFactory.create(filename).exists();
	}

	public boolean isValidArchiveFilename(String filename) {

		return FilenameUtils.wildcardMatch(FilenameUtils.getName(filename), getZipFilenameWildcardMatcher());
	}

	public String getZipFilenameWildcardMatcher() {

		return getZipFilename("*");
	}

	private String getZipFilename(String etablissement) {
		return String.format("%s%s.%s", zipPrefix, etablissement, zipSuffix);
	}

	public boolean existsArchiveFile(String filename) {
		return fileFactory.create(filename).exists();
	}

	public boolean isValidArchiveFile(String filename) {

		try {
			final ZipFile zipFile = createZipFile(filename);

			return zipFile.isValidZipFile();
		} catch (ZipException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		}
	}

	public boolean containsExpectedArchives(String filename, String archiveHeaderFilename) {

		try {
			final ZipFile zipFile = createZipFile(filename);
			FileHeader fileHeader = zipFile.getFileHeader(archiveHeaderFilename);

			return fileHeader != null;
		} catch (ZipException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		}
	}

	public boolean isEncryptedArchiveFile(String filename) {
		try {
			final ZipFile zipFile = createZipFile(filename);
			return zipFile.isEncrypted();
		} catch (ZipException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		}
	}

	public void unpack(String archiveFilename, String password, String archiveHeaderFilename, String destPath) throws DataException {

		try {
			ZipFile zipfile = createZipFile(archiveFilename);
			zipfile.setPassword(password);

			FileHeader fileHeader = zipfile.getFileHeader(archiveHeaderFilename);

			File destDir = fileFactory.create(destPath);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			zipfile.extractFile(fileHeader, destPath);

		} catch (ZipException e) {
			if (e.getMessage().contains(WRONG_PASSWORD_MSG)) {
				throw new PasswordException(
						SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.unpack.password.failed"),
						null);
			}
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.unpack.zipException"), e);
		}

	}

	private ZipFile createZipFile(String filename) throws ZipException {
		return zipFileFactory.create(filename);
	}
}
