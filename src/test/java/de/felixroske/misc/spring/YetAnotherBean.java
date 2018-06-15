package de.felixroske.misc.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class YetAnotherBean {

    private final String name;
    
    @Autowired
    private AutowiredBean auto;

    public YetAnotherBean(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    public AutowiredBean getAutowiredBean() {
    	return auto;
    }

    public void sayHello() {
        System.out.println(String.format("YetAnotherB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
