package edu.tongji.sse.profileexpert.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.control.MyTimeSpinner;
import edu.tongji.sse.profileexpert.provider.MyProfileTable;
import edu.tongji.sse.profileexpert.provider.RoutineTable;

public class CreateRoutineActivity extends Activity
{
	private Button bt_cancel = null;
	private Button bt_save = null;
	private EditText et_title = null;
	private EditText et_explain = null;
	private Spinner sp_weekday_from = null;
	private Spinner sp_weekday_to = null;
	private MyTimeSpinner my_time_spinner_from = null;
	private MyTimeSpinner my_time_spinner_to = null;
	private Spinner sp_profile = null;

	private int weekday_selected = -1;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_routine);

		// ����id�ҵ��ؼ�
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_save = (Button) findViewById(R.id.bt_save);
		et_title = (EditText) findViewById(R.id.et_title);
		et_explain = (EditText) findViewById(R.id.et_explain);
		sp_weekday_from = (Spinner) findViewById(R.id.sp_weekday_from);
		sp_weekday_to = (Spinner) findViewById(R.id.sp_weekday_to);
		my_time_spinner_from = (MyTimeSpinner) findViewById(R.id.my_time_spinner_from);
		my_time_spinner_to = (MyTimeSpinner) findViewById(R.id.my_time_spinner_to);
		sp_profile = (Spinner) findViewById(R.id.sp_profile);

		initSpinners();

		// ���ü�����
		bt_cancel.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				back();
			}
		});
		bt_save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				save();
			}
		});
	}

	//���浱ǰ�ճ�
	protected void save()
	{
		String title = et_title.getText().toString();
		String time_from = (String) my_time_spinner_from.getSelectedItem();
		String time_to = (String) my_time_spinner_to.getSelectedItem();
		String explain = et_explain.getText().toString();
		weekday_selected = sp_weekday_from.getSelectedItemPosition();
		long profile_id = sp_profile.getSelectedItemId();
		boolean is_same_day = sp_weekday_to.getSelectedItemPosition() == 0;

		if(title == null || title.equals(""))
		{
			Toast.makeText(this, getString(R.string.title_not_null), Toast.LENGTH_SHORT).show();
			return;
		}

		saveRoutine(title, weekday_selected, is_same_day, time_from, time_to, explain, profile_id);
	}

	//�����ճ̵����ݿ�
	private void saveRoutine(String title, int weekdaySelected, boolean is_same_day,
			String time_from, String time_to, String explain, long profile_id)
	{
		ContentValues values = new ContentValues();
		values.put(RoutineTable.TITLE, title);

		if(!checkTime(weekdaySelected, is_same_day, time_from, time_to))
			return;

		String show_str = time_from
				+ "-" + time_to
				+ "  " + shortString(title,5)
				+ "  " + shortString(getProfileTitle(profile_id),5);

		values.put(RoutineTable.START_DAY, weekdaySelected);
		values.put(RoutineTable.IS_SAME_DAY, is_same_day);
		values.put(RoutineTable.TIME_FROM, time_from);
		values.put(RoutineTable.TIME_TO, time_to);
		values.put(RoutineTable.DESCRIPTION, explain);
		values.put(RoutineTable.SHOW_STRING, show_str);
		values.put(RoutineTable.PROFILE_ID, profile_id);
		
		getContentResolver().insert(RoutineTable.CONTENT_URI, values);
		setResult(RESULT_OK);
		
		back();
	}


	//�������ʱ�� �ĺϷ���
	private boolean checkTime(int weekdaySelected, boolean is_same_day,
			String time_from, String time_to)
	{
		//���ʱ��˳���Ƿ����
		if(is_same_day)
		{
			int hour = -1, minute = -1;
			hour = Integer.parseInt(time_from.substring(0,2));
			minute = Integer.parseInt(time_from.substring(3));
			int from = hour * 60 + minute;

			hour = Integer.parseInt(time_to.substring(0,2));
			minute = Integer.parseInt(time_to.substring(3));
			int to = hour * 60 + minute;

			if(from>=to)
			{
				Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG).show();
				return false;
			}
		}
		
		//����Ƿ��������ճ̲�����ͻ
		Cursor cursor = null;
		if(is_same_day)
		{
			cursor = getContentResolver().query(
				RoutineTable.CONTENT_URI,
				null,
				"(" +
					"(" + RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=?" +
					") AND ("+"("+ RoutineTable.TIME_FROM + ">? AND " + RoutineTable.TIME_FROM + "<?" +
						      ") OR (" + RoutineTable.TIME_TO + ">? AND " + RoutineTable.TIME_TO + "<?" +
							       ") OR ("  + RoutineTable.TIME_FROM + "<? AND " + RoutineTable.TIME_TO + ">?" +
							       		")" +
						  ")" +
				") OR (" + RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=? AND " +
						RoutineTable.TIME_FROM + "<?" +
					 ")" +
				"OR (" + RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=? AND " +
						RoutineTable.TIME_TO + ">?" +
					")"
				,
				new String[]{""+weekdaySelected, ""+1,
						time_from, time_to,
						time_from, time_to,
						time_from, time_to,
						""+weekdaySelected, ""+0,
						time_to,
						""+getYesterday(weekdaySelected), ""+0,
						time_from,
						},
				null);
		}
		else
		{
			cursor = getContentResolver().query(
				RoutineTable.CONTENT_URI,
				null,
				"("
					+ RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=? " +
				") OR " + 
				"("
				 	+ RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=? AND " +
				 		RoutineTable.TIME_TO + ">?" +
				") OR " +
				"(" 
					+ RoutineTable.START_DAY + "=? AND " + RoutineTable.IS_SAME_DAY + "=? AND " +
			 			RoutineTable.TIME_TO + ">?" +
				") OR " +
				"(" 
					+ RoutineTable.START_DAY + "=? AND " +
						RoutineTable.TIME_FROM + "<?" +
				")"
				,
					
				new String[]{""+weekdaySelected, ""+0,
						""+weekdaySelected, ""+1,
						time_from,
						""+getYesterday(weekdaySelected), ""+0,
						time_from,
						""+getTomorrow(weekdaySelected),
						time_to,
						},
				null);
		}

		if(cursor.moveToFirst())
		{
			Toast.makeText(this, getString(R.string.conflict_time), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	//�õ���һ��
	private int getTomorrow(int today)
	{
		if(today == 6)
			return 0;
		else
			return today + 1;
	}
	
	//�õ�ǰһ��
	private int getYesterday(int today)
	{
		if(today > 0)
			return today - 1;
		else
			return 6;
	}

	//��profile��id�õ������
	private String getProfileTitle(long profile_id)
	{
		Cursor cursor = getContentResolver().query(
				MyProfileTable.CONTENT_URI,
				null,
				MyProfileTable._ID + "=?",
				new String[]{""+profile_id},
				null);

		if(!cursor.moveToFirst())
		{
			return getString(R.string.show_profile_not_exist);
		}
		else return cursor.getString(cursor.getColumnIndex(MyProfileTable.NAME));
	}

	//����string
	private String shortString(String title, int length)
	{
		if(title.length() < length + 2)
			return title;
		else
			return title.substring(0, length)+"..";
	}
	
	//����
	private void back()
	{
		this.finish();
	}
	//��ʼ��Spinner
	@SuppressWarnings("deprecation")
	private void initSpinners()
	{
		Intent intent = getIntent();
		weekday_selected = intent.getIntExtra(RoutineActivity.WEEKDAY_SELECTED, -1);

		if(weekday_selected == -1)
		{
			Toast.makeText(CreateRoutineActivity.this,
					getString(R.string.weekday_not_selected),
					Toast.LENGTH_SHORT).show();
			back();
		}

		//sp_weekday_from
		ArrayAdapter<CharSequence> sp_weekday_from_adapter = ArrayAdapter.createFromResource(
				this, R.array.sp_weekday_from_display,
				R.layout.weekday_select_spinner_item);
		sp_weekday_from_adapter.setDropDownViewResource(R.layout.weekday_select_spinner_dropdown_item);
		sp_weekday_from.setAdapter(sp_weekday_from_adapter);
		sp_weekday_from.setSelection(weekday_selected);

		//sp_weekday_to
		ArrayAdapter<CharSequence> sp_weekday_to_adapter = ArrayAdapter.createFromResource(
				this, R.array.sp_weekday_to_display,
				R.layout.weekday_select_spinner_item);
		sp_weekday_to_adapter.setDropDownViewResource(R.layout.weekday_select_spinner_dropdown_item);
		sp_weekday_to.setAdapter(sp_weekday_to_adapter);

		//sp_profile
		Cursor cursor = this.getContentResolver().query(MyProfileTable.CONTENT_URI, null, null, null, null);
		startManagingCursor(cursor);
		SpinnerAdapter sp_profile_adapter = new SimpleCursorAdapter(
				this,
				R.layout.profile_list_item,
				cursor,
				new String[]{MyProfileTable.NAME, MyProfileTable.DESCRIPTION},
				new int[]{R.id.profile_list_item_name, R.id.profile_list_item_discription});
		sp_profile.setAdapter(sp_profile_adapter);
	}
}
