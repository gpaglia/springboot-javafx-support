package de.felixroske.jfxsupport;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The annotation {@link FXMLController} is used to mark JavaFX controller
 * classes. Usage of this annotation happens besides registration of such within
 * fxml descriptors.
 *
 * @author Felix Roske
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLController {

}
