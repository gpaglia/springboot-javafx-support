package de.felixroske.jfxsupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.control.action.AnnotatedAction;
import org.controlsfx.control.action.AnnotatedActionFactory;
import org.controlsfx.control.action.DefaultActionFactory;

import javafx.event.ActionEvent;

public class ActionHolder {

	// TODO: customize the factory to create actions that can gracefully handle exceptions
	private AnnotatedActionFactory actionFactory = new DefaultActionFactory();

	private final Map<String, AnnotatedAction> actions = new HashMap<>();

	public ActionHolder() {

	}

	public ActionHolder(ActionHolder ah) {
		this();
		registerAll(ah);
	}
	
	public static ActionHolder on(Object target, String prefix, String[] filteredIds) {
		ActionHolder ah = new ActionHolder();
		ah.register(target, prefix, filteredIds);
		return ah;
	}
	
	public static ActionHolder on(Object target, String prefix) {
		return on(target, prefix, null);
	}
	
	public static ActionHolder on(Object target) {
		return on(target, null, null);
	}

	
	public static ActionHolder on(ActionHolder superAh, Object target, String prefix, String[] filteredIds) {
		ActionHolder ah = new ActionHolder(superAh);
		ah.register(target, prefix, filteredIds);
		return ah;
	}

	public static ActionHolder on(ActionHolder superAh, Object target, String prefix) {
		return on(superAh, target, prefix, null);
	}
	
	public static ActionHolder on(ActionHolder superAh, Object target) {
		return on(superAh, target, null, null);
	}
	
	//
	
    /**
     * Returns action by its id.
     * @param id action id
     * @return action or null if id was not found
     */
    public Action getAction(String id) {
        return actions.get(id);
    }

    /**
     * Returns collection of actions by ids. Useful to create ActionGroups.
     * Ids starting with "---" are converted to ActionUtils#ACTION_SEPARATOR.
     * Incorrect ids are ignored. 
     * @param ids action ids
     * @return collection of actions
     */
    public Collection<Action> getActions(String... ids) {
        List<Action> result = new ArrayList<>();
        for( String id: ids ) {
            if ( id.startsWith("---")) {
            	result.add(ActionUtils.ACTION_SEPARATOR); //$NON-NLS-1$
            } else {
            	Action action = getAction(id);
            	if ( action != null ) result.add(action);
            }
        }
        return result;
    }
    
    public Map<String, AnnotatedAction> getActionMap(String... ids) {
    	Map<String, AnnotatedAction> map = new HashMap<>();
    	for( String id: ids ) {
            AnnotatedAction action = actions.get(id);
            if ( action != null ) map.put(id, action);
        }
    	return map;
    }
    
    public Map<String, AnnotatedAction> getActionMap(String prefix) {
    	Map<String, AnnotatedAction> map = new HashMap<>();
    	for( String id: actions.keySet() ) {
    		if (id.startsWith(prefix)) {
    			AnnotatedAction action = actions.get(id);
    			if ( action != null ) map.put(id, action);
        
    		}
    	}
    	return map;
    }

	public void register(Object target, String prefix) {
		register(target, prefix, null);
	}

	public void register(Object target) {
		register(target, null, null);
	}


	/**
	 * Attempts to convert target's methods annotated with {@link ActionProxy} to {@link Action}s.
	 * Three types of methods are currently converted: parameter-less methods, 
	 * methods with one parameter of type {@link ActionEvent}, and methods with two parameters
	 * ({@link ActionEvent}, {@link Action}).
	 * 
	 * Note that this method supports safe re-registration of a given instance or of another instance of the
	 * same class that has already been registered. If another instance of the same class is registered, then
	 * those actions will now be associated with the new instance. The first instance is implicitly unregistered.
	 * 
	 * Actions are registered with their id or method name if id is not defined.
	 * 
	 * @param target object to work on
	 * @param prefix (may be null) to be added in front of id
	 * @param filteredIds array of ids to be registered from the ones resulting from the scanning of annotations
	 * @throws IllegalStateException if a method with unsupported parameters is annotated with {@link ActionProxy}.
	 */
	public void register(Object target, String prefix, String[] filteredIds) {
		if (prefix == null) {
			prefix = "";
		}

		for (Method method : target.getClass().getDeclaredMethods()) {
			// Only process methods that have the ActionProxy annotation
			Annotation[] annotations = method.getAnnotationsByType(ActionProxy.class);
			if (annotations.length == 0) {
				continue;
			}

			// Only process methods that have
			// a) no parameters OR 
			// b) one parameter of type ActionEvent OR
			// c) two parameters (ActionEvent, Action)
			int paramCount = method.getParameterCount();
			Class<?>[] paramTypes = method.getParameterTypes();

			if (paramCount > 2) {
				throw new IllegalArgumentException( String.format( "Method %s has too many parameters", method.getName() ) );
			}

			if (paramCount == 1 && !ActionEvent.class.isAssignableFrom( paramTypes[0] )) {
				throw new IllegalArgumentException( String.format( "Method %s -- single parameter must be of type ActionEvent", method.getName() ) );
			}

			if (paramCount == 2 && (!ActionEvent.class.isAssignableFrom( paramTypes[0] ) || 
					!Action.class.isAssignableFrom( paramTypes[1] ))) {
				throw new IllegalArgumentException( String.format( "Method %s -- parameters must be of types (ActionEvent, Action)", method.getName() ) );
			}

			ActionProxy annotation = (ActionProxy) annotations[0];

			AnnotatedActionFactory factory = determineActionFactory( annotation );
			AnnotatedAction action = factory.createAction( annotation, method, target );

			String id = annotation.id().isEmpty() ? method.getName() : annotation.id();
			if (checkFilteredId(id, filteredIds)) { 
				actions.put( prefix + id, action );
			}
		}
	}
	
	public void registerAll(ActionHolder ah) {
		actions.putAll(ah.actions);
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


	private AnnotatedActionFactory determineActionFactory( ActionProxy annotation ) {
		// Default to using the global action factory
		AnnotatedActionFactory factory = actionFactory;

		// If an action-factory has been specified on this specific ActionProxy, then
		// instantiate it and use it instead.
		String factoryClassName = annotation.factory();
		if (!factoryClassName.isEmpty()) {
			try {
				Class<?> factoryClass = Class.forName( factoryClassName );
				factory = (AnnotatedActionFactory) factoryClass.newInstance();

			} catch (ClassNotFoundException ex) {
				throw new IllegalArgumentException( String.format( "Action proxy refers to non-existant factory class %s", factoryClassName ), ex );

			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalStateException( String.format( "Unable to instantiate action factory class %s", factoryClassName ), ex );
			}
		}

		return factory;
	}
}
