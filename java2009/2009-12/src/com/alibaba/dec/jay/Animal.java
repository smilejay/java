package com.alibaba.dec.jay;


interface Animal {
	void eat();
	void sleep();
}

class Zoo {
	class Tiger implements Animal
	{
		public void eat()
		{
			System.out.println("Tiger eat...");
		}
		public void sleep()
		{
			System.out.println("Tiger sleep...");
		}
	}
	Animal getAnimal()
	{
		return new Tiger();
	}
}

class TestAnimal
{
	public static void main(String[] args) {
		Zoo zoo = new Zoo();
		Animal an = zoo.getAnimal();
		an.eat();
		an.sleep();
	}
}
