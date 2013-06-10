package edu.tongji.sse.profileexpert.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class RoutineTable implements BaseColumns
{
	public static final String  AUTHORITY = "edu.tongji.sse.profileexpert.routineprovider";
	
	//����
	public static final String  TABLE_NAME = "routine";
	
	//Uri����
	public static final Uri		CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/routine");

	//�µ�MIME����-���
	public static final String 	CONTENT_ITEM = "vnd.android.cursor.dir/vnd.tongji.sse.profileexpert.routine";

	//�µ�MIME����-����
	public static final String 	CONTENT_ITEM_TYPE  = "vnd.android.cursor.item/vnd.tongji.sse.profileexpert.routine";

	//����
	public static final String  DEFAULT_SORT_ORDER = "_id DESC";

	//�ֶ�
	public static final String  TITLE 			   		= "title";
	public static final String  START_DAY				= "startDay";
	public static final String  IS_SAME_DAY				= "isSameDay";
	public static final String  TIME_FROM				= "timeFrom";
	public static final String  TIME_TO					= "timeTo";
	public static final String	DESCRIPTION				= "description";
	public static final String  SHOW_STRING				= "showString";
	public static final String  PROFILE_ID				= "profileId";
}
