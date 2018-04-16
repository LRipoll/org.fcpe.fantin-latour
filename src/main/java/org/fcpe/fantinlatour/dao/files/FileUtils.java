package org.fcpe.fantinlatour.dao.files;

import java.io.File;

public class FileUtils {

	public static String getAbsolutePath(String parentPath, String filename) {
		return String.format("%s%s%s", parentPath, File.separator, filename);
	}

}
