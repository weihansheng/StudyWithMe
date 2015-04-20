package com.app.swm.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.swm.R;
import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.entity.User;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.util.LoginUtil;
import com.app.swm.util.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public final class UserListAdapter extends BaseAdapter {
	private final class ViewHolder {
		ImageView itemIcon;
		TextView itemName;
		TextView itemJianjie;
	}
	private List<User> userList = new ArrayList<User>();;
	private User user;
	DisplayImageOptions options;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	// 单行的布局
	private Context context;

	public UserListAdapter(Context context, List<User> userList,
			DisplayImageOptions options) {

		this.context = context;
		if(userList!=null){
			this.userList = userList;
		}
		this.options = options;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return userList==null ? 0:userList.size();
	}

	public void appendToList(List<User> addList) {

		if (addList == null) {
			return;
		}
		if(userList==null){
			userList = new ArrayList<User>();
		}
		userList.addAll(addList);
		notifyDataSetChanged();
	}
	public void clear() {
		

		if(userList==null){
			userList = new ArrayList<User>();
		}
		userList.clear();
		notifyDataSetChanged();
	}
	@Override
	public Object getItem(int position) {
		
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public int appendData(List<User> addList,boolean refesh) {
		int result =0;
		if (addList == null || addList.size()==0) {
			result = userList.size()/10;
			if(userList.size()%10!=0){
				result++;
			}
			return result;
		}
		// 首次加载
		if(userList.size()==0){
			userList.addAll(addList);//在加载新的
			notifyDataSetChanged();
			return 1; //当前是第一页
		}
		// 有新的数据
		if(!userList.get(0).getStudentID().equals(addList.get(0).getStudentID())){
			
			if(refesh){
				userList.clear();  //  先清空
				userList.addAll(addList);//在加载新的
				notifyDataSetChanged();
				return 1;
			}else{
				userList.addAll(addList);//直接追加
				notifyDataSetChanged();
				result = userList.size()/10;
				if(userList.size()%10!=0){
					result++;
				}
				return result;
			}
		}
		// 没有新的数据
		result = userList.size()/10;
		if(userList.size()%10!=0){
			result++;
		}
		return result;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		user = userList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.listitem_members, null);
			holder.itemIcon = (ImageView) convertView
					.findViewById(R.id.user_image);
			holder.itemName = (TextView) convertView
					.findViewById(R.id.instructor_name);
			holder.itemJianjie = (TextView) convertView
					.findViewById(R.id.instructor_jianjie);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.itemName.setText(user.getFirst_name());
		holder.itemJianjie.setText(user.getIntro());
		
		
		// 给ImageView设置路径Tag,这是异步加载图片的小技巧
		convertView.setTag(holder);
		final int position2 =position;
		imageLoader.displayImage(user.getHeadUrl(), holder.itemIcon, options,
				new SimpleImageLoadingListener() {});
		
		return convertView;
	}
	
}
