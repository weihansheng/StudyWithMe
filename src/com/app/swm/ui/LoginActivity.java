package com.app.swm.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.entity.User;
import com.app.swm.fragment.LeftDrawerFragment;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.util.AESCipher;
import com.app.swm.util.PixelUtil;
import com.app.swm.util.SharedPreferencesUtil;
import com.app.swm.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.app.swm.R;

public class LoginActivity extends Activity {
	public static LoginActivity mactivity;
	private EditText user_email;
	private EditText user_psw;
	private View login_back;
	private Button loginBtn;
	private View losspsw;
	private View losspsw_underline;
	private LinearLayout signup_layout;
	private View signup;
	private View signup_underline;
	private View cancel;
	private View cansel_underline;
	
	InputMethodManager inputMethodManager;
	boolean isLogin = false;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		mactivity = this;
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		MyApplication.getInstance().addActivity(this); //把Activity添加到栈中

		findById();
		setOnclick();
		initView();
	}

	private void findById() {
		// TODO Auto-generated method stub
		user_email = (EditText) findViewById(R.id.user_email);
		user_psw = (EditText) findViewById(R.id.user_password);
		losspsw = findViewById(R.id.loss_password);
		losspsw_underline=findViewById(R.id.loss_password_underline);
		signup=findViewById(R.id.signup);
		signup_underline=findViewById(R.id.signup_underline);
		signup_layout=(LinearLayout) findViewById(R.id.signup_layout);
		loginBtn = (Button) findViewById(R.id.login_btn);
		login_back=findViewById(R.id.login_back_layout);
		cansel_underline=findViewById(R.id.cancle_underline);

	}

	private void setOnclick() {
		// TODO Auto-generated method stub
		clickListener myListener = new clickListener();
		losspsw.setOnClickListener(myListener);
		signup_layout.setOnClickListener(myListener);
		loginBtn.setOnClickListener(myListener);
		login_back.setOnClickListener(myListener);
		

	}

	private void initView() {
		// TODO Auto-generated method stub
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		losspsw.measure(w, h); 
		login_back.measure(w, h);
		signup.measure(w, h);
		int losspsw_width=losspsw.getMeasuredWidth();
		int sign_width=signup.getMeasuredWidth();
		int cancel_width=login_back.getMeasuredWidth();
		losspsw_underline.setLayoutParams(new  LinearLayout.LayoutParams(losspsw_width, PixelUtil.dp2px(1)));
		cansel_underline.setLayoutParams(new  LinearLayout.LayoutParams(cancel_width, PixelUtil.dp2px(1)));
		signup_underline.setLayoutParams(new  LinearLayout.LayoutParams(sign_width, PixelUtil.dp2px(1)));
		String email = SharedPreferencesUtil.get(LoginActivity.this, "email");
		String password = SharedPreferencesUtil.get(LoginActivity.this, "password");
		user_email.setText(email);
		//user_psw.setText(password);
		
		

	}

	class clickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.loss_password: 
				findPsw();
				break;
			case R.id.login_back_layout:
				finish();
				break;
			case R.id.login_btn:
				loginBtn.setText(R.string.login_ing);
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				//login();
				break;
			case R.id.signup_layout:
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
			

		}
	}

	private void findPsw(){
		
		 Intent intent = new Intent();        
         intent.setAction("android.intent.action.VIEW");    
         Uri content_url = Uri.parse(Config.BASE_URL+Config.AC_FINDPSW);   
         intent.setData(content_url);  
         //startActivity(intent);
         ToastUtil.showErrorMsg(LoginActivity.this, R.string.implemeting);
	}
	public void login() {
		// TODO Auto-generated method stub}


		if (TextUtils.isEmpty(user_email.getText())|| TextUtils.isEmpty(user_psw.getText())) {
			
			loginBtn.setText(R.string.login);
			ToastUtil.showErrorMsg(LoginActivity.this, R.string.error_account_pasword_null);
			
		} else {
			String useraccount = user_email.getText().toString();
			final String userpsw = user_psw.getText().toString();
			TwitterRestClient.keepCookie();
			RequestParams params = new RequestParams();
			params.put("key", Config.LOGIN_KEY);
			params.put("email",useraccount);
			params.put("password",userpsw);
			TwitterRestClient.get(Config.AC_USER_LOGIN, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					System.out.println("failure");
					ToastUtil.showErrorMsg(LoginActivity.this, R.string.error_account_pasword);
					loginBtn.setText(R.string.login);
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] data) {
					//加载成功
					
					Gson gson = new Gson();
					String response = new String(data);
					System.out.println("sucess----"+response);
					// 失败返回 "fail"  长度为7
					if(response!=null&& response.length()>20){
						user  = gson.fromJson( response, new TypeToken<User>(){}.getType());
						SharedPreferencesUtil.add(LoginActivity.this, "email",user.getEmail());
						String password = AESCipher.encrypt(AESCipher.key, userpsw);
						SharedPreferencesUtil.add(LoginActivity.this, "password",password);
						MyApplication.user = user;
						LeftDrawerFragment.setInfo();
						Intent intent=new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
					else{
						ToastUtil.showErrorMsg(LoginActivity.this, R.string.error_account_pasword);
						loginBtn.setText(R.string.login);
					}
				}});
		}
	}



}
