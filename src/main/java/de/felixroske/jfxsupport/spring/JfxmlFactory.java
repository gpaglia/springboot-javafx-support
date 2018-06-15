package de.felixroske.jfxsupport.spring;

import org.springframework.context.ConfigurableApplicationContext;

public final class JfxmlFactory {
	
	public static final String JFXML_SESSION_SCOPE = "JfxmlSessionScope";
	public static final String JFXML_VIEW_SCOPE = "JfxmlViewScope";
	
	private JfxmlFactory() {
		throw new UnsupportedOperationException("Cannot instantiate");
	}
	
	
	public static ConfigurableApplicationContext createContext(
			String type, 
			ConfigurableApplicationContext parentContext) {
		
		
		return null;
	}
}
