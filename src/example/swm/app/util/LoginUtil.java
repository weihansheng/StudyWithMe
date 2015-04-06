package example.swm.app.util;

import java.util.Calendar;


import org.apache.http.Header;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import example.swm.app.config.Config;
import example.swm.app.config.MyApplication;
import example.swm.app.entity.User;
import example.swm.app.fragment.LeftDrawerFragment;
import example.swm.app.http.TwitterRestClient;
import example.swm.app.receiver.LoginReceiver;

/**   
 * @Description  自动登录工具类
 * @author MR.Wang  
 * @date 2014-8-10 
 * @version V1.0   
 */
public class LoginUtil {
	
	public static void Login(Context context){
		String id = SharedPreferencesUtil.get(context, "id");
		String password = SharedPreferencesUtil.get(context, "password");
		if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(password)){
			password = AESCipher.decrypt(AESCipher.key, password);
			RequestParams params = new RequestParams();
			params.put("key", Config.LOGIN_KEY);
			params.put("id",id);
			params.put("password",password);
			
			TwitterRestClient.keepCookie();
			TwitterRestClient.post(Config.AC_USER_LOGIN, params, new AsyncHttpResponseHandler(){
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] data) {
					//加载成功
					Gson gson = new Gson();
					String response = new String(data);
					if(!response.isEmpty() && response.length()>8){
						User user  = gson.fromJson( response, new TypeToken<User>(){}.getType());
						MyApplication.user = user;
						LeftDrawerFragment.setInfo();
					}
				}});
		}

	}
	// 退出
	public static void LogOut(Context context){
		
		cancel(context);
		RequestParams params = new RequestParams();
		TwitterRestClient.post(Config.AC_USER_LOGOUT, params, new AsyncHttpResponseHandler(){
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] resultByte) {

			}});
	}
	
	/**
	 * @Description  添加闹钟
	 * @param context
	 */
	public static void post(Context  context){

		Intent intent =new Intent(context, LoginReceiver.class);
		intent.setAction("login");
		PendingIntent sender=PendingIntent.getBroadcast(context, 0, intent, 0);
		//设定一个五分钟以后 自动从服务器获取新的数据
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		AlarmManager alarm  = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		//alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),sender);
		long firstime=SystemClock.elapsedRealtime();  
		alarm.setRepeating(AlarmManager.RTC_WAKEUP,firstime, 5*1000*60-100, sender); 

	}
	/**
	 * @Description  取消闹钟
	 * @param context
	 */
	public static void cancel(Context  context){
		MyApplication.user=null;
		Config.IS_SEND=false;
		AlarmManager alarm  = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent =new Intent(context, LoginReceiver.class);
		intent.setAction("login");
        // 创建PendingIntent对象
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarm.cancel(pendingIntent);
		
	}
	
}
