package edu.tongji.sse.profileexpert.control;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import edu.tongji.sse.profileexpert.R;
 
public class MyDateSpinner extends Spinner
{
    public MyDateSpinner(Context context){
        super(context);
    }
 
    public MyDateSpinner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        final Time time = new Time();
        time.setToNow();
        //ΪMyDateSpinner����adapter����Ҫ������ʾspinner��textֵ
        MyDateSpinner.this.setAdapter(new BaseAdapter()
        {
            @Override
            public int getCount() {
                return 1;
            }
 
            @Override
            public Object getItem(int arg0) {
            	String str = time.year
                        + "-"
                        + format((time.month + 1))
                        + "-"
                        + format(time.monthDay);
                return str;
            }
 
            @Override
            public long getItemId(int arg0) {
                return 0;
            }
 
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                TextView text = new TextView(MyDateSpinner.this.getContext());
                text.setTextSize(getResources().getDimension(R.dimen.time_text_size));
                text.setText(time.year
                        + "-"
                        + format((time.month + 1))
                        + "-"
                        + format(time.monthDay)
                        + MyDatePickerDialog.caculateWeekDay(time.year,
                                time.month+1, time.monthDay));
                text.setTextColor(Color.BLACK);
                return text;
            }
        });
    }
 
    @Override
    public boolean performClick()
    {
        Time time = new Time();
        time.setToNow();
        MyDatePickerDialog tpd = new MyDatePickerDialog(getContext(),
                new OnDateSetListener()
        		{
                    @Override
                    public void onDateSet(DatePicker view, final int year,
                            final int month, final int day)
                    {
                        //ΪMyDateSpinner��̬����adapter����Ҫ�����޸�spinner��textֵ
                        MyDateSpinner.this.setAdapter(new BaseAdapter() {
 
                            @Override
                            public int getCount() {
                                return 1;
                            }
 
                            @Override
                            public Object getItem(int arg0) {
                                return year
                                        + "-"
                                        + format((month + 1))
                                        + "-"
                                        + format(day);
                            }
 
                            @Override
                            public long getItemId(int arg0) {
                                return 0;
                            }
 
                            @Override
                            public View getView(int arg0, View arg1,
                                    ViewGroup arg2) {
                                TextView text = new TextView(MyDateSpinner.this
                                        .getContext());
                                text.setTextSize(getResources().getDimension(R.dimen.time_text_size));
                                text.setText(year
                                        + "-"
                                        + format((month + 1))
                                        + "-"
                                        + format(day)
                                        + MyDatePickerDialog.caculateWeekDay(
                                                year, month + 1, day));
                                text.setTextColor(Color.BLACK);
                                return text;
                            }
                        });
                    }
 
                }, time.year, time.month, time.monthDay);
        tpd.show();
        return true;
    }
    
    private String format(int value)
    {
    	if(value<10)
    		return "0"+value;
    	else
    		return ""+value;
    }
}
