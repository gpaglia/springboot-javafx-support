package de.felixroske.misc.context;

public class AnotherBean {

    private final String name;

    public AnotherBean(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    public void sayHello() {
        System.out.println(String.format("AnotherB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
