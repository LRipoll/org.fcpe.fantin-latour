package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;

public class FileFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	@Before
	public void setup() {

	}

	@Test
	public void testCreate() {

		FileFactory fileFactory = new FileFactory();
		assertEquals(new File("a.b/c.txt"), fileFactory.create("a.b/c.txt"));
	}

	@Test
	public void testCreateOutputStreamFile() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		File file = File.createTempFile("test", "output");

		FileOutputStream createdtedOutputStream = fileFactory.createOutputStream(file);
		createdtedOutputStream.close();
		file.delete();
	}

	@Test
	public void testCreateOutputStreamFilename() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		File file = File.createTempFile("test", "output");

		FileOutputStream createdtedOutputStream = fileFactory.createOutputStream(file.getAbsolutePath());
		assertNotNull(createdtedOutputStream);
		createdtedOutputStream.close();
		file.delete();
	}

	@Test
	public void testCreateOutputStreamUnknowwFilename() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		try {
			fileFactory.createOutputStream("a.b/c.txt");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.fileFactory.createOutputStream.fileNotFoundException",
					aExp.getMessage());

		}

	}

	@Test
	public void testCloseOutputWhenOutputIsNull() throws IOException {

		FileFactory fileFactory = new FileFactory();

		OutputStream fileOutputStream = null;

		support.replayAll();
		fileFactory.close(fileOutputStream);
		support.verifyAll();

	}

	@Test
	public void testCloseOutputWhenOutputIsNotNull() throws IOException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		OutputStream fileOutputStream = ctrl.createMock(OutputStream.class);
		fileOutputStream.close();
		EasyMock.expectLastCall().once();

		support.replayAll();
		fileFactory.close(fileOutputStream);
		support.verifyAll();

	}

	@Test
	public void testCloseOutputWhenOutputIsNotNullButCloseException() throws IOException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		IOException ioException = ctrl.createMock(IOException.class);
		EasyMock.expect(ioException.getLocalizedMessage()).andReturn("Error");

		OutputStream fileOutputStream = ctrl.createMock(OutputStream.class);
		fileOutputStream.close();
		EasyMock.expectLastCall().andThrow(ioException);

		support.replayAll();
		fileFactory.close(fileOutputStream);
		support.verifyAll();

	}

	@Test
	public void testCloseInputWhenOutputIsNull() throws IOException {

		FileFactory fileFactory = new FileFactory();

		InputStream fileInputStream = null;

		support.replayAll();
		fileFactory.close(fileInputStream);
		support.verifyAll();

	}

	@Test
	public void testCloseInputWhenOutputIsNotNull() throws IOException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		InputStream fileInputStream = ctrl.createMock(InputStream.class);
		fileInputStream.close();
		EasyMock.expectLastCall().once();

		support.replayAll();
		fileFactory.close(fileInputStream);
		support.verifyAll();

	}

	@Test
	public void testCloseInputWhenInputIsNotNullButCloseException() throws IOException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		IOException ioException = ctrl.createMock(IOException.class);
		EasyMock.expect(ioException.getLocalizedMessage()).andReturn("Error");

		InputStream fileInputStream = ctrl.createMock(InputStream.class);
		fileInputStream.close();
		EasyMock.expectLastCall().andThrow(ioException);

		support.replayAll();
		fileFactory.close(fileInputStream);
		support.verifyAll();

	}

	@Test
	public void testCreateInputStreamFile() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		File file = File.createTempFile("test", "input");

		FileInputStream createdtedInputStream = fileFactory.createInputStream(file);
		createdtedInputStream.close();
		file.delete();
	}

	@Test
	public void testCreateOutputStreamWriter() throws IOException, DataException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		FileOutputStream fileOutputStream = ctrl.createMock(FileOutputStream.class);

		OutputStreamWriter createdtedOutputStreamWriter = fileFactory.createOutputStreamWriter(fileOutputStream,
				"UTF-8");
		assertNotNull(createdtedOutputStreamWriter);

	}

	@Test
	public void testCreateOutputStreamWriterWhenWrongEncoding() throws IOException, DataException {
		ctrl = support.createControl();
		FileFactory fileFactory = new FileFactory();

		FileOutputStream fileOutputStream = ctrl.createMock(FileOutputStream.class);
		try {
			fileFactory.createOutputStreamWriter(fileOutputStream, "UTF-8-Inexistant");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals(
					"org.fcpe.fantinlatour.dao.files.fileFactory.createOutputStreamWriter.unsupportedEncodingException",
					aExp.getMessage());

		}

	}

	@Test
	public void testCreateInputStreamFilename() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		File file = File.createTempFile("test", "input");

		FileInputStream createdtedInputStream = fileFactory.createInputStream(file.getAbsolutePath());
		assertNotNull(createdtedInputStream);
		createdtedInputStream.close();
		file.delete();
	}

	@Test
	public void testCreateInputStreamUnknowwFilename() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		try {
			fileFactory.createInputStream("a.b/c.txt");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.fileFactory.createInputStream.fileNotFoundException",
					aExp.getMessage());

		}
	}

	@Test
	public void testCreateFileWriter() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();

		File file = File.createTempFile("test", "input");

		FileWriter createdFileWriter = fileFactory.createFileWriter(file);
		assertNotNull(createdFileWriter);
		createdFileWriter.close();
		file.delete();
	}

	@Test
	public void testCreateFileWriterUnknowwFilename() throws IOException, DataException {

		FileFactory fileFactory = new FileFactory();
		File file = File.createTempFile("test", "input");
		try {

			fileFactory.createFileWriter(file.getParentFile());
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.fileFactory.createFileWriter.ioException", aExp.getMessage());
		} finally {
			file.delete();
		}
	}

}
