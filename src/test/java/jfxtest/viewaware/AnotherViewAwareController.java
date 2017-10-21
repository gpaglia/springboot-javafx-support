package jfxtest.viewaware;

import org.controlsfx.control.action.ActionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.ViewContextObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@FXMLController
public class AnotherViewAwareController extends AbstractFxmlController {
	private static final Logger logger = LoggerFactory.getLogger(AnotherViewAwareController.class);
	
	// for testing purposes
	public ViewContextObject getContext() {
		return getViewContextObject();
	}

	@FXML
	public void initialize() {
		logger.info("Initializing a view aware custom controller");
		logger.info("View context object is {}", getViewContextObject());
	}

	@ActionProxy(id="ID1", text="Action1")
	public void action(ActionEvent event) {
		
	}

}
