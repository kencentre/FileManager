package com.example.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.viewpagerdemo.R;

public class SDCardManagerActivity extends Activity {

	private TextView mpath;
	private TextView actionbarname;
	private ListView lvFiles;

	
	private final static String TAG="SDCardManagerActivity";
	

	// ��¼��ǰ�ĸ��ļ���
	File currentParent;
	// ��¼��ǰ·���µ������ļ��е��ļ�����
	File[] currentFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localfile_main);

		mpath = (TextView) findViewById(R.id.mPath);
		actionbarname=(TextView) findViewById(R.id.actionbar_name);
		lvFiles = (ListView) findViewById(R.id.file_list);
		
		actionbarname.setText("SD��");

		// ��ȡϵͳ��SDCard��Ŀ¼
		File root = new File("/mnt/sdcard/");

		// ���SD���Ƿ����
		if (root.exists()) {

			currentParent = root;
			currentFiles = root.listFiles();
			// ʹ�õ�ǰĿ¼�µ�ȫ���ļ����ļ��������ListView
			inflateListView(currentFiles);
		}

		lvFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// ����û��������ļ���ֱ�ӷ��أ������κδ���
				if (currentFiles[position].isFile()) {
					// Ҳ���Զ�����չ������ļ���
					return;
				}
				// ��ȡ�û�������ļ��� �µ������ļ�
				File[] tem = currentFiles[position].listFiles();
				if (tem == null || tem.length == 0) {

					Toast.makeText(SDCardManagerActivity.this,
							"��·����û�пɷ��ʵ��ļ�", Toast.LENGTH_LONG).show();
				} else {
					// ��ȡ�û��������б����Ӧ���ļ��У���Ϊ��ǰ�ĸ��ļ���
					currentParent = currentFiles[position];
					// ���浱ǰ�ĸ��ļ����ڵ�ȫ���ļ����ļ���
					currentFiles = tem;
					// �ٴθ���ListView
					inflateListView(currentFiles);
				}

			}
		});

	}

	/**
	 * �����ļ������ListView
	 * 
	 * @param files
	 */
	private void inflateListView(File[] files) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < files.length; i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();

			if (files[i].isDirectory()) {
				// ������ļ��о���ʾ��ͼƬΪ�ļ��е�ͼƬ
				listItem.put("icon", R.drawable.folder);
			} else {
				listItem.put("icon", R.drawable.file);
			}
			// ����һ���ļ�����
			listItem.put("filename", files[i].getName());

			// File myFile = new File(files[i].getName());

			listItems.add(listItem);
		}

		// ����һ��SimpleAdapter
		SimpleAdapter adapter = new SimpleAdapter(SDCardManagerActivity.this,
				listItems, R.layout.list_item, new String[] { "filename",
						"icon" }, new int[] { R.id.file_name, R.id.icon });

		// ������ݼ�
		lvFiles.setAdapter(adapter);

		try {
			mpath.setText("�ļ�·��:" + currentParent.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//icon��С�任
	//public static int dip2px(Context context, float dpValue) {  
	  //  final float scale = context.getResources().getDisplayMetrics().density;  
	   // return (int) (dpValue * scale + 0.5f);  
	//} 

}