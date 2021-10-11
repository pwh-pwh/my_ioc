package org.crazyit.ioc.context;

import java.util.List;

public interface BeanCreator {

    /**
     * 使用无参的构造器创建bean实例, 不设置任何属性
     * @param className
     * @return
     */
    Object createBeanUseDefaultConstruct(String className);

    /**
     * 使用有参数的构造器创建bean实例, 不设置任何属性
     * @param className
     * @param args 参数集合
     * @return
     */
    Object createBeanUseDefineConstruct(String className, List<Object> args);
}
