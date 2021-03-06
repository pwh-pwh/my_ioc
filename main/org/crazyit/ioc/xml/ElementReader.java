package org.crazyit.ioc.xml;

import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Element;

import java.util.List;

public interface ElementReader {
    /**
     * 判断一个bean元素是否需要延迟加载
     * @param element
     * @return
     */
    boolean isLazy(Element element);

    /**
     * 获得一个bean元素下的constructor-arg
     * @param element
     * @return
     */
    List<Element> getConstructorElements(Element element);

    /**
     * 得到元素属性为name的属性值
     * @param element
     * @param name
     * @return
     */
    String getAttribute(Element element, String name);

    /**
     * 判断一个元素是否为单态
     * @param element
     * @return
     */
    boolean isSingleton(Element element);

    /**
     * 获得一个bean元素下所有property元素
     * @param element
     * @return
     */
    List<Element> getPropertyElements(Element element);

    /**
     * 返回一个bean元素对应的Autowire对象
     * @param element
     * @return
     */
    Autowire getAutowire(Element element);

    /**
     * 获取bean元素下所有constructor-arg的值(包括value和ref)
     * @param element
     * @return
     */
    List<DataElement> getConstructorValue(Element element);

    /**
     * 获取bean元素下所有property元素的值(包括value和ref)
     * @param element
     * @return
     */
    List<PropertyElement> getPropertyValue(Element element);
}
