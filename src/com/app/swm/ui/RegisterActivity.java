package com.app.swm.ui;

import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.swm.config.Config;
import com.app.swm.http.TwitterRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.app.swm.R;
public class RegisterActivity extends Activity implements OnClickListener{
	private EditText firstName;
	private EditText lastName;
	private EditText Email;
	private EditText passWord;
	//private EditText university;
	private int university;
	private EditText major;
	//private Button btnRegister;
	//private Button btnRegisterCancel;
	private TextView tvSignUp;
	private Spinner universitySpiner;
	private ArrayAdapter<String> adapter = null;// 声明一个ArrayAdapter来适配年龄
	private List<CharSequence> age_data = null;// 声明一个放置年龄数据的List  
	private View back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findID();
		initView();
		initEvent();
	}

	private void findID() {
		// TODO Auto-generated method stub
		firstName=(EditText) findViewById(R.id.user_firstname);
		lastName=(EditText) findViewById(R.id.user_lastname);
		Email=(EditText) findViewById(R.id.user_email);
		passWord=(EditText) findViewById(R.id.user_password);
		//university=(EditText) findViewById(R.id.user_university);
		universitySpiner=(Spinner) findViewById(R.id.university_spinner);
		major=(EditText) findViewById(R.id.user_major);
		//btnRegister=(Button) findViewById(R.id.register_btn);
		//btnRegisterCancel=(Button) findViewById(R.id.register_cancel_btn);
		back=findViewById(R.id.register_back_layout);
		tvSignUp=(TextView) findViewById(R.id.tv_signup);
		tvSignUp.setOnClickListener(this);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		   //universitySpiner.setPrompt("Select University");// 设置Prompt  
	       String[] universities=new String[]{"ZZULI","Napier"};
	        adapter = new ArrayAdapter<String>(this,  
	                android.R.layout.simple_spinner_item, universities);// 实例化ArrayAdapter  
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置列表项显示风格  
	        universitySpiner.setAdapter(adapter);// 设置显示信息 
		
	}
	private void initEvent() {
		// TODO Auto-generated method stub
		//btnRegister.setOnClickListener(this);
		//btnRegisterCancel.setOnClickListener(this);
		universitySpiner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				university=arg2;
				System.out.println("University==="+university);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		back.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		/*case R.id.register_btn:
			Register();
			break;
		case R.id.register_cancel_btn:
			finish();
			
			break;*/
		case R.id.register_back_layout:
			finish();
			
			break;
		case R.id.tv_signup:
			Register();
			break;

		default:
			break;
		}
	}

	private void Register() {
		// TODO Auto-generated method stub
		String uFirstName=firstName.getText().toString();
		String uLastName=lastName.getText().toString();
		String uPassword=passWord.getText().toString();
		String uEmail=Email.getText().toString();
		int uUniversity=university;
		String uMajor=major.getText().toString();
		

		// TODO Auto-generated method stub}


		RequestParams params=new RequestParams();
		params.put("fullname", uFirstName+uLastName);
		params.put("password", uPassword);
		params.put("email", uEmail);
		params.put("university", uUniversity);
		params.put("major", uMajor);
		TwitterRestClient.get(Config.AC_USER_REGISTER, params, 
				new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String response = new String(arg2);
				System.out.println("register success+"+response);
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("register failed");
				
			}
		});
	
		
	}

}
