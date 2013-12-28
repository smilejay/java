/**
 * 
 */
package com.smilejay.puzzlers;

/**
 * @author: Jay
 * @date: Nov 6, 2013
 * @description: 长整数的计算
 */
public class LongOperation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		longDivisionR();
		longDivisionW();
		joyOfHexR();
		joyOfHexW();
	}
	
	public static void longDivisionR() {
		// 用long类型的数值来做第一个乘积因子，就避免了溢出
		// 另外，最好不要难过用小写的l来表示long而要是用大写的L，因为l容易被人眼看成是数字1.
		final long MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;
		final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
		System.out.println(MICROS_PER_DAY/MILLIS_PER_DAY);		
	}
	
	public static void longDivisionW() {
		// 会以int来计算，然后转化为long，所以会溢出
		final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
		final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
		System.out.println(MICROS_PER_DAY/MILLIS_PER_DAY);
	}
	
	// 全部都用Long类型，十六进制数字这么表示也就能像我们期望的那样了。
	public static void joyOfHexR() {
		System.out.println(Long.toHexString(0x100000000L + 0xcafebabeL));
	}
	
	// 如果十六进制和八进制字面常量的最高位被置位了，那么它们就是负数。 0xcafebabe 表示的其实是一个int型的负数。
	// 一般要避免混合类型的计算
	public static void joyOfHexW() {
		System.out.println(Long.toHexString(0x100000000L + 0xcafebabe));
	}

}
