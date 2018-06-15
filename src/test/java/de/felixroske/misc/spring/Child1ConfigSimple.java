package de.felixroske.misc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Child1ConfigSimple {

	@Bean
	public TestBean testBean() {
		return new TestBean("testBean in child1Simple");
	}
}
