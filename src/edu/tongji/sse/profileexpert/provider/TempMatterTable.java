package edu.tongji.sse.profileexpert.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TempMatterTable implements BaseColumns
{
	public static final String  AUTHORITY = "edu.tongji.sse.profileexpert.tempmatterprovider";
	
	//����
	public static final String  TABLE_NAME = "tempmatter";
	
	//Uri����
	public static final Uri		CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tempmatter");

	//�µ�MIME����-���
	public static final String 	CONTENT_ITEM = "vnd.android.cursor.dir/vnd.tongji.sse.profileexpert.tempmatter";

	//�µ�MIME����-����
	public static final String 	CONTENT_ITEM_TYPE  = "vnd.android.cursor.item/vnd.tongji.sse.profileexpert.tempmatter";

	//����
	public static final String  DEFAULT_SORT_ORDER = "_id DESC";

	//�ֶ�
	public static final String  TITLE 			   		= "title";
	public static final String  TIME_FROM 				= "timeFrom";
	public static final String  TIME_TO					= "timeTo";
	public static final String	DESCRIPTION				= "description";
}
