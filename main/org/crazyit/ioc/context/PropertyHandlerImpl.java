package org.crazyit.ioc.context;

import org.crazyit.ioc.context.exception.ExecuteMethodException;
import org.crazyit.ioc.context.exception.PropertyException;
import org.crazyit.ioc.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyHandlerImpl implements PropertyHandler{
    //返回一个对象里面所有的setter方法, 封装成map, key为setter方法名不要set
    @Override
    public Map<String, Method> getSettingMethodsMap(Object obj) {
        Map<String,Method> map = new HashMap<>();
        List<Method> list = getSettingMethodList(obj);
        list.forEach(e->map.put(getMethodNameWithOutSet(e.getName()),e));
        return map;
    }
    private String getMethodNameWithOutSet(String methodName) {
        String valueUpName = methodName.replaceFirst("set", "");
        char[] chars = valueUpName.toCharArray();
        chars[0] = (char)(chars[0] + 32);
        return new String(chars);
    }
    private List<Method> getSettingMethodList(Object obj) {
        Class<?> aClass = obj.getClass();
        List<Method> list = new ArrayList<>();
        for (Method method : aClass.getMethods()) {
            if (method.getName().startsWith("set")){
                list.add(method);
            }
        }
        return list;
    }
    @Override
    public void executeMethod(Object obj, Object arg, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        try{
            if(parameterTypes.length==1) {
                if (isMethodArgs(method,arg.getClass()) || isMethodArgs(method,ClassUtil.getClass(arg))){
                    method.invoke(obj,arg);
                }else {
                    throw new ExecuteMethodException("not this method");
                }
            }else {
                throw new ExecuteMethodException("not this method");
            }
        }catch (Exception e) {
            throw new ExecuteMethodException(e.getMessage());
        }
    }

    @Override
    public Object setProperties(Object obj, Map<String, Object> map) {
        Class aClass = ClassUtil.getClass(obj);
        try{
            for (String s : map.keySet()) {
                String methodName = getSetMethodName(s);
                Object parm = map.get(s);
                Method method = null;
                if (!ClassUtil.getClass(parm).getName().equals(parm.getClass())) {
                    for (Method m : aClass.getMethods()) {
                        if (m.getName().equals(methodName)) {
                            method = m;
                        }
                    }
                }else{
                    method = getSetMethod(aClass,methodName,ClassUtil.getClass(parm));
                }
                method.invoke(obj, parm);
            }
            return obj;
        }catch(NoSuchMethodException e) {
            throw new PropertyException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new PropertyException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new PropertyException(e.getMessage());
        }
    }
    private String getSetMethodName(String value) {
        char[] chars = value.toCharArray();
        chars[0] =(char)(chars[0] - 32);
        return "set" + new String(chars);
    }
    private Method getSetMethod(Class objClazz,String methodName,Class argClass) throws NoSuchMethodException {
        Method method = getMethod(objClazz, methodName, argClass);
        if (method == null) {
            List<Method> methods = getMethods(objClazz, methodName);
            method = findMethod(objClazz, methods);
            if (method == null) {
                throw new NoSuchMethodException();
            }
        }
        return method;
    }
    private boolean isMethodArgs(Method method,Class argClass) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length==1) {
            try{
                argClass.asSubclass(parameterTypes[0]);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    private Method findMethod(Class objClazz, List<Method> methods) {
        for (Method method : methods) {
            if (isMethodArgs(method,objClazz)) {
                return method;
            }
        }
        return null;
    }

    private List<Method> getMethods(Class objClazz,String methodName) {
        List<Method> result = new ArrayList<>();
        for (Method method : objClazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                if (method.getParameterTypes().length == 1) {
                    result.add(method);
                }
            }
        }
        return result;
    }

    private Method getMethod(Class objClazz,String methodName,Class argClass) {
        try{
            return objClazz.getMethod(methodName,argClass);
        }catch (NoSuchMethodException e) {
            return null;
        }
    }
}
