package de.felixroske.jfxtest.annotated;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class AnnotatedApp extends AbstractJavaFxApplicationSupport{

    public static void main(String[] args) {
            launch(AnnotatedApp.class, AnnotatedView.class, args);
    }
}