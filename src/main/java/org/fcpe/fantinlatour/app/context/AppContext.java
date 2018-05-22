package org.fcpe.fantinlatour.app.context;

public class AppContext {

	public static final String ID = "appContext";

	private String conseiLocalToBeOpened;
	private String currentConseiLocal;
	
	public AppContext() {
		
	}

	public String getCurrentConseiLocal() {
		return currentConseiLocal;
	}

	public void setCurrentConseiLocal(String currentConseiLocal) {
		this.currentConseiLocal = currentConseiLocal;
	}

	public String getConseiLocalToBeOpened() {
		return conseiLocalToBeOpened;
	}

	public void setConseiLocalToBeOpened(String conseiLocalToBeOpened) {
		this.conseiLocalToBeOpened = conseiLocalToBeOpened;
	}
	
	
	

	



	

}
