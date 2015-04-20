package com.app.swm.ui;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.util.LoginUtil;
import com.app.swm.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import com.app.swm.R;
/**
 * @author miss
 * 
 */
public class WelcomeActivity extends Activity {
    
	public Context mContext;
	//程序是否使用过
	private boolean isUse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		mContext = WelcomeActivity.this;
		isUse = SharedPreferencesUtil.readIsFirstUse(mContext);
		if (!isUse) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					/**
                     * 
                     */
					Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
					startActivity(intent);
					finish();
				}
			}, Config.TIME_DELAY_GUIDE);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
                    /**
                     * 
                     */
					//openActivity(YixinMainActivity.class);
					//自动登录
					/*String login = SharedPreferencesUtil.get(WelcomeActivity.this, "auto_login");
					Log.i("WelcomeActivity", "login"+login);
					if("0".equals(login) || "".equals(login)){
						LoginUtil.post(WelcomeActivity.this);
						
					}
					String update = SharedPreferencesUtil.get(WelcomeActivity.this, "auto_update");
					if("0".equals(update) || "".equals(update)){
						
						PackageManager manager = WelcomeActivity.this.getPackageManager();
						try {
							PackageInfo info = manager.getPackageInfo(
									WelcomeActivity.this.getPackageName(), 0);
							String appVersion = info.versionName; // 版本名
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch blockd
							e.printStackTrace();
						}
						// 上面是获取manifest中的版本数据，我是使用versionCode
						// 在从服务器获取到最新版本的versionCode,比较
						//getUpdate();
					}*/
					//自动登录结束
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this,LoginActivity.class);
					/*if (MyApplication.loginStatus) {
						intent.setClass(WelcomeActivity.this,MainActivity.class);
					}else {
						intent.setClass(WelcomeActivity.this,LoginActivity.class);
					}*/
					
					startActivity(intent);
					finish();
				}
			}, Config.TIME_DELAY_WELCOME);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
