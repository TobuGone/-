package com.kapphk.pms.test;

public class Test {
	public String name;
	public int count;
	
	public Test(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}

	public static void main(String[] args) {
		Test t = new Test("胡子",20);
		System.out.println(t.name);
		System.out.println(t.count);
		
	}

}
