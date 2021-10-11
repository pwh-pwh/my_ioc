package org.crazyit.ioc.context;

import junit.framework.TestCase;
import org.crazyit.ioc.context.object.BeanCreatorObject1;
import org.crazyit.ioc.context.object.BeanCreatorObject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanCreatorTest extends TestCase {
    BeanCreator beanCreator;
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    public void testObj1() {
        BeanCreatorObject1 beanCreatorObject1 = new BeanCreatorObject1();
        Object beanUseDefaultConstruct = beanCreator.createBeanUseDefaultConstruct("org.crazyit.ioc.context.object.BeanCreatorObject1");
        System.out.println(beanUseDefaultConstruct);
    }
    public void testObj2() {
        List<Object> list = new ArrayList<>();
        list.add(2);
        list.add("value");
        BeanCreatorObject2 b =(BeanCreatorObject2) beanCreator.createBeanUseDefineConstruct("org.crazyit.ioc.context.object.BeanCreatorObject2", list);
        System.out.println(b.getName());
        System.out.println(b.getValue());

    }
    @Override
    protected void setUp() throws Exception {
        beanCreator = new BeanCreatorImpl();
    }
}
