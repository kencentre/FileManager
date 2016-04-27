package com.example.activity;

import java.io.File;
import java.util.List;
import com.example.adapter.PictureChildAdapter;
import com.example.viewpagerdemo.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class PictureShowActivity extends Activity {
	private GridView mGridView;
	private List<String> list;
	private PictureChildAdapter adapter;
	private TextView actionbarname;
	
	private int pos;
	private String mPath;
	
    private File rootFile = null;  //x
    private File currentFile = null;//x


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_media_content);

		actionbarname = (TextView) findViewById(R.id.actionbar_name);
		actionbarname.setText("ͼƬ");
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");

		adapter = new PictureChildAdapter(this, mGridView, list);
		mGridView.setAdapter(adapter);
		
		
		//��ȡSD����·��  
       // rootFile = Environment.getExternalStorageDirectory();//x  
       // currentFile = rootFile;  //x
        //��ʾ�ļ��б�  

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPath=list.get(position);
				//pos=mGridView.getSelectedItemPosition();
				
				//�жϴ�IDֵ�ǲ���-1������ʾ��û��ѡ��ͼƬ��û��ѡ��ͼƬΪ-1�����Ϊѡ��

				//if (pos == AdapterView.INVALID_POSITION){
					//return;
				//}
				//��ImageList�����е�ͼƬ·��ת��Ϊ�ɹ�Fileʶ���String���ݣ�
				//String value = String.valueOf(list.get(pos));
				File file = new File(mPath);
				//ͨ��Intent����ϵͳ��ͼƬ�鿴���Ĺؼ�����
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				startActivity(intent);

			}
		});
	}

}
