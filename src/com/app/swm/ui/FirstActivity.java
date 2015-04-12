package com.app.swm.ui;

import com.app.swm.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.app.swm.R;

public class FirstActivity extends Activity implements OnClickListener {
	private Button btnLogin;
	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frist);
		findId();
		initView();
		initEvent();
	}

	private void findId() {
		// TODO Auto-generated method stub
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnSignUp = (Button) findViewById(R.id.btn_signup);

	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {

		case R.id.btn_login:
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_signup:
			//ToastUtil.showErrorMsg(this, R.string.implemeting);
			intent.setClass(getApplicationContext(), RegisterActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		

	}
}
