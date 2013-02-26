/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sentaca;

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.as.server.ServerEnvironmentService;
import org.jboss.logging.Logger;
import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.value.InjectedValue;

public class HASpringActivator implements ServiceActivator {
  private final Logger log = Logger.getLogger(this.getClass());

  @Override
  public void activate(ServiceActivatorContext context) {
    log.info("HATimerService will be installed!");

    /*
     * We can pass a chain of election policies to the singleton, for example to
     * tell JGroups to prefer running the singleton on a node with a particular
     * name
     */
    // singleton.setElectionPolicy(new PreferredSingletonElectionPolicy(new
    // SimpleSingletonElectionPolicy(), new NamePreference("node2/cluster")));

    InjectedValue<ServerEnvironment> env = new InjectedValue<ServerEnvironment>();

    HASpringService service = new HASpringService(env);
    SingletonService<String> singleton = new SingletonService<String>(service, HASpringService.SINGLETON_SERVICE_NAME);

    singleton.build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
        .addDependency(ServerEnvironmentService.SERVICE_NAME, ServerEnvironment.class, env).setInitialMode(ServiceController.Mode.ACTIVE).install();
  }
}
