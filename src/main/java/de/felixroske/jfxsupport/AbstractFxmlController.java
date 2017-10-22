package de.felixroske.jfxsupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.context.MethodHolder;
import de.felixroske.jfxsupport.context.MethodWrapper;
import de.felixroske.jfxsupport.context.ViewContextObject;


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
	
	protected MethodHolder getMethodHolder() {
		return getViewContextObject().getMethodHolder();
	}
	
	protected MethodWrapper getAMethodWrapper(String id) {
		return getViewContextObject().getMethodHolder().getMethodWrapper(id);
	}
	

}
