package com.yuhaowin.genericity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * https://www.jianshu.com/p/d7494d7796ed
 * https://www.jianshu.com/p/2bf15c5265c5
 */
public class GenericTest {
    public static void main(String[] args) {

        /**
         * 编译报错的,类型是不匹配的，java 泛型是不变的
         * Imagine you create a list of Dogs.
         * You then declare this as List<Animal> and hand it to a colleague.
         * He, not unreasonably, believes he can put a Cat in it.
         *
         * He then gives it back to you, and you now have a list of Dogs,
         * with a Cat in the middle of it. Chaos ensues.
         */
        //List<Animal> animals = new ArrayList<Dog>();

        /**
         *泛型的斜变
         *不能在添加数据了，其表示 list 所持有的类型为 Animal 或 Animal 的子类，但是并不知道这个类具体是什么类型，
         * 只好阻止向其中加人任何子类。为了类型安全，不能往使用了 <? extends E> 的数据结构中写入任何值(null除外)。
         */
        List<? extends Animal> animals = new ArrayList<Dog>();
        //animals.add()


        /**
         * 泛型的逆变
         *对于<? super T>来说，参数类型 ? 表示的是 T 或者 T 的超类。如果是 T 的子类那么也是 T 超类的子类，
         * 所以将元素添加到容器是允许的，因为取出来一定符合 T 或者 T 的超类型。
         * 但是如果是 T 的超类型是不允许向容器添加元素的，因为无法确定 T 的超类具体是什么类型，取出元素的时候可能会引起类型转换错误。
         */
        List<? super Dog> dogs = new ArrayList<Animal>();
        dogs.add(new Dog());
    }


    /**
     * Collections.java
     * PECS 原则 全称为 Producer-Extends-Consumer-Super。
     * <? extends T>子类限定通配符：从泛型类读取类型 T 的数据，并且不能写入，用于生产者环境（Producer）；
     * <? super T>父类限定通配符： 从集合中写入类型 T 的数据，并且不需要读取，用于消费者场景（Consumer）；
     * 如果既要存数据又要取数据，那么通配符无法满足需求。
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if ((src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i = 0; i < srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator<? super T> di = dest.listIterator();
            ListIterator<? extends T> si = src.listIterator();
            for (int i = 0; i < srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }
}
