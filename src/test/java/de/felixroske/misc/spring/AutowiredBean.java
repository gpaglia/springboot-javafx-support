package de.felixroske.misc.spring;

import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.spring.ViewScope;

@ViewScope
public class AutowiredBean {

    private final String name;
    private AnotherBeanChild anotherBean;

    public AutowiredBean(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    @Autowired
    public void setAnotherBeanChild(AnotherBeanChild anotherBean) {
    	this.anotherBean = anotherBean;
    }
    
    public AnotherBeanChild getAnotherBeanChild() {
    	return anotherBean;
    }
    
    public void sayHello() {
        System.out.println(String.format("AB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
