package example.swm.app.fragment;

import cn.sharesdk.framework.m;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import example.swm.app.R;
import example.swm.app.ui.ScheduleDetailActivity;

/**
 * @Description 收藏 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentSchedule extends Fragment {

	private View rootView;
	private Context mContext;
	private int colors[] = { Color.rgb(0xee, 0xff, 0xff),
			Color.rgb(0xf0, 0x96, 0x09), Color.rgb(0x8c, 0xbf, 0x26),
			Color.rgb(0x00, 0xab, 0xa9), Color.rgb(0x99, 0x6c, 0x33),
			Color.rgb(0x3b, 0x92, 0xbc), Color.rgb(0xd5, 0x4d, 0x34),
			Color.rgb(0xcc, 0xcc, 0xcc) };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_schedule, container,
				false);
		mContext = getActivity();
		findID();
		initView(rootView);
		initEvent();
		return rootView;

	}

	private void findID() {
		// TODO Auto-generated method stub

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		// 分别表示周一到周日
		LinearLayout ll1 = (LinearLayout) view.findViewById(R.id.ll1);
		LinearLayout ll2 = (LinearLayout) view.findViewById(R.id.ll2);
		LinearLayout ll3 = (LinearLayout) view.findViewById(R.id.ll3);
		LinearLayout ll4 = (LinearLayout) view.findViewById(R.id.ll4);
		LinearLayout ll5 = (LinearLayout) view.findViewById(R.id.ll5);
		LinearLayout ll6 = (LinearLayout) view.findViewById(R.id.ll6);
		LinearLayout ll7 = (LinearLayout) view.findViewById(R.id.ll7);
		// 每天的课程设置
		setClass(ll1, "", "", "", "", 2, 0);
		setClass(ll1, "windows编程实践", "教三503", "1-9周，每一周", "9:50-11:25", 2,
				1);
		setClass(ll1, "Java", "教三504", "1-15周，每一周", "14:55-17:25", 2, 6);
		setClass(ll1, "概率论与数理统计", "教三304", "1-15周，每一周", "14:55-17:25", 2, 2);
		setNoClass(ll1, 2, 0);

		setClass(ll2, "大学英语", "教三302", "1-18周，每一周", "8:00-9:35", 2, 3);
		setClass(ll2, "计算机组织体系与结构", "教三204", "1-15，每一周", "9:50-12:15", 2, 5);
		setNoClass(ll2, 2, 0);
		setClass(ll2, "团队激励和沟通", "教三204", "1-9周，每一周", "15:45-17:25", 2, 6);
		setClass(ll2, "C++", "教三204", "1-9周，每一周", "15:45-17:25", 2, 4);

		setNoClass(ll3, 2, 0);
		setClass(ll3, "中国近现代史纲要", "教三328", "1-9周，每一周", "9:50-12:15", 2, 1);
		setNoClass(ll3, 2, 0);
		setClass(ll3, "体育（网球）", "操场", "6-18周，每一周", "14:00-15:40", 2, 2);
		setNoClass(ll3, 2, 0);

		setClass(ll4, "计算机组织体系与结构", "教三204", "1-15，每一周", "8:00-9:35", 2, 5);
		setClass(ll4, "数据结构与算法", "教三304", "1-18周，每一周", "9:50-12:15", 2, 4);
		setNoClass(ll4, 2, 0);
		setClass(ll4, "面向对象程序设计（JAVA）", "教三103", "1-18周，每一周", "14:00-16:30",
				2, 5);
		setNoClass(ll4, 2, 0);

		setClass(ll5, "c#程序设计", "教三102", "1-9周，每一周", "8:00-9:35", 2, 6);
		setClass(ll5, "大学英语", "教三302", "1-18周，每一周", "9:50-11:25", 2, 3);
		setNoClass(ll5, 2, 0);
		setClass(ll5, "基础物理", "教三304", "1-18周，每一周", "14:00-16:30", 2, 1);
		setNoClass(ll5, 2, 0);

		setNoClass(ll6, 2, 0);

		setNoClass(ll7, 2, 0);

	}

	private void initEvent() {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置课程的方法
	 * 
	 * @param ll
	 * @param title
	 *            课程名称
	 * @param place
	 *            地点
	 * @param last
	 *            时间
	 * @param time
	 *            周次
	 * @param classes
	 *            节数
	 * @param color
	 *            背景色
	 */
	void setClass(LinearLayout ll, String title, String place, String last,
			String time, int classes, int color) {
		// LayoutParams lp=ll.getLayoutParams();
		// lp.height=LayoutParams.WRAP_CONTENT;
		// lp.width=LayoutParams.FILL_PARENT;
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.schedule_item, null);
		view.setMinimumHeight(dip2px(mContext, classes * 48));
		view.setBackgroundColor(colors[color]);
		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.place)).setText(place);
		// ((TextView)view.findViewById(R.id.last)).setText(last);
		// ((TextView)view.findViewById(R.id.time)).setText(time);
		// 为课程View设置点击的监听器
		view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(mContext);
		TextView blank2 = new TextView(mContext);
		blank1.setHeight(dip2px(mContext, classes));
		blank2.setHeight(dip2px(mContext, classes));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}

	/**
	 * 设置无课（空百）
	 * 
	 * @param ll
	 * @param classes
	 *            无课的节数（长度）
	 * @param color
	 */
	void setNoClass(LinearLayout ll, int classes, int color) {
		TextView blank = new TextView(mContext);
		if (color == 0)
			blank.setMinHeight(dip2px(mContext, classes * 50));
		blank.setBackgroundColor(colors[color]);
		ll.addView(blank);
	}

	// 点击课程的监听器
	class OnClickClassListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mIntent = new Intent(getActivity(),ScheduleDetailActivity.class);
			String title = (String)((TextView)v.findViewById(R.id.title)).getText();
			mIntent.putExtra("title", title);
			startActivity(mIntent);
			
			 /*String title; title = (String)
			 ((TextView)v.findViewById(R.id.title)).getText();
			 Toast.makeText(getActivity(), "你点击的是:" + title,
			 Toast.LENGTH_SHORT).show();*/
			 
		}
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
