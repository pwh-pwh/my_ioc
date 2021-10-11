package org.crazyit.ioc.context;

public interface ApplicationContext {
    Object getBean(String id);
    boolean isContainsBean(String id);
    boolean isSingleton(String id);
    Object getBeanIgnoreCreate(String id);
}
