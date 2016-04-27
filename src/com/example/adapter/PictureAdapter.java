package com.example.adapter;

import java.util.List;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PictureAdapter extends BaseAdapter {
	private List<PictureBean> list;
	private Point mPoint = new Point(0, 0);// 用来封装PictureView的宽和高的对象
	private ListView mListView;
	protected LayoutInflater mInflater;

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

	// 构造函数
	public PictureAdapter(Context context, List<PictureBean> list,
			ListView mListView) {
		this.list = list;
		this.mListView = mListView;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		PictureBean mPictureBean = list.get(position);
		String mPath = mPictureBean.getTopImagePath();
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			viewHolder.mPictureView = (PictureView) convertView
					.findViewById(R.id.icon);
			viewHolder.mTextViewTitle = (TextView) convertView
					.findViewById(R.id.file_name);
			viewHolder.mTextViewCounts = (TextView) convertView
					.findViewById(R.id.file_number);
			
			//viewHolder.mTextViewCounts.setText("共"+ +"张");

			//用来监听PictureView的宽和高
			viewHolder.mPictureView.setOnMeasureListener(new OnMeasureListener(){

				@Override
				public void onMeasureSize(int width, int height) {
					// TODO Auto-generated method stub
					mPoint.set(width, height);
					
				}
				
			});
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mPictureView.setImageResource(R.drawable.pictures_no);
		}
		
		viewHolder.mTextViewTitle.setText(mPictureBean.getFolderName());
		viewHolder.mTextViewCounts.setText("共"+Integer.toString(mPictureBean.getImageCounts())+"张");
		//给ImageView设置路径Tag,这是异步加载图片的小技巧
		viewHolder.mPictureView.setTag(mPath);
		//利用NativeImageLoader类加载本地图片
		Bitmap bitmap=NativeImageLoader.getInstance().loadNativeImage(mPath, mPoint, new NativeImageCallBack() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
				// TODO Auto-generated method stub
				PictureView mPictureView=(PictureView) mListView.findViewWithTag(path);
				if(bitmap != null && mPictureView != null){
					mPictureView.setImageBitmap(bitmap);
				}
			}
		});
		if(bitmap != null){
			viewHolder.mPictureView.setImageBitmap(bitmap);
		}else{
			viewHolder.mPictureView.setImageResource(R.drawable.pictures_no);
		}
		
		return convertView;
	}

	public static class ViewHolder {
		public PictureView mPictureView;
		public TextView mTextViewTitle;
		public TextView mTextViewCounts;
	}

}
