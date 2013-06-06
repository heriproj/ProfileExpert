package edu.tongji.sse.profileexpert.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.tongji.sse.profileexpert.R;
import edu.tongji.sse.profileexpert.util.MyConstant;

public class TempMatterActivity extends /*List*/Activity
{
	/*private static final String HOUR_NUMBER_KEY = "hourNum";*/

	Paint paint = new Paint(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp_matter);

		TextView tv_5 = (TextView) findViewById(R.id.tv_hour_num_5); 
		TextView tv_7 = (TextView) findViewById(R.id.tv_hour_num_7);
		
		Canvas canvas = new Canvas();
		paint.setAntiAlias(true);                       //���û���Ϊ�޾��  
	    paint.setColor(Color.BLACK);                    //���û�����ɫ  
	    canvas.drawColor(Color.WHITE);                  //��ɫ����  
	    paint.setStrokeWidth((float) 3.0);              //�߿�  
	    paint.setStyle(Style.STROKE);                   //����Ч��  
	    Rect r1=new Rect();                         //Rect����  
	    r1.left=tv_5.getLeft();                                 //���  
	    r1.top=tv_5.getTop();                                  //�ϱ�  
	    r1.right=tv_7.getRight();                                   //�ұ�  
	    r1.bottom=tv_7.getBottom();                              //�±�  
	    canvas.drawRect(r1, paint);                 //���ƾ���  

	    LinearLayout ll = (LinearLayout) findViewById(R.id.ll_hour_list);
	    canvas.drawText("100,100", 100, 100, paint);
	    canvas.drawText("200,200", 200, 200, paint);
	    canvas.drawText("300,300", 300, 300, paint);
	    canvas.drawText("300,200", 300, 200, paint);
	    canvas.drawText("400,200", 400, 200, paint);
	    canvas.drawText("500,400", 500, 400, paint);
	    ll.draw(canvas);
		/*ListAdapter adapter = new SimpleAdapter(
				this,
				getHourNumMap(),
				R.layout.hour_as_list_item,
				new String[]{HOUR_NUMBER_KEY},
				new int[]{R.id.tv_hour_num});
		
		setListAdapter(adapter);
		
		draw();*/
	}
/*
	private List<HashMap<String, String>> getHourNumMap()
	{
		List<HashMap<String,String>> items = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<24;i++)
		{
			HashMap<String,String> hm = new HashMap<String, String>();
			hm.put(HOUR_NUMBER_KEY, i+"");
			items.add(hm);
		}
		return items;
	}*/


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.temp_matter, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_add_temp_matter:
			//��ת�����ý���
			Intent intent=new Intent();
			intent.setClass(TempMatterActivity.this, CreateTempMatterActivity.class);
			startActivityForResult(intent, MyConstant.REQUEST_CODE_CREATE_TEMP_MATTER);
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == MyConstant.REQUEST_CODE_CREATE_TEMP_MATTER)
		{
			if (resultCode == RESULT_OK)
			{
				draw();
			}
		}
	}

	private void draw()
	{
		Canvas canvas = new Canvas();
		paint.setAntiAlias(true);                       //���û���Ϊ�޾��  
	    paint.setColor(Color.BLACK);                    //���û�����ɫ  
	    canvas.drawColor(Color.WHITE);                  //��ɫ����  
	    paint.setStrokeWidth((float) 3.0);              //�߿�  
	    paint.setStyle(Style.STROKE);                   //����Ч��  
	    Rect r1=new Rect();                         //Rect����  
	    r1.left=50;                                 //���  
	    r1.top=50;                                  //�ϱ�  
	    r1.right=450;                                   //�ұ�  
	    r1.bottom=250;                              //�±�  
	    canvas.drawRect(r1, paint);                 //���ƾ���  
	    RectF r2=new RectF();                           //RectF����  
	    r2.left=50;                                 //���  
	    r2.top=400;                                 //�ϱ�  
	    r2.right=450;                                   //�ұ�  
	    r2.bottom=600;                              //�±�  
	    canvas.drawRoundRect(r2, 10, 10, paint);        //����Բ�Ǿ���
	    
	    canvas.drawText("100,100", 100, 100, paint);
	    canvas.drawText("200,200", 200, 200, paint);
	    canvas.drawText("300,300", 300, 300, paint);
	    canvas.drawText("300,200", 300, 200, paint);
	    canvas.drawText("400,200", 400, 200, paint);
	    canvas.drawText("500,400", 500, 400, paint);
		
	}
}
