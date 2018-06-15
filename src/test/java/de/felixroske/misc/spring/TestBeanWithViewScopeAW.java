package de.felixroske.misc.spring;

import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.spring.ViewScope;

@ViewScope
public class TestBeanWithViewScopeAW {

    private final String name;
    
    @Autowired
    private TestBean testBean;
    
    public TestBeanWithViewScopeAW(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    public TestBean getTestBean() {
    	return testBean;
    }

    public void sayHello() {
        System.out.println(String.format("TBvVSAW Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
