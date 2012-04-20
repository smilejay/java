

public class BiggerThanFive {
	public static void main(String[] args) {
		
		String str = "6";
		boolean result = BiggerThanFive.isBiggerThanFive(str);
		if ( result == true) {
			System.out.println("True");
		}
		else {
			System.out.println("False");
		}
		
	}
	public static boolean isBiggerThanFive(String str) {
		int num = 0;
		try {
			num = Integer.parseInt(str);      //字符串转化为int整数
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( num > 5) {
			return true;
		}
		else {
			return false;
		}
	}
}
