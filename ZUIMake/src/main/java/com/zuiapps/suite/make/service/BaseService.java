package com.zuiapps.suite.make.service;

import android.content.Context;

/**
 * @author yu.jingye
 * @version created at 15/1/13.
 */
public class BaseService {

	protected static Context mContext;

	public static void init(Context applicationContext) {
		mContext = applicationContext;
	}

}
