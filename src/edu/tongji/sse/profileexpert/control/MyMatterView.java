package edu.tongji.sse.profileexpert.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyMatterView extends View
{
	Paint paint = new Paint(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
	
	/*@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	   setMeasuredDimension(100, 100);
	}*/
	
	public MyMatterView(Context context, AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		paint.setAntiAlias(true);                       //���û���Ϊ�޾��  
	    paint.setColor(Color.BLACK);                    //���û�����ɫ  
	    canvas.drawColor(Color.WHITE);                  //��ɫ����  
	    paint.setStrokeWidth((float) 3.0);              //�߿� 
	    canvas.drawText("10,10", 10, 10, paint);
		super.onDraw(canvas);
	}

}
