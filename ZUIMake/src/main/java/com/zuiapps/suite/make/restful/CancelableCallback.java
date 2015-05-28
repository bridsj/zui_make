package com.zuiapps.suite.make.restful;

import retrofit.Callback;

public abstract class CancelableCallback<T> implements Callback<T> {
	
	boolean mIsCanceled = false;
	
	public void cancel() {
		mIsCanceled = true;
	}
	
	public boolean isCanceled() {
		return mIsCanceled;
	}
}
