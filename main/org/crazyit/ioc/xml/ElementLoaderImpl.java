package org.crazyit.ioc.xml;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementLoaderImpl implements ElementLoader{
    private Map<String,Element> elements = new HashMap<>();
    @Override
    public void addElements(Document document) {
        Element rootElement = document.getRootElement();
        List<Element> es = rootElement.elements();
        for (Element element : es) {
            String id = element.attributeValue("id");
            elements.put(id,element);
        }
    }

    @Override
    public Element getElement(String id) {
        return elements.get(id);
    }

    @Override
    public Collection<Element> getElements() {
        return this.elements.values();
    }
}
