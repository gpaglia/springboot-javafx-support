package jfxtest.plainuserdata;

import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.util.Callback;

@FXMLView
@Scope("prototype")
public class PlainUserDataView extends AbstractFxmlView {

	PlainUserDataView() {
		super();
	}
	
	PlainUserDataView(String data) {
		super(data);
	}
	
	PlainUserDataView(Callback<Class<?>, Object> factory) {
		super(factory);
	}
}
