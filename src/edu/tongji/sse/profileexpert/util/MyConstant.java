package edu.tongji.sse.profileexpert.util;

public class MyConstant
{
	//������ 
	public static final int VIBRATE_SETTING_UNCHANGED = 0;
	public static final int VIBRATE_SETTING_CHANGE_TO_OPEN = 1;
	public static final int VIBRATE_SETTING_CHANGE_TO_CLOSE = 2;

	public static final String STRING_VIBRATE_SETTING_UNCHANGED = "���ֲ���";
	public static final String STRING_VIBRATE_SETTING_CHANGE_TO_OPEN = "�л�������";
	public static final String STRING_VIBRATE_SETTING_CHANGE_TO_CLOSE = "�л����ر�";
	
	public static String getVibtareText(int type)
	{
		switch(type)
		{
		case VIBRATE_SETTING_UNCHANGED:
			return STRING_VIBRATE_SETTING_UNCHANGED;
		case VIBRATE_SETTING_CHANGE_TO_OPEN:
			return STRING_VIBRATE_SETTING_CHANGE_TO_OPEN;
		case VIBRATE_SETTING_CHANGE_TO_CLOSE:
			return STRING_VIBRATE_SETTING_CHANGE_TO_CLOSE;
		}
		return null;
	}

	//�л��ӳ� 
	public static final int SEITCHING_DELAY_1 = 1;
	public static final int SEITCHING_DELAY_2 = 2;
	public static final int SEITCHING_DELAY_5 = 5;
	public static final int SEITCHING_DELAY_10 = 10;
	public static final int SEITCHING_DELAY_15 = 15;
	
	public static final String MINUTE = "����";
	public static final String SECOND = "��";
	
	public static String getDelayText(int type)
	{
		return type + MINUTE;
	}

	//����ʱ��
	public static final int REMING_TIME_30 = 30;
	public static final int REMING_TIME_1 = 1;
	public static final int REMING_TIME_2 = 2;
	public static final int REMING_TIME_5 = 5;
	public static final int REMING_TIME_10 = 10;
	public static final int REMING_TIME_15 = 15;
	
	public static String getRemindingTimeText(int type)
	{
		if(type == 30)
			return type + MINUTE;
		else
			return type + SECOND;
	}
	
	//activity֮��Ĵ�ֵ����
	public static final int REQUEST_CODE_CREATE_PROFILE = 1;
	public static final int REQUEST_CODE_EDIT_PROFILE = 2;
	public static final int REQUEST_CODE_CREATE_TEMP_MATTER = 3;
	public static final int REQUEST_CODE_CREATE_ROUTINE = 4;
	
	//MyProfile��toString()������ʹ�õ�������
	public static final String RINGTONE = "����";
	public static final String DONT_CHANGE = "����";
	public static final String VIBRATE = "��";
	
	//���ڼ�
	public static final String Monday = "��һ";
	public static final String Tuesday = "�ܶ�";
	public static final String Wednesday = "����";
	public static final String Thursday = "����";
	public static final String Friday = "����";
	public static final String Saturday = "����";
	public static final String Sunday = "����";
	
	public static String getDayOfWeek(int dayOfWeek)
	{
		switch(dayOfWeek)
		{
		case 1: return Monday;
		case 2: return Tuesday;
		case 3: return Wednesday;
		case 4: return Thursday;
		case 5: return Friday;
		case 6: return Saturday;
		default : return Sunday;
		}
	}
}
