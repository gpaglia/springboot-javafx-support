package jfxtest.plainuserdata;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class PlainUserDataApp extends AbstractJavaFxApplicationSupport{

    public static void main(String[] args) {
            launchApp(PlainUserDataApp.class, PlainUserDataView.class, args);
    }
}