package de.felixroske.misc.context;

public class AnotherBeanChild {

    private final String name;

    public AnotherBeanChild(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    public void sayHello() {
        System.out.println(String.format("AnotherB Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
