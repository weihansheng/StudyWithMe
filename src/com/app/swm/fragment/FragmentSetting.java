package com.app.swm.fragment;

import com.app.swm.R;

import java.io.File;
import java.text.DecimalFormat;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.service.DownloadService;
import com.app.swm.service.DownloadService.DownloadBinder;
import com.app.swm.ui.AboutActivity;
import com.app.swm.ui.FeedBackActivity;
import com.app.swm.ui.LoginActivity;
import com.app.swm.ui.MainActivity;
import com.app.swm.ui.AboutActivity.ICallbackResult;
import com.app.swm.util.FileUtil;
import com.app.swm.util.LoginUtil;
import com.app.swm.util.SharedPreferencesUtil;
import com.app.swm.widget.CustomDialog;
import com.app.swm.widget.UISwitchButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * @Description 设置 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentSetting extends Fragment {

	//
	private CustomDialog.Builder ibuilder;
	Dialog dialog = null;
	private View rootView;
	private Context context;
	private View aboutView;
	private View feedbackView;
	private View cleanView;
	private Button exit_btn;
	private TextView cache_size;
	private String size;
	private UISwitchButton sb_auto_update;
	private UISwitchButton sb_auto_login;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = inflater.getContext();

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_setting, container,
					false);
			

		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		
		return rootView;
	}




}
