package com.example.songxing.shop;

public class Head {
	public static String isOk() {
		long time = System.currentTimeMillis();
		time = time / 60000;
		String s = "tjj" + time;
		return Md5Util.md5(s, false);
	}
//	public static String isOk(String str) {
//		
//		return Md5Util.md5(str, false);
//	}
}
