package com.app.swm.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.swm.R;
import com.app.swm.adapter.GroupListAdapter;
import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.entity.Group;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.util.SharedPreferencesUtil;
import com.app.swm.util.TimeUtil;
import com.app.swm.xlistview.XListView;
import com.app.swm.xlistview.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class GroupListActivity extends Activity implements IXListViewListener{
	private XListView mListView;
	private List<Group> groupList; 
	private GroupListAdapter adapter;
	private Context context;
	private LinearLayout loadingLayout;
	private LinearLayout loadFaillayout;
	private Button bn_refresh;
	private View back;
	private int page = 0;
	private TextView tv_title;
	DisplayImageOptions options_head;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grouplist);
		MyApplication.getInstance().addActivity(this);
		context = this;
		findById();
		initView();
		initEvent();
	}
	
	private void findById() {
		// TODO Auto-generated method stub
		mListView=(XListView) findViewById(R.id.list_group);
		loadingLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
		back=findViewById(R.id.content_back_layout);
		tv_title=(TextView) findViewById(R.id.back_title);
		tv_title.setText("Groups");
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		
		groupList=new ArrayList<Group>();
		/*Group group1=new Group();
		
		for (int i = 0; i < 10; i++) {
			group1.setAdmin("Johan"+i);
			group1.setPeopleNum(5);
			group1.setStartTime(new Date());
			group1.setPostTime(new Date());
			groupList.add(group1);
		}*/
	
		
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);
		adapter = new GroupListAdapter(context, groupList, options_head);
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				adapter);
		animAdapter.setAbsListView(mListView);
		animAdapter.setInitialDelayMillis(100);
		mListView.setAdapter(animAdapter);
		mListView.setXListViewListener(this);
		//mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, GroupDetailsActivity.class);
				//Group group = (Group) adapter.getItem(position - 2); // 减去header
																	// 还有xlistview的header
				//intent.putExtra("group", group);
				context.startActivity(intent);
			}
		});
		loadGroups(1, true);
		
	}
	private void initEvent() {
		// TODO Auto-generated method stub
		myClick click=new myClick();
		bn_refresh = (Button) findViewById(R.id.bn_refresh);
		bn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*loadGroups(1, true);
				loadNotice();
				LoginUtil.Login(context);*/
			}
		});
		back.setOnClickListener(click);		
	}
	@Override
	public void onRefresh() {
		//loadGroups(1, true);
		String curDate = TimeUtil.dateToString(new Date(), TimeUtil.FORMAT_MONTH_DAY_TIME_EN);
		SharedPreferencesUtil.add(context, "news_time", curDate);
		onLoadMore();
		onLoad();
	}

	@Override
	public void onLoadMore() {
		loadGroups( page + 1, false);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		// 获取当前时间
		String curDate = SharedPreferencesUtil.get(context, "group_list_time");
		if(!"".equals(curDate)){
			mListView.setRefreshTime(curDate);
		}else{
			curDate = TimeUtil
					.dateToString(new Date(), TimeUtil.FORMAT_MONTH_DAY_TIME_EN);
			mListView.setRefreshTime(curDate);
		}

	}
	// 加载news
		private void loadGroups(int page_num, final boolean refesh) {

			/*Group group=new Group();
			for (int i = 0; i < 10; i++) {
				group.setAdmin("Johan"+i);
				group.setPeopleNum(5);
				group.setStartTime(new Date());
				group.setPostTime(new Date(2015, 04, 16));
				groupList.add(group);
			}
			//mListView.setPullLoadEnable(false);
			page = adapter.appendData(groupList, refesh);
			*/


			RequestParams params = new RequestParams();
			params.put("page", page_num);
			TwitterRestClient.get(Config.AC_GET_GROUPLIST, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onFailure(int arg0, Header[] arg1, byte[] arg2,
								Throwable arg3) {
							// TODO Auto-generated method stub
							System.out.println("group list fail");
							onLoad();
							loadingLayout.setVisibility(View.GONE);
							if (page == 0) {
								loadFaillayout.setVisibility(View.VISIBLE);
							}
						}

						@Override
						public void onSuccess(int arg0, Header[] arg1, byte[] data) {
							// 加载成功
							System.out.println("group list sucess");
							onLoad();
							loadingLayout.setVisibility(View.GONE);
							loadFaillayout.setVisibility(View.GONE);
							Gson gson = new GsonBuilder().setDateFormat(
									"yyyy-MM-dd HH:mm:ss").create();
							groupList = gson.fromJson(new String(data),
									new TypeToken<List<Group>>() {
									}.getType());
							System.out.println("result=="+groupList.toString());
							try {
								page = adapter.appendData(groupList, refesh);
							} catch (Exception e) {
								// TODO: handle exception
								MyApplication.getInstance().exit();
								System.out.println("catch到appendData出错");
							}
							

							if (groupList != null && groupList.size() == 5) {
								mListView.setPullLoadEnable(true);
							} else {
								mListView.setPullLoadEnable(false);

							}

						}
					});
		
			
		}
		class myClick implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.content_back_layout:
					finish();
					break;

				default:
					break;
				}
				
			}
			
		}
}
