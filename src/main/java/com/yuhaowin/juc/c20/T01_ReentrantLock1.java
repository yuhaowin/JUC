package com.yuhaowin.juc.c20;

import java.util.concurrent.TimeUnit;

/**
 * reentrantlock 用于替代 synchronized
 * 本例中由于 m1 锁定 this,只有 m1 执行完毕的时候,m2 才能执行
 * 这里是复习 synchronized 最原始的语义
 */
public class T01_ReentrantLock1 {
	// m1 和 m2 锁的是同一个对象 this
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			if(i == 2) m3("123");
		}
	}
	
	synchronized void m2() {
		System.out.println("m2 ...");
	}


	Object o = new Object();
	void m3(String name){
		synchronized (this){
			System.out.println("m3 ...start " + name) ;
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("m3 ...end " + name);
		}
	}
	
	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(() -> rl.m3("456")).start();
	}
}
