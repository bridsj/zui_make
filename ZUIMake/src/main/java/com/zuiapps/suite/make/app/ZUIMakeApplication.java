package com.zuiapps.suite.make.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.zuiapps.suite.make.service.FontService;

import java.util.List;

/**
 * @author yu.jingye
 * @version created at 15/1/13.
 */
public class ZUIMakeApplication extends Application {

	public static final String TAG = ZUIMakeApplication.class.getSimpleName();

	// Mi push app id
	public static final String MI_APP_ID = "2882303761517295440";
	// Mi push app key.
	public static final String MI_APP_KEY = "5681729527440";

	private static ZUIMakeApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;

		FontService.init(getApplicationContext());

		// 注册push服务，注册成功后会向MakePushMessageReceiver发送广播
		if (shouldInit()) {
			MiPushClient.registerPush(this, MI_APP_ID, MI_APP_KEY);
		}
	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}

		return false;
	}

	public static Context getAppContext() {
		return application.getApplicationContext();
	}

}
