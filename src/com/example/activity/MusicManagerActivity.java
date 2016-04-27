package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.viewpagerdemo.R;

public class MusicManagerActivity extends Activity{
	
	private TextView actionbarname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.classify_list);
		
		actionbarname=(TextView) findViewById(R.id.actionbar_name);
		actionbarname.setText("“Ù∆µ");
	}

}
