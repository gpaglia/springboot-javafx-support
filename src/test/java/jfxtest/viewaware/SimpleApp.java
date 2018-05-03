package jfxtest.viewaware;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class SimpleApp extends AbstractJavaFxApplicationSupport{

    public static void main(String[] args) {
            launch(SimpleApp.class, SimpleView.class, args);
    }
}