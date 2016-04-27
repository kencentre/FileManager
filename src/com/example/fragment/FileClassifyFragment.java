package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.activity.MusicManagerActivity;
import com.example.activity.PictureManagerActivity;
import com.example.activity.VideoManagerActivity;
import com.example.viewpagerdemo.R;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class FileClassifyFragment extends Fragment {

	private static final String TAG = "FileClassFragment";

	private GridView gview;
	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;

	// ͼƬ��װΪһ������
	private int[] icon = { R.drawable.picture, R.drawable.music,
			R.drawable.video, R.drawable.document, R.drawable.zip,
			R.drawable.apply, R.drawable.recentsee, R.drawable.bookmark,
			R.drawable.secret };
	private String[] iconName = { "ͼƬ", "��Ƶ", "��Ƶ", "�ĵ�", "ѹ����", "Ӧ��", "�������",
			"�ļ���ǩ", "���ܹ�" };

	int mSelectPos = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View fileClassifyView = inflater.inflate(
				R.layout.activity_tab_fileclassify, container, false);

		gview = (GridView) fileClassifyView.findViewById(R.id.gview);

		gview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				mSelectPos = position;
				switch (position) {
				case 0:
					intent.setClass(getActivity(), PictureManagerActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent.setClass(getActivity(), MusicManagerActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent.setClass(getActivity(), VideoManagerActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}

			}
		});

		// �½�List
		data_list = new ArrayList<Map<String, Object>>();

		// ��ȡ����
		getData();
		// �½�������
		String[] from = { "image", "text" };
		int[] to = { R.id.image, R.id.text };
		sim_adapter = new SimpleAdapter(getActivity(), data_list,
				R.layout.gridview_item, from, to);
		// ����������
		gview.setAdapter(sim_adapter);

		return fileClassifyView;

	}

	// ���ɶ�̬���飬����ת������
	public List<Map<String, Object>> getData() {
		// cion��iconName�ĳ�������ͬ�ģ�������ѡ��һ������
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}

		return data_list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
