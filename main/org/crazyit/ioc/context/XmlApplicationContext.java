package org.crazyit.ioc.context;

public class XmlApplicationContext extends AbstractApplicationContext{
    public XmlApplicationContext(String[] xmlPaths) {
        setUpElements(xmlPaths);
        createBeans();
    }
}
