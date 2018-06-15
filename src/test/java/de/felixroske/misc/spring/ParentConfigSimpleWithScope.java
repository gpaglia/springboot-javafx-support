package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ParentConfigSimpleWithScope {

    @Bean
    @Scope("testscope")
    public TestBean testBean() {
        return new TestBean("testBean");
    }
    
}
