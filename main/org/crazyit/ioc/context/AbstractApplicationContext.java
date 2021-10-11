package org.crazyit.ioc.context;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.crazyit.ioc.context.exception.BeanCreateException;
import org.crazyit.ioc.xml.*;
import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.autowire.NoAutowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.construct.ValueElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public abstract class AbstractApplicationContext implements ApplicationContext{
    protected DocumentHolder documentHolder = new XmlDocumentHolder();
    protected ElementLoader elementLoader = new ElementLoaderImpl();
    protected ElementReader elementReader = new ElementReaderImpl();
    protected BeanCreator beanCreator = new BeanCreatorImpl();
    PropertyHandler propertyHandler = new PropertyHandlerImpl();
    protected Map<String,Object> beans = new HashMap<>();
    protected void setUpElements(String[] xmlPaths) {
        URL resourceUrl = AbstractApplicationContext.class.getClassLoader().getResource(".");
        String classPath = null;
        try {
            classPath = URLDecoder.decode(resourceUrl.getPath(), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String xmlPath : xmlPaths) {
            String filePath = classPath + xmlPath;
            Document document = documentHolder.getDocument(filePath);
            elementLoader.addElements(document);
        }
    }
    protected void createBeans() {
        Collection<Element> elements = elementLoader.getElements();
        for (Element element : elements) {
            boolean lazy = elementReader.isLazy(element);
            if(!lazy) {
                String id = element.attributeValue("id");
                Object bean = this.getBean(id);
                if(bean == null) {
                    handleSingleton(id);
                }
            }
        }
    }


    @Override
    public Object getBean(String id) {
        Object o = this.beans.get(id);
        if(o == null) {
            o = handleSingleton(id);
        }
        return o;
    }
    protected Object handleSingleton(String id) {
        boolean isSingleton = elementReader.isSingleton(elementLoader.getElement(id));
        Object bean = null;
        bean = createBean(id);
        if(isSingleton) {
            this.beans.put(id,bean);
        }
        return bean;
    }
    protected Object createBean(String id) {
        Element element = elementLoader.getElement(id);
        if (element == null) {
            throw new BeanCreateException("not such element exception");
        }
        Object result = instance(element);
        System.out.println("class id : "+id+" , result : "+result);
        Autowire autowire = elementReader.getAutowire(element);
        if (autowire instanceof NoAutowire) {
            setterInject(result,element);
        }else {
            autowireByName(result,element);
        }
        return result;
    }
    protected void setterInject(Object obj,Element e) {
        List<PropertyElement> propertyValue = elementReader.getPropertyValue(e);
        Map<String, Object> propertyArg = getPropertyArg(propertyValue);
        propertyHandler.setProperties(obj,propertyArg);
    }
    protected Map<String,Object> getPropertyArg(List<PropertyElement> list) {
        Map<String,Object> result = new HashMap<>();
        for (PropertyElement element : list) {
            String type = element.getValue().getType();
            String name = element.getName();
            DataElement dataElement = element.getValue();
            switch (type){
                case "value":
                    result.put(name,dataElement.getValue());
                    break;
                case "ref":
                    result.put(name,getBean((String)dataElement.getValue()));
                    break;
                default:
                    break;
            }
        }
        return result;
    }
    protected void autowireByName(Object obj,Element e) {
        Map<String, Method> settingMethodsMap = propertyHandler.getSettingMethodsMap(obj);
        for (String s : settingMethodsMap.keySet()) {
            Element element = elementLoader.getElement(s);
            if(element == null) continue;
            Object bean = this.getBean(s);
            Method method = settingMethodsMap.get(s);
            propertyHandler.executeMethod(obj,bean,method);
        }
    }

    protected Object instance(Element e) {
        List<Element> constructorElements = elementReader.getConstructorElements(e);
        String className = elementReader.getAttribute(e, "class");
        Object bean = null;
        if(constructorElements.size() == 0) {
            bean = beanCreator.createBeanUseDefaultConstruct(className);
        }else {
            bean = beanCreator.createBeanUseDefineConstruct(className,getConstructArgs(e));
        }
        return bean;
    }
    protected List<Object> getConstructArgs(Element e) {
        List<DataElement> dataList = elementReader.getConstructorValue(e);
        List<Object> result = new ArrayList<>();
        for (DataElement dataElement : dataList) {
            String type = dataElement.getType();
            if (type.equals("value")) {
                result.add(dataElement.getValue());
            }else {
                String ref = (String)dataElement.getValue();
                Object bean = getBean(ref);
                result.add(bean);
            }
        }

        return result;
    }

    @Override
    public Object getBeanIgnoreCreate(String id) {
        return beans.get(id);
    }

    @Override
    public boolean isContainsBean(String id) {
        return elementLoader.getElement(id)!=null;
    }

    @Override
    public boolean isSingleton(String id) {
        Element element = elementLoader.getElement(id);
        return elementReader.isSingleton(element);
    }

    public static void main(String[] args) {
        String xmlPath = "resources/XmlHolder.xml";
        URL resourceUrl = AbstractApplicationContext.class.getClassLoader().getResource(".");
        String filePath = resourceUrl.getPath()+xmlPath;
        XmlDocumentHolder xmlDocumentHolder = new XmlDocumentHolder();
        Document document = xmlDocumentHolder.getDocument(filePath);
        System.out.println(document.getRootElement());

    }
}
