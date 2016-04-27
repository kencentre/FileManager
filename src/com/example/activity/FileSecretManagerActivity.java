package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.viewpagerdemo.R;


public class FileSecretManagerActivity extends Activity{
	
	private TextView actionbarname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localfile_main);
		actionbarname=(TextView) findViewById(R.id.actionbar_name);
		actionbarname.setText("±£√‹πÒ");
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
