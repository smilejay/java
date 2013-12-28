package com.smilejay.puzzlers;

/**
 * @author Jay
 * @date Dec 28, 2013
 * 当且仅当+操作符的操作数中至少有一个是String类型时，才会执行字符串连接操作。
 * 
 * output:
Ha
169

Ha
Ha
Ha

2+2=22
 */

public class LastLaugh {
	public static void main(String[] args) {
		System.out.println("H" + "a");
		System.out.println('H' + 'a');  // 72+97=169
		System.out.println();
		
		StringBuffer sb = new StringBuffer();
		sb.append('H');
		sb.append('a');
		System.out.println(sb);
		System.out.println("" + 'H' + 'a');
		System.out.printf("%c%c\n", 'H', 'a');
		
		System.out.println();
		System.out.println("2+2=" + 2+2);
		
	}

}
