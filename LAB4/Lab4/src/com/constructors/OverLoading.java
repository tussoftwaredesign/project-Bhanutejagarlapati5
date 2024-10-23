package com.constructors;

public class OverLoading {
	public static void main(String[] args) {
		Tree t=new Tree();
		System.out.println(t.getType());
		Tree t1=new Tree("oak");
		System.out.println(t1.getType());
		System.out.println(t.getType("The tree type is"));
	}
}
