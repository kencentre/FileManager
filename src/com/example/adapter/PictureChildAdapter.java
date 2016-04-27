package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.bean.PictureBean;
import com.example.loader.NativeImageLoader;
import com.example.loader.NativeImageLoader.NativeImageCallBack;
import com.example.view.PictureView;
import com.example.view.PictureView.OnMeasureListener;
import com.example.viewpagerdemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PictureChildAdapter extends BaseAdapter {

	private Point mPoint = new Point(0, 0);

	/**
	 * 用来存储图片的选中情况
	 */
	private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;

	public PictureChildAdapter(Context context, GridView mGridView,
			List<String> list) {
		this.list = list;
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		String mPath = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			viewHolder.mPictureView = (PictureView) convertView
					.findViewById(R.id.icon);
			viewHolder.number_text = (TextView) convertView
					.findViewById(R.id.file_number);

			// 用来监听PictureView的宽和高
			viewHolder.mPictureView
					.setOnMeasureListener(new OnMeasureListener() {

						@Override
						public void onMeasureSize(int width, int height) {
							// TODO Auto-generated method stub
							mPoint.set(width, height);
						}

					});
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mPictureView.setImageResource(R.drawable.pictures_no);
		}
		viewHolder.mPictureView.setTag(mPath);
		
		//final PictureBean mPictureBean=getItem(position);

		// 利用NativeImageLoader类加载本地图片
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(mPath,
				mPoint, new NativeImageCallBack() {

					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						PictureView mPictureView = (PictureView) mGridView
								.findViewWithTag(path);
						if (bitmap != null && mPictureView != null) {
							mPictureView.setImageBitmap(bitmap);
						}
					}
				});

		if (bitmap != null) {
			viewHolder.mPictureView.setImageBitmap(bitmap);
		} else {
			viewHolder.mPictureView
					.setImageResource(R.drawable.pictures_no);
		}
		return convertView;
	}

	
	/**
	 * 获取选中的Item的position
	 * 
	 * @return
	 */
	public List<Integer> getSelectItems() {
		List<Integer> list = new ArrayList<Integer>();
		for (Iterator<Map.Entry<Integer, Boolean>> it = mSelectMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Integer, Boolean> entry = it.next();
			if (entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	public static class ViewHolder {
		public PictureView mPictureView;
		public TextView number_text;
	}

}
