package com.zuiapps.suite.make.restful;

import android.content.Context;

import com.zuiapps.suite.make.constant.ApiConstants;
import com.zuiapps.suite.utils.app.AppUtil;
import com.zuiapps.suite.utils.device.PhoneUtil;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ReqRestAdapter {

    public static RestAdapter getInstance(final Context context) {
        return getInstance(context, ApiConstants.API_BASE_URL);
    }

    public static RestAdapter getInstance(final Context context, String baseUrl) {
        RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(new RequestInterceptor() {

            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("from_client", "ZUIMakeAndroid");

                if (context != null) {
                    requestFacade.addQueryParam("openUDID", PhoneUtil.getDeviceUUId(context) + "");
                    requestFacade.addQueryParam("appVersion", AppUtil.getVersionName(context) + "");
                    requestFacade.addQueryParam("appVersionCode", AppUtil.getVersionCode(context) + "");
                    requestFacade.addQueryParam("systemVersion", android.os.Build.VERSION.SDK_INT + "");
                    requestFacade.addQueryParam("platform", "android");
                    requestFacade.addQueryParam("packageName", AppUtil.getPackageName(context));
                    requestFacade.addQueryParam("timestamp", System.currentTimeMillis() + "");//预留该字段，方便之后的数据加密
                    requestFacade.addQueryParam("channel", ApiConstants.REQUEST_CHANNEL);
                    requestFacade.addQueryParam("resolution", PhoneUtil.getResolution(context) + "");
                    requestFacade.addQueryParam("lang", PhoneUtil.getPhoneLanguage());
                }
            }

        }).setConverter(new JsonObjectConverter()).setEndpoint(baseUrl).build();
        return restAdapter;
    }


}
