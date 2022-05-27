package com.yuhaowin.bridgemethod;

import java.lang.reflect.Method;

/**
 * https://developer.51cto.com/article/703055.html
 */
public class BridgeMethodTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(StringOperator.class.getName());
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method.toString());
            /**
             * public void com.yuhaowin.bridgemethod.StringOperator.process(java.lang.String)
             * public void com.yuhaowin.bridgemethod.StringOperator.process(java.lang.Object)
             */
        }
    }
}
