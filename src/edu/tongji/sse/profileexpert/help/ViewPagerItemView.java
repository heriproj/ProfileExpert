package edu.tongji.sse.profileexpert.help;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import edu.tongji.sse.profileexpert.R;

public class ViewPagerItemView extends FrameLayout
{
	private ImageView albumImageView;
	private TextView aLbumNameTextView;
	private Bitmap bitmap;
	private JSONObject object;
	
	public ViewPagerItemView(Context context){
		super(context);
		setupViews();
	}
	
	public ViewPagerItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	//��ʼ��View.
	private void setupViews()
	{
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.viewpager_itemview, null);
		
		albumImageView = (ImageView)view.findViewById(R.id.album_imgview);
		aLbumNameTextView = (TextView)view.findViewById(R.id.album_name); 
		addView(view);
	}

	//������ݣ����ⲿ����.
	public void setData(JSONObject object)
	{
		this.object = object;
		try {
			int resId = object.getInt("resid");
			String name = object.getString("name");
			albumImageView.setImageResource(resId);
			aLbumNameTextView.setText(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
		
	//�ڴ����
	public void recycle()
	{
		albumImageView.setImageBitmap(null);
		if ((this.bitmap == null) || (this.bitmap.isRecycled()))
			return;
		this.bitmap.recycle();
		this.bitmap = null;
	}
	
	public void reload()
	{
		try {
			int resId = object.getInt("resid");
			//ʵս�����ͼƬ��ʱӦ������һ���߳�ȥ��ͼƬ�첽,��Ȼ��UI�߳̿���.
			albumImageView.setImageResource(resId);
		}catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
