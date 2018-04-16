package org.fcpe.fantinlatour.dao.files.xstream;

import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.service.SpringFactory;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class EncryptedStringConverter implements SingleValueConverter {
	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return type == String.class;
	}

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			EncryptHelper encryptHelper = getEncryptHelper();
			return encryptHelper.encrypt(obj.toString());
		}
	}

	

	@Override
	public Object fromString(String str) {
		if (str == null) {
			return null;
		} else {
			
			return getEncryptHelper().decrypt(str);
		}
	}
	
	private EncryptHelper getEncryptHelper() {
		EncryptHelper encryptHelper = SpringFactory.getService(EncryptHelper.ID) ;
		return encryptHelper;
	}
}