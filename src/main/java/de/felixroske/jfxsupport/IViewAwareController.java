package de.felixroske.jfxsupport;

public interface IViewAwareController {
	AbstractFxmlView getView();
	void setView(AbstractFxmlView view);
}
