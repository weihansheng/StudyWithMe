package com.app.swm.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.swm.R;
import com.app.swm.adapter.CommentAdapter;
import com.app.swm.entity.Comment;
import com.app.swm.util.PixelUtil;
import com.app.swm.xlistview.XListView;
import com.app.swm.xlistview.XListView.IXListViewListener;

public class GroupDetailsActivity extends Activity implements OnClickListener ,IXListViewListener{
	public static GroupDetailsActivity instance=null;
	private TextView tvAdmin;
	private TextView tvWhere;
	private TextView tvTime;
	private TextView tvDate;
	private TextView tvNum;
	private TextView tvApplicants;
	private TextView tvDescription;
	private LinearLayout members_layout;
	private View members;
	private View members_underline;
	private TextView tvJoin;
	private View back;
	private TextView tv_title;
	private boolean joined = false;
	//comment
	private Button bt_comment;
	private EditText et_comment;
	private LinearLayout loadingLayout;
    private LinearLayout loadNoneLayout;
	private XListView xListview;
	private CommentAdapter adapter;
	List<Comment> commentList = new ArrayList<Comment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance=this;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groupdetails);
		findID();
		initView();
		initEvent();
	}

	private void findID() {
		// TODO Auto-generated method stub
		tvAdmin = (TextView) findViewById(R.id.group_admin);
		tvWhere = (TextView) findViewById(R.id.group_where);
		tvNum = (TextView) findViewById(R.id.group_people_num);
		tvDate = (TextView) findViewById(R.id.group_date);
		tvTime = (TextView) findViewById(R.id.group_time);
		tvDescription = (TextView) findViewById(R.id.group_description);
		tvApplicants = (TextView) findViewById(R.id.group_people_num_now);
		tvJoin = (TextView) findViewById(R.id.tv_join);
		tv_title=(TextView) findViewById(R.id.groupdetail_title);
		tv_title.setText("Details");
		back=findViewById(R.id.groupdetail_back_layout);
		members_layout=(LinearLayout) findViewById(R.id.member_layout);
		members=findViewById(R.id.member);
		members_underline=findViewById(R.id.member_underline);
		
		et_comment=(EditText) findViewById(R.id.et_comment);
		bt_comment=(Button) findViewById(R.id.bt_comment);
		loadingLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadingLayout.setVisibility(View.GONE);
		loadNoneLayout = (LinearLayout) findViewById(R.id.view_load_nodata);

	}

	private void initView() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i++) {
			Comment comment=new Comment();
			comment.setId(i);
			comment.setAuthorName("Johan"+i);
			comment.setPostTime(new Date());
			comment.setContent("When will you ge there?");
			commentList.add(comment);
		}
		
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		members.measure(w, h); 
		int members_width=members.getMeasuredWidth();
		members_underline.setLayoutParams(new  LinearLayout.LayoutParams(members_width, PixelUtil.dp2px(1)));
		
		adapter = new CommentAdapter(this, commentList,
				R.layout.listitem_comment);

		adapter.appendToList(commentList);
		xListview = (XListView) findViewById(R.id.listview_comment);
		xListview.setPullLoadEnable(true);
		xListview.setPullRefreshEnable(false);
		xListview.setXListViewListener(this);
		xListview.setAdapter(adapter);
		setListViewHeightBasedOnChildren(xListview);
		onLoad();
		//setListViewHeightBasedOnChildren(xListview);// 解决listview与scrollview冲突问题
		//loadComment(1,true);

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		tvJoin.setOnClickListener(this);
		back.setOnClickListener(this);
		members_layout.setOnClickListener(this);
		bt_comment.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_join:
			addMembers();
			break;
		case R.id.member_layout:
			Members();
			break;
		case R.id.groupdetail_back_layout:
			finish();
			break;
		case R.id.bt_comment:
			addComment();
			break;

		default:
			break;
		}

	}

	private void addComment() {
		// TODO Auto-generated method stub
		Random random=new Random();
		int id=random.nextInt();
		String newcomment=et_comment.getText().toString();
		Date postDate=new Date();
		String name="IVY";
		Comment comment=new Comment();
		comment.setId(id);
		comment.setAuthorName(name+id);
		comment.setPostTime(postDate);
		comment.setContent(newcomment);
		commentList.add(comment);
		adapter.clear();//不clear的话listview不更新
		adapter.appendData(commentList, true);
		setListViewHeightBasedOnChildren(xListview);
		et_comment.setText("");
	}

	private void Members() {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(GroupDetailsActivity.this, GroupMembersActivity.class);
		startActivity(intent);

	}

	private void addMembers() {
		// TODO Auto-generated method stub
		// 需要判断是否已参加过该小组
		if (joined) {
			Toast.makeText(this, "Have joined!", 0).show();
		} else {
			int groupNum = Integer.parseInt(tvNum.getText().toString());
			tvNum.setText(String.valueOf(groupNum + 1));
			joined = true;
		}

	}
	// 解决listview与scrollview冲突问题
		private void setListViewHeightBasedOnChildren(ListView listView) {

			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}

			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		onLoad();
		
	}
	private void onLoad() {
		xListview.stopRefresh();
		xListview.stopLoadMore();
	}

}
