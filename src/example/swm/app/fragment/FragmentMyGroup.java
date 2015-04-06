package example.swm.app.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import example.swm.app.R;
import example.swm.app.entity.Group;
import example.swm.app.xlistview.XListView;

/**
 * @Description 草稿 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentMyGroup extends Fragment{

	private View rootView;
	View parentView;
	private XListView listView;
	DisplayImageOptions options;
	private List<Group> newsList;
	public static FragmentMyGroup mactivity;
	private LinearLayout emptylLayout;
	private LinearLayout listlLayout;
	private Context context;

	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentMyGroup singleton;

	public static FragmentMyGroup getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentMyGroup();
		}
		return singleton;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mactivity = this;
		context = inflater.getContext();

		rootView = inflater.inflate(R.layout.fragment_freeroom, container, false);
		//initView(rootView);

		return rootView;

	}

	

}
