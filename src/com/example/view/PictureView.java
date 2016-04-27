package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PictureView extends ImageView {

	private OnMeasureListener onMeasureListener;

	public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
		this.onMeasureListener = onMeasureListener;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 将图片测量的大小回调到onMeasureSize()方法中
		if (onMeasureListener != null) {
			onMeasureListener.onMeasureSize(getMeasuredWidth(),
					getMeasuredHeight());
		}
	}

	public PictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PictureView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public interface OnMeasureListener {
		public void onMeasureSize(int width, int height);
	}

}
