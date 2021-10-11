package org.crazyit.ioc.xml;

import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Element;

import java.util.List;

public interface ElementReader {
    /**
     * �ж�һ��beanԪ���Ƿ���Ҫ�ӳټ���
     * @param element
     * @return
     */
    boolean isLazy(Element element);

    /**
     * ���һ��beanԪ���µ�constructor-arg
     * @param element
     * @return
     */
    List<Element> getConstructorElements(Element element);

    /**
     * �õ�Ԫ������Ϊname������ֵ
     * @param element
     * @param name
     * @return
     */
    String getAttribute(Element element, String name);

    /**
     * �ж�һ��Ԫ���Ƿ�Ϊ��̬
     * @param element
     * @return
     */
    boolean isSingleton(Element element);

    /**
     * ���һ��beanԪ��������propertyԪ��
     * @param element
     * @return
     */
    List<Element> getPropertyElements(Element element);

    /**
     * ����һ��beanԪ�ض�Ӧ��Autowire����
     * @param element
     * @return
     */
    Autowire getAutowire(Element element);

    /**
     * ��ȡbeanԪ��������constructor-arg��ֵ(����value��ref)
     * @param element
     * @return
     */
    List<DataElement> getConstructorValue(Element element);

    /**
     * ��ȡbeanԪ��������propertyԪ�ص�ֵ(����value��ref)
     * @param element
     * @return
     */
    List<PropertyElement> getPropertyValue(Element element);
}
