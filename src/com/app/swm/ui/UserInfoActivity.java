package com.app.swm.ui;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.http.Header;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.entity.User;
import com.app.swm.fragment.LeftDrawerFragment;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.util.FileUtil;
import com.app.swm.util.TimeUtil;
import com.app.swm.util.ToastUtil;
import com.app.swm.widget.ActionSheetPic;
import com.app.swm.widget.ActionSheetPic.OnActionSheetSelected;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.app.swm.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements OnActionSheetSelected{
	//config
		protected ImageLoader imageLoader = ImageLoader.getInstance();
		protected DisplayImageOptions options;
		//view
	    private LinearLayout loadingLayout;
	    private View loadNoneLayout;
	    private LinearLayout backLayout;
		ImageView headImg ;
		TextView tv_userID;
		TextView tv_userEmail;
		private TextView tv_save;
		private Context context;
		String photPath;
		String fileName = "local_head.png";
		String filepath = Config.TEMP_PATH;
		String photo;
		boolean request = false;
		Bitmap bitmap;
		public static final int REQUEST_TAKE_PHOTO = 101;
		public static final int REQUEST_LOCAL_IMG = 102;
		public static final int RESULT_CUT_IMG = 103;
	    
		private ImageView mHeadImg;
		//data
		private User user = MyApplication.user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frament_user_info);
		context=this;
		findViewById();
		setOnclick();
		initView();
	}

	protected void findViewById() {
		tv_userEmail=(TextView) findViewById(R.id.tv_user_email);
		tv_userID=(TextView) findViewById(R.id.tv_userID);
		headImg=(ImageView) findViewById(R.id.user_info_headImg);
		backLayout=(LinearLayout) findViewById(R.id.content_back_layout);
		tv_save=(TextView) findViewById(R.id.tv_save);
		//从服务器获取头像
		//imageLoader.displayImage(Config.HEAD_URL +user.getHeadUrl(),headImg, options);
	}

	protected void setOnclick() {
		MyOnClick onclick = new MyOnClick();
		
		headImg.setOnClickListener(onclick);
		backLayout.setOnClickListener(onclick);
		tv_save.setOnClickListener(onclick);

	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {

			case R.id.user_info_headImg:
				ActionSheetPic.showSheet(context, UserInfoActivity.this);
				//intent.setClass(mactivity,ReviseInfoActivity.class);
				//startActivity(intent);
				break;
			case R.id.content_back_layout:
				finish();	
				break;
			case R.id.tv_save:
				saveInfo();
				break;
			
			default:
				break;
			}

		}

	}

	protected void initView() {
		


	}

	@Override
	public void onClick(int whichButton) {
		// TODO Auto-generated method stub
				switch (whichButton) {

				case R.id.local_pic:

					getLocalPohot();
					break;

				case R.id.take_photo:
					takePhoto();
					break;
				case R.id.cancel:
					break;

				default:
					break;
				}
			
	}

	private void getLocalPohot() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent,REQUEST_LOCAL_IMG);
	}

	private void takePhoto() {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0); 
		String path = Config.TEMP_PATH;
		if (FileUtil.makeDir(path)) {

			String photoName = TimeUtil.dateToString(new Date(),
					TimeUtil.FORMAT_DATE_TIME_SECOND) + ".png";
			photPath = Config.TEMP_PATH + photoName;
			Uri imageUri = Uri.fromFile(new File(path, photoName));
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
		} else {

			ToastUtil.showErrorMsg((Activity)context, R.string.error_sdcard_null);
		}

	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_CUT_IMG);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photoBitmap = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photoBitmap);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photoBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream); 
			headImg.setImageDrawable(drawable);
			/**
			 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
			 */
			copyImageTolocal(photoBitmap);
			request = true;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {


		switch (requestCode) {
		// 如果是直接从相册获取
		case REQUEST_LOCAL_IMG:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			
			break;
		// 如果是调用相机拍照时
		case REQUEST_TAKE_PHOTO:

			 Uri imageUri = Uri.fromFile(new File(photPath));
			 startPhotoZoom(imageUri);
			break;
		// 取得裁剪后的图片
		case RESULT_CUT_IMG:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	void saveInfo(){
		/*if(TextUtils.isEmpty(intro_et.getText())){
			ToastUtil.showErrorMsg(UserInfoActivity.this, R.string.error_intro_null);
			return;
		}
		if(intro_et.getText().length()>35){
			ToastUtil.showErrorMsg(UserInfoActivity.this, R.string.error_intro_tolong);
			return;
		}
		final String intro =intro_et.getText().toString();
		if(!MyApplication.user.getIntro().equals(intro)){
			tv_save.setText(R.string.saving);
			modifyIntro(intro);
		}*/
		if(request){
			request = false;
			uplaodHead(photo);
			tv_save.setText(R.string.saving);
		}
		
	}
	
	private void copyImageTolocal(Bitmap bm) {
		File file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			}
		file = new File(filepath , fileName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void uplaodHead(String path){
		RequestParams params = new RequestParams();
		TwitterRestClient.client.addHeader("Content-Type", "multipart/form-data");
		File file = new File(filepath+fileName);
		try {
			params.put("file", file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TwitterRestClient.post(Config.AC_USER_HEAD, params, new AsyncHttpResponseHandler(){
			

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				tv_save.setText(R.string.save);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				//加载成功
				tv_save.setText(R.string.save);
				String headUrl = new String(arg2);
				User user = MyApplication.user;
				user.setHeadUrl(headUrl);
				MyApplication.user = user;
				LeftDrawerFragment.setInfo();  //更新侧滑菜单的头像
				ToastUtil.showSuccessMsg(UserInfoActivity.this, R.string.success_intro);
				request = false;
		}});
	
	}
	private void modifyIntro(final String intro){
		RequestParams params = new RequestParams();
		params.put("user.intro", intro);
		TwitterRestClient.post(Config.AC_USER_INTRO, params, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				//更新成功
				String result= null;
		
				result = new String (arg2);
		
				if(result.indexOf("success")!=-1){
					MyApplication.user.setIntro(intro);
					tv_save.setText(R.string.save);
					ToastUtil.showSuccessMsg(UserInfoActivity.this, R.string.success_intro);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}});
		
	}
}
