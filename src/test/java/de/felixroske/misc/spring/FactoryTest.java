package de.felixroske.misc.spring;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.felixroske.jfxsupport.spring.JfxmlBeanFactory;

public class FactoryTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public final void givenConfiguredBeanWithUnknownScope_whenRegister_thenOK() {
		try (
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new DefaultListableBeanFactory());
				) {

			ctx.register(ParentConfigSimpleWithScope.class);
			ctx.refresh();

			assertThat(ctx, notNullValue());

		} finally {
		}
	}

	@Test
	public final void givenRegisteredBeanWithUnknownScope_whenInstantiate_thenException() {
		try (
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new DefaultListableBeanFactory());
				) {

			ctx.register(ParentConfigSimpleWithScope.class);
			ctx.refresh();

			assertThat(ctx, notNullValue());
			
			exception.expect(IllegalStateException.class);
		    exception.expectMessage("No Scope registered for scope name 'testscope'");
			TestBean tb = (TestBean) ctx.getBean("testBean", TestBean.class);

		} finally {
		}
	}

	
    @Test
    @Ignore
    public final void whenRegisterBeans_thenContextContainsBeans() {
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Parent"));
        	) {
        
            ctx.register(ParentConfig.class);
            ctx.refresh();

            checkParent(ctx);
            
            
        } finally {
        }
    }
    
    @Test
    public final void givenSimpleParentContext_whenRegisterBeanWithViewScope_thenBeanCreatedInChildContext() {
    	
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Parent"));
        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Child"));
        		) {
        	
        	// given parent context
            ctx.register(ParentConfigSimple.class);
            ctx.refresh();
            
            // when register child beans
            
            childCtx1.setParent(ctx);
            childCtx1.refresh();

            TestBeanWithViewScope tbvs = (TestBeanWithViewScope) childCtx1.getBean("testBeanWithViewScope", TestBeanWithViewScope.class);

            assertThat(tbvs, notNullValue());
            assertThat(tbvs.getName(), equalTo("testBeanWithViewScope"));
            assertThat(childCtx1.getBeanDefinitionNames(), hasItemInArray("testBeanWithViewScope"));
            
        } finally {

        }
    }
    
    
    @Test
    @Ignore
    public final void givenSimpleParentContext_whenRegisterBeanWithViewScopeAndAW_thenBeanCreatedInChildContextWithAW() {
    	
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Parent"));
        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Child"));
//        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new DefaultListableBeanFactory());
//        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext(new DefaultListableBeanFactory());
        		) {
        	
        	// given parent context
            ctx.register(ParentConfigSimpleAW.class);
            ctx.refresh();
            
            // when register child beans
            
            childCtx1.setParent(ctx);
            childCtx1.register(Child1ConfigSimple.class);
            childCtx1.refresh();

            TestBeanWithViewScopeAW tbaw = (TestBeanWithViewScopeAW) childCtx1.getBean("testBeanWithViewScopeAW", TestBeanWithViewScopeAW.class);

            assertThat(tbaw, notNullValue());
            assertThat(tbaw.getTestBean(), notNullValue());
            
        } finally {

        }
    }
    
    
    @Test
    @Ignore
    public final void givenParentContext_whenRegisterBeansAndChildContexts_thenChildBeansPresentOnlyInChildContext() {
    	
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Parent"));
        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext(new JfxmlBeanFactory("Child"));
        		) {
        	
        	// given parent context
            ctx.register(ParentConfig.class);
            ctx.refresh();

            checkParent(ctx);
            
            // when register child beans
            
            childCtx1.setParent(ctx);
            childCtx1.register(Child1Config.class);
            childCtx1.refresh();

            checkParentWithChild1(ctx, childCtx1);
            
        } finally {

        }
    }
    
    @Test
    @Ignore
    public final void givenParentContext_whenRegisterBeansAndChildContextsWithCopy_thenChildBeansPresentOnlyInChildContext() {
    	
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext();
        		) {
        	
        	// given parent context
            ctx.register(ParentConfig.class);
            ctx.refresh();

            checkParent(ctx);
            
            // when register child beans
            
            childCtx1.setParent(ctx);
            for (String beanDefinitionName : ctx.getBeanDefinitionNames()) {
            	BeanDefinition bd = ctx.getBeanDefinition(beanDefinitionName);
            	childCtx1.registerBeanDefinition(beanDefinitionName, bd);
            }
            childCtx1.register(Child1Config.class);
            childCtx1.refresh();

            checkParentWithChild1(ctx, childCtx1);
            
        } finally {

        }
    }
    
    @Test
    @Ignore
    public final void whenComponentScan_thenContextContainsBeans() {
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        		AnnotationConfigApplicationContext childCtx1 = new AnnotationConfigApplicationContext();
        	) {
            ctx.scan("de.felixroske.misc.context.parent");
            ctx.refresh();

            checkParent(ctx);
            
            childCtx1.scan("de.felixroske.misc.context.parent.child1");
            childCtx1.refresh();

            checkParentWithChild1(ctx, childCtx1);

            
        } finally {
        	//
        }
    }
    
    @Test
    @Ignore
    public final void givenParentContext_whenChildComponentScan_thenChildBeansPresentOnlyInChildContext() {
        try (
        		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        	) {
            ctx.scan("de.felixroske.misc.context.parent");
            ctx.refresh();

            checkParent(ctx);
        } finally {
        	//
        }
    }

    public void checkParent(AnnotationConfigApplicationContext ctx) {
        TestBean proto_1 = (TestBean) ctx.getBean("proto_1", TestBean.class);
        TestBean proto_2 = (TestBean) ctx.getBean("proto_2", TestBean.class);
        TestBean single_1 = (TestBean) ctx.getBean("single_1", TestBean.class);
        TestBean single_2 = (TestBean) ctx.getBean("single_2", TestBean.class);

        Map<String, TestBean> beans = ctx.getBeansOfType(TestBean.class);

        assertThat(proto_1, not(equalTo(proto_2)));
        assertThat(proto_1, not(equalTo(single_1)));            
        assertThat(proto_1, not(equalTo(single_2)));            
        assertThat(single_1, not(equalTo(single_2)));
        assertThat(proto_2, not(equalTo(single_2)));
        
        assertThat(beans.size(), equalTo(4));
        assertTrue(!beans.containsValue(proto_1));
        assertTrue(!beans.containsValue(proto_2));
        assertTrue(beans.containsValue(single_1));
        assertTrue(beans.containsValue(single_2));

        assertThat(proto_1.getName(), equalTo("proto_1"));
        assertThat(proto_2.getName(), equalTo("proto_2"));
        assertThat(single_1.getName(), equalTo("single_1"));
        assertThat(single_2.getName(), equalTo("single_2"));
        
    }
    
    private void checkParentWithChild1(AnnotationConfigApplicationContext ctx, AnnotationConfigApplicationContext childCtx1) {
        TestBean single_1_p = (TestBean) ctx.getBean("single_1", TestBean.class);
        TestBean single_2_p = (TestBean) ctx.getBean("single_2", TestBean.class);
        TestBean single_1_c_1 = (TestBean) childCtx1.getBean("single_1", TestBean.class);
        TestBean single_2_c_1 = (TestBean) childCtx1.getBean("single_2", TestBean.class);
       
        AutowiredBeanChild auto_c_1 = (AutowiredBeanChild) childCtx1.getBean("autoChild", AutowiredBeanChild.class); 
        YetAnotherBean yetAnother = (YetAnotherBean) childCtx1.getBean("yetAnotherBean", YetAnotherBean.class);
        
        assertThat(single_1_p, not(equalTo(single_1_c_1)));
        assertThat(single_2_p, not(equalTo(single_2_c_1)));
        assertThat(single_1_p.getName(), equalTo("single_1"));
        assertThat(single_1_c_1.getName(), equalTo("single_1_child_1"));
        assertThat(single_2_c_1.getName(), equalTo("single_2_child_1"));
        assertThat(auto_c_1.getAnotherBean().getName(), equalTo("another"));
        assertThat(yetAnother.getName(), equalTo("yetAnother"));
        
        AutowiredBean auto = (AutowiredBean) childCtx1.getBean("auto", AutowiredBean.class);
        assertThat(auto.getAnotherBeanChild().getName(), equalTo("another_child"));
        assertThat(yetAnother.getAutowiredBean().getAnotherBeanChild().getName(), equalTo(""));

    }
}
