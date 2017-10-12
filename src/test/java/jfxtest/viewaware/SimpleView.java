package jfxtest.viewaware;


import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.util.Callback;

@FXMLView
public class SimpleView extends AbstractFxmlView {
	
	private String data;

	SimpleView() {
		super();
	}
	
	SimpleView(String data) {
		super();
		this.data = data;
	}
	
	SimpleView(String data, Callback<Class<?>, Object> factory) {
		super(factory);
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
}
