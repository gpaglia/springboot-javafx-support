package de.felixroske.jfxsupport.context;

import java.util.HashMap;
import java.util.Map;

import de.felixroske.jfxsupport.AbstractFxmlView;

public class ViewContextObject {
	private AbstractFxmlView view;
	private MethodHolder methodHolder;
	private Map<String, Object> properties;
	private ViewContextObject parentContext;
	
	public ViewContextObject(
			ViewContextObject parentContext,
			AbstractFxmlView view,
			MethodHolder methodHolder, 
			Map<String, Object> properties) {
		this.parentContext = parentContext;
		this.view = view;
		this.methodHolder = methodHolder == null ? new MethodHolder() : methodHolder;
		if (parentContext != null) {
			this.methodHolder.setParent(parentContext.getMethodHolder());
		}
		this.properties = properties == null ? new HashMap<>() : new HashMap<>(properties);
	}
	
	public ViewContextObject(
			AbstractFxmlView view,
			MethodHolder methodHolder, 
			Map<String, Object> properties) {
		this(null, view, methodHolder, properties);
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

	public Map<String, Object> getProperties() {
		return new HashMap<>(properties);
	}
	
	public boolean hasProperty(final String name, final boolean global) {
		return 	properties.containsKey(name) || 
				(global && parentContext != null && parentContext.hasProperty(name, global));
	}

	public <T> T getProperty(String name, Class<T> clazz, boolean global) {
		if (hasProperty(name, false)) {
			return clazz.cast(properties.get(name));
		} else if (global && parentContext != null) {
			return parentContext.getProperty(name, clazz, global);
		} else {
			return null;
		}
	}

	
	public String getStringProperty(String name, boolean global) {
		return getProperty(name, String.class, global);
	}
	
	public Integer getIntegerProperty(String name, boolean global) {
		return getProperty(name, Integer.class, global);
	}
	
	public Object removeProperty(String name) {
		return properties.remove(name);
	}
	
	public Object setProperty(String name, Object value) {
		return properties.put(name, value);
	}

	@Override
	public String toString() {
		return "ViewContextObject [view=" + (view == null ? "<null>" : view.toString()) + ", methodHolder=" + methodHolder.toString() + ", context=" + ( properties == null ? "null" : properties.toString())
				+ "]";
	}


}
