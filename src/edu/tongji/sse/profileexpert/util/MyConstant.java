package edu.tongji.sse.profileexpert.util;

public class MyConstant
{
	//������ 
	public static final int VIBRATE_SETTING_UNCHANGED = 0;
	public static final int VIBRATE_SETTING_CHANGE_TO_OPEN = 1;
	public static final int VIBRATE_SETTING_CHANGE_TO_CLOSE = 2;
	
	public static String getVibtareText(int type)
	{
		switch(type)
		{
		case VIBRATE_SETTING_UNCHANGED:
			return "���ֲ���";
		case VIBRATE_SETTING_CHANGE_TO_OPEN:
			return "�л�������";
		case VIBRATE_SETTING_CHANGE_TO_CLOSE:
			return "�л����ر�";
		}
		return null;
	}

	//�л��ӳ� 
	public static final int SEITCHING_DELAY_1 = 1;
	public static final int SEITCHING_DELAY_2 = 2;
	public static final int SEITCHING_DELAY_5 = 5;
	public static final int SEITCHING_DELAY_10 = 10;
	public static final int SEITCHING_DELAY_15 = 15;
	
	public static String getDelayText(int type)
	{
		return type+"����";
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
			return type+"��";
		else
			return type+"����";
	}
}
