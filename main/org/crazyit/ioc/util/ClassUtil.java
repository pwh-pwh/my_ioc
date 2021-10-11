package org.crazyit.ioc.util;

public class ClassUtil {
    public static Class getClass(Object obj) {
        if (obj instanceof Boolean) {
            return Boolean.TYPE;
        }else if(obj instanceof Integer) {
            return Integer.TYPE;
        }else if(obj instanceof Long) {
            return Long.TYPE;
        }else if(obj instanceof Short) {
            return Short.TYPE;
        }else if(obj instanceof Double) {
            return Double.TYPE;
        }else if(obj instanceof Float) {
            return Float.TYPE;
        }else if(obj instanceof Character){
            return Character.TYPE;
        }else if(obj instanceof Byte) {
            return Byte.TYPE;
        }else {
            return obj.getClass();
        }
    }
}
