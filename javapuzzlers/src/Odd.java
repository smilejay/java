package javapuzzlers.src;

/*
 * summary: check an integer is an odd number or not.	
 */

public class Odd {
	
	//  this can work.
	public static boolean isOddA(int i) {
		return (i % 2) != 0;
	}
	
	//	this one is more effective.
	public static boolean isOddB(int i) {
		return (i & 1) != 0;
	}
	
	//	this method is not right.
	public static boolean isOddW(int i) {
		return (i %2) == 1;
	}
}
