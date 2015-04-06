package example.swm.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import example.swm.app.R;
import example.swm.app.entity.Group;
import example.swm.app.util.TimeUtil;

public class GroupListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	DisplayImageOptions dOptions;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private List<Group> projectList = new ArrayList<Group>();
	private Group projectes;

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
		projectes = projectList.get(position);
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_group, null);
			mHolder.title = (TextView) convertView
					.findViewById(R.id.list_item_project_title);
			mHolder.description = (TextView) convertView
					.findViewById(R.id.list_item_project_description);
			mHolder.post_time = (TextView) convertView
					.findViewById(R.id.list_item_project_posttime);
			convertView.setTag(mHolder);  //不设置标记会报空指针
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.title.setText(projectes.getTitle().toString());
		mHolder.description.setText(projectes.getDescription().toString());
		mHolder.post_time.setText(TimeUtil.dateToString(projectes.getPostTime(),TimeUtil.FORMAT_MONTH_DAY_TIME_EN));

		return convertView;
	}

	private final class ViewHolder {
		public TextView title;
		public TextView description;
		public TextView post_time;

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
			result = projectList.size()/10;
			if(projectList.size()%10!=0){
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
				result = projectList.size()/10;
				if(projectList.size()%10!=0){
					result++;
				}
				return result;
			}
		}
		// 没有新的数据
		result = projectList.size()/10;
		if(projectList.size()%10!=0){
			result++;
		}
		return result;
	
	}

}
