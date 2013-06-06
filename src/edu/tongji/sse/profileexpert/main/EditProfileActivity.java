package edu.tongji.sse.profileexpert.main;

import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.control.SeekBarPreference;
import edu.tongji.sse.profileexpert.entity.MyProfile;
import edu.tongji.sse.profileexpert.provider.MyProfileTable;
import edu.tongji.sse.profileexpert.util.MyConstant;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.RingtonePreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EditProfileActivity extends PreferenceActivity implements OnPreferenceChangeListener
{
	private EditTextPreference etp_profile_name = null;
	private CheckBoxPreference cbp_sound_change = null;
	private SeekBarPreference sbp_sound_change_value = null;
	private ListPreference lp_vibrate = null;
	private CheckBoxPreference cbp_ringtone_change = null;
	private RingtonePreference rp_ringtone = null;
	private EditTextPreference etp_message_content = null;

	private Button bt_cancel = null;
	private Button bt_save = null;

	private Cursor cursor = null;
	private long id = -1;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.create_profile_preferences);
		setContentView(R.layout.create_profile_display);

		getCursor();

		findPreferences();

		setInitialValueForPreferences();

		// ���ü�����
		etp_profile_name.setOnPreferenceChangeListener(this);
		sbp_sound_change_value.setOnPreferenceChangeListener(this);
		lp_vibrate.setOnPreferenceChangeListener(this);
		rp_ringtone.setOnPreferenceChangeListener(this);
		etp_message_content.setOnPreferenceChangeListener(this);

		// ����id�ҵ��ؼ�
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_save = (Button) findViewById(R.id.bt_save);

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

	// ����keyֵ�ҵ��ؼ�
	@SuppressWarnings("deprecation")
	private void findPreferences()
	{
		etp_profile_name = (EditTextPreference) findPreference("profile_name");
		cbp_sound_change = (CheckBoxPreference) findPreference("sound_change");
		sbp_sound_change_value = (SeekBarPreference) findPreference("sound_change_value");
		lp_vibrate = (ListPreference) findPreference("vibrate");
		cbp_ringtone_change = (CheckBoxPreference) findPreference("ringtone_change");
		rp_ringtone = (RingtonePreference) findPreference("ringtone");
		etp_message_content = (EditTextPreference) findPreference("message_content");
	}

	//�õ�extra�е�id����ȡ��Cursor
	private void getCursor()
	{
		Intent intent = getIntent();
		id = intent.getLongExtra(ProfileActivity.EDIT_PROFILE_ID_KEY, -1);
		cursor = getContentResolver().query(
				MyProfileTable.CONTENT_URI,
				null,
				MyProfileTable._ID + "=?",
				new String[]{""+id},
				null);

		if(!cursor.moveToFirst())
		{
			Toast.makeText(EditProfileActivity.this,
					getString(R.string.profile_not_exist),
					Toast.LENGTH_SHORT).show();
			this.finish();
		}

	}

	// Ϊ�ؼ����ó�ֵ
	private void setInitialValueForPreferences()
	{
		String str = cursor.getString(cursor.getColumnIndex(MyProfileTable.NAME));
		etp_profile_name.setText(str);
		etp_profile_name.setTitle(str);

		boolean b = cursor.getString(cursor.getColumnIndex(MyProfileTable.ALLOW_CHANGING_VOLUME))
				.equals("1");
		cbp_sound_change.setChecked(b);
		if(b)
		{
			int value = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyProfileTable.VOLUME)));
			sbp_sound_change_value.setProgress(value);
			sbp_sound_change_value.setSummary(getString(R.string.change_to) + ":" + value + "%");
		}

		int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyProfileTable.VIBRATE_TYPE)));
		lp_vibrate.setValue(""+type);
		lp_vibrate.setSummary(MyConstant.getVibtareText(type));

		b = cursor.getString(cursor.getColumnIndex(MyProfileTable.ALLOW_CHANGING_RINGTONE))
				.equals("1");
		cbp_ringtone_change.setChecked(b);
		if(b)
		{
			rp_ringtone.setSummary(getString(R.string.current_ringtone) + ":" + cursor.getString(cursor.getColumnIndex(MyProfileTable.RINGTONE)));
		}

		str = cursor.getString(cursor.getColumnIndex(MyProfileTable.MESSAGE_CONTENT));
		etp_message_content.setText(str);
		etp_message_content.setSummary(str);

		cursor.close();
	}

	//����
	private void back()
	{
		this.finish();
	}

	//������ĺ���龰ģʽ
	private void save()
	{
		String name = etp_profile_name.getText();
		boolean allowChangingVolume = cbp_sound_change.isChecked();
		int volume = sbp_sound_change_value.getProgress();
		int vibrate_type = Integer.parseInt(lp_vibrate.getValue());
		boolean allowChangingRingtone = cbp_ringtone_change.isChecked();
		String ringtone = rp_ringtone.getSummary().toString().substring(5);
		String message_content = etp_message_content.getText();

		if(name == null || name.equals(""))
		{
			Toast.makeText(EditProfileActivity.this,
					getString(R.string.name_not_null),
					Toast.LENGTH_SHORT).show();
			return;
		}

		MyProfile mp = new MyProfile();
		mp.setName(name);
		mp.setAllowChangingVolume(allowChangingVolume);
		if(allowChangingVolume)
			mp.setVolume(volume);
		mp.setVibrate_type(vibrate_type);
		mp.setAllowChangingRingtone(allowChangingRingtone);
		if(allowChangingRingtone)
			mp.setRingtone(ringtone);
		mp.setMessage_content(message_content);

		// ���ض���  
		Intent intent = new Intent();
		intent.putExtra(ProfileActivity.EDIT_PROFILE_ID_KEY, id);
		intent.putExtra(ProfileActivity.MY_PROFILE_KEY, mp);
		setResult(RESULT_OK, intent);
		this.finish();
	}

	//preference�ı��������
	public boolean onPreferenceChange(Preference preference, Object newValue)
	{
		if(preference == etp_profile_name)
		{
			String str = (String) newValue;
			if(str == null || str.equals(""))
			{
				Toast.makeText(EditProfileActivity.this,
						getString(R.string.name_not_null),
						Toast.LENGTH_SHORT).show();
				return false;
			}
			preference.setTitle(str);
			return true;
		}
		else if(preference == sbp_sound_change_value)
		{
			int progress = Integer.parseInt(newValue.toString());
			sbp_sound_change_value.setProgress(progress);
			preference.setSummary(getString(R.string.change_to) + ":" + newValue + "%");
			return true;
		}
		else if(preference == lp_vibrate)
		{
			preference.setSummary(MyConstant.getVibtareText(Integer.parseInt(newValue.toString())));
			return true;
		}
		else if(preference == rp_ringtone)
		{
			preference.setSummary(getString(R.string.current_ringtone) + ":" + newValue.toString());
			return true;
		}
		else if(preference == etp_message_content)
		{
			preference.setSummary(newValue.toString());
			return true;
		}
		return false;
	}

}
