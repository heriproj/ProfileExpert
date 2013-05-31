package edu.tongji.sse.profileexpert.main;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.RingtonePreference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.preference.SeekBarPreference;
import edu.tongji.sse.profileexpert.util.MyConstant;

public class CreateProfileActivity extends PreferenceActivity implements OnPreferenceChangeListener
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
	
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.create_profile_preferences);
		setContentView(R.layout.create_profile_display);
		
		// ����keyֵ�ҵ��ؼ�
		etp_profile_name = (EditTextPreference) findPreference("profile_name");
		cbp_sound_change = (CheckBoxPreference) findPreference("sound_change");
		sbp_sound_change_value = (SeekBarPreference) findPreference("sound_change_value");
		lp_vibrate = (ListPreference) findPreference("vibrate");
		cbp_ringtone_change = (CheckBoxPreference) findPreference("ringtone_change");
		rp_ringtone = (RingtonePreference) findPreference("ringtone");
		etp_message_content = (EditTextPreference) findPreference("message_content");
		
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
				
			}
		});
    }

	//����
	private void back()
	{
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
				Toast.makeText(CreateProfileActivity.this,
		    			"���Ʋ���Ϊ��",
		    			Toast.LENGTH_SHORT).show();
				return false;
			}
			preference.setTitle(str);
			return true;
		}
		else if(preference == sbp_sound_change_value)
		{
			preference.setSummary("�ı���:" + newValue + "%");
			return true;
		}
		else if(preference == lp_vibrate)
		{
			preference.setSummary(MyConstant.getVibtareText(Integer.parseInt(newValue.toString())));
			return true;
		}
		else if(preference == rp_ringtone)
		{
			preference.setSummary("��ǰ����:" + newValue.toString());
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
