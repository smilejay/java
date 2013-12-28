package com.smilejay.puzzlers;

/**
 * @author Jay
 * @date Dec 28, 2013
 * 
 * ==操作符并不测试两个对象是否相等，而是测试两个对象引用是否相同。
 * 比较对象引用时，应该优先使用equals方法而不是==操作符。
 * 
 * System.identityHashCode(obj)方法会打印出一个object的引用标识
 * 
 * 
 * 本程序的打印输出信息为: 
false
Animals are equal: false
Animals are equal: true
identityHashCode of pig is :1386089593
identityHashCode of dog is :105186895
identityHashCode of pig1 is :1386089593
Animals are equal: true
 */

public class AnimalFarm {
	public static void main(String[] args) {
		final String pig = "length: 10";
		final String dog = "length: " + pig.length();
		System.out.println("Animals are equal: " + pig == dog);
		System.out.println("Animals are equal: " + (pig == dog));
		System.out.println("Animals are equal: " + pig.equals(dog));
		
		System.out.println("identityHashCode of pig is :" + System.identityHashCode(pig));
		System.out.println("identityHashCode of dog is :" + System.identityHashCode(dog));
		
		final String pig1 = "length: 10";
		System.out.println("identityHashCode of pig1 is :" + System.identityHashCode(pig1));
		System.out.println("Animals are equal: " + (pig == pig1));
	}

}
