package com.example.fragment;

import com.example.activity.FileSecretManagerActivity;
import com.example.activity.InnerCardManagerActivity;
import com.example.activity.SDCardManagerActivity;
import com.example.viewpagerdemo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;

public class FileLocalFragment extends Fragment {

	private LinearLayout inner_storage_layout;
	private LinearLayout sd_storage_layout;
	private LinearLayout file_secret_layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View fileLocalView = inflater.inflate(R.layout.activity_tab_filelocal,
				container, false);

		initLayoutView(fileLocalView);
		initListenser();
		return fileLocalView;

	}

	private void initListenser() {
		// TODO Auto-generated method stub
		inner_storage_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), InnerCardManagerActivity.class);
				startActivity(intent);

			}
		});

		sd_storage_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), SDCardManagerActivity.class);
				startActivity(intent);

			}
		});

		file_secret_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), FileSecretManagerActivity.class);
				startActivity(intent);

			}
		});

	}

	private void initLayoutView(View view) {
		// TODO Auto-generated method stub
		inner_storage_layout = (LinearLayout) view
				.findViewById(R.id.inner_storage_layout);
		sd_storage_layout = (LinearLayout) view
				.findViewById(R.id.sd_storage_layout);
		file_secret_layout = (LinearLayout) view
				.findViewById(R.id.file_secret_layout);

	}
	
	  

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
