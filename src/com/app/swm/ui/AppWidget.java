package com.app.swm.ui;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.app.swm.R;

public class AppWidget extends AppWidgetProvider 
{
    private final String Send = "Send"; 
    private final String Send2 = "Send2"; 
     
    /** 
     * 删除一个AppWidget时调用 
     * */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) 
    { 
    	Log.i("huahua", "AppWidget --> onDeleted");
    	Toast.makeText(context, "删除小部件", Toast.LENGTH_SHORT).show();
        super.onDeleted(context, appWidgetIds); 
    }
 
    /** 
     * 最后一个appWidget被删除时调用 
     * */
    @Override
    public void onDisabled(Context context) 
    { 
    	Log.i("huahua", "AppWidget --> onDisabled");
    	Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT).show();
        super.onDisabled(context); 
    }
 
    /** 
     * AppWidget的实例第一次被创建时调用 
     * */
    @Override
    public void onEnabled(Context context) 
    { 
    	Log.i("huahua", "AppWidget --> onEnabled");
    	Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show();
        super.onEnabled(context); 
    }
 
    /** 
     * 接受广播事件 
     * */
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Log.i("huahua", "AppWidget --> onReceive");
    	Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();
    	 if (intent.getAction().equals(Send)) 
         {           
    		 Log.i("huahua", "AppWidget --> 相应Btn1按钮");
         } 
    	 else if(intent.getAction().equals(Send2))
    	 {
    		 Log.i("huahua", "AppWidget --> 相应Btn2按钮");
    	 }
         super.onReceive(context, intent); 
    }
 
    /** 
     *到达指定的更新时间或者当用户向桌面添加AppWidget时被调用 
     *@param AppWidgetManager 顾名思义是AppWidget的管理器
     *@param appWidgetIds 桌面上 所有的widget都会被分配一个唯一的ID标识，那么这个数组就是他们的列表
     * */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
            int[] appWidgetIds) 
    {
    	Log.i("huahua", "AppWidget --> onUpdate");
    	Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show();
    	
    	//创建一个Intent对象 
        Intent intent1 = new Intent(Send); 
		Intent intent2 = new Intent(Send2);

		//设置pendingIntent的作用 
		PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0,intent1, 0);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, 0); 
        
        //小部件在Launcher的布局
        RemoteViews remoteViews  = new RemoteViews(context.getPackageName(),R.layout.appwidgetlayout); 
        
        //绑定事件 
        remoteViews.setOnClickPendingIntent(R.id.btnSend, pendingIntent1); 
        remoteViews.setOnClickPendingIntent(R.id.btnSend2, pendingIntent2); 
         
        //更新Appwidget 
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);       
    } 
     
}