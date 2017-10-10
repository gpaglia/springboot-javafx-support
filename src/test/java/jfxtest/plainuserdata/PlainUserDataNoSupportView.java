package jfxtest.plainuserdata;

import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;

@FXMLView
@Scope("prototype")
public class PlainUserDataNoSupportView extends AbstractFxmlView {

	PlainUserDataNoSupportView() {
		super();
	}
	
	PlainUserDataNoSupportView(String data) {
		super(data);
	}
}
