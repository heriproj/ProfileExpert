package edu.tongji.sse.profileexpert.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.main.MainActivity;

public class NotificationUtil
{
	public static int NOTIFICATIONS_ID = R.layout.activity_main;
	private static NotificationManager notificationManager = null;
	
	//����֪ͨ
	@SuppressWarnings("deprecation")
	public static void sendNotify(Context ctx, String title, String content, int defaults)
	{
		Notification notification = new Notification(R.drawable.ic_launcher, title,
				System.currentTimeMillis());

		Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
		appIntent.setClass(ctx, MainActivity.class);
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        		| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        		| Intent.FLAG_ACTIVITY_CLEAR_TOP);//�ؼ���һ������������ģʽ
		
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,	appIntent, 0);

		notification.setLatestEventInfo(ctx,title,content,contentIntent);
		
		//����֪ͨ�����뷢����ʽ
		notification.defaults = defaults;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notificationManager.notify(NOTIFICATIONS_ID, notification);
	}
}
