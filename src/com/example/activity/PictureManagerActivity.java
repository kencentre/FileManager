package com.example.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.adapter.PictureAdapter;
import com.example.adapter.PictureAdapter.ViewHolder;
import com.example.bean.PictureBean;
import com.example.viewpagerdemo.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PictureManagerActivity extends Activity {

	private HashMap<String, List<String>> mGroupMap = new HashMap<String, List<String>>();
	private final static int SCAN_OK = 1;
	private List<PictureBean> list = new ArrayList<PictureBean>();
	private PictureAdapter adapter;
	private ProgressDialog mProgressDialog;
	private ListView lvFiles;
	private TextView actionbarname;

    
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				// 关闭进度条
				mProgressDialog.dismiss();

				adapter = new PictureAdapter(PictureManagerActivity.this,
						list = subGroupOfImage(mGroupMap), lvFiles);
				lvFiles.setAdapter(adapter);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.classify_list);
		
		

		lvFiles = (ListView) findViewById(R.id.id_classify_listview);
		actionbarname = (TextView) findViewById(R.id.actionbar_name);

		actionbarname.setText("图片");
		
		getImages();
		
		lvFiles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				List<String> childList = mGroupMap.get(list.get(position)
						.getFolderName());
				Intent mIntent = new Intent(PictureManagerActivity.this,
						PictureShowActivity.class);
				mIntent.putStringArrayListExtra("data",
						(ArrayList<String>) childList);
				startActivity(mIntent);
			}
		});

	}

	private void getImages() {
		// TODO Auto-generated method stub
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Uri mPictureUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = PictureManagerActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mPictureUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String mPath = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					// 获取该图片的父路径名
					String parentName = new File(mPath).getParentFile()
							.getName();

					// 根据父路径名将图片放入到mGruopMap中
					if (!mGroupMap.containsKey(parentName)) {
						List<String> chileList = new ArrayList<String>();
						chileList.add(mPath);
						mGroupMap.put(parentName, chileList);
					} else {
						mGroupMap.get(parentName).add(mPath);
					}
				}
				mCursor.close();

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);
			}

		}).start();

	}

	protected List<PictureBean> subGroupOfImage(
			HashMap<String, List<String>> mGroupMap) {
		// TODO Auto-generated method stub
		if(mGroupMap.size() == 0){
			return null;
		}
		List<PictureBean> list = new ArrayList<PictureBean>();
		Iterator<Map.Entry<String, List<String>>> it = mGroupMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			PictureBean mPictureBean = new PictureBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			
			
			mPictureBean.setFolderName(key);
			
			mPictureBean.setImageCounts(value.size());
			mPictureBean.setTopImagePath(value.get(0));//获取该组的第一张图片
			
			
			list.add(mPictureBean);
			
		}
		
		return list;
	}
}
