package com.alibaba.dec.jay;

public class InnerClassStudy {
	class Wheel
	{
		
	}
}

class PlaneWheel extends InnerClassStudy.Wheel
{
	PlaneWheel(InnerClassStudy car)
	{
		car.super();
	}
	public static void main(String[] args) {
		InnerClassStudy car = new InnerClassStudy();
		PlaneWheel pw = new PlaneWheel(car);
	}
}
