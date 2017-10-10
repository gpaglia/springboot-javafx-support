package jfxtest.plainuserdata;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.IUserDataSupport;
import de.roskenet.jfxsupport.test.GuiTest;
import javafx.util.Callback;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlainUserDataViewTest extends GuiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(PlainUserDataViewTest.class);
	
	@Autowired
	private PlainUserDataView buttonsView;
	
	@Autowired
	private BeanFactory beanFactory;
	
    @PostConstruct
    public void constructView() throws Exception {
        init(buttonsView);
    }
	
	@Test
	public void appStartsUp1() throws Exception {
		
		assertThat(buttonsView, isA(AbstractFxmlView.class));
		assertThat(buttonsView.getPresenter(), is(instanceOf(PlainUserDataController.class)));
		assertThat(buttonsView.getPresenter(), is(instanceOf(IUserDataSupport.class)));
		IUserDataSupport p = (IUserDataSupport) buttonsView.getPresenter();
		assertThat(p.getUserData(), is(nullValue()));
	}
	
	@Test
	public void appStartsUp2() throws Exception {
		PlainUserDataView buttonsView2 = beanFactory.getBean(PlainUserDataView.class, "USERDATA");
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(PlainUserDataController.class)));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(IUserDataSupport.class)));
		IUserDataSupport p = (IUserDataSupport) buttonsView2.getPresenter();
		assertThat(p.getUserData(), is(equalTo("USERDATA")));
	}
	
	@Test
	public void appStartsUp3() throws Exception {
		Callback<Class<?>, Object> factory = (type) -> {
			logger.info("*** Ignoring controller type {}", type);
			IUserDataSupport p = new PlainUserDataController();
			p.setUserData("USERDATA");
			return p;
		};
		PlainUserDataView buttonsView2 = beanFactory.getBean(PlainUserDataView.class, factory);
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(PlainUserDataController.class)));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(IUserDataSupport.class)));
		IUserDataSupport p = (IUserDataSupport) buttonsView2.getPresenter();
		assertThat(p.getUserData(), is(equalTo("USERDATA")));
	}

	@Test
	public void appStartsUp4() throws Exception {
		PlainUserDataNoSupportView buttonsView2 = beanFactory.getBean(PlainUserDataNoSupportView.class, "USERDATA");
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(PlainUserDataNoSupportController.class)));
		assertThat(buttonsView2.getPresenter(), not(is(instanceOf(IUserDataSupport.class))));
		
	}
	
	
	@Test
	public void appStartsUp5() throws Exception {
		Callback<Class<?>, Object> factory = (type) -> {
			logger.info("*** Ignoring controller type {}", type);
			IUserDataSupport p = new PlainUserDataController();
			p.setUserData("USERDATA");
			return p;
		};
		
		PlainUserDataNoControllerView buttonsView2 = beanFactory.getBean(PlainUserDataNoControllerView.class, factory);
		assertThat(buttonsView2, isA(AbstractFxmlView.class));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(PlainUserDataController.class)));
		assertThat(buttonsView2.getPresenter(), is(instanceOf(IUserDataSupport.class)));
		IUserDataSupport p = (IUserDataSupport) buttonsView2.getPresenter();
		assertThat(p.getUserData(), is(equalTo("USERDATA")));
	}
}
