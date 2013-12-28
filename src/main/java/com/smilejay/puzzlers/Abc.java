package com.smilejay.puzzlers;

/**
 * @author Jay
 * @date Dec 28, 2013
 * 将一个char数组转换成字符串，应该调用String.valueOf(char [])方法。
 * 
 * output:
ABC easy as [C@2b1be57f
ABC easy as [C@2b1be57f
ABC easy as 123
ABC easy as 123
 */

public class Abc {
	public static void main(String[] args) {
		String letters = "ABC";
		char[] numbers = {'1', '2', '3'};
		
		System.out.println(letters + " easy as " + numbers);
		
		System.out.println(letters + " easy as " + numbers.toString());
		
		System.out.println(letters + " easy as " + String.valueOf(numbers));
		
		System.out.print(letters + " easy as ");
		System.out.println(numbers);
		
	}

}
