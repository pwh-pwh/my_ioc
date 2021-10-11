package org.crazyit.ioc.context;

public class XmlBeanFactory extends AbstractApplicationContext{
    public XmlBeanFactory(String[] xmlPaths) {
        setUpElements(xmlPaths);
    }
}
