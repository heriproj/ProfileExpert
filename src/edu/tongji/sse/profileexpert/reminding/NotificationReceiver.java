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
		int open_type = intent.getIntExtra(AlarmItem.OPEN_TYPE_KEY, -1);
		if(open_type == -1)
			return;
		
		//���������Ƿ�������֪ͨ����
		String content = "";
		if(open_type == AlarmItem.OPEN_TYPE_BEGIN)
		{
			content = "���л���ָ��ģʽ,�����������";
		}
		else
		{
			content = "���л��ص�ԭģʽ,�����������";
		}
		
		//����֪ͨ
		NotificationUtil.sendNotify(context, "ģʽ�л�Ԥ��", content,
				Notification.DEFAULT_VIBRATE);
		
		MainActivity.rm.notificationHappened();
	}
}


