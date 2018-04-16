package org.fcpe.fantinlatour.dao.files;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.service.SpringFactory;

public class FileFactory {

	private static final Logger logger = Logger.getLogger(FileFactory.class);

	public static final String ID = "fileFactory";

	public FileFactory() {

	}

	public File create(String filename) {
		return new File(filename);
	}

	public FileOutputStream createOutputStream(File file) throws DataException {
		FileOutputStream result = null;
		try {
			result = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.fileFactory.createOutputStream.fileNotFoundException"),e);
			
		}
		return result;
	}

	public FileOutputStream createOutputStream(String filename) throws DataException {

		return createOutputStream(create(filename));
	}

	

	public FileInputStream createInputStream(String filename) throws DataException {

		return createInputStream(create(filename));
	}

	public FileInputStream createInputStream(File file) throws DataException {

		FileInputStream result = null;
		try {
			result = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.fileFactory.createInputStream.fileNotFoundException"),e);
		}
		return result;
	}

	public OutputStreamWriter createOutputStreamWriter(FileOutputStream outputStream, String encoding) throws DataException {
		
		try {
			return new OutputStreamWriter(outputStream, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.fileFactory.createOutputStreamWriter.unsupportedEncodingException"),e);
		}
	}
	
	public void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}

	}

	public FileWriter createFileWriter(File file) throws DataException {
		
		try {
			return new FileWriter(file.getAbsolutePath());
		} catch (IOException e) {
			
			throw new DataException(SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.fileFactory.createFileWriter.ioException"),e);
		}
	}

	

	public FilenameFilter createExtentionFilenameFilter(String appFileExt) {
		return new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				
				return name.endsWith(appFileExt);
			}
			
		};
		
	}

}
