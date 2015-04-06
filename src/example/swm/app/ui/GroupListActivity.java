package example.swm.app.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import example.swm.app.R;
import example.swm.app.adapter.GroupListAdapter;
import example.swm.app.entity.Group;
import example.swm.app.util.SharedPreferencesUtil;
import example.swm.app.util.TimeUtil;
import example.swm.app.xlistview.XListView;
import example.swm.app.xlistview.XListView.IXListViewListener;

public class GroupListActivity extends Activity implements IXListViewListener{
	private XListView mListView;
	private List<Group> GroupList; 
	private GroupListAdapter adapter;
	private Context context;
	private LinearLayout loadingLayout;
	private LinearLayout loadFaillayout;
	private Button bn_refresh;
	private View back;
	private TextView tv_title;
	DisplayImageOptions options_head;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grouplist);
		context = this;
		findById();
		initView();
		initEvent();
	}
	private void findById() {
		// TODO Auto-generated method stub
		mListView=(XListView) findViewById(R.id.list_project);
		loadingLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
		back=findViewById(R.id.content_back_layout);
		tv_title=(TextView) findViewById(R.id.back_title);
		tv_title.setText("Groups");
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		
		GroupList=new ArrayList<Group>();
		Group group1=new Group();
		
		for (int i = 0; i < 10; i++) {
			group1.setTitle("css"+i);
			group1.setDescription("We will study css"+i+" in....");
			group1.setPostTime(new Date());
			GroupList.add(group1);
		}
	
		
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);
		adapter = new GroupListAdapter(context, GroupList, options_head);
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
				TextView title = (TextView) view.findViewById(R.id.list_item_project_title);
				title.setTextColor(context.getResources().getColor(R.color.light_black));
				Intent intent = new Intent();
				intent.setClass(context, GroupDetailsActivity.class);
				//Group group = (Group) adapter.getItem(position - 2); // 减去header
																	// 还有xlistview的header
				//intent.putExtra("group", group);
				context.startActivity(intent);
			}
		});
		
	}
	private void initEvent() {
		// TODO Auto-generated method stub
		myClick click=new myClick();
		bn_refresh = (Button) findViewById(R.id.bn_refresh);
		bn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*loadNews(1, true);
				loadNotice();
				LoginUtil.Login(context);*/
			}
		});
		back.setOnClickListener(click);		
	}
	@Override
	public void onRefresh() {
		//loadNews(1, true);
		String curDate = TimeUtil.dateToString(new Date(), TimeUtil.FORMAT_MONTH_DAY_TIME_EN);
		SharedPreferencesUtil.add(context, "news_time", curDate);
		// 通知 不用刷新
		/*if (!notice_success) {
			loadNotice();
		}*/
		//onLoadMore();
		onLoad();
	}

	@Override
	public void onLoadMore() {
		loadNews( 1, false);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		// 获取当前时间
		String curDate = SharedPreferencesUtil.get(context, "news_time");
		if(!"".equals(curDate)){
			mListView.setRefreshTime(curDate);
		}else{
			curDate = TimeUtil
					.dateToString(new Date(), TimeUtil.FORMAT_MONTH_DAY_TIME_EN);
			mListView.setRefreshTime(curDate);
		}

	}
	// 加载news
		private void loadNews(int page_num, final boolean refesh) {

			Group group=new Group();
			group.setTitle("css2");
			group.setDescription("We will study css in....");
			group.setPostTime(new Date(2015, 04, 16));
			GroupList.add(group);
			//mListView.setPullLoadEnable(false);
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
