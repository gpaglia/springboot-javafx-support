package de.felixroske.jfxsupport;

import org.controlsfx.control.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AbstractFxmlController implements IFxmlController {
	private static final Logger logger = LoggerFactory.getLogger(AbstractFxmlController.class);

	private ViewContextObject viewContextObject;
	
	public AbstractFxmlController() {}
	
	@Override
	public void setViewContextObject(ViewContextObject vco) {
		logger.trace("Setting view context object {}", vco);

		this.viewContextObject = vco;
	}
	
	protected ViewContextObject getViewContextObject() {
		return viewContextObject;
	}
	
	protected ActionHolder getActionHolder() {
		return getViewContextObject().getActionHolder();
	}
	
	protected Action getAction(String id) {
		return getViewContextObject().getActionHolder().getAction(id);
	}
	

}
