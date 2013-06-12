package edu.tongji.sse.profileexpert.reminding;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.main.MainActivity;
import edu.tongji.sse.profileexpert.provider.MyProfileTable;
import edu.tongji.sse.profileexpert.provider.RoutineTable;
import edu.tongji.sse.profileexpert.provider.TempMatterTable;
import edu.tongji.sse.profileexpert.util.NotificationUtil;
import edu.tongji.sse.profileexpert.util.ProfileUtil;

public class ChangeProfileReceiver extends BroadcastReceiver
{
	public void onReceive(Context context, Intent intent)
	{
		RemindingItem ri = MainActivity.rm.getCurrentItem();
		
		Cursor cursor = getCursor(context, ri.getType(),ri.getId());
		String title = null;
		if(cursor.moveToFirst())
		{
			long profileId = cursor.getLong(cursor.getColumnIndex(TempMatterTable.PROFILE_ID));
			title = getProfileTitle(context, profileId);
		}
		
		if(!ri.isHappened())
		{
			NotificationUtil.sendNotify(context,
					"ģʽ�л�",	"���л���ģʽ:" + title
					, Notification.DEFAULT_LIGHTS);
			ProfileUtil.switchToSilent(context);
		}
		else
		{
			NotificationUtil.sendNotify(context, "ģʽ�л�",	"���л���ԭģʽ", Notification.DEFAULT_LIGHTS);
			ProfileUtil.switchBack(context);
		}
		
		MainActivity.rm.changeModeHappened();
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
	
	//��profile��id�õ������
	private String getProfileTitle(Context ctx,long profile_id)
	{
		Cursor cursor = ctx.getContentResolver().query(
				MyProfileTable.CONTENT_URI,
				null,
				MyProfileTable._ID + "=?",
				new String[]{""+profile_id},
				null);

		if(!cursor.moveToFirst())
		{
			return ctx.getString(R.string.show_profile_not_exist);
		}
		else return cursor.getString(cursor.getColumnIndex(MyProfileTable.NAME));
	}
}