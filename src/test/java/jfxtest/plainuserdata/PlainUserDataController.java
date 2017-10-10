package jfxtest.plainuserdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.IUserDataSupport;
import javafx.fxml.FXML;

@FXMLController
public class PlainUserDataController implements IUserDataSupport {
	private static final Logger logger = LoggerFactory.getLogger(PlainUserDataController.class);
	
	private Object userData;
	
	@Override
	public void setUserData(Object userData) {
		this.userData = userData;
		
	}
	
	@Override
	public Object getUserData() {
		return userData;
	}
	
	@FXML
	public void initialize() {
		logger.info("Initializing controller");
		logger.info("UserData is {}", userData);
	}

}
