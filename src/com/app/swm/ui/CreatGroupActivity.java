package com.app.swm.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.swm.R;
import com.app.swm.config.Config;
import com.app.swm.config.MyApplication;
import com.app.swm.http.TwitterRestClient;
import com.app.swm.util.TimeUtil;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class CreatGroupActivity extends FragmentActivity implements OnClickListener,
		OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	private Spinner spSubject;
	private Spinner spRoom;
	private EditText etGroupName;
	private EditText etDiscription;
	private EditText etPeopleNum;
	private Button btn_date;
	private Button btn_time;
	private String selectRoom;
	private String selectSubject;
	private Date date;
	private String startDate;
	private String startTime;
	private TextView tvPost;
	private TextView groupDate;
	private TextView groupTime;
	private ArrayAdapter<String> adapterRoom = null;// 声明一个ArrayAdapter来适配教室
	private ArrayAdapter<String> adapterSubject = null;// 声明一个ArrayAdapter来适配学科
	private View back;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	final Calendar calendar = Calendar.getInstance();

	final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
			this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), true);
	final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
			this, calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), false, false);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addgroup);
		findID();
		initView();
		initEvent();
	}

	private void findID() {
		// TODO Auto-generated method stub
		btn_date = (Button) findViewById(R.id.btn_select_date);
		btn_time = (Button) findViewById(R.id.btn_select_time);
		etDiscription = (EditText) findViewById(R.id.add_group_description);
		etGroupName = (EditText) findViewById(R.id.add_group_name);
		etPeopleNum = (EditText) findViewById(R.id.add_group_peopleNum);
		spRoom = (Spinner) findViewById(R.id.add_group_room_spinner);
		spSubject = (Spinner) findViewById(R.id.add_group_subject_spinner);
		tvPost=(TextView) findViewById(R.id.add_group_post);
		groupDate=(TextView) findViewById(R.id.group_date);
		groupTime=(TextView) findViewById(R.id.group_time);
		back = findViewById(R.id.add_group_back_layout);

	}

	private void initView() {
		// TODO Auto-generated method stub
		// universitySpiner.setPrompt("Select University");// 设置Prompt
		String[] subjects = new String[] { "Computer", "Math", "English",
				"Games", "Sports" };
		adapterSubject = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subjects);// 实例化ArrayAdapter
		adapterSubject
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置列表项显示风格
		spSubject.setAdapter(adapterSubject);// 设置显示信息
		String[] rooms = new String[] { "A11", "B12", "C01", "D13", "E22" };
		adapterRoom = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, rooms);// 实例化ArrayAdapter
		adapterRoom
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置列表项显示风格
		spRoom.setAdapter(adapterRoom);// 设置显示信息

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		// btnRegister.setOnClickListener(this);
		// btnRegisterCancel.setOnClickListener(this);
		btn_date.setOnClickListener(this);
		btn_time.setOnClickListener(this);
		tvPost.setOnClickListener(this);
		spRoom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// university=arg2;
				selectSubject=arg0.getItemAtPosition(arg2).toString();
				System.out.println("Subject===" + selectSubject);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spRoom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				selectRoom=arg0.getItemAtPosition(arg2).toString();
				System.out.println("Room===" + selectRoom);
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

		case R.id.btn_select_date:
			datePickerDialog.setVibrate(true);
			datePickerDialog.setYearRange(1985, 2037);
			datePickerDialog.setCloseOnSingleTapDay(true);
			datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
			break;
		case R.id.btn_select_time:
			timePickerDialog.setVibrate(true);
			timePickerDialog
					.setCloseOnSingleTapMinute(true);
			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);

			break;
		case R.id.add_group_back_layout:
			finish();

			break;
		case R.id.add_group_post:
			CreateGroup();
			break;

		default:
			break;
		}
	}

	private void CreateGroup() {
		// TODO Auto-generated method stub
		String description = etDiscription.getText().toString();
		String groupName = etGroupName.getText().toString();
		String peopleNum = etPeopleNum.getText().toString();

		// TODO Auto-generated method stub}

		RequestParams params = new RequestParams();
		params.put("description", description);
		params.put("groupname", groupName);
		params.put("peoplenum", peopleNum);
		params.put("room", selectRoom);
		params.put("subject", selectSubject);
		params.put("startdate", startDate);
		params.put("starttime", startTime);
		params.put("adminid", MyApplication.user.getStudentID());
		System.out.println("adminID=="+MyApplication.user.getStudentID() );
		TwitterRestClient.get(Config.AC_CREAT_GROUP, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String response = new String(arg2);
						System.out.println("creat group success+" + response);

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println("creat group failed");

					}
				});

	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
	    date=new Date();
	    date.setDate(day);
	    date.setMonth(month);
	    date.setYear(year-1900);
		startDate=TimeUtil.dateToString(date, TimeUtil.FORMAT_DATE);
		System.out.println("select date==="+startDate);
		System.out.println("select year==="+date.getYear());
		
		groupDate.setText(startDate);
		Toast.makeText(CreatGroupActivity.this,
				"new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		//startTime=new Time(timezone);
		date.setHours(hourOfDay);
		date.setMinutes(minute);
		startTime=TimeUtil.dateToString(date, TimeUtil.FORMAT_TIME);
		System.out.println("select date==="+startTime);
		groupTime.setText(startTime);
		Toast.makeText(CreatGroupActivity.this,
				"new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG)
				.show();
	}

}
