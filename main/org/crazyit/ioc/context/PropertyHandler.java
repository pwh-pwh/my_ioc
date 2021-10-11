package org.crazyit.ioc.context;

import java.lang.reflect.Method;
import java.util.Map;

public interface PropertyHandler {
    Object setProperties(Object obj, Map<String,Object> map);
    Map<String, Method> getSettingMethodsMap(Object obj);
    void executeMethod(Object obj,Object arg,Method method);
}
