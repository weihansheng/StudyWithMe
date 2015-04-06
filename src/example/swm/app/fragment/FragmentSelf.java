package example.swm.app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import example.swm.app.R;
import example.swm.app.config.ImageOptionsUtil;
import example.swm.app.config.MyApplication;
import example.swm.app.entity.Group;
import example.swm.app.entity.User;
import example.swm.app.widget.PullScrollView;
import example.swm.app.xlistview.XListView;

public class FragmentSelf extends Fragment {

	public static FragmentActivity mactivity;
	
	//config
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	//view
    private LinearLayout loadingLayout;
    private View loadNoneLayout;
    private View v_line;
    private View post_layout;
    private LinearLayout bt_revise_user_info;
    private LinearLayout layout_follow;
    private LinearLayout layout_fans;
	TextView name_tv ;
	TextView fansnum_tv ;
	TextView follownum_tv ;
	TextView newsnum_tv ;
	TextView intro_tv ;
	ImageView headImg ;
    
	private XListView xListView;
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private View rootView;
	//data
	private int page=0;
	private User user = MyApplication.user;
	private List<Group> newsList = new ArrayList<Group>();;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mactivity = getActivity();
		options = ImageOptionsUtil.headImageOptions();
		rootView = inflater.inflate(R.layout.frament_user_info, container,false);
		return rootView;

	}
	

}
