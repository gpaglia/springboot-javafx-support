package jfxtest.viewaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.IViewAwareController;
import javafx.fxml.FXML;

@FXMLController
public class AnotherViewAwareController implements IViewAwareController {
	private static final Logger logger = LoggerFactory.getLogger(AnotherViewAwareController.class);
	
	private AbstractFxmlView view;
	
	@Override
	public void setView(AbstractFxmlView view) {
		this.view = view;
		
	}
	
	@Override
	public AbstractFxmlView getView() {
		return view;
	}
	
	@FXML
	public void initialize() {
		logger.info("Initializing a view aware custom controller");
		logger.info("View is {}", view);
	}

}
