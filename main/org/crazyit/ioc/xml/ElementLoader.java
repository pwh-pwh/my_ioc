package org.crazyit.ioc.xml;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Collection;

public interface ElementLoader {
    void addElements(Document document);
    Element getElement(String id);
    Collection<Element> getElements();
}
