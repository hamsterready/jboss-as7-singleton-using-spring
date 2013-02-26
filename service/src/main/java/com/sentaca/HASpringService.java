package com.sentaca;

import org.jboss.as.server.ServerEnvironment;
import org.jboss.logging.Logger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HASpringService implements Service<String> {
  private static final Logger LOGGER = Logger.getLogger(HASpringService.class);

  public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("sentaca/jboss-as7", "ha", "singleton", "spring-context");
  private InjectedValue<ServerEnvironment> env;

  private AnnotationConfigApplicationContext springContext;

  public HASpringService(InjectedValue<ServerEnvironment> env) {
    this.env = env;
  }

  @Override
  public String getValue() throws IllegalStateException, IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void start(StartContext context) throws StartException {
    springContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    LOGGER.info("DEFINED " + springContext.getBeanDefinitionCount() + " BEAN(S). ENV: " + env.getValue().getNodeName());
  }

  @Override
  public void stop(StopContext context) {
    LOGGER.info("DESTROYING... ENV: " + env.getValue().getNodeName());
    springContext.destroy();
  }

}
