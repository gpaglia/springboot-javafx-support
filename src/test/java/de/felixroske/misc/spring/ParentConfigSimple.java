package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.spring.ViewScope;

//@Configuration
public class ParentConfigSimple {

    @Bean
    @Scope("prototype")
    public TestBeanWithViewScope testBeanWithViewScope() {
        return new TestBeanWithViewScope("testBeanWithViewScope");
    }
    
}
