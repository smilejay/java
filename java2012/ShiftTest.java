/**
 * @className: ShiftTest.java
 * @author: 笑遍世界
 * @description: Java中的算术移位与逻辑移位
 * @date 2012-04-19     
*/

public class ShiftTest {
	public static void main(String[] args) {
		int a = 0x80000000;
		int a1 = a>>2;	 //算术移位
		int a2 = a>>>2;  //逻辑移位
		System.out.println("a is " + a + "; "+Integer.toBinaryString(a));
		System.out.println("a>>2 is " + a1 + "; "+Integer.toBinaryString(a1));
		System.out.println("a>>>2 is " + a2 + "; "+Integer.toBinaryString(a2));
		System.out.println("integer size is " +Integer.SIZE);
	}
}