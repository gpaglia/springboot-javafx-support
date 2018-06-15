package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.spring.ViewScope;

@Configuration
public class ParentConfigSimpleAW {

    @Bean
    @Scope("prototype")
    @ViewScope()
    public TestBeanWithViewScopeAW testBeanWithViewScopeAW() {
        return new TestBeanWithViewScopeAW("testBeanWithViewScopeAW");
    }
    
}
