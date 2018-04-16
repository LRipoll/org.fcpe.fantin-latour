package org.fcpe.fantinlatour.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

final public class SpringFactory {

	private static volatile ApplicationContext context = null;
	private static String resource = null;
	private static final Map<String, Object> CUSTOM_SERVICES = new HashMap<String, Object>();

	private SpringFactory() {
	}

	public static void setResource(String resource) {
		SpringFactory.resource = resource;

	}

	@SuppressWarnings("unchecked")
	public static <T> T getService(String id) {
		if (CUSTOM_SERVICES.containsKey(id)) {
			return (T) CUSTOM_SERVICES.get(id);
		}
		return (T) getContext().getBean(id);
	}

	public static <T> void setService(String id, T service) {
		CUSTOM_SERVICES.put(id, service);
	}

	public static String getMessage(String code) {
		String result = code;
		if (context != null)
			result = getMessage(code, null);

		return result;
	}

	public static String getMessage(String code, Object[] params) {
		return getContext().getMessage(code, params, code, Locale.getDefault());
	}

	public static void deleteServices() {
		CUSTOM_SERVICES.clear();
		context = null;
	}

	private static final ApplicationContext getContext() {
		if (SpringFactory.context == null) {
			synchronized (SpringFactory.class) {
				if (SpringFactory.context == null) {
					context = new ClassPathXmlApplicationContext(SpringFactory.resource);
				}
			}
		}
		return SpringFactory.context;
	}
}