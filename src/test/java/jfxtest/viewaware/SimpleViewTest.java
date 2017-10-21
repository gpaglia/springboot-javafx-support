package jfxtest.viewaware;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.ActionHolder;
import de.felixroske.jfxsupport.IFxmlController;
import de.felixroske.jfxsupport.test.GuiTest;
import javafx.util.Callback;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleViewTest extends GuiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleViewTest.class);
	
	@Autowired
	private SimpleView buttonsView;
	
	@Autowired
	private BeanFactory beanFactory;
	
    @PostConstruct
    public void constructView() throws Exception {
        init(buttonsView);
    }
	
	@Test
	public void appStartsUp1() throws Exception {
		
		assertThat(buttonsView, isA(AbstractFxmlView.class));
		assertThat(buttonsView.getPresenter(), is(instanceOf(SimpleViewController.class)));
		assertThat(buttonsView.getPresenter(), is(instanceOf(IFxmlController.class)));
		assertThat(buttonsView.getPresenter(), is(instanceOf(AbstractFxmlController.class)));

		SimpleViewController p = (SimpleViewController) buttonsView.getPresenter();
		assertThat(p.getContext().getView(), is(buttonsView));
		assertThat(p.getContext().getActionHolder().getAction("ID1"), is(notNullValue()));
	}

	@Test
	public void appStartsUp2() throws Exception {
		Callback<Class<?>, Object> factory = (type) -> {
			logger.info("*** Ignoring controller type {}", type);
			IFxmlController p = new AnotherViewAwareController();
			return p;
		};
		SimpleViewControllerParent svcp = new SimpleViewControllerParent();
		
		ActionHolder actions = new ActionHolder();
		actions.register(svcp);
		
		SimpleView buttonsView2 = beanFactory.getBean(SimpleView.class, "USERDATA", actions, factory);
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(AnotherViewAwareController.class)));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(IFxmlController.class)));
		assertThat(buttonsView.getPresenter(), is(instanceOf(AbstractFxmlController.class)));

		AnotherViewAwareController p = (AnotherViewAwareController) buttonsView2.getPresenter();
		assertThat(p.getContext().getView(), is(equalTo(buttonsView2)));
		assertThat(p.getContext().getUserData(), is(equalTo("USERDATA")));
		assertThat(p.getContext().getActionHolder().getAction("ID1"), is(notNullValue()));
		assertThat(p.getContext().getActionHolder().getAction("ID2"), is(notNullValue()));

	}


	@Test
	public void appStartsUp3() throws Exception {
		SimpleNoSupportView buttonsView2 = beanFactory.getBean(SimpleNoSupportView.class, "USERDATA");
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(SimpleNoSupportViewController.class)));
		assertThat(buttonsView2.getPresenter(), not(is(instanceOf(IFxmlController.class))));
		assertThat(buttonsView2.getUserData(), is(equalTo("USERDATA")));
	}
	
	
	@Test
	public void appStartsUp4() throws Exception {
		SimpleNoSupportView buttonsView2 = beanFactory.getBean(SimpleNoSupportView.class, "USERDATA");
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(SimpleNoSupportViewController.class)));
		assertThat(buttonsView2.getPresenter(), not(is(instanceOf(IFxmlController.class))));
		
	}
	
}
