package edu.tongji.sse.profileexpert.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.main.MainActivity;
import edu.tongji.sse.profileexpert.main.ShowEventActivity;
import edu.tongji.sse.profileexpert.reminding.RemindingItem;

public class NotificationUtil
{
	public static int NOTIFICATIONS_ID = R.layout.activity_main;
	private static NotificationManager notificationManager = null;
	
	//�õ�ϵͳ�����ӷ���
	private static NotificationManager getNotificationManager(Context ctx)
	{
		return (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	//����֪ͨ
	@SuppressWarnings("deprecation")
	public static void sendNotify(Context ctx, String title, String content, RemindingItem ri )
	{
		Notification notification = new Notification(R.drawable.ic_launcher, title,
				System.currentTimeMillis());

		Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
		appIntent.setClass(ctx, ShowEventActivity.class);
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        		| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        		| Intent.FLAG_ACTIVITY_CLEAR_TOP);//�ؼ���һ������������ģʽ
		appIntent.putExtra(RemindingItem.ID, ri.getId());
		appIntent.putExtra(RemindingItem.TYPE, ri.getType());
		
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,	appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		notification.setLatestEventInfo(ctx,title,content,contentIntent);
		
		//����֪ͨ�����뷢����ʽ
		boolean notification_silent =
				MainActivity.preference.getBoolean("notification_silent", true);
		
		notification.defaults = notification_silent?
				Notification.DEFAULT_VIBRATE : Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		notificationManager = getNotificationManager(ctx);
		
		notificationManager.notify(NOTIFICATIONS_ID, notification);
	}
	
	public static void cancelAll(Context ctx)
	{
		getNotificationManager(ctx).cancelAll();
	}
}
