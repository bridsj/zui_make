package com.zuiapps.suite.make.activity;

import android.content.Intent;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.zuiapps.suite.make.R;
import com.zuiapps.suite.make.constant.PushConstants;


public class SplashActivity extends AbsBaseActivity {

	@Override
	protected void initData() {
		subscribeMiPushForAll();
	}

	@Override
	protected void initWidgets() {
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void initWidgetsActions() {
	}

	@Override
	protected void initComplete() {
		getHandler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getContext(), MainActivity.class));
				finish();
			}
		}, 1500);
	}

	private void subscribeMiPushForAll() {
		MiPushClient.subscribe(this, PushConstants.TOPIC_ALL, null);
	}

	@Override
	public void onBackPressed() {
		// Block back to finish
	}
}
