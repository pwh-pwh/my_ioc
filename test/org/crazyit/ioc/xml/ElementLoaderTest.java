package org.crazyit.ioc.xml;

import junit.framework.TestCase;
import org.dom4j.Document;

public class ElementLoaderTest extends TestCase {
    XmlDocumentHolder holder;
    ElementLoader elementLoader;
    @Override
    protected void tearDown() throws Exception {

    }
    public void testELementLoader() {
        String path = "test/resources/ElementLoader.xml";
        Document document = holder.getDocument(path);
        elementLoader.addElements(document);
//        elementLoader.getElements().forEach(System.out::println);
        System.out.println(elementLoader.getElement("test2"));
    }

    @Override
    protected void setUp() throws Exception {
        holder = new XmlDocumentHolder();
        elementLoader = new ElementLoaderImpl();
    }
}
