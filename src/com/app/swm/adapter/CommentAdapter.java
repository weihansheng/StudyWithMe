package com.app.swm.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.swm.R;
import com.app.swm.entity.Comment;
import com.app.swm.util.TimeUtil;

public class CommentAdapter extends BaseAdapter {
	
	
	private class ViewHolder {
		TextView itemName;
		TextView itemTime;
		TextView itemContent;
	}

	// 单行的布局
	private int mResource;
	private Context context;
	List<Comment> commentList = new ArrayList<Comment>();


	/**
	 * @param context
	 * @param commentList
	 * @param resource
	 * @param editText
	 */
	public CommentAdapter(Context context,
			List<Comment> commentList, int resource) {
		
		this.context = context;
		if(commentList==null){
			
			this.commentList =commentList;
		}
		mResource = resource;
	}

	@Override
	public int getCount() {
		return commentList ==null ? 0:commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}
/*	public void appendToFirst(Comment comment) {

		if (comment == null) {
			return;
		}
		commentList.add(0, comment);
		notifyDataSetChanged();
	}*/
	@Override
	public long getItemId(int position) {
		return position;
	}
	public void appendToList(List<Comment> lists) {

		if (lists == null) {
			return;
		}
		commentList.addAll(lists);
		notifyDataSetChanged();
	}
		public void clear() {

			commentList.clear();  //  先清空
			notifyDataSetChanged();
	}
	
	public int appendData(List<Comment> addList,boolean refesh) {
		int result =0;
		if (addList == null || addList.size()==0) {
			result = commentList.size()/10;
			if(commentList.size()%10!=0){
				result++;
			}
			return result;
		}
		// 首次加载
		if(commentList.size()==0){
			commentList.addAll(addList);//在加载新的
			notifyDataSetChanged();
			return 1; //当前是第一页
		}
		// 有新的数据
		if(!commentList.get(0).getId().equals(addList.get(0).getId())){
			
			if(refesh){
				commentList.clear();  //  先清空
				commentList.addAll(addList);//在加载新的
				notifyDataSetChanged();
				return 1;
			}else{
				commentList.addAll(addList);//直接追加
				notifyDataSetChanged();
				result = commentList.size()/10;
				if(commentList.size()%10!=0){
					result++;
				}
				return result;
			}
		}
		// 没有新的数据
		result = commentList.size()/10;
		if(commentList.size()%10!=0){
			result++;
		}
		return result;
	
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
		
		convertView = LayoutInflater.from(context).inflate(mResource,parent, false);
		holder = new ViewHolder();
		holder.itemName = (TextView) convertView.findViewById(R.id.commenter_name);
		holder.itemTime = (TextView) convertView.findViewById(R.id.comment_time);
		holder.itemContent = (TextView) convertView.findViewById(R.id.comment_content );
		// 设置数据到view
		Comment comment = commentList.get(position);
		holder.itemName.setText(comment.getAuthorName());
		holder.itemTime.setText(TimeUtil.dateToString(comment.getPostTime(), TimeUtil.FORMAT_DATE));
		holder.itemContent.setText(commentList.get(position).getContent());
			
		return convertView;
	}

}
