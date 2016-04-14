package com.BTyang.test;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Toast;

import com.BTyang.test.WenbaPopupMenu.OnWenbaPopUpMenuClickListener;

public class TestActivity extends Activity implements OnClickListener, OnWenbaPopUpMenuClickListener {

	private WenbaPopupMenu wenbaMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		// 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
		setContentView(R.layout.activity_main);
		wenbaMenu = (WenbaPopupMenu) findViewById(R.id.popupmenu);
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add("分享");
		menuItems.add("求助哈哈哈");
		wenbaMenu.setMenuItems(menuItems);
		wenbaMenu.setOnWenbaPopUpMenuClickListener(this);
		findViewById(R.id.btn_menu).setOnClickListener(this);
		findViewById(R.id.btn_do_not_click).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_menu:
			wenbaMenu.show();
			break;
		case R.id.btn_do_not_click:
			DatePickerDialog dialog = new DatePickerDialog(TestActivity.this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					
				}
			}, 2016, 7, 8);
			dialog.show();
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onMenuItemClick(int index, String title) {
		Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
				//熄屏时上传统计
				Log.e("TestActivity", "ACTION_SCREEN_OFF");
			}
		}
	};
	
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	};
}
