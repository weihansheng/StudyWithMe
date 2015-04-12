package com.app.swm.fragment;

import com.app.swm.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.swm.config.ImageOptionsUtil;
import com.app.swm.entity.User;
import com.app.swm.xlistview.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
* @Description 公众号 Fragment

* @author MR.Wang

* @date 2014-7-5 上午1:13:26 

* @version V1.0
*/

public class FragmentFreeRoom extends Fragment{

	//view
	private View rootView;
	private XListView xListView;
    private LinearLayout loadLayout;
    private LinearLayout loadFaillayout;
    private Button bn_refresh;
	private ImageView ivDeleteText;
	private EditText etSearch;
	private Button btnSearch;
	//config
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	//data
	private List<User> userList = new ArrayList<User>();
	private int page=0;
	private String searchName;
	private static FragmentNotifition singleton;
	
	public static FragmentNotifition getInstance() {
		if (singleton == null) {
			singleton = new FragmentNotifition();
		}
		return singleton;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		options = ImageOptionsUtil.headImageOptions();
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_freeroom,
					container, false);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	
}
