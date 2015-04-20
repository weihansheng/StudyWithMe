package com.app.swm.ui;

import org.apache.http.Header;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.service.DownloadService;
import com.app.swm.service.DownloadService.DownloadBinder;
import com.app.swm.widget.CustomDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.app.swm.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {
	
	private DownloadBinder binder;
	private View back_layout;
	private View check_view;
	private TextView progress_text;
	private CustomDialog.Builder ibuilder;
	Dialog dialog = null;
	private AlertDialog.Builder abuilder;
	private String content;
	private boolean isDestroy = true;
	private MyApplication app;
	public static  boolean isBinded;
	public static  boolean hasNews = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		MyApplication.getInstance().addActivity(this);
		
		app = (MyApplication) getApplication();
		progress_text=(TextView) findViewById(R.id.progress);
		check_view = (View) findViewById(R.id.check_update);
		back_layout=(View) findViewById(R.id.setting_back_layout);
		back_layout.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		check_view.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isBinded){
					cancel();
					return;
				}
				PackageManager manager = AboutActivity.this.getPackageManager();
				try {
					PackageInfo info = manager.getPackageInfo(AboutActivity.this.getPackageName(), 0);
					String appVersion = info.versionName; // 版本名
					
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch blockd
					e.printStackTrace();
				}
				//上面是获取manifest中的版本数据，我是使用versionCode
				//在从服务器获取到最新版本的versionCode,比较
				getUpdate();
			}
		});
	}
	private void cancel(){
		
		binder.cancel();
		binder.cancelNotification();
		app.setDownload(false);
		if (isBinded) {
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			Intent it = new Intent(AboutActivity.this, DownloadService.class);
			stopService(it);
		}
		isBinded = false;
		progress_text.setText(getResources().getString(R.string.update));
		
	}
	private void showUpdateDialog() {

		ibuilder = new CustomDialog.Builder(AboutActivity.this);
		ibuilder.setTitle(R.string.update);
		ibuilder.setMessage(content);
		ibuilder.setPositiveButton(R.string.confirm, new MyOnClickListener());
		ibuilder.setNegativeButton(R.string.cancel, null);
		dialog = ibuilder.create();
		dialog.show();
		
	}
	class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if(hasNews){
				
				app.setDownload(true);
				Intent it = new Intent(AboutActivity.this, DownloadService.class);
				startService(it);
				bindService(it, conn, Context.BIND_AUTO_CREATE);
			}
			dialog.dismiss();
		}


		
	}
	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadBinder) service;
			// 开始下载
			isBinded = true;
			binder.addCallback(callback);
			binder.start();

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(AboutActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(AboutActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isDestroy = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				finish();
				return;
			}
			int i = (Integer) result;
			mHandler.sendEmptyMessage(i);
		}

	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progress_text.setText("当前进度 ： " + msg.what + "%"+"取消");

		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}
	
	//加载news
	private void getUpdate(){
		
		RequestParams params = new RequestParams();
		TwitterRestClient.post(Config.AC_UPDATE, params, new AsyncHttpResponseHandler(){
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] data) {
				//加载成功
				String response = new String(data);
				PackageManager manager = AboutActivity.this.getPackageManager();
				PackageInfo info = null;
				try {
					info = manager.getPackageInfo(AboutActivity.this.getPackageName(), 0);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String appVersion = info.versionName; // 版本名
				if(response.indexOf(appVersion)==-1){
					hasNews = true;
					String res = getResources().getString(R.string.update_message);
					content = String.format(res , response);
				}else{
					content = getResources().getString(R.string.current_version);
					hasNews=false;
				}
				showUpdateDialog();
				
			}});
	}
}
