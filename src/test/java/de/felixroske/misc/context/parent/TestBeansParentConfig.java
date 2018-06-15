package de.felixroske.misc.context.parent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.misc.context.AnotherBean;
import de.felixroske.misc.context.AutowiredBean;
import de.felixroske.misc.context.AutowiredOnlyParentBean;
import de.felixroske.misc.context.TestBean;

@Configuration
public class TestBeansParentConfig {

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
