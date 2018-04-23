package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

public class XMLFileManagerTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private FileFactory fileFactory;
	private Marshaller marshaller;
	private XMLFileManager xmlFileManager;
	private Unmarshaller unmarshaller;

	@Before
	public void setup() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		marshaller = ctrl.createMock(Marshaller.class);
		unmarshaller = ctrl.createMock(Unmarshaller.class);

		xmlFileManager = new XMLFileManager(marshaller, unmarshaller, fileFactory);

	}
	
	@Test
	public void testConstructorWithXStreamMarshaller() {
		new XMLFileManager(new XStreamMarshaller(), unmarshaller, fileFactory);
	
	}
	
	@Test
	public void testStoreWhenCreateWriterReturnExceptionShouldRaiseDataException() throws DataException {
		File file = ctrl.createMock(File.class);

		DataException dataException = ctrl.createMock(DataException.class);
		Throwable cause = ctrl.createMock(Throwable.class);

		fileFactory.createFileWriter(file);

		EasyMock.expectLastCall().andThrow(dataException);
		EasyMock.expect(dataException.getMessage()).andReturn("Error");
		EasyMock.expect(dataException.getCause()).andReturn(cause);

		support.replayAll();

		try {
			xmlFileManager.store(null, file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("Error", aExp.getMessage());
			assertSame(cause, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testStoreWhenMarshallerReturnExceptionShouldRaiseDataException() throws DataException, IOException {
		File file = ctrl.createMock(File.class);

		IOException ioException = ctrl.createMock(IOException.class);

		FileWriter fileWriter = ctrl.createMock(FileWriter.class);
		EasyMock.expect(fileFactory.createFileWriter(file)).andReturn(fileWriter);

		Object object = ctrl.createMock(Object.class);
		marshaller.marshal(EasyMock.anyObject(Object.class), EasyMock.anyObject(Result.class));
		EasyMock.expectLastCall().andThrow(ioException);

		support.replayAll();

		try {
			xmlFileManager.store(object, file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.xmlFileManager.store.ioException", aExp.getMessage());
			assertSame(ioException, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testStoreWhenNoExceptionShouldReturnTrue() throws DataException, IOException {
		File file = ctrl.createMock(File.class);

		FileWriter fileWriter = ctrl.createMock(FileWriter.class);
		EasyMock.expect(fileFactory.createFileWriter(file)).andReturn(fileWriter);

		Object object = ctrl.createMock(Object.class);
		marshaller.marshal(EasyMock.anyObject(Object.class), EasyMock.anyObject(Result.class));

		support.replayAll();

		assertTrue(xmlFileManager.store(object, file));

		support.verifyAll();
	}

	@Test
	public void testLoadWhenUnMarshallerReturnIOExceptionShouldRaiseDataException() throws DataException, IOException {
		File file = ctrl.createMock(File.class);

		IOException ioException = ctrl.createMock(IOException.class);

		FileInputStream fileInputStream = ctrl.createMock(FileInputStream.class);
		EasyMock.expect(fileFactory.createInputStream(file)).andReturn(fileInputStream);

		unmarshaller.unmarshal(EasyMock.anyObject(StreamSource.class));
		EasyMock.expectLastCall().andThrow(ioException);
		fileFactory.close(fileInputStream);
		EasyMock.expectLastCall().once();
		support.replayAll();

		try {
			xmlFileManager.load(file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.xmlFileManager.load.ioException", aExp.getMessage());
			assertSame(ioException, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testLoadWhenUnMarshallerReturnXmlMappingExceptionShouldRaiseDataException()
			throws DataException, IOException {
		File file = ctrl.createMock(File.class);

		XmlMappingException xmlException = ctrl.createMock(XmlMappingException.class);

		FileInputStream fileInputStream = ctrl.createMock(FileInputStream.class);
		EasyMock.expect(fileFactory.createInputStream(file)).andReturn(fileInputStream);

		unmarshaller.unmarshal(EasyMock.anyObject(StreamSource.class));
		EasyMock.expectLastCall().andThrow(xmlException);
		fileFactory.close(fileInputStream);
		EasyMock.expectLastCall().once();
		support.replayAll();

		try {
			xmlFileManager.load(file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.xmlFileManager.load.xmlMappingException", aExp.getMessage());
			assertSame(xmlException, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testLoadWhenUnMarshallerReturnObject() throws DataException, IOException {
		File file = ctrl.createMock(File.class);

		FileInputStream fileInputStream = ctrl.createMock(FileInputStream.class);
		EasyMock.expect(fileFactory.createInputStream(file)).andReturn(fileInputStream);

		Object object =ctrl.createMock(Object.class);
		EasyMock.expect(unmarshaller.unmarshal(EasyMock.anyObject(StreamSource.class))).andReturn(object);

		fileFactory.close(fileInputStream);
		EasyMock.expectLastCall().once();
		support.replayAll();

		
		assertSame (object, xmlFileManager.load(file));
			
		

		support.verifyAll();
	}

}
