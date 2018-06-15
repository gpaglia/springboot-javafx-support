package de.felixroske.jfxtest.viewaware;


import java.util.Collections;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.context.ViewContextObject;
import javafx.util.Callback;

@FXMLView
public class SimpleView extends AbstractFxmlView {
	
	SimpleView() {
		super();
	}
	
	SimpleView(String data) {
		super(null, Collections.singletonMap("KEY", data));
	}
	
	SimpleView(String data, ViewContextObject parentContext) {
		super(parentContext, Collections.singletonMap("KEY", data));
	}
	
	SimpleView(String data, Callback<Class<?>, Object> factory) {
		super(null, Collections.singletonMap("KEY", data), factory);
	}
	
	SimpleView(String data, ViewContextObject parentContext, Callback<Class<?>, Object> factory) {
		super(parentContext, Collections.singletonMap("KEY", data), factory);
	}
	
}
