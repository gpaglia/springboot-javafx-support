package de.felixroske.jfxsupport.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextMethod {
    
    /**
     * By default the method name that this annotation is applied to, but if not
     * null then this ID is what you use when requesting the execution
     * from the {@link MethodHolder}.
     * 
     * @return The id of the method
     */
	String id() default "";
	
}
