package com.yuhaowin.test;

/**
 * @author yuhao
 * @version 5.11.0
 * @date 2020年11月27日 18:09:00
 */
public class User {
    private String name;
    private String fullName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
