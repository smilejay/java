// find the first pulicated char in a string.

public class FirstDupChar {
	public static void main(String[] args) {
		String str = "abcdefgd=etli";
		int num = 0;
		num = FirstDupChar.FindFirstDupChar(str);
		if (num == -1) {
			System.out.println("There's no duplicated char in string:");
			System.out.println(str);
		}
		else {
			System.out.println("The duplicated char is "+str.charAt(num)+" the index is "+num+" in string:");
			System.out.println(str);
		}

	}
	private static int FindFirstDupChar(String str) {
		int i;
		for (i = 0; i < str.length(); i++) {
			if( str.indexOf(str.charAt(i), i+1) != -1) {
				return i;
			}
		}
		if (i == str.length()) {
			return -1;
		}
		return i;
	}

}
