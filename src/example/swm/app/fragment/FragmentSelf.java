package example.swm.app.fragment;


import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import example.swm.app.R;
import example.swm.app.config.Config;
import example.swm.app.config.ImageOptionsUtil;
import example.swm.app.config.MyApplication;
import example.swm.app.entity.User;
import example.swm.app.util.FileUtil;
import example.swm.app.util.TimeUtil;
import example.swm.app.util.ToastUtil;
import example.swm.app.widget.ActionSheetPic;
import example.swm.app.widget.ActionSheetPic.OnActionSheetSelected;

public class FragmentSelf extends Fragment implements OnActionSheetSelected{

	public static FragmentActivity mactivity;
	
	//config
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	//view
    private LinearLayout loadingLayout;
    private View loadNoneLayout;
	ImageView headImg ;
	TextView tv_userID;
	TextView tv_userEmail;
	private Context context;
	String photPath;
	public static final int REQUEST_TAKE_PHOTO = 101;
	public static final int REQUEST_LOCAL_IMG = 102;
	public static final int RESULT_CUT_IMG = 103;
	
    
	private ImageView mHeadImg;
	private View rootView;
	//data
	private User user = MyApplication.user;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mactivity = getActivity();
		options = ImageOptionsUtil.headImageOptions();
		rootView = inflater.inflate(R.layout.frament_user_info, container,false);
		context=getActivity();
		findViewById();
		setOnclick();
		initView();
		return rootView;

	}
	
	protected void findViewById() {
		tv_userEmail=(TextView) rootView.findViewById(R.id.tv_user_email);
		tv_userID=(TextView) rootView.findViewById(R.id.tv_userID);
		headImg=(ImageView) rootView.findViewById(R.id.user_info_headImg);
		//从服务器获取头像
		//imageLoader.displayImage(Config.HEAD_URL +user.getHeadUrl(),headImg, options);
	}

	protected void setOnclick() {
		MyOnClick onclick = new MyOnClick();
		
		headImg.setOnClickListener(onclick);

	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {

			case R.id.user_info_headImg:
				ActionSheetPic.showSheet(context, (OnActionSheetSelected) getActivity());
				//intent.setClass(mactivity,ReviseInfoActivity.class);
				//startActivity(intent);
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

	
	


}
