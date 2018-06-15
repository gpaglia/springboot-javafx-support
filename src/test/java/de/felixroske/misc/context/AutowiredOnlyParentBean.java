package de.felixroske.misc.context;

public class AutowiredOnlyParentBean {

    private final String name;

    public AutowiredOnlyParentBean(String name) {
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }

    public void sayHello() {
        System.out.println(String.format("ABop Hello from %s of type %s", this.name, this.getClass().getName()));
    }
}
