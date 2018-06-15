package de.felixroske.misc.spring;

import de.felixroske.jfxsupport.spring.ViewScope;

@ViewScope
public class TestBeanWithViewScope {

    private final String name;

    public TestBeanWithViewScope(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    public void sayHello() {
        System.out.println(String.format("TBvVS Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
