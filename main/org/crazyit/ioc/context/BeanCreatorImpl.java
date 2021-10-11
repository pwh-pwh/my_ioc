package org.crazyit.ioc.context;

import org.crazyit.ioc.context.exception.BeanCreateException;
import org.crazyit.ioc.util.ClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanCreatorImpl implements BeanCreator{
    @Override
    public Object createBeanUseDefaultConstruct(String className) {
        try{
            Class<?> aClass = Class.forName(className);
//            Object o = aClass.newInstance();
            Constructor<?> constructor = aClass.getConstructor();
            return constructor.newInstance();
        }catch (ClassNotFoundException e) {
            throw new BeanCreateException("class not found:"+e.getMessage());
        }catch (Exception e) {
            throw new BeanCreateException(e.getMessage());
        }

    }

    @Override
    public Object createBeanUseDefineConstruct(String className, List<Object> args) {
        try{
            Class[] classes = getArgsClasses(args);
            Class<?> aClass = Class.forName(className);
            Constructor constructor = this.findConstructor(aClass, classes);
            Object o = constructor.newInstance(args.toArray());
            return o;
        }catch (ClassNotFoundException e) {
            throw new BeanCreateException("class not found:"+e.getMessage());
        }catch (NoSuchMethodException e){
            throw new BeanCreateException(e.getMessage());
        } catch (Exception e) {
            throw new BeanCreateException(e.getMessage());
        }
    }
    private Constructor findConstructor(Class clazz,Class[] argsClass) throws NoSuchMethodException {
        Constructor constructor = getConstructor(clazz, argsClass);
        if (constructor == null) {
            int length = argsClass.length;
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor c : constructors) {
                if(c.getParameterCount()==length) {
                    Class[] parameterTypes = c.getParameterTypes();
                    if (isSameArgs(argsClass,parameterTypes)){
                        return c;
                    }

                }
            }
        }else {
            return constructor;
        }
        throw new NoSuchMethodException("could not find any constructor");
    }
    private boolean isSameArgs(Class[] argsClass,Class[] constructorArgsClass) {
        for (int i = 0; i < argsClass.length; i++) {
            try{
                argsClass[i].asSubclass(constructorArgsClass[i]);
            }catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    private Constructor getConstructor(Class clazz,Class[] argsClass) {
        try {
            Constructor constructor = clazz.getConstructor(argsClass);
            return constructor;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
    private Class[] getArgsClasses(List<Object> args) {
        List<Class> classList = new ArrayList<>();
        for (Object arg : args) {
            Class aClass = ClassUtil.getClass(arg);
            classList.add(aClass);
        }
        Class[] cLasses = new Class[classList.size()];
        classList.toArray(cLasses);
        return cLasses;
    }
}
