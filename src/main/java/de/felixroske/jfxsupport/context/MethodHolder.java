package de.felixroske.jfxsupport.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MethodHolder {

	private final Map<String, MethodWrapper> wrappers = new HashMap<>();
	private MethodHolder parent = null;

	public MethodHolder() {
		this(null);
	}

	public MethodHolder(MethodHolder mh) {
		parent = mh;
	}
	
	public static MethodHolder on(Object target, String[] filteredIds) {
		return on(null, target, filteredIds);
	}
	
	
	public static MethodHolder on(Object target) {
		return on(target, null);
	}

	public static MethodHolder on(MethodHolder superMh, Object target) {
		return on(superMh, target, null);
	}
	
	public static MethodHolder on(MethodHolder superMh, Object target, String[] filteredIds) {
		MethodHolder mh = new MethodHolder(superMh);
		mh.register(target, filteredIds);
		return mh;
	}
	//
	
	private MethodWrapper shallowGet(String id) {
		return this.wrappers.get(id);
	}
	
	 /**
     * Returns MethodWrapper by its id, with defined earch depth.
     * @param id action id
     * @param depth the search depth in the parent holders, or null for infinite depth
     * 			(a depth == 0 means search in topmost holder)
     * @return MethodWrapper or null if id was not found
     */
	public MethodWrapper getMethodWrapper(String id, Integer depth) {
		for (MethodHolder h = this; h != null && (depth == null || depth >= 0); h = h.getParent(), depth = (depth == null ? null : depth-1)) {
			MethodWrapper m = h.shallowGet(id);
			if (m != null) {
				return m;
			}
		}
		return null;
	}
	
    /**
     * Returns MethodWrapper by its id.
     * @param id action id
     * @return MethodWrapper or null if id was not found
     */
	public MethodWrapper getMethodWrapper(String id) {
		return getMethodWrapper(id, null);
	}
	
	public <T> T call(String id, Class<T> clazz, Object... args) {
		MethodWrapper mw = getMethodWrapper(id);
		if (mw != null) {
			return mw.call(clazz,  args);
		} else {
			throw new IllegalArgumentException("Method not found, id: " + id);
		}
	}
	
	public void call(String id, Object... args) {
		MethodWrapper mw = getMethodWrapper(id);
		if (mw != null) {
			mw.call(args);
		} else {
			throw new IllegalArgumentException("Method not found, id: " + id);
		}
	}
	
    /**
     * Returns the parent MethodWrapper or null.
     * @return parent MethodWrapper or null 
     */
	public MethodHolder getParent() {
		return parent;
	}
	
	void setParent(MethodHolder parent) {
		this.parent = parent;
	}


    /**
     * Returns collection of actions by ids. 
     * @param ids action ids
     * @return collection of method wrappers
     */
    public Collection<MethodWrapper> getMethodWrappers(String... ids) {
        List<MethodWrapper> result = new ArrayList<>();
        for( String id: ids ) {
        	MethodWrapper mw = getMethodWrapper(id);
            	if ( mw != null ) {
            		result.add(mw);
            	}
        }
        return result;
    }
    
    public Map<String, MethodWrapper> getMethodWrapperMap(String... ids) {
    	Map<String, MethodWrapper> map = new HashMap<>();
    	for( String id: ids ) {
    		MethodWrapper mw = getMethodWrapper(id);
    		if ( mw != null ) {
    			map.put(id, mw);
    		}
        }
    	return map;
    }
    

	public void register(Object target) {
		register(target, null);
	}


	/**
	 * Attempts to convert target's methods annotated with {@link ContextMethod} to {@link MethodWrapper}s.
	 * 
	 * 
	 * Note that this method supports safe re-registration of a given instance or of another instance of the
	 * same class that has already been registered. If another instance of the same class is registered, then
	 * those actions will now be associated with the new instance. The first instance is implicitly unregistered.
	 * 
	 * Actions are registered with their id or method name if id is not defined.
	 * 
	 * @param target object to work on
	 * @param filteredIds array of ids to be registered from the ones resulting from the scanning of annotations
	 */
	public void register(Object target, String[] filteredIds) {

		for (Method method : target.getClass().getDeclaredMethods()) {
			// Only process methods that have the ActionProxy annotation
			Annotation[] annotations = method.getAnnotationsByType(ContextMethod.class);
			if (annotations.length == 0) {
				continue;
			}

			ContextMethod annotation = (ContextMethod) annotations[0];

			String id = annotation.id().isEmpty() ? method.getName() : annotation.id();

			MethodWrapper mw = new MethodWrapper( id, method, target );

			if (checkFilteredId(id, filteredIds)) { 
				wrappers.put( id, mw );
			}
		}
	}

	private boolean checkFilteredId(String id, String[] filteredIds) {
		if (filteredIds == null) {
			return true;
		} else {
			for (String f : filteredIds) {
				if (f.equals(id)) {
					return true;
				}
			}
			return false;
		}
	}
}
