package de.felixroske.misc.context.child2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.misc.context.TestBean;

@Configuration
public class TestBeansChild2Config {

    @Bean
    @Scope("prototype")
    public TestBean proto_1() {
        return new TestBean("proto_1_child_2");
    }

    @Bean
    @Scope("prototype")
    public TestBean proto_2() {
        return new TestBean("proto_2_child_2");
    }
    
    @Bean
    @Scope("singleton")
    public TestBean single_1() {
        return new TestBean("single_1_child_2");
    }

    @Bean
    @Scope("singleton")
    public TestBean single_2() {
        return new TestBean("single_2_child_2");
    }
    
}
