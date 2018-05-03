package de.felixroske.jfxtest.viewaware;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;

@FXMLView
public class SimpleNoSupportView extends AbstractFxmlView {
	
	SimpleNoSupportView() {
		super();
	}
	
	SimpleNoSupportView(String data) {
		super(null, data);
	}
	
}
