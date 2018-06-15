package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.jfxsupport.spring.ViewScope;

@Configuration
public class ParentConfig {

    @Bean
    @Scope("prototype")
    public TestBean proto_1() {
        return new TestBean("proto_1");
    }

    @Bean
    @Scope("prototype")
    public TestBean proto_2() {
        return new TestBean("proto_2");
    }
    @Bean
    @Scope("singleton")
    public TestBean single_1() {
        return new TestBean("single_1");
    }

    @Bean
    @Scope("singleton")
    public TestBean single_2() {
        return new TestBean("single_2");
    }

    @Bean
    @Scope("prototype")
    @ViewScope()
    public AutowiredBean auto() {
        return new AutowiredBean("auto");
    }
    
    @Bean
    @Scope("prototype")
    public AutowiredOnlyParentBean autoop() {
        return new AutowiredOnlyParentBean("autoop");
    }
    
    @Bean
    @Scope("prototype")
    public AnotherBean another() {
        return new AnotherBean("another");
    }
    
}
