package example.swm.app.ui;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import example.swm.app.R;
import example.swm.app.config.Config;
import example.swm.app.config.MyApplication;
import example.swm.app.entity.User;
import example.swm.app.fragment.LeftDrawerFragment;
import example.swm.app.http.TwitterRestClient;
import example.swm.app.util.AESCipher;
import example.swm.app.util.PixelUtil;
import example.swm.app.util.SharedPreferencesUtil;
import example.swm.app.util.ToastUtil;

public class LoginActivity extends Activity {
	public static LoginActivity mactivity;
	private EditText user_name;
	private EditText user_psw;
	private View login_back;
	private Button loginBtn;
	private View losspsw;
	private View losspsw_underline;
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

		findById();
		setOnclick();
		initView();
	}

	private void findById() {
		// TODO Auto-generated method stub
		user_name = (EditText) findViewById(R.id.user_email);
		user_psw = (EditText) findViewById(R.id.user_password);
		losspsw = findViewById(R.id.loss_password);
		losspsw_underline=findViewById(R.id.loss_password_underline);
		loginBtn = (Button) findViewById(R.id.login_btn);
		login_back=findViewById(R.id.login_back_layout);
		cansel_underline=findViewById(R.id.cancle_underline);

	}

	private void setOnclick() {
		// TODO Auto-generated method stub
		clickListener myListener = new clickListener();
		losspsw.setOnClickListener(myListener);
		loginBtn.setOnClickListener(myListener);
		login_back.setOnClickListener(myListener);
		

	}

	private void initView() {
		// TODO Auto-generated method stub
		int losspsw_w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int losspsw_h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		losspsw.measure(losspsw_w, losspsw_h); 
		login_back.measure(losspsw_w, losspsw_h);
		int losspsw_width=losspsw.getMeasuredWidth();
		int cancel_width=login_back.getMeasuredWidth();
		losspsw_underline.setLayoutParams(new  LinearLayout.LayoutParams(losspsw_width, PixelUtil.dp2px(1)));
		cansel_underline.setLayoutParams(new  LinearLayout.LayoutParams(cancel_width, PixelUtil.dp2px(1)));
		

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
				//login();
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


		if (TextUtils.isEmpty(user_name.getText())|| TextUtils.isEmpty(user_psw.getText())) {
			
			loginBtn.setText(R.string.login);
			ToastUtil.showErrorMsg(LoginActivity.this, R.string.error_account_pasword_null);
			
		} else {
			String useraccount = user_name.getText().toString();
			final String userpsw = user_psw.getText().toString();
			TwitterRestClient.keepCookie();
			RequestParams params = new RequestParams();
			params.put("key", Config.LOGIN_KEY);
			params.put("id",useraccount);
			params.put("password",userpsw);
			TwitterRestClient.post(Config.AC_USER_LOGIN, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					ToastUtil.showErrorMsg(LoginActivity.this, R.string.error_account_pasword);
					loginBtn.setText(R.string.login);
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] data) {
					//加载成功
					Gson gson = new Gson();
					String response = new String(data);
					// 失败返回 "fail"  长度为7
					if(response!=null&& response.length()>8){
						user  = gson.fromJson( response, new TypeToken<User>(){}.getType());
						SharedPreferencesUtil.add(LoginActivity.this, "id",user.getId());
						String password = AESCipher.encrypt(AESCipher.key, userpsw);
						SharedPreferencesUtil.add(LoginActivity.this, "password",password);
						MyApplication.user = user;
						LeftDrawerFragment.setInfo();
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
