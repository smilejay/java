package com.alibaba.dec.jay;

//import java.util.logging.Logger;

public class Outer {
	private int index = 100;
	class Inner {
		private int index = 50;
		void print()
		{
			int index = 30;
			System.out.println("Innner index = "+index);
//			 Logger.fine("Innner index = "+index);
			System.out.println("Innner index = "+this.index);
			System.out.println("Outer index = "+Outer.this.index);
			
		}
	}
	void print()
	{
		Inner inner = new Inner();
		inner.print();
	}
	Inner getInner()
	{
		return new Inner();
	}

}

class Test {
	public static void main(String[] args) {
		Outer outer = new Outer();
//		outer.print();
		Outer.Inner inner = outer.getInner();
		inner.print();
	}
}
