package jfxtest.viewaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;

@FXMLController
public class SimpleNoSupportViewController {
	private static final Logger logger = LoggerFactory.getLogger(SimpleNoSupportViewController.class);
	
	
	@FXML
	public void initialize() {
		logger.info("Initializing controller");
	}

}
