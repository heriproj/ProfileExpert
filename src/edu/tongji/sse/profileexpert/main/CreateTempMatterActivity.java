package edu.tongji.sse.profileexpert.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.control.MyDateSpinner;
import edu.tongji.sse.profileexpert.control.MyTimeSpinner;
import edu.tongji.sse.profileexpert.provider.MyProfileTable;
import edu.tongji.sse.profileexpert.provider.TempMatterTable;
import edu.tongji.sse.profileexpert.util.CommonUtil;
import edu.tongji.sse.profileexpert.util.ContentResolverUtil;

@SuppressLint("SimpleDateFormat")
public class CreateTempMatterActivity extends Activity
{
	private Button bt_cancel = null;
	private Button bt_save = null;
	private EditText et_title = null;
	private EditText et_explain = null;
	private MyDateSpinner my_date_spinner_from = null;
	private MyDateSpinner my_date_spinner_to = null;
	private MyTimeSpinner my_time_spinner_from = null;
	private MyTimeSpinner my_time_spinner_to = null;
	private Spinner sp_profile = null;
	
	/*private static String calanderURL = "";
	private static String calanderEventURL = "";
    
    private int gmailPosition = -1;
    //Ϊ�˼��ݲ�ͬ�汾������,2.2�Ժ�url�����ı�
	static{
		if(Integer.parseInt(Build.VERSION.SDK) >= 8){
			calanderURL = "content://com.android.calendar/calendars";
			calanderEventURL = "content://com.android.calendar/events";
			//calanderRemiderURL = "content://com.android.calendar/reminders";

		}else{
			calanderURL = "content://calendar/calendars";
			calanderEventURL = "content://calendar/events";
			//calanderRemiderURL = "content://calendar/reminders";		
		}
	}*/

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_temp_matter);

        /*gmailPosition = getGmailPosition();*/
		
		// ����id�ҵ��ؼ�
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_save = (Button) findViewById(R.id.bt_save);
		et_title = (EditText) findViewById(R.id.et_title);
		et_explain = (EditText) findViewById(R.id.et_explain);
		my_date_spinner_from = (MyDateSpinner) findViewById(R.id.my_date_spinner_from);
		my_date_spinner_to = (MyDateSpinner) findViewById(R.id.my_date_spinner_to);
		my_time_spinner_from = (MyTimeSpinner) findViewById(R.id.my_time_spinner_from);
		my_time_spinner_to = (MyTimeSpinner) findViewById(R.id.my_time_spinner_to);
		sp_profile = (Spinner) findViewById(R.id.sp_profile);

		try {
			initDateSpinner();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		initSpinner();
		
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

	//��intent�д�������showDay��ʼ��DateSpinner
	private void initDateSpinner() throws ParseException
	{
		Intent intent = getIntent();
		String showDay = intent.getStringExtra(TempMatterActivity.SHOW_DAY_KEY);
		if(showDay!=null && !showDay.equals(""))
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			long time = format.parse(showDay).getTime();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			my_date_spinner_from.setDefault(
					c.get(Calendar.YEAR),
					c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			my_date_spinner_to.setDefault(
					c.get(Calendar.YEAR),
					c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
		}
	}

	//��ʼ��ѡ���龰ģʽ��spinner
	@SuppressWarnings("deprecation")
	private void initSpinner()
	{
		Cursor cursor = this.getContentResolver().query(MyProfileTable.CONTENT_URI, null, null, null, null);
		startManagingCursor(cursor);
		SpinnerAdapter adapter = new SimpleCursorAdapter(
				this,
				R.layout.profile_list_item,
				cursor,
				new String[]{MyProfileTable.NAME, MyProfileTable.DESCRIPTION},
				new int[]{R.id.profile_list_item_name, R.id.profile_list_item_discription});
		sp_profile.setAdapter(adapter);
	}

	//���浱ǰ��ʱ����
	protected void save()
	{
		String title = et_title.getText().toString();
		String date = (String) my_date_spinner_from.getSelectedItem();
		String time = (String) my_time_spinner_from.getSelectedItem();
		String time_from = date + " " + time;
		date = (String) my_date_spinner_to.getSelectedItem();
		time = (String) my_time_spinner_to.getSelectedItem();
		String time_to = date + " " + time;
		String explain = et_explain.getText().toString();
		long profile_id = sp_profile.getSelectedItemId();

		if(title == null || title.equals(""))
		{
			Toast.makeText(this, getString(R.string.title_not_null), Toast.LENGTH_SHORT).show();
			return;
		}

		saveTempMatter(title,time_from,time_to,explain,profile_id);
	}

	//����������ʱ�������ӵ����ݿ�
	private void saveTempMatter(String title, String time_from, String time_to,
			String explain, long profile_id)
	{
		try {
			ContentValues values = new ContentValues();
			values.put(TempMatterTable.TITLE, title);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			long l_time_from = format.parse(time_from).getTime();
			long l_time_to = format.parse(time_to).getTime();

			if(l_time_from >= l_time_to)
			{
				Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG).show();
				return;
			}
			
			String show_str = time_from.substring(11)
					+ "-" + time_to.substring(11)
					+ "  " + CommonUtil.shortString(title,5)
					+ "  " + CommonUtil.shortString(ContentResolverUtil.getProfileTitle(this,profile_id),5);
			
			values.put(TempMatterTable.TIME_FROM, l_time_from);
			values.put(TempMatterTable.TIME_TO, l_time_to);
			values.put(TempMatterTable.TIME_FROM_STR, time_from);
			values.put(TempMatterTable.TIME_TO_STR, time_to);
			values.put(TempMatterTable.DESCRIPTION, explain);
			values.put(TempMatterTable.SHOW_STRING, show_str);
			values.put(TempMatterTable.PROFILE_ID, profile_id);
			getContentResolver().insert(TempMatterTable.CONTENT_URI, values);
			setResult(RESULT_OK);
			
			/*saveToGoogleCalendar(title,explain,l_time_from,l_time_to);*/
			
			back();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	//����
	private void back()
	{
		this.finish();
	}
}
