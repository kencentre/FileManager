package com.example.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.viewpagerdemo.R;

public class InnerCardManagerActivity extends Activity {

	private TextView mpath;
	private TextView actionbarname;
	private ListView lvFiles;

	// 记录当前的父文件夹
	File currentParent;
	// 记录当前路径下的所有文件夹的文件数组
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
		

		actionbarname.setText("内部存储");
		// 获取系统的内部存储的目录
		File root = new File("/");
		currentParent = root;
		currentFiles = root.listFiles();
		// 使用当前目录下的全部文件、文件夹来填充ListView
		inflateListView(currentFiles);
		
		lvFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// 如果用户单击了文件，直接返回，不做任何处理
				if (currentFiles[position].isFile()) {
					// 也可自定义扩展打开这个文件等
					return;
				}
				// 获取用户点击的文件夹 下的所有文件
				File[] tem = currentFiles[position].listFiles();
				if (tem == null || tem.length == 0) {

					Toast.makeText(InnerCardManagerActivity.this,
							"该路径下没有可访问的文件", Toast.LENGTH_LONG).show();
				} else {
					// 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
					currentParent = currentFiles[position];
					// 保存当前的父文件夹内的全部文件和文件夹
					currentFiles = tem;
					// 再次更新ListView
					inflateListView(currentFiles);
				}

			}
		});

	}

	private void inflateListView(File[] files) {
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < files.length; i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();

			if (files[i].isDirectory()) {
				// 如果是文件夹就显示的图片为文件夹的图片
				listItem.put("icon", R.drawable.folder);
			} else {
				listItem.put("icon", R.drawable.file);
			}
			// 添加一个文件名称
			listItem.put("filename", files[i].getName());

			// File myFile = new File(files[i].getName());

			listItems.add(listItem);
		}

		// 定义一个SimpleAdapter
		SimpleAdapter adapter = new SimpleAdapter(InnerCardManagerActivity.this,
				listItems, R.layout.list_item, new String[] { "filename",
						"icon" }, new int[] { R.id.file_name, R.id.icon });

		// 填充数据集
		lvFiles.setAdapter(adapter);

		try {
			mpath.setText("文件路径:" + currentParent.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		
	}
	
	//返回上一级目录
		private void upOneLevel()
		{
			if(this.currentParent.getParent() != null)
				this.browseTo(this.currentParent.getParentFile());
		}

	private void browseTo(File file) {
			// TODO Auto-generated method stub
		this.setTitle(file.getAbsolutePath());
		}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
