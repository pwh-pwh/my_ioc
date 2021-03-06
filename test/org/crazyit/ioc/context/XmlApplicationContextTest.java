package org.crazyit.ioc.context;

import junit.framework.TestCase;
import org.crazyit.ioc.context.object.XmlApplicationContextObject1;
import org.crazyit.ioc.context.object.XmlApplicationContextObject2;
import org.crazyit.ioc.context.object.XmlApplicationContextObject3;

public class XmlApplicationContextTest extends TestCase {
    ApplicationContext ctx;

    @Override
    protected void tearDown() throws Exception {
        ctx = null;
    }

    @Override
    protected void setUp() throws Exception {
        ctx = new XmlApplicationContext(new String[]{"resources/context/XmlApplicationContext1.xml"});
    }

    public void testGetBean() {
        Object test1 = ctx.getBean("test1");
        System.out.println(test1);
    }
    public void testSingleton() {
        //test1是单态bean
        XmlApplicationContextObject1 obj1 = (XmlApplicationContextObject1)ctx.getBean("test1");
        XmlApplicationContextObject1 obj2 = (XmlApplicationContextObject1)ctx.getBean("test1");
        assertEquals(obj1, obj2);
        //test3不是单态bean
        XmlApplicationContextObject1 obj3 = (XmlApplicationContextObject1)ctx.getBean("test3");
        XmlApplicationContextObject1 obj4 = (XmlApplicationContextObject1)ctx.getBean("test3");
        assertFalse(obj3.equals(obj4));
    }
    public void testConstructInjection() {
        XmlApplicationContextObject1 obj1 = (XmlApplicationContextObject1)ctx.getBean("test1");
        //拿到第二个, 使用多参数构造器创建
        XmlApplicationContextObject2 obj2 = (XmlApplicationContextObject2)ctx.getBean("test2");
        assertNotNull(obj2);
        assertEquals(obj2.getName(), "yangenxiong");
        assertEquals(obj2.getAge(), 10);
        assertEquals(obj2.getObject1(), obj1);
    }
    public void testAutowire() {

        XmlApplicationContextObject3 obj1 = (XmlApplicationContextObject3)ctx.getBean("test4");
        assertNotNull(obj1);
        XmlApplicationContextObject1 obj2 = obj1.getObject1();
        assertNotNull(obj2);
        XmlApplicationContextObject1 obj3 = (XmlApplicationContextObject1)ctx.getBean("object1");
        assertEquals(obj2, obj3);
    }
    public void testContainsBean() {
        boolean result = ctx.isContainsBean("test1");
        assertTrue(result);
        result = ctx.isContainsBean("test5");
        assertTrue(result);
        result = ctx.isContainsBean("No exists");
        assertFalse(result);
    }
    public void testLazyInit() {
        //test5是延迟加载的, 没有调用过getBean方法, 那么容器中就不会创建这个bean
        Object obj = ctx.getBeanIgnoreCreate("test5");
        assertNull(obj);
        System.out.println(obj);
        obj = ctx.getBean("test5");
        assertNotNull(obj);
        System.out.println(obj);
    }
    public void testSetProperties() {
        XmlApplicationContextObject3 obj1 = (XmlApplicationContextObject3)ctx.getBean("test6");
        XmlApplicationContextObject1 obj2 = (XmlApplicationContextObject1)ctx.getBean("object1");
        assertEquals(obj1.getName(), "yangenxiong");
        assertEquals(obj1.getAge(), 10);
        assertEquals(obj1.getObject1(), obj2);
    }
}
