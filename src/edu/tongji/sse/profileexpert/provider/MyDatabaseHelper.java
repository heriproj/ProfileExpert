package edu.tongji.sse.profileexpert.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
	//���ݿ���
	public static final String 		       DATABASE_NAME = "my_profile.db";
	private static final int 			   DATABASE_VERSION = 1;
	//����
	private static final String 		   TABLE_NAME = MyProfileTable.TABLE_NAME;
	//������SQL���
	private static final String 		   CREATE_TABLE="CREATE TABLE "
														+ TABLE_NAME
														+ "(" + MyProfileTable._ID
														+ " INTEGER PRIMARY KEY,"
														+ MyProfileTable.NAME
														+ " TEXT,"
														+ MyProfileTable.ALLOW_CHANGING_VOLUME
														+ " BOOLEAN,"
														+ MyProfileTable.VOLUME
														+ " INTEGER,"
														+ MyProfileTable.VIBRATE_TYPE
														+ " INTEGER,"
														+ MyProfileTable.ALLOW_CHANGING_RINGTONE
														+ " BOOLEAN,"
														+ MyProfileTable.RINGTONE
														+ " TEXT,"
														+ MyProfileTable.MESSAGE_CONTENT
														+ " TEXT,"
														+ MyProfileTable.DESCRIPTION
														+ " TEXT" + ");";

	//���캯��-�������ݿ�
	public MyDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//������
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
	}

	//�������ݿ�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
