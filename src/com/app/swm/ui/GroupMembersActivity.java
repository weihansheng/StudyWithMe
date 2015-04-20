package com.app.swm.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.swm.R;
import com.app.swm.adapter.UserListAdapter;
import com.app.swm.config.ImageOptionsUtil;
import com.app.swm.entity.User;
import com.app.swm.xlistview.XListView;
import com.app.swm.xlistview.XListView.IXListViewListener;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class GroupMembersActivity extends Activity implements IXListViewListener{
	// private MyDialog dialog;
	private LinearLayout layout;
	private XListView xListView;
	private UserListAdapter adapter;
	DisplayImageOptions options;
	private List<User> userList = new ArrayList<User>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_members);
		options = ImageOptionsUtil.headImageOptions();
		findById();
		initView();
		initEvent();
		
		layout = (LinearLayout) findViewById(R.id.exit_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void findById() {
		// TODO Auto-generated method stub
		xListView = (XListView) findViewById(R.id.members_listview);

	}

	private void initView() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			User user=new User();
			user.setFirst_name("JOhan");
			user.setHeadUrl("http://g.hiphotos.baidu.com/image/pic/item/d1a20cf431adcbef0009b002aeaf2edda3cc9ff1.jpg");
			user.setIntro("Hi! I am Johan.");
			userList.add(user);
		}
		
		adapter = new UserListAdapter(GroupMembersActivity.this, userList, options);
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				adapter);

		xListView.setPullLoadEnable(true);
		xListView.setPullRefreshEnable(false);// 取消下拉刷新

		animAdapter.setAbsListView(xListView);
		animAdapter.setInitialDelayMillis(100);
		xListView.setXListViewListener(this);
		xListView.setAdapter(animAdapter);

		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == 0) {
					return;// 0 是header
				}
				TextView instructor_name = (TextView) arg1
						.findViewById(R.id.instructor_name);
				instructor_name.setTextColor(getResources().getColor(
						R.color.light_black));
				//Intent intent = new Intent();
				//intent.setClass(getActivity(), UserDetailActivity.class);
				//User user = (User) adapter.getItem(position - 1); // -1
				//intent.putExtra("user", user);
				//startActivity(intent);

				/*
				 * Intent intent = new Intent(); intent.setClass(getActivity(),
				 * SearchActivity.class); startActivity(intent);
				 */

			}
		});

	}

	private void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void exitbutton1(View v) {
		this.finish();
	}

	public void exitbutton0(View v) {
		this.finish();
		GroupDetailsActivity.instance.finish();// 关闭Main 这个Activity
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		onLoad();
		User user=new User();
		user.setFirst_name("IVY");
		user.setHeadUrl("http://d.hiphotos.baidu.com/image/pic/item/9213b07eca806538277daa1e95dda144ad348223.jpg");
		user.setIntro("Hi! I am Ivy.");
		userList.add(user);
		
	}
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
	}

}
