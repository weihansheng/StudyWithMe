package example.swm.app.ui;

import example.swm.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GroupDetailsActivity extends Activity implements OnClickListener {
	private TextView tvAdmin;
	private TextView tvRoom;
	private TextView tvTime;
	private TextView tvDate;
	private TextView tvNum;
	private TextView tvApplicants;
	private TextView tvDescription;
	private Button btnMembers;
	private Button btnJoin;
	private View back;
	private TextView tv_title;
	private boolean joined = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groupdetails);
		findID();
		initView();
		initEvent();
	}

	private void findID() {
		// TODO Auto-generated method stub
		tvAdmin = (TextView) findViewById(R.id.group_admin);
		tvRoom = (TextView) findViewById(R.id.group_room);
		tvNum = (TextView) findViewById(R.id.group_people_num);
		tvDate = (TextView) findViewById(R.id.group_date);
		tvTime = (TextView) findViewById(R.id.group_time);
		tvDescription = (TextView) findViewById(R.id.group_description);
		tvApplicants = (TextView) findViewById(R.id.group_applicants);
		btnJoin = (Button) findViewById(R.id.group_btn_join);
		btnMembers = (Button) findViewById(R.id.group_btn_members);
		tv_title=(TextView) findViewById(R.id.back_title);
		tv_title.setText("Details");
		back=findViewById(R.id.content_back_layout);

	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnJoin.setOnClickListener(this);
		btnMembers.setOnClickListener(this);
		back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.group_btn_join:
			addMembers();
			break;
		case R.id.group_btn_members:
			Members();
			break;
		case R.id.content_back_layout:
			finish();
			break;

		default:
			break;
		}

	}

	private void Members() {
		// TODO Auto-generated method stub

	}

	private void addMembers() {
		// TODO Auto-generated method stub
		// 需要判断是否已参加过该小组
		if (joined) {
			Toast.makeText(this, "Have joined!", 0).show();
		} else {
			int groupNum = Integer.parseInt(tvNum.getText().toString());
			tvNum.setText(String.valueOf(groupNum + 1));
			joined = true;
		}

	}

}
