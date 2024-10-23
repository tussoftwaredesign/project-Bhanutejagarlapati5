package com.constructors;

public class Tree {

	private String type;

	public Tree() {
		type = "beech";
	}

	public Tree(String type) {
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public String getType(String prefix) {
		return prefix+ " "+ type;
	}
}
