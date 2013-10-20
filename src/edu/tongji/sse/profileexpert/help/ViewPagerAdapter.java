package edu.tongji.sse.profileexpert.help;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


public class ViewPagerAdapter extends PagerAdapter
{

	private Context context;
	private JSONArray jsonArray;
	private HashMap<Integer, ViewPagerItemView> hashMap;//Hashmap������Ƭ��λ���Լ�ItemView.
	
	
	@SuppressLint("UseSparseArrays")
	public ViewPagerAdapter(Context context,JSONArray arrays)
	{
		this.context = context;
		this.jsonArray = arrays;
		hashMap = new HashMap<Integer, ViewPagerItemView>();
	}
	
	//������л��գ����������һ�����ʱ�򣬻�����ڵ�ͼƬ���յ�.
	@Override
	public void destroyItem(View container, int position, Object object) {
		ViewPagerItemView itemView = (ViewPagerItemView)object;
		itemView.recycle();
	}
	
	@Override
	public void finishUpdate(View view) {

	}

	//���ﷵ������ж�����,��BaseAdapterһ��.
	@Override
	public int getCount() {
		return jsonArray.length();
	}

	//������ǳ�ʼ��ViewPagerItemView.���ViewPagerItemView�Ѿ�����,
	//����reload��������newһ�������������.
	@Override
	public Object instantiateItem(View container, int position)
	{	
		ViewPagerItemView itemView;
		if(hashMap.containsKey(position)){
			itemView = hashMap.get(position);
			itemView.reload();
		}else{
			itemView = new ViewPagerItemView(context);
			try {
				JSONObject dataObj = (JSONObject) jsonArray.get(position);
				itemView.setData(dataObj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			hashMap.put(position, itemView);
			((ViewPager) container).addView(itemView);
		}
		
		return itemView;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View view) {

	}
}
