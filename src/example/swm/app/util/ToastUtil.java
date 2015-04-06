package example.swm.app.util;

import static com.devspark.appmsg.AppMsg.LENGTH_SHORT;
import android.app.Activity;
import com.devspark.appmsg.AppMsg;

public class ToastUtil {

    public static void showAppMsg(Activity context,String msg ,int color) {
    	final AppMsg.Style style = new AppMsg.Style(LENGTH_SHORT, color);
    	AppMsg appMsg = AppMsg.makeText(context, msg, style);
    	appMsg.setPriority(AppMsg.PRIORITY_HIGH);
    	appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    	appMsg.show();
    }
    public static  void showSuccessMsg(Activity context,int msg_res) {
    	AppMsg.Style style = new AppMsg.Style(LENGTH_SHORT,example.swm.app.R.color.success);
    	String msg = context.getResources().getString(msg_res);
    	AppMsg appMsg = AppMsg.makeText(context, msg, style);
    	appMsg.setPriority(AppMsg.PRIORITY_HIGH);
    	appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    	appMsg.show();
    }

    public static  void showErrorMsg(Activity context,int msg_res) {
    	
    	AppMsg.Style style = new AppMsg.Style(LENGTH_SHORT,example.swm.app.R.color.error);
    	String msg = context.getResources().getString(msg_res);
    	AppMsg appMsg = AppMsg.makeText(context, msg, style);
    	appMsg.setPriority(AppMsg.PRIORITY_HIGH);
    	appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    	appMsg.show();
    }
    
    public static  void showInfoMsg(Activity context,int msg_res) {
    	
    	AppMsg.Style style = new AppMsg.Style(LENGTH_SHORT,example.swm.app.R.color.info);
    	String msg = context.getResources().getString(msg_res);
    	AppMsg appMsg = AppMsg.makeText(context, msg, style);
    	appMsg.setPriority(AppMsg.PRIORITY_HIGH);
    	appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    	appMsg.show();
    }
}
