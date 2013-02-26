package com.sentaca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

  @Bean
  public MyBean myBean() {
    return new MyBean();
  }

  public class MyBean {

  }
}
