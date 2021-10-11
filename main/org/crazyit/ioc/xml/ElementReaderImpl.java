package org.crazyit.ioc.xml;

import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.autowire.ByNameAutowire;
import org.crazyit.ioc.xml.autowire.NoAutowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.construct.RefElement;
import org.crazyit.ioc.xml.construct.ValueElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementReaderImpl implements ElementReader{
    @Override
    public boolean isLazy(Element element) {
        String sonIsLazy = getAttribute(element, "lazy-init");
        Element parent = element.getParent();
        Boolean parentIsLazy = new Boolean(getAttribute(parent, "default-lazy-init"));
        if(parentIsLazy) {
            return !"false".equals(sonIsLazy);
        }else {
            return "true".equals(sonIsLazy);
        }
    }

    @Override
    public List<Element> getConstructorElements(Element element) {
        List<Element> children = element.elements();
        List<Element> result = new ArrayList<>();
        for (Element e : children) {
            if ("constructor-arg".equals(e.getName())) {
                result.add(e);
            }
        }

        return result;
    }

    @Override
    public String getAttribute(Element element, String name) {
        return element.attributeValue(name);
    }

    @Override
    public boolean isSingleton(Element element) {
        return "true".equals(getAttribute(element,"singleton"));
    }

    @Override
    public List<Element> getPropertyElements(Element element) {
        List<Element> children = element.elements();
        List<Element> result = new ArrayList<>();
        for (Element e : children) {
            if ("property".equals(e.getName())) {
                result.add(e);
            }
        }

        return result;
    }

    @Override
    public Autowire getAutowire(Element element) {
        String value = getAttribute(element,"autowire");
        Element parent = element.getParent();
        String parentValue = getAttribute(parent, "default-autowire");
        if("no".equals(parentValue)) {
            if("byName".equals(value)) {
                return new ByNameAutowire(value);
            }
            return new NoAutowire(value);
        }else {
            if("no".equals(value)) {
                return new NoAutowire(value);
            }
            return new ByNameAutowire(value);
        }
    }

    @Override
    public List<DataElement> getConstructorValue(Element element) {
        List<Element> constructorElements = getConstructorElements(element);
        List<DataElement> result = new ArrayList<>();
        for (Element constructorElement : constructorElements) {
            DataElement dataElement = getDataElement((Element)constructorElement.elements().get(0));
            result.add(dataElement);
        }
        return result;
    }
    /**
     * 判断Element类型是ref还是value, 并将其封装成DataElement对象
     * @param dataElement
     * @return
     */
    private DataElement getDataElement(Element dataElement) {
        String name = dataElement.getName();
        if("ref".equals(name)) {
            String bean = getAttribute(dataElement, "bean");
            return new RefElement(bean);
        }else if("value".equals(name)){
            String type = getAttribute(dataElement, "type");
            String data = dataElement.getText();
            return new ValueElement(getValue(type,data));
        }
        return null;
    }
    private Object getValue(String className,String data) {
        if (isType(className, "Integer")) {
            return Integer.parseInt(data);
        } else if (isType(className, "Boolean")) {
            return Boolean.valueOf(data);
        } else if (isType(className, "Long")) {
            return Long.valueOf(data);
        } else if (isType(className, "Short")) {
            return Short.valueOf(data);
        } else if (isType(className, "Double")) {
            return Double.valueOf(data);
        } else if (isType(className, "Float")) {
            return Float.valueOf(data);
        } else if (isType(className, "Character")) {
            return data.charAt(0);
        } else if (isType(className, "Byte")) {
            return Byte.valueOf(data);
        } else {
            return data;
        }
    }
    private boolean isType(String target,String type) {
        return target.contains(type);
    }

    @Override
    public List<PropertyElement> getPropertyValue(Element element) {
        List<Element> elements = getPropertyElements(element);
        List<PropertyElement> propertyElementList = new ArrayList<>();
        for (Element e : elements) {
            Element target =(Element) e.elements().get(0);
            String propertyName = getAttribute(e,"name");
            DataElement dataElement = getDataElement(target);
            propertyElementList.add(new PropertyElement(propertyName,dataElement));
        }
        return propertyElementList;
    }
}
