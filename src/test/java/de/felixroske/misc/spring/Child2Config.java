package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class Child2Config {

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
