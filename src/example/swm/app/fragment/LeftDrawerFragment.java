package example.swm.app.fragment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import example.swm.app.config.Config;
import example.swm.app.config.ImageOptionsUtil;
import example.swm.app.config.MyApplication;
import example.swm.app.entity.User;
import example.swm.app.ui.LoginActivity;
import example.swm.app.ui.MainActivity;
import example.swm.app.widget.CustomScrollView;
import example.swm.app.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Description 侧滑左Fragment
 * 
 * @author Johan
 * 
 * @date 2014-7-5 上午1:13:57
 * 
 * @version V1.0
 */

public class LeftDrawerFragment extends Fragment {

	// toolboxLayout
	private RelativeLayout menuHome;
	private View menuMyGroup;
	private View menuSchedule;
	private View menuSettings;
	private View menuNotification;
	private View menuFreeRomm;
	private ImageView new_message_iv;

	private View currentView; // 当前选中的View
	private View rootView; // 当前整个View
	private static ImageView headImageView; // 左侧图像 ImageView
	public static TextView nameTv; // 左侧名字 textView
	private TextView titleTv; // 顶部栏目标题 textView
	Fragment newContent = null;
	private CustomScrollView scrollView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (MyApplication.user != null) {
			if (MyApplication.user.getNewReply() > 0) {
				new_message_iv.setVisibility(View.VISIBLE);
			} else {
				new_message_iv.setVisibility(View.GONE);
			}

		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.main_left_fragment, container,
				false);
		findViewById();
		setOnclick();
		if (MyApplication.user != null) {
			if (MyApplication.user.getNewReply() > 0) {
				new_message_iv.setVisibility(View.VISIBLE);
			}
		}
		return rootView;
	}

	protected void findViewById() {

		// 设置CustomScrollView
		ImageView backgroundImageView = (ImageView) rootView
				.findViewById(R.id.personal_background_image);
		scrollView = (CustomScrollView) rootView
				.findViewById(R.id.personal_scrollView);
		scrollView.setImageView(backgroundImageView);

		// findbyID
		menuHome = (RelativeLayout) rootView
				.findViewById(R.id.menu_layout_home);
		menuMyGroup = rootView.findViewById(R.id.menu_layout_mygroup);
		menuNotification = rootView.findViewById(R.id.menu_layout_notification);
		menuSchedule = rootView.findViewById(R.id.menu_layout_schedule);
		menuFreeRomm = rootView.findViewById(R.id.menu_layout_freerooms);
		menuSettings = rootView.findViewById(R.id.menu_layout_settings);

		headImageView = (ImageView) rootView.findViewById(R.id.headImageView);
		nameTv = (TextView) rootView.findViewById(R.id.nameTextView);

		titleTv = MainActivity.titleTv;
		currentView = menuHome;
		currentView.setSelected(true);

	}

	public static void setInfo() {

		if (LeftDrawerFragment.nameTv != null) {
			if (MyApplication.user != null) {
				LeftDrawerFragment.nameTv.setText(MyApplication.user.getName());

				DisplayImageOptions options = ImageOptionsUtil
						.headImageOptions();
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(
						Config.HEAD_URL + MyApplication.user.getHeadUrl(),
						headImageView, options);
			} else {
				nameTv.setText(R.string.login_hint);
				headImageView.setImageResource(R.drawable.head_default_local);

			}
		}

	}

	/*
	 * @Override public void onResume() { super.onResume(); setInfo(); }
	 */

	protected void setOnclick() {

		// 添加监听事件
		MyOClick click = new MyOClick();
		menuHome.setOnClickListener(click);
		menuMyGroup.setOnClickListener(click);
		menuNotification.setOnClickListener(click);
		menuFreeRomm.setOnClickListener(click);
		menuSchedule.setOnClickListener(click);
		menuSettings.setOnClickListener(click);

		headImageView.setOnClickListener(click);

	}

	class MyOClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent();
			switch (v.getId()) {

			case R.id.menu_layout_home:
				// newContent = FragmentHome.getInstance(getActivity());
				newContent = new FragmentHome();
				titleTv.setText(R.string.home);
				currentView.setSelected(false);
				currentView = menuHome;
				currentView.setSelected(true);
				break;

			case R.id.menu_layout_mygroup:
				if (currentView != menuMyGroup) {
					newContent = new FragmentMyGroup();
				}
				titleTv.setText(R.string.menu_mygroups);
				currentView.setSelected(false);
				currentView = menuMyGroup;
				currentView.setSelected(true);
				break;

			case R.id.menu_layout_notification:

				if (currentView != menuNotification) {
					newContent = new FragmentNotifition();
				}
				titleTv.setText(R.string.menu_notifition);
				currentView.setSelected(false);
				currentView = menuNotification;
				currentView.setSelected(true);
				break;
			case R.id.menu_layout_schedule:

				if (currentView != menuSchedule) {
					newContent = new FragmentSchedule();
				}
				titleTv.setText(R.string.menu_schedule);
				currentView.setSelected(false);
				currentView = menuSchedule;
				currentView.setSelected(true);
				break;
			case R.id.menu_layout_freerooms:

				if (currentView != menuFreeRomm) {
					newContent = new FragmentFreeRoom();
				}
				titleTv.setText(R.string.menu_freeroom);
				currentView.setSelected(false);
				currentView = menuFreeRomm;
				currentView.setSelected(true);
				break;
			case R.id.menu_layout_settings:

				if (currentView != menuSettings) {
					newContent = new FragmentSetting();
				}
				titleTv.setText(R.string.menu_settings);
				currentView.setSelected(false);
				currentView = menuSettings;
				currentView.setSelected(true);
				break;

			case R.id.headImageView:
				if (MyApplication.user == null) {

					//delayLoginActivity();

				} else {
					// userActivity();
					if (currentView == headImageView) {

						titleTv.setText(MyApplication.user.getName());
						currentView.setSelected(false);
						currentView = headImageView;
						currentView.setSelected(true);
					} else {
						delayFragmentSelf();
						break;
					}

				}
				break;

			default:
				break;
			}
			if (newContent != null)
				switchFragment(newContent);
		}

	}

	// 延迟跳转登陆
	private void delayLoginActivity() {
		((MainActivity) getActivity()).closeDrawer();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		}, 400);

	}

	// 延迟加载 个人页面
	private void delayFragmentSelf() {
		((MainActivity) getActivity()).closeDrawer();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				newContent = new FragmentSelf();
				switchFragment(newContent);
				titleTv.setText(MyApplication.user.getName());
				currentView.setSelected(false);
				currentView = headImageView;
				currentView.setSelected(true);
			}
		}, 400);

	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null) {
			return;
		}
		MainActivity ra = (MainActivity) getActivity();
		ra.switchFrame(fragment);
	}

}
