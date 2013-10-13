package edu.tongji.sse.profileexpert.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.calendar.MyCalendarView;
import edu.tongji.sse.profileexpert.calendar.MyCell;
import edu.tongji.sse.profileexpert.calendar.OnCellTouchListener;
import edu.tongji.sse.profileexpert.entity.MyRingerSetting;
import edu.tongji.sse.profileexpert.reminding.RemindingManager;
import edu.tongji.sse.profileexpert.util.CallUtil;
import edu.tongji.sse.profileexpert.util.MyConstant;
import edu.tongji.sse.profileexpert.util.ProfileUtil;

public class MainActivity extends Activity implements OnCellTouchListener
{
	private ImageButton ib_setting = null;
	private ImageButton ib_customeProfile = null;
	private ImageButton ib_tempMatter = null;
	private ImageButton ib_routine = null;
	private MyCalendarView calendar = null;
	public static SharedPreferences preference = null;
	public static RemindingManager rm = null;
	public static MyRingerSetting mrs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ib_setting = (ImageButton) findViewById(R.id.ib_setting);
		ib_setting.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				//��ת�����ý���
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});

		ib_customeProfile = (ImageButton) findViewById(R.id.ib_customeProfile);
		ib_customeProfile.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				//��ת���Զ���ģʽ����
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, ProfileActivity.class);
				startActivity(intent);
			}
		});

		ib_tempMatter = (ImageButton) findViewById(R.id.ib_tempmatter);
		ib_tempMatter.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				//��ת���Զ���ģʽ����
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, TempMatterActivity.class);
				startActivity(intent);
			}
		});

		ib_routine = (ImageButton) findViewById(R.id.ib_routine);
		ib_routine.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				//��ת���Զ���ģʽ����
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, RoutineActivity.class);
				startActivity(intent);
			}
		});
		
		calendar = (MyCalendarView) findViewById(R.id.my_calendar);
		calendar.setOnCellTouchListener(this);
		
		rm = new RemindingManager(this,this.getContentResolver());
		
		preference = getSharedPreferences(
				MyConstant.preference_name, Context.MODE_PRIVATE);
		boolean arm_status = preference.getBoolean("arm_status", false);
		if(arm_status == true)
		{
			rm.startReminding(this);
		}
		
		boolean message_shortcut_status = preference.getBoolean("message_shortcut_enable", false);
		if(message_shortcut_status == true)
		{
			CallUtil.registerReceiver(this);
		}
		
		mrs = ProfileUtil.getCurrentRingtone(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_settings:
			//��ת�����ý���
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_exit:
			this.finish();
			return true;
		case R.id.action_save_ringer_mode:
			mrs = ProfileUtil.getCurrentRingtone(this);
			Toast.makeText(this, getString(R.string.save_ringer_mode_success), Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_help:
			//��ת����������
			intent=new Intent();
			intent.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onTouch(MyCell cell)
	{
		Intent intent = new Intent();
		MonthDisplayHelper helper = 
				new MonthDisplayHelper(calendar.getYear(), calendar.getMonth());
		int color = cell.getPaint().getColor();
		if(color == Color.GRAY)
			helper.previousMonth();
		else if(color == Color.LTGRAY)
			helper.nextMonth();

		int year = helper.getYear();
		int month = helper.getMonth();
		int day = cell.getDayOfMonth();
		
		intent.putExtra("selected", true);
		intent.putExtra("selected_year", year);
		intent.putExtra("selected_month", month);
		intent.putExtra("selected_day", day);
		
		intent.setClass(this, TempMatterActivity.class);
		startActivity(intent);
	}

	protected void onDestroy()
	{
		super.onDestroy();
	}
}
