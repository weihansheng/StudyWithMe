package com.app.swm.fragment;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swm.R;
import com.app.swm.widget.PagerSlidingTabStrip;

/**
 * @Description MyGroups Fragment
 * 
 * @author MR.Johan
 * 
 * 
 */

@SuppressLint("ValidFragment")
public class FragmentMyGroup extends Fragment {
	//
	private Context context;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = inflater.getContext();
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_mygroups, container,
					false);
			tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
			pager = (ViewPager) rootView.findViewById(R.id.pagers_signup);
			tabs.setShouldExpand(true);
			tabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
			tabs.setIndicatorColor(Color.parseColor("#1ba9b7")); // 下划线颜色
			//tabs.setTextColor(Color.parseColor("#1ba9b7"));
			tabs.setIndicatorHeight(9);

			Handler handler = new Handler();
			handler.postDelayed(run, 380);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			initView(rootView);
		}
	};

	private void initView(View rootView) {
		adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);

	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = {
				getResources().getString(R.string.my_created),
				getResources().getString(R.string.my_joined) };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {

			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {

			if (position == 0) {
				return new FragmentMyCreated();
			} else {
				return new FragmentMyJoined();
			}
		}

	}

	/*
	 * @Override public void onDestroyView() { // TODO Auto-generated method
	 * stub super.onDestroyView(); FragmentTransaction fragmentTransaction
	 * =getActivity().getSupportFragmentManager().beginTransaction();
	 * fragmentTransaction.remove(getTargetFragment()); }
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}
}
