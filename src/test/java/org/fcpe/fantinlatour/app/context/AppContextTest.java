package org.fcpe.fantinlatour.app.context;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class AppContextTest {

	
	@Test
	public void testSetter() {
		JavaBeanTester.builder(AppContext.class).test();
	}

}
