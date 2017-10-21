package jfxtest.viewaware;


import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.ActionHolder;
import de.felixroske.jfxsupport.FXMLView;
import javafx.util.Callback;

@FXMLView
public class SimpleView extends AbstractFxmlView {
	
	SimpleView() {
		super();
	}
	
	SimpleView(String data) {
		super(null, data);
	}
	
	SimpleView(String data, ActionHolder actions) {
		super(actions, data);
	}
	
	SimpleView(String data, Callback<Class<?>, Object> factory) {
		super(null, data, factory);
	}
	
	SimpleView(String data, ActionHolder actions, Callback<Class<?>, Object> factory) {
		super(actions, data, factory);
	}
	
}
