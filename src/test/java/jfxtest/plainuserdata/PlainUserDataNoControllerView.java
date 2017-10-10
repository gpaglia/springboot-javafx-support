package jfxtest.plainuserdata;

import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.util.Callback;

@FXMLView
@Scope("prototype")
public class PlainUserDataNoControllerView extends AbstractFxmlView {

	PlainUserDataNoControllerView() {
		super();
	}
	
	PlainUserDataNoControllerView(String data) {
		super(data);
	}
	
	PlainUserDataNoControllerView(Callback<Class<?>, Object> factory) {
		super(factory);
	}
}