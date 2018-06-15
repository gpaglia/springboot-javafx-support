package de.felixroske.misc.context.child1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.felixroske.misc.context.AnotherBeanChild;
import de.felixroske.misc.context.AutowiredBeanChild;
import de.felixroske.misc.context.TestBean;
import de.felixroske.misc.context.YetAnotherBean;

@Configuration
public class TestBeansChild1Config {

    @Bean
    @Scope("prototype")
    public TestBean proto_1() {
        return new TestBean("proto_1_child_1");
    }

    @Bean
    @Scope("prototype")
    public TestBean proto_2() {
        return new TestBean("proto_2_child_1");
    }
    
    @Bean
    @Scope("singleton")
    public TestBean single_1() {
        return new TestBean("single_1_child_1");
    }

    @Bean
    @Scope("singleton")
    public TestBean single_2() {
        return new TestBean("single_2_child_1");
    }

    @Bean
    @Scope("singleton")
    public AutowiredBeanChild autoChild() {
        return new AutowiredBeanChild("auto_child");
    }
   
    @Bean
    @Scope("prototype")
    public AnotherBeanChild anotherChild() {
        return new AnotherBeanChild("another_child");
    }

    @Bean
    @Scope("prototype")
    public YetAnotherBean yetAnotherBean() {
    	return new YetAnotherBean("yetAnother");
    }
}
