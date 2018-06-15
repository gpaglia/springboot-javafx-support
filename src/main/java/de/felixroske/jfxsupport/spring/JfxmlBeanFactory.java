package de.felixroske.jfxsupport.spring;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


public class JfxmlBeanFactory extends DefaultListableBeanFactory {
	Logger logger = LoggerFactory.getLogger(JfxmlBeanFactory.class);

	private String name;
	
	public JfxmlBeanFactory(String name) {
		super();
		this.name = name;
	}

	public JfxmlBeanFactory(String name, BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
		this.name = name;
	}
	
	boolean containsLocalBeanDefinition(String beanName) {
		return super.containsBeanDefinition(beanName);
	}
	
	BeanDefinition getLocalBeanDefinition(String beanName) {
		return super.getBeanDefinition(beanName);
	}
	
	<A extends Annotation> A findAnnotationOnLocalBean(String beanName, Class<A> annotationType) {
		return super.findAnnotationOnBean(beanName,  annotationType);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		logger.trace("{}: Checking bean definition for bean {}, parentFactory {}", name, beanName, getParentBeanFactory());
		
		if ((beanName.equals("auto") || beanName.matches("test.*")) && name.equals("Child")) {
			logger.trace("Hello...");
		}
		
		if (containsLocalBeanDefinition(beanName)) { 
			logger.trace("{}: Found local bean definition", name);
			return true;
		} else if (! (getParentBeanFactory() instanceof ConfigurableListableBeanFactory)){
			logger.trace("{}: Not found because parent is null or wrong type ({})", name, getParentBeanFactory() == null ? "null" : getParentBeanFactory().getClass().getName());
			return false;
		}

		// search the hierarchy for the bean definition
		boolean found = false;
		
		ConfigurableListableBeanFactory ancestor = (ConfigurableListableBeanFactory) getParentBeanFactory();
		
		logger.trace("{}: Checking the ancestors...", name);
		
		do {
			
			if (false && logger.isTraceEnabled()) {
				logger.trace("=== Ancestor {}, class: {}", ancestor, ancestor.getClass().getName());
				BeanDefinition tmp = ancestor instanceof JfxmlBeanFactory ? ((JfxmlBeanFactory) ancestor).getLocalBeanDefinition(beanName) : ancestor.getBeanDefinition(beanName);
				logger.trace("=== BD for name {} is {}", beanName, tmp);
				if (tmp != null) {
					logger.trace("=== Annotation {}", ancestor.findAnnotationOnBean(beanName, ViewScope.class));
				} else {
					logger.trace("=== No annotation");
				}
			}

			if (checkFactoryForBean(ancestor, beanName, ViewScope.class)) {
				found = true;
				logger.trace("{}: Bean definition found (contains) in jfxml ancestor for name {}", name, beanName);				
			} else if (ancestor.getParentBeanFactory() instanceof ConfigurableListableBeanFactory){
				ancestor = (ConfigurableListableBeanFactory) ancestor.getParentBeanFactory();
				logger.trace("{}: Bean definition {} not found (contains), ancestor not null", name, beanName);
			} else {
				ancestor = null;
				logger.trace("{}: Bean {} not found (contains), no more ancestors", name, beanName);
			}
			
		} while (! found && ancestor != null);
		
		logger.trace("Returning {}", found);
		return found;
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		logger.trace("{}: Getting bean definition for bean {}, parentFactory {}", name, beanName, getParentBeanFactory());
		
		if ((beanName.equals("auto") || beanName.matches("test.*")) && name.equals("Child")) {
			logger.trace("Hello again ...");
		}
		
		if (containsLocalBeanDefinition(beanName)) { 
			logger.trace("{}: Returning local bean definition", name);
			return getLocalBeanDefinition(beanName);
		} else if (! (getParentBeanFactory() instanceof ConfigurableListableBeanFactory)){
			logger.trace("{}: Throwing NoSuchBeanDefinitionException because parent is null or wrong type ({})", name, getParentBeanFactory() == null ? "null" : getParentBeanFactory().getClass().getName());
			throw new NoSuchBeanDefinitionException(beanName);
		}

		// search the hierarchy for the bean definition
		BeanDefinition bd = null;
		
		ConfigurableListableBeanFactory ancestor = (ConfigurableListableBeanFactory) getParentBeanFactory();
		
		logger.trace("{}: Searching the ancestors...", name);
		
		do {
			if (logger.isTraceEnabled()) {
				logger.trace("*** Ancestor {}, class: {}", ancestor, ancestor.getClass().getName());
				BeanDefinition tmp = ancestor instanceof JfxmlBeanFactory ? ((JfxmlBeanFactory) ancestor).getLocalBeanDefinition(beanName) : ancestor.getBeanDefinition(beanName);
				logger.trace("*** BD for name {} is {}", beanName, tmp);
				if (tmp != null) {
					logger.trace("*** Annotation {}", ancestor instanceof JfxmlBeanFactory ? ((JfxmlBeanFactory) ancestor).findAnnotationOnLocalBean(beanName, ViewScope.class): ancestor.findAnnotationOnBean(beanName, ViewScope.class));
				} else {
					logger.trace("*** No annotation");
				}
			}
			
			if (ancestor instanceof JfxmlBeanFactory) {
				JfxmlBeanFactory jf = (JfxmlBeanFactory) ancestor;
				if (jf.containsLocalBeanDefinition(beanName) && jf.findAnnotationOnLocalBean(beanName, ViewScope.class) != null) {
					bd = ((JfxmlBeanFactory) ancestor).getLocalBeanDefinition(beanName);
					logger.trace("{}: Bean definition found in jfxml ancestor for name {}, bd: {}", name, beanName, bd);				
				}
			} else {
				if (ancestor.containsBeanDefinition(beanName) && ancestor.findAnnotationOnBean(beanName,  ViewScope.class) != null) {
					bd = ancestor.getBeanDefinition(beanName);
					logger.trace("{}: Bean definition found in ancestor for name {}, bd: {}", name, beanName, bd);
				}
			} 
			
			if (bd == null) {
			
				if (ancestor.getParentBeanFactory() instanceof ConfigurableListableBeanFactory){
					logger.trace("{}: Bean definition {} not found, ancestor not null", name, beanName);
					ancestor = (ConfigurableListableBeanFactory) ancestor.getParentBeanFactory();
				} else {
					ancestor = null;
					logger.trace("{}: Bean definition {} not found, no more ancestors", name, beanName);
				}
			}
		} while (bd == null && ancestor != null);
		
		if (bd != null) {
			this.registerBeanDefinition(beanName, bd);
			logger.trace("{}: Bean definition registered, names registered so far: {}", name, Arrays.toString(this.getBeanDefinitionNames()));
			logger.trace("Returning bean definition {}", bd);
			return bd;
		} else {
			logger.trace("{}: Throwing NoSuchBeanDefinitionException because bean not found in ancestors", name);
			throw new NoSuchBeanDefinitionException(beanName);
		}

	}

	
	private boolean checkFactoryForBean(ConfigurableListableBeanFactory factory, String beanName, Class<? extends Annotation> annotationType) {
		if (factory instanceof JfxmlBeanFactory) {
			JfxmlBeanFactory jf = (JfxmlBeanFactory) factory;
			if (jf.containsLocalBeanDefinition(beanName) && jf.findAnnotationOnLocalBean(beanName, annotationType) != null) {
				logger.trace("{}: Found bean {} in ancestor (jfxml) {} with annotation {}", name, beanName, factory.getClass().getName(), annotationType.getClass().getName());
				return true;
			}
		} else {
			if (factory.containsBeanDefinition(beanName) && factory.findAnnotationOnBean(beanName,  annotationType) != null) {
				logger.trace("{}: Found bean {} in ancestor {} with annotation {}", name, beanName, factory.getClass().getName(), annotationType.getClass().getName());
				return true;
			}
		}
		
		logger.trace("{}: Not found... ", name);
		
		return false;
	}
	
	
}
