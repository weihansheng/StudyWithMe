package com.app.swm.fragment;

import java.util.Random;

import com.app.swm.ui.AddGroupActivity;
import com.app.swm.ui.GroupListActivity;
import com.app.swm.ui.MainActivity;
import com.app.swm.widget.KeywordsFlow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.swm.R;

/**
 * 
 * @Description 首页 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午12:32:06
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment implements OnClickListener,OnTouchListener,OnGestureListener {

	// view
	public static View rootView;
	private Context context;
	private LinearLayout loadingLayout;
	private LinearLayout loadFaillayout;
	private Button btn_refresh;
	public String[] keywords = { "C++", "Java", "English", "Php", "Ruby",
			"Phython", "Chinese", "MacBook Pro", "SPY Mouse", "Thinkpad E40",
			"CSDN leak", "3D", "4743G", "mmShow", "iciba", "App", "Internet",
			"Time", "Chrome", "Safari", "Siri", "A5", "iPhone4S", "ME525",
			" M9", "S2500" };
	private KeywordsFlow keywordsFlow;
	private ImageView ivDeleteText;
	private EditText etSearch;
	private Button btnSearch;
	private ImageButton ibAddGroup;
	private String searchName;
	@SuppressWarnings("deprecation")
	private GestureDetector detector = new GestureDetector(this);
	private RelativeLayout wordflowLatout;
	// 限制最小移动像素
	private int FLING_MIN_DISTANCE = 110;
	private static final String TAG = "GestureDetector";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity();
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			findId();
			initView(rootView);
			initEvent();
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void findId() {
		// TODO Auto-generated method stub
		wordflowLatout=(RelativeLayout) rootView.findViewById(R.id.layout_wordflow);
		loadFaillayout = (LinearLayout) rootView.findViewById(R.id.view_load_fail);
		loadingLayout = (LinearLayout) rootView.findViewById(R.id.view_loading);
		loadFaillayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		btn_refresh = (Button) rootView.findViewById(R.id.bn_refresh);
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// loadNews(1, true);
				// loadNotice();
				// LoginUtil.Login(context);
			}
		});
		ivDeleteText = (ImageView) rootView.findViewById(R.id.ivDeleteText);
		etSearch = (EditText) rootView.findViewById(R.id.etSearch);
		btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

		keywordsFlow = (KeywordsFlow) rootView.findViewById(R.id.keywordsflow);
		keywordsFlow.setDuration(800l);
		KeyWordClick mClick = new KeyWordClick();
		keywordsFlow.setOnItemClickListener(mClick);
		// 添加
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		ibAddGroup=MainActivity.ib_add;
		ibAddGroup.setOnClickListener(this);

	}

	private void initView(View rootView2) {
		// TODO Auto-generated method stub
		keywordsFlow.rubKeywords();
		// keywordsFlow.rubAllViews();
		feedKeywordsFlow(keywordsFlow, keywords);
		ivDeleteText.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		searchName = etSearch.getText().toString();

		etSearch.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}
		});

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		wordflowLatout.setOnTouchListener(this);
		ivDeleteText.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		searchName = etSearch.getText().toString();

		etSearch.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}
		});

	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.ivDeleteText:
			etSearch.setText("");
			break;
		case R.id.ib_add:
			//System.out.println("dianji le ");
			intent.setClass(getActivity(), AddGroupActivity.class);
			startActivity(intent);
			break;
		case R.id.btnSearch:
			if (!searchName.equals(etSearch.getText().toString())) {
				searchName = etSearch.getText().toString();
				/*
				 * loadLayout.setVisibility(View.VISIBLE); adapter.clear();
				 * page=0; searchName= etSearch.getText().toString();
				 * loadSeek(1,true); InputMethodManager imm =
				 * (InputMethodManager
				 * )getActivity().getSystemService(Context.INPUT_METHOD_SERVICE
				 * );
				 * imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				 * .getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
				 */
			}
			break;

		default:
			break;
		}

	}

	public class KeyWordClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v instanceof TextView) {
				String keyword = ((TextView) v).getText().toString();
				System.out.println(keyword);
				Intent intent = new Intent();
				intent.setClass(getActivity(), GroupListActivity.class);
				//intent.setAction(Intent.ACTION_VIEW);
				//intent.addCategory(Intent.CATEGORY_DEFAULT);
				//intent.setData(Uri.parse("http://www.google.com.hk/#q=" +keyword));
				startActivity(intent);
			}
		}

	}

	/**
	 * 手势滑动时别调用
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
		if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE) {
			// 向左滑动
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		} else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE) {
			// 向右滑动
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		}
		return false;
	}

	/**
	 * 长按时被调用
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		Log.d(TAG, "触发长按回调");
	}

	/**
	 * 滚动时调用
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	/**
	 * 在按下动作时被调用
	 */
	@Override
	public boolean onDown(MotionEvent e) {
		Log.d(TAG, "按下回调");
		return false;
	}

	/**
	 * 按住时被调用
	 */
	@Override
	public void onShowPress(MotionEvent e) {
		Log.d(TAG, "按住不松回调");
	}

	/**
	 * 抬起时被调用
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.d(TAG, "触发抬起回调");
		return false;
	}

	/**
	 * 重写OnTouchListener的onTouch方法 此方法在触摸屏被触摸，即发生触摸事件（接触和抚摸两个事件）的时候被调用
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		detector.onTouchEvent(event);
		return true; //返回false时不能用
	}
}
