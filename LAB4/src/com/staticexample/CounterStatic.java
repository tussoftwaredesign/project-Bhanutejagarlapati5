package com.staticexample;

public class CounterStatic {
	private static int count;

	public CounterStatic() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public static int getCounter() {
		return count;
	}
}
