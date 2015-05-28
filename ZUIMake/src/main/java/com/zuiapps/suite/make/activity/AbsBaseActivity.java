package com.zuiapps.suite.make.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;

public abstract class AbsBaseActivity extends FragmentActivity {

	private Context mContext;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mContext = this;

		initData();
		initWidgets();
		initWidgetsActions();
		initComplete();
	}

	protected void initComplete() {
	}

	protected Context getContext() {
		return mContext;
	}

	protected Handler getHandler() {
		if (mHandler == null) {
			synchronized (this) {
				if (mHandler == null) {
					mHandler = new Handler(Looper.getMainLooper());
				}
			}
		}

		return mHandler;
	}

	@Override
	protected void onResume() {
		super.onResume();

		MobclickAgent.onPageStart(getClass().getSimpleName());
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}

	protected abstract void initData();

	protected abstract void initWidgets();

	protected abstract void initWidgetsActions();

}
