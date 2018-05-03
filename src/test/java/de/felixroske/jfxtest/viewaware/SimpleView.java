package de.felixroske.jfxtest.viewaware;


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
		super(null, data);
	}
	
	SimpleView(String data, ViewContextObject parentContext) {
		super(parentContext, data);
	}
	
	SimpleView(String data, Callback<Class<?>, Object> factory) {
		super(null, data, factory);
	}
	
	SimpleView(String data, ViewContextObject parentContext, Callback<Class<?>, Object> factory) {
		super(parentContext, data, factory);
	}
	
}
