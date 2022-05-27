package com.yuhaowin.bridgemethod;

public class StringOperator implements Operator<String> {
    @Override
    public void process(String param) {
        System.out.println(param);
    }
}
