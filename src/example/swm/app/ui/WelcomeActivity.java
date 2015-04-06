package example.swm.app.ui;

import example.swm.app.config.Config;
import example.swm.app.util.LoginUtil;
import example.swm.app.util.SharedPreferencesUtil;
import example.swm.app.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
/**
 * @author miss
 * 
 */
public class WelcomeActivity extends Activity {
    
	public Context mContext;
	//程序是否使用过
	private boolean isUse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		mContext = WelcomeActivity.this;
		isUse = SharedPreferencesUtil.readIsFirstUse(mContext);
		if (!isUse) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					/**
                     * 
                     */
					Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
					startActivity(intent);
					finish();
				}
			}, Config.TIME_DELAY_GUIDE);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
                    /**
                     * 
                     */
					//openActivity(YixinMainActivity.class);
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			}, Config.TIME_DELAY_WELCOME);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
