package de.felixroske.jfxsupport.context;

import de.felixroske.jfxsupport.AbstractFxmlView;

public class ViewContextObject {
	private AbstractFxmlView view;
	private MethodHolder methodHolder;
	private Object userData;
	private ViewContextObject parentContext;
	
	public ViewContextObject(
			ViewContextObject parentContext,
			AbstractFxmlView view,
			MethodHolder methodHolder, 
			Object userData) {
		this.parentContext = parentContext;
		this.view = view;
		this.methodHolder = methodHolder == null ? new MethodHolder() : methodHolder;
		if (parentContext != null) {
			this.methodHolder.setParent(parentContext.getMethodHolder());
		}
		this.userData = userData;
	}
	
	public ViewContextObject(
			AbstractFxmlView view,
			MethodHolder methodHolder, 
			Object userData) {
		this(null, view, methodHolder, userData);
	}
	
	
	public ViewContextObject getParentContext() {
		return parentContext;
	}

	public AbstractFxmlView getView() {
		return view;
	}
	
	public MethodHolder getMethodHolder() {
		return methodHolder;
	}

	public Object getUserData() {
		return userData;
	}

	@Override
	public String toString() {
		return "ViewContextObject [view=" + (view == null ? "<null>" : view.toString()) + ", methodHolder=" + methodHolder.toString() + ", context=" + ( userData == null ? "null" : userData.toString())
				+ "]";
	}


}
