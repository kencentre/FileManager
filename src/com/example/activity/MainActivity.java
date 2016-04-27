package com.example.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.FragmentAdapter;
import com.example.fragment.FileClassifyFragment;
import com.example.fragment.FileLocalFragment;
import com.example.fragment.FileNetworkFragment;
import com.example.viewpagerdemo.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ViewPager mPageVp;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	/**
	 * Tab鏄剧ず鍐呭TextView
	 */
	private TextView mTabFileClassifyTv, mTabFileLocalTv, mTabFileNetworkTv;
	/**
	 * Tab鐨勯偅涓紩瀵肩�?
	 */
	private ImageView mTabLineIv;
	/**
	 * Fragment
	 */
	private FileClassifyFragment mFileClassifyFg;
	private FileNetworkFragment mFileNetworkFg;
	private FileLocalFragment mFileLocalFg;
	/**
	 * ViewPager鐨勫綋鍓嶉�変腑椤�
	 */
	private int currentIndex;
	/**
	 * 灞忓箷鐨勫搴�
	 */
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findById();
		init();
		initTabLineWidth();

	}

	private void findById() {
		mTabFileClassifyTv = (TextView) this.findViewById(R.id.id_fileclassify_tv);
		mTabFileLocalTv = (TextView) this.findViewById(R.id.id_filelocal_tv);
		mTabFileNetworkTv = (TextView) this.findViewById(R.id.id_filenetwork_tv);

		mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);

		mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
	}

	private void init() {
		mFileNetworkFg = new FileNetworkFragment();
		mFileLocalFg = new FileLocalFragment();
		mFileClassifyFg = new FileClassifyFragment();
		mFragmentList.add(mFileClassifyFg);
		mFragmentList.add(mFileLocalFg);
		mFragmentList.add(mFileNetworkFg);

		mFragmentAdapter = new FragmentAdapter(
				this.getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

			/**
			 * state婊戝姩涓殑鐘舵�� 鏈変笁绉嶇姸鎬侊�?0锛�1锛�2锛� 1锛氭鍦ㄦ粦鍔� 2锛氭粦鍔ㄥ畬姣� 0锛氫粈涔堥兘娌�?�仛銆�
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}

			/**
			 * position :褰撳墠椤甸潰锛屽強浣犵偣鍑绘粦鍔ㄧ殑椤甸�? offset:褰撳墠椤甸潰鍋忕Щ鐨勭櫨鍒嗘瘮
			 * offsetPixels:褰撳墠椤甸潰鍋忕Щ鐨勫儚绱犱綅缃�
			 */
			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
						.getLayoutParams();

				Log.e("offset:", offset + "");
				/**
				 * 鍒╃敤currentIndex(褰撳墠鎵�鍦ㄩ�?�闈�?)鍜宲osition(涓嬩竴涓�?�闈�?)浠ュ強offset鏉�
				 * 璁剧疆mTabLineIv鐨勫乏杈硅窛 婊戝姩鍦烘櫙锛�
				 * 璁�3涓〉闈�?,
				 * 浠庡乏鍒板彸鍒嗗埆涓�?0,1,2 
				 * 0->1; 1->2; 2->1; 1->0
				 */

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				} else if (currentIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				}
				mTabLineIv.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					mTabFileClassifyTv.setTextColor(Color.BLUE);
					break;
				case 1:
					mTabFileLocalTv.setTextColor(Color.BLUE);
					break;
				case 2:
					mTabFileNetworkTv.setTextColor(Color.BLUE);
					break;
				}
				currentIndex = position;
			}
		});

	}

	/**
	 * 璁剧疆婊戝姩鏉＄殑�?�藉害涓哄睆骞曠�?1/3(鏍规嵁Tab鐨勪釜鏁拌�屽畾)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / 3;
		mTabLineIv.setLayoutParams(lp);
	}

	/**
	 * 閲嶇疆棰滆壊
	 */
	private void resetTextView() {
		mTabFileClassifyTv.setTextColor(Color.BLACK);
		mTabFileLocalTv.setTextColor(Color.BLACK);
		mTabFileNetworkTv.setTextColor(Color.BLACK);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
