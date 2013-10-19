package edu.tongji.sse.profileexpert.reminding;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.main.MainActivity;
import edu.tongji.sse.profileexpert.provider.RoutineTable;
import edu.tongji.sse.profileexpert.provider.TempMatterTable;
import edu.tongji.sse.profileexpert.util.ContentResolverUtil;
import edu.tongji.sse.profileexpert.util.MyConstant;
import edu.tongji.sse.profileexpert.util.NotificationUtil;

public class NotificationReceiver extends BroadcastReceiver
{
	private Calendar c = Calendar.getInstance();
	private boolean isCurrentItem = true;
	public void onReceive(Context context, Intent intent)
	{
		RemindingItem ri = MainActivity.rm.getCurrentItem();

		if(ri.isReminded())
		{
			int advanced_time = Integer.parseInt(
					MainActivity.preference.getString("first_reminding_time", "3"));

			if(advanced_time != 30)
				advanced_time *= 60;
			c.setTimeInMillis(ri.getEndTime());
			c.add(Calendar.SECOND, -advanced_time);
			if(c.getTimeInMillis() >= System.currentTimeMillis())
			{
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
				
				c.setTimeInMillis(System.currentTimeMillis());
				if(c.get(Calendar.HOUR_OF_DAY) != hour
						|| c.get(Calendar.MINUTE) != minute)
				{
					ri = MainActivity.rm.getNextItem();
					isCurrentItem = false;
				}
			}
		}
		
		String reminding_time = MainActivity.preference.getString("first_reminding_time", "3");
		String time = MyConstant.getRemindingTimeText(Integer.parseInt(reminding_time));

		Cursor cursor = getCursor(context, ri.getType(),ri.getId());
		
		String title = null;
		if(cursor.moveToFirst())
		{
			long profileId = cursor.getLong(cursor.getColumnIndex(TempMatterTable.PROFILE_ID));
			title = ContentResolverUtil.getProfileTitle(context, profileId);
		}

		//���������Ƿ�������֪ͨ����
		String content = "";
		if(!ri.isReminded())
		{

			content = context.getString(R.string.notification_text_1) + time
					+ context.getString(R.string.notification_text_2) + ":" + title
					+ "," + context.getString(R.string.notification_text_3);
		}
		else
		{
			content = context.getString(R.string.notification_text_1) + time
					+ context.getString(R.string.notification_text_4)+","+
					context.getString(R.string.notification_text_3);
		}

		//����֪ͨ
		NotificationUtil.sendNotify(context, context.getString(R.string.notification_text_5)
				, content, ri);

		MainActivity.rm.notificationHappened(isCurrentItem);
	}

	private Cursor getCursor(Context ctx,int type,long id)
	{
		if(type==AlarmItem.MATTER_TYPE_TEMP_MATTER)
		{
			return ctx.getContentResolver().query(
					TempMatterTable.CONTENT_URI,
					null,
					TempMatterTable._ID + "=?",
					new String[]{""+id},
					null);
		}
		else
		{
			return ctx.getContentResolver().query(
					RoutineTable.CONTENT_URI,
					null,
					RoutineTable._ID + "=?",
					new String[]{""+id},
					null);
		}
	}
}


