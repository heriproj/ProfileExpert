package edu.tongji.sse.profileexpert.util;

import edu.tongji.sse.profileexpert.provider.MyProfileTable;

public class DefaultProfile
{
	public static final String INSERT_PROFILE_NORMAL = 
			"INSERT INTO " + MyProfileTable.TABLE_NAME
			+ " VALUES ('0','��׼','1','75','0','0',null,"
			+ "'��Ǹ�������ڲ�����ӵ绰���Ժ�����ص�','����:75% ��:����')";
	
	public static final String INSERT_PROFILE_WORK = 
			"INSERT INTO " + MyProfileTable.TABLE_NAME
			+ " VALUES ('1','����','1','0','0','0',null,"
			+ "'��Ǹ�������ڹ������п����������ص�','����:0% ��:����')";
	
	public static final String INSERT_PROFILE_CONFERENCE = 
			"INSERT INTO " + MyProfileTable.TABLE_NAME
			+ " VALUES ('2','����','1','0','2','0',null,"
			+ "'��Ǹ�������ڿ��ᣬɢ�������ص�','����:0% ��:�ر�')";
	
	public static final String INSERT_PROFILE_BREAK = 
			"INSERT INTO " + MyProfileTable.TABLE_NAME
			+ " VALUES ('3','��Ϣ','1','0','2','0',null,"
			+ "'��Ǹ����������Ϣ���Ժ������ص�','����:0% ��:�ر�')";
}
