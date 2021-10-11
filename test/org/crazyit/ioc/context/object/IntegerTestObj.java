package org.crazyit.ioc.context.object;

import org.crazyit.ioc.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IntegerTestObj {
    private Integer age;
    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        IntegerTestObj obj = new IntegerTestObj();
        Class<? extends IntegerTestObj> aClass = obj.getClass();
        Method setAge = aClass.getMethod("setAge", Integer.class);
        System.out.println(setAge);
        setAge.invoke(obj,20);
        System.out.println(obj.getAge());
        System.out.println(Integer.class);


/*        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            if(method.getName().equals("setAge")) {
                System.out.println(method.getParameterTypes()[0]);
            }
        }*/

    }
}
