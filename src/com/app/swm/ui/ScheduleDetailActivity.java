package com.app.swm.ui;

import com.app.swm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ScheduleDetailActivity extends Activity implements OnClickListener{
	private TextView tv_title;
	private View back;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.schedule_details);
			findID();
			initView();
			initEvent();
		}

	private void findID() {
		// TODO Auto-generated method stub
		tv_title=(TextView) findViewById(R.id.back_title);
		back=findViewById(R.id.content_back_layout);
		back.setOnClickListener(this);
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		String title=intent.getExtras().getString("title");
		tv_title.setText(title);
		
		
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		
		
	}

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
