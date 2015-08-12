package com.todoapp.utils;

public class Util {

	public static <T> T getOrElse(T obj, T def) {
		return obj != null ? obj : def; 
	}
	
}
