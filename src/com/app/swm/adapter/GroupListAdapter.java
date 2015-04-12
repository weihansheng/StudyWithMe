package com.app.swm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.swm.entity.Group;
import com.app.swm.util.TimeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.app.swm.R;

public class GroupListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	DisplayImageOptions dOptions;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private List<Group> projectList = new ArrayList<Group>();
	private Group group;

	public GroupListAdapter(Context context, List<Group> list,
			DisplayImageOptions options) {
		if (list != null) {
			this.projectList = list;
		}
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.dOptions = options;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return projectList == null ? 0 : projectList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return projectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder mHolder;
		group = projectList.get(position);
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_group, null);
			mHolder.admin = (TextView) convertView
					.findViewById(R.id.group_list_admin);
			mHolder.num_love = (TextView) convertView
					.findViewById(R.id.group_list_num_love);
			mHolder.start_time = (TextView) convertView
					.findViewById(R.id.group_list_starttime);
			mHolder.post_time = (TextView) convertView
					.findViewById(R.id.group_list_posttime);
			convertView.setTag(mHolder);  //不设置标记会报空指针
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.admin.setText(group.getAdmin());
		mHolder.num_love.setText(group.getPeopleNum().toString());
		mHolder.start_time.setText(TimeUtil.dateToString(group.getPostTime(),TimeUtil.FORMAT_DATE_TIME));
		mHolder.post_time.setText(TimeUtil.dateToString(group.getPostTime(),TimeUtil.FORMAT_MONTH_DAY_TIME_EN));

		return convertView;
	}

	private final class ViewHolder {
		public TextView admin;
		public TextView num_love;
		public TextView where;
		public TextView post_time;
		public TextView start_time;

	}
	
	public void appendToList(List<Group> addList) {

		if (addList == null) {
			return;
		}
		if (projectList == null) {
			projectList = new ArrayList<Group>();
		}
		projectList.addAll(addList);
		notifyDataSetChanged();
	}
	public void removeList(List<Group> addList) {
		
		if (addList == null) {
			return;
		}
		if (projectList == null) {
			projectList = new ArrayList<Group>();
			projectList.addAll(addList);//在加载新的
			notifyDataSetChanged();
			return;
		}
		if(!addList.get(0).getId().equals(projectList.get(0).getId())){
			
			projectList.clear();//  先清空
			projectList.addAll(addList);//在加载新的
			notifyDataSetChanged();
		}
		
	}
	
	public int appendData(List<Group> addList,boolean refesh) {
		int result =0;
		if (addList == null || addList.size()==0) {
			result = projectList.size()/5;
			if(projectList.size()%5!=0){
				result++;
			}
			return result;
		}
		// 首次加载
		if(projectList.size()==0){
			projectList.addAll(addList);//在加载新的
			notifyDataSetChanged();
			return 1; //当前是第一页
		}
		// 有新的数据
		if(!projectList.get(0).getId().equals(addList.get(0).getId())){
			
			if(refesh){
				projectList.clear();  //  先清空
				projectList.addAll(addList);//在加载新的
				notifyDataSetChanged();
				return 1;
			}else{
				projectList.addAll(addList);//直接追加
				notifyDataSetChanged();
				result = projectList.size()/5;
				if(projectList.size()%5!=0){
					result++;
				}
				return result;
			}
		}
		// 没有新的数据
		result = projectList.size()/5;
		if(projectList.size()%5!=0){
			result++;
		}
		return result;
	
	}

}
