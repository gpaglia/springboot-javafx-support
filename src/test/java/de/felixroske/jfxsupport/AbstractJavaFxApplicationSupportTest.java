package de.felixroske.jfxsupport;

import javafx.scene.image.Image;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import de.felixroske.jfxtest.annotated.AnnotatedView;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertThat;

/**
 * Created on 11/3/2017 for Onyx.
 */
public class AbstractJavaFxApplicationSupportTest {

    private AbstractJavaFxApplicationSupport app;

    public static class TestApp extends AbstractJavaFxApplicationSupport {

    }

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("testfx.headless", "true");
    }

    @AfterClass
    public static void afterClass() throws InterruptedException {
        System.setProperty("testfx.headless", "false");
        Thread.sleep(2000);
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new TestApp();
        AbstractJavaFxApplicationSupport.savedInitialView = AnnotatedView.class;
        AbstractJavaFxApplicationSupport.splashScreen = new SplashScreen();
        FxToolkit.setupApplication(() -> app);
    }

    @Test
    public void loadDefaultIcons() throws Exception {
        final Collection<Image> images = new ArrayList<>();
        images.addAll(app.loadDefaultIcons());
        assertThat(images.size(), CoreMatchers.is(5));
    }

}