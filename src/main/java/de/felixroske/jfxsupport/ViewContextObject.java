package de.felixroske.jfxsupport;

public class ViewContextObject {
	private AbstractFxmlView view;
	private ActionHolder actionHolder;
	private Object userData;
	
	public ViewContextObject(
			AbstractFxmlView view,
			ActionHolder actionHolder, 
			Object userData) {
		this.view = view;
		this.actionHolder = actionHolder == null ? new ActionHolder() : actionHolder;
		this.userData = userData;
	}
	
	public AbstractFxmlView getView() {
		return view;
	}
	
	public ActionHolder getActionHolder() {
		return actionHolder;
	}

	public Object getUserData() {
		return userData;
	}

	@Override
	public String toString() {
		return "ViewContextObject [view=" + view.toString() + ", actionHolder=" + actionHolder.toString() + ", context=" + ( userData == null ? "null" : userData.toString())
				+ "]";
	}


}
