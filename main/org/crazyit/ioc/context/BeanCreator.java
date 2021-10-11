package org.crazyit.ioc.context;

import java.util.List;

public interface BeanCreator {

    /**
     * ʹ���޲εĹ���������beanʵ��, �������κ�����
     * @param className
     * @return
     */
    Object createBeanUseDefaultConstruct(String className);

    /**
     * ʹ���в����Ĺ���������beanʵ��, �������κ�����
     * @param className
     * @param args ��������
     * @return
     */
    Object createBeanUseDefineConstruct(String className, List<Object> args);
}
