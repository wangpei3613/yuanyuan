package com.sensebling.common.util;

@SuppressWarnings("serial")
public class R extends Result{
	public static R ok() {
		R r = new R();
		r.res = "0"; 
		r.success = true;
		return r;
	}
	public static R ok(Object data) {
		R r = new R();
		r.res = "0"; 
		r.success = true;
		r.data = data;
		return r;
	}
	public static R err(String msg) {
		R r = new R();
		r.error = msg;
		r.message = msg;
		return r;
	}
}
