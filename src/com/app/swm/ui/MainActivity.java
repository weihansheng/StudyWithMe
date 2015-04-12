package com.app.swm.ui;

import org.apache.http.Header;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.fragment.FragmentHome;
import com.app.swm.fragment.LeftDrawerFragment;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.service.DownloadService;
import com.app.swm.service.DownloadService.DownloadBinder;
import com.app.swm.util.LoginUtil;
import com.app.swm.util.SharedPreferencesUtil;
import com.app.swm.widget.ActionSheetExit;
import com.app.swm.widget.CustomDialog;
import com.app.swm.widget.ActionSheetExit.OnActionSheetSelected;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import com.app.swm.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;


@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
OnActionSheetSelected, OnCancelListener {
	private DrawerLayout drawerLayout;
	private FrameLayout leftLayout;
	public static TextView titleTv;
	public static float density;
	public static float xdpi;
	public static float ydpi;
	public static float screenWidth;
	public static float screenHeight;
	public static float densityDPI;
	public static int screenWidthDip;
	public static int screenHeightDip;
	public View leftBun;
	
	private DownloadBinder binder;
	private boolean hasNews = true;
	private String content;
	private CustomDialog.Builder ibuilder;
	Dialog dialog = null;
	private MyApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		drawerLayout = (DrawerLayout) findViewById(R.id.content_drawer);
		leftLayout = (FrameLayout) findViewById(R.id.left_drawer);
		titleTv = (TextView) findViewById(R.id.main_title_name);
		drawerLayout.setDrawerShadow(R.drawable.shadow,
				GravityCompat.START);
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame,new FragmentHome()).commit();
		//.replace(R.id.content_frame,FragmentHome.getInstance(MainActivity.this)).commit();
		
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.left_drawer,
						new LeftDrawerFragment()).commit();
		
		leftBun = findViewById(R.id.slidingmenu_left_title);
		leftBun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!drawerLayout.isDrawerVisible(leftLayout)){
					drawerLayout.openDrawer(leftLayout);
					
				}else{
					drawerLayout.closeDrawers();
				}
			}
		});
		//自动登录
		String login = SharedPreferencesUtil.get(MainActivity.this, "auto_login");
		if("0".equals(login) || "".equals(login)){
			LoginUtil.post(MainActivity.this);
			
		}
		String update = SharedPreferencesUtil.get(MainActivity.this, "auto_update");
		if("0".equals(update) || "".equals(update)){
			
			PackageManager manager = MainActivity.this.getPackageManager();
			try {
				PackageInfo info = manager.getPackageInfo(
						MainActivity.this.getPackageName(), 0);
				String appVersion = info.versionName; // 版本名
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch blockd
				e.printStackTrace();
			}
			// 上面是获取manifest中的版本数据，我是使用versionCode
			// 在从服务器获取到最新版本的versionCode,比较
			//getUpdate();
		}
	}
	
	public DrawerLayout getmDrawerLayout() {
		return drawerLayout;
	}
	public void closeDrawer() {
		if(drawerLayout.isDrawerVisible(leftLayout)){
			drawerLayout.closeDrawers();
		}
	}

	public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
		this.drawerLayout = mDrawerLayout;
	}

	public  void  switchFrame(Fragment fragment){
		drawerLayout.closeDrawers();
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame,
				fragment).commit();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		ActionSheetExit.showSheet(MainActivity.this, MainActivity.this,
				MainActivity.this);
	}



	@Override
	public void onClick(int whichButton) {
		// TODO Auto-generated method stub
		switch (whichButton) {
		case 0:
			//ActivityStackControlUtil.finishProgram();
			FragmentHome.rootView=null;
			LoginUtil.cancel(getApplication());
			finish();
			break;

		case 1:
			break;

		default:
			break;
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
	}
	
	private void getUpdate() {

		RequestParams params = new RequestParams();
		TwitterRestClient.post(Config.AC_UPDATE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] data) {
						// 加载成功
						String response = new String(data);
						PackageManager manager = MainActivity.this
								.getPackageManager();
						PackageInfo info = null;
						try {
							info = manager.getPackageInfo(MainActivity.this
									.getPackageName(), 0);
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String appVersion = info.versionName; // 版本名
						if (response.indexOf(appVersion) == -1) {
							hasNews = true;
							String res = getResources().getString(
									R.string.update_message);
							content = String.format(res, response);
							showUpdateDialog();
						} 

					}
				});
	}

	 @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(!drawerLayout.isDrawerVisible(leftLayout)){
			drawerLayout.openDrawer(leftLayout);
			
		}else{
			drawerLayout.closeDrawers();
		}
		return false;
	}
	private void showUpdateDialog() {

		ibuilder = new CustomDialog.Builder(MainActivity.this);
		ibuilder.setTitle(R.string.update);
		ibuilder.setMessage(content);
		ibuilder.setPositiveButton(R.string.confirm, new MyOnClickListener2());
		ibuilder.setNegativeButton(R.string.cancel, null);
		dialog = ibuilder.create();
		dialog.show();

	}

	class MyOnClickListener2 implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if (hasNews) {
				app = (MyApplication) MainActivity.this.getApplication();
				app.setDownload(true);
				Intent it = new Intent(MainActivity.this, DownloadService.class);
				MainActivity.this.startService(it);
				MainActivity.this.bindService(it, conn, Context.BIND_AUTO_CREATE);
			}
			dialog.dismiss();
		}
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			AboutActivity.isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadBinder) service;
			// 开始下载
			AboutActivity.isBinded = true;
			// binder.addCallback(callback);
			binder.start();

		}
	};
	
	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				MainActivity.this.finish();
				return;
			}
			int i = (Integer) result;
			mHandler.sendEmptyMessage(i);
		}

	};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}
}
