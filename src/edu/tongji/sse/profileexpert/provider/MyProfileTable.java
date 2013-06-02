package edu.tongji.sse.profileexpert.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MyProfileTable implements BaseColumns
{
	public static final String  AUTHORITY = "edu.tongji.sse.provider.profileexpert";
	
	//����
	public static final String  TABLE_NAME = "myprofile";
	
	//Uri����
	public static final Uri		CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/myprofile");

	//�µ�MIME����-���
	public static final String 	CONTENT_ITEM = "vnd.android.cursor.dir/vnd.tongji.sse.profileexpert.myprofile";

	//�µ�MIME����-����
	public static final String 	CONTENT_ITEM_TYPE  = "vnd.android.cursor.item/vnd.tongji.sse.profileexpert.myprofile";

	//����
	public static final String  DEFAULT_SORT_ORDER = "_id ASC";

	//�ֶ�
	public static final String  NAME 			   		= "name";
	public static final String  ALLOW_CHANGING_VOLUME 	= "allowChangingVolume";
	public static final String  VOLUME					= "volume";
	public static final String  VIBRATE_TYPE 	   		= "vibrate_type";
	public static final String  ALLOW_CHANGING_RINGTONE = "allowChangingRingtone";
	public static final String  RINGTONE 	  		 	= "ringtone";
	public static final String  MESSAGE_CONTENT			= "message_content";
	public static final String	DESCRIPTION				= "description";
}
