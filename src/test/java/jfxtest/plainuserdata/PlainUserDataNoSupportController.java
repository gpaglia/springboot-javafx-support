package jfxtest.plainuserdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;

@FXMLController
public class PlainUserDataNoSupportController {
	private static final Logger logger = LoggerFactory.getLogger(PlainUserDataNoSupportController.class);
	
	
	@FXML
	public void initialize() {
		logger.info("Initializing controller");
	}

}
