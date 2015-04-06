package example.swm.app.receiver;

import example.swm.app.util.LoginUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LoginReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("login")) {
			LoginUtil.Login(context);
		} 
	}
}