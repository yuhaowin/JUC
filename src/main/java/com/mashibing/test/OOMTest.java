package com.mashibing.test;

public class OOMTest {

    public static void main(String[] args) {
        int byteLength = 991225581;
        //char[] ca = new char[byteLength];
        //char[] ca1 = new char[byteLength];
        byte[] bytes = new byte[byteLength];
        System.out.println("bytes 的大小：" + bytes.length);
        String str = new String(bytes);
        System.out.println(str.length());
    }

}
