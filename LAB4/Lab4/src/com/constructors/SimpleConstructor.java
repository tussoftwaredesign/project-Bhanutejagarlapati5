package com.constructors;

public class SimpleConstructor {

	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Creating instance of "+ i +" Rock");
			Rock r =new Rock();
		}
		Rock r=new Rock();
		r.Rock();
	}
}
