package de.felixroske.jfxsupport.context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MethodWrapper {
	private final String id;
    private final Method method;
    private final WeakReference<Object> target;

    /**
     * Instantiates a method wrapper that will call the specified method on the specified target.
     * A weak reference is created to the target instance defining the method
     * 
     * @param id The id of the wrapped method
     * @param method The Method to be wrapped
     * @param target The Object (instance) declaring method
     */
    public MethodWrapper(String id, Method method, Object target) {
    	Objects.requireNonNull(id);
        Objects.requireNonNull( method );
        Objects.requireNonNull( target );
        
        this.id = id;
        this.method = method;
        this.method.setAccessible(true);
        this.target = new WeakReference<Object>( target );
    }
    
    /**
     * Returns the id.
     * 
     * @return The id.
     */
    public String getId() {
    	return id;
    }
    
    /**
     * Returns the target object (the object on which the annotated method will be called).
     * 
     * @return The target object, or null if the target object has been garbage-collected.
     */
    public Object getTarget() {
        return target.get();
    }

    /**
     * Invoke the annotated method on the target object. If an exception is
     * thrown, then the default implementation of this method will rethrow the exception
     * possibly extracting the target exception in case of InvocationTargetException.
     * 
     * @param <T> The expected return type, must be class assignable from the return type of the wrapped method
     * @param clazz the class the result should be cast to (must be assignable from the method return type
     * @param args the arguments into method invocation; must be assignable-compatible with the method signature
     * 
     * @return the method return value cast to clazz
     */
    public <T> T call(Class<T> clazz, Object... args) {
    	Object actionTarget = getTarget();
    	if (actionTarget == null) {
    		throw new IllegalStateException( "Action target object is no longer reachable" );
    	} else if (! clazz.isAssignableFrom(method.getReturnType())) {
    		throw new IllegalArgumentException( "Incomaptible return type" );    		
    	} else if (! checkParamsCompatibility(args)) {
    		throw new IllegalArgumentException( "Incomaptible param types" );
    	}

    	try {
    		Object result = method.invoke(actionTarget, args);
    		return clazz.cast(result);
    	} catch (Throwable e) {
    		if (e instanceof InvocationTargetException) {
        		Throwable th = ((InvocationTargetException) e).getCause();
        		if (th instanceof RuntimeException) {
        			throw (RuntimeException) th;
        		} else {
        			throw new WrappedActionException(th);
        		}
        	}
        	
        	throw new WrappedActionException(e);
    	}
    }
    
    /**
     * Invoke the annotated method on the target object, ignoring its result. If an exception is
     * thrown, then the default implementation of this method will rethrow the exception
     * possibly extracting the target exception in case of InvocationTargetException.
     * 
     * @param args the arguments into method invokation; must be assignable-compatible with the method signature
     * 
     */
    public void call(Object... args) {
    	call(Object.class, args);
    }

    private boolean checkParamsCompatibility(Object... args) {
    	if (args.length != method.getParameterCount()) {
    		return false;
    	}
    	
    	Class<?>[] types = method.getParameterTypes();
    	
    	for (int i = 0; i < args.length; i++) {
    		if (! types[i].isAssignableFrom(args[i].getClass())) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    /**
     * Overridden to return the text of this action.
     */
    @Override
    public String toString() {
        return getId();
    }
}

