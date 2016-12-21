package com.jonas.yun_library.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mcxiaoke
 * Date: 15/7/30
 * Time: 18:09
 */
public class SugNotify {

    private static class SingletonHolder {
        static final SugNotify INSTANCE = new SugNotify();
    }

    public static SugNotify getDefault() {
        return SingletonHolder.INSTANCE;
    }

    private Map<Object, List<Method>> mMethodMap = new HashMap<Object, List<Method>>();

    public static List<Method> findAnnotatedMethods(final Class<?> type) {
        final List<Method> methods = new ArrayList<Method>();
        Method[] ms = type.getDeclaredMethods();
        for (Method method : ms) {
            // must not static
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            // must be public
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            // must has only one parameter
            if (method.getParameterTypes().length != 1) {
                continue;
            }
            // must has annotation
            if (!method.getName().equals("onNotify")) {
                continue;
            }
            methods.add(method);
        }
        return methods;
    }

    public void register(final Object target) {
        List<Method> methods = findAnnotatedMethods(target.getClass());
        if (methods == null || methods.isEmpty()) {
            return;
        }
        mMethodMap.put(target, methods);
    }

    public void unregister(final Object target) {
        mMethodMap.remove(target);
    }

    public void post(Object event) {
        final Class<?> eventClass = event.getClass();
        for (Map.Entry<Object, List<Method>> entry : mMethodMap.entrySet()) {
            final Object target = entry.getKey();
            final List<Method> methods = entry.getValue();
            if (methods == null || methods.isEmpty()) {
                continue;
            }
            for (Method method : methods) {
                if (eventClass.equals(method.getParameterTypes()[0])) {
                    try {
                        method.invoke(target, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
