package org.crazyit.ioc.context;

import junit.framework.TestCase;
import org.crazyit.ioc.context.object.*;
import org.crazyit.ioc.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertyHandlerTest extends TestCase {
    PropertyHandler handler;
    public void testPropertyHandler() {
        School school = new School();
        Map<String,Object> schoolPropertyMap = new HashMap<>();
        schoolPropertyMap.put("name","scnu");
        handler.setProperties(school,schoolPropertyMap);
        System.out.println(school);
        PropertyHandlerObj handlerObj = new PropertyHandlerObj();
        Map<String,Object> objPropertyMap = new HashMap<>();
        objPropertyMap.put("name","pwh");
        objPropertyMap.put("age",9876);
        objPropertyMap.put("school",school);
        handler.setProperties(handlerObj,objPropertyMap);
        System.out.println(handlerObj);
    }
    public void testBasicType() {
        int a = 10;
        Object o = (Object) a;
        System.out.println(ClassUtil.getClass(o));
    }
    public void testInteger() {
        IntegerTestObj obj = new IntegerTestObj();
        Map<String,Object> map = new HashMap<>();

        map.put("age",20);
        Object age = map.get("age");
        System.out.println(age.getClass());
        System.out.println(((Object) 20).getClass());
        handler.setProperties(obj,map);
        System.out.println(obj.getAge());

    }
    public void testNewMethod() throws NoSuchMethodException {
        PropertyHandlerObj obj = new PropertyHandlerObj();
        Set<Map.Entry<String, Method>> entries = handler.getSettingMethodsMap(obj).entrySet();
        entries.forEach(e-> System.out.println(e.getKey()+" : " +e.getValue()));
        Method setName = obj.getClass().getMethod("setName", String.class);
        handler.executeMethod(obj,"pwh",setName);
        System.out.println(obj.getName());
    }
    @Override
    protected void tearDown() throws Exception {
        handler = null;
    }

    @Override
    protected void setUp() throws Exception {
        handler = new PropertyHandlerImpl();
    }
}
