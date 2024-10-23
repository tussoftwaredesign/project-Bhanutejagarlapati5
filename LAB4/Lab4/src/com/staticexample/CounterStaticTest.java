package com.staticexample;

public class CounterStaticTest {

	public static void main(String[] args) {
		CounterStatic c1=new CounterStatic(); 
		CounterStatic c2=new CounterStatic(); 
		CounterStatic c3=new CounterStatic(); 
		System.out.println(c1.getCount());
		System.out.println(c2.getCount());
		System.out.println(c3.getCount());
	}

}
