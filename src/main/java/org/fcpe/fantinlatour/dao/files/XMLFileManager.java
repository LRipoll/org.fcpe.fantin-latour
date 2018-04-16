package org.fcpe.fantinlatour.dao.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.XStream;

public class XMLFileManager {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private FileFactory filefactory;

	public XMLFileManager(Marshaller marshaller, Unmarshaller unmarshaller, FileFactory filefactory) {
		this.marshaller = marshaller;
		this.unmarshaller = unmarshaller;
		this.filefactory = filefactory;
		
		if (marshaller instanceof XStreamMarshaller) {
			XStreamMarshaller xstream = (XStreamMarshaller) marshaller;
			XStream.setupDefaultSecurity(xstream.getXStream());
			xstream.getXStream().allowTypesByWildcard(new String[] { "org.fcpe.fantinlatour.model.**" });
		}

	}

	public boolean store(Object object, File file) throws DataException {
		boolean result = false;

		try {

			FileWriter writer = filefactory.createFileWriter(file);
			marshaller.marshal(object, new StreamResult(writer));
			result = true;
		} catch (IOException e) {
			throw new DataException(
					SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.xmlFileManager.store.ioException"), e);
		}

		return result;
	}

	public Object load(File file) throws DataException {
		Object result = null;
		FileInputStream inputStream = filefactory.createInputStream(file);
		try {
			result = unmarshaller.unmarshal(new StreamSource(inputStream));
		} catch (XmlMappingException e) {
			throw new DataException(
					SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.xmlFileManager.load.xmlMappingException"),
					e);
		} catch (IOException e) {
			throw new DataException(
					SpringFactory.getMessage("org.fcpe.fantinlatour.dao.files.xmlFileManager.load.ioException"), e);
		} finally {
			filefactory.close(inputStream);
		}
		return result;
	}

}
