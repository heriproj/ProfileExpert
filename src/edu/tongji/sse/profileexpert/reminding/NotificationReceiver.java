package edu.tongji.sse.profileexpert.reminding;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import edu.tongji.sse.profileexpert.main.MainActivity;
import edu.tongji.sse.profileexpert.util.NotificationUtil;

public class NotificationReceiver extends BroadcastReceiver
{
	public void onReceive(Context context, Intent intent)
	{
		RemindingItem currentItem = MainActivity.rm.getCurrentItem();
		
		//���������Ƿ�������֪ͨ����
		String content = "";
		if(currentItem.isHappened())
		{
			content = "���л��ص�ԭģʽ,�����������";
		}
		else
		{
			content = "���л���ָ��ģʽ,�����������";
		}
		
		//����֪ͨ
		NotificationUtil.sendNotify(context, "ģʽ�л�Ԥ��", content,
				Notification.DEFAULT_VIBRATE);
		
		RemindingManager.notificationHappened();
	}
}


