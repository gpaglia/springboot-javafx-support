package de.felixroske.misc.spring;

public class TestBean {

    private final String name;

    public TestBean(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    public void sayHello() {
        System.out.println(String.format("TB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
