package com.smilejay.puzzlers;

/**
 * @author Jay
 * @date Dec 28, 2013
 * 
 * 较窄的整型转换成较宽的整型时的符号扩展规则：
 *    如果最初的数值类型是有符号的，就执行符号扩展；
 *    如果它是char，那么不管它要被转成什么类型，都执行零扩展。
 *    
 * output:
65535
 */

public class Multicast {
	public static void main(String[] args) {
		System.out.println((int)(char)(byte) -1);
	}

}
