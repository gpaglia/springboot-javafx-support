package jfxtest.plainuserdata;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;

@FXMLView
public class SimpleNoSupportView extends AbstractFxmlView {

	private String data;
	
	SimpleNoSupportView() {
		super();
	}
	
	SimpleNoSupportView(String data) {
		super();
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
}
