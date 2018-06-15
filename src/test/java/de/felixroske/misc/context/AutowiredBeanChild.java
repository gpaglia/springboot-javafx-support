package de.felixroske.misc.context;

import org.springframework.beans.factory.annotation.Autowired;

public class AutowiredBeanChild {

    private final String name;
    private AnotherBean anotherBean;

    public AutowiredBeanChild(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    @Autowired
    public void setAnotherBean(AnotherBean anotherBean) {
    	this.anotherBean = anotherBean;
    }
    
    public AnotherBean getAnotherBean() {
    	return anotherBean;
    }
    
    public void sayHello() {
        System.out.println(String.format("AB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
