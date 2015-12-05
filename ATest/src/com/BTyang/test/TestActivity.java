package com.BTyang.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TestActivity extends Activity implements OnClickListener {
	
	private WenbaPopupMenu wenbaMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wenbaMenu = (WenbaPopupMenu) findViewById(R.id.popupmenu);
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add("分享");
		menuItems.add("求助哈哈哈");
		wenbaMenu.setMenuItems(menuItems);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_menu:

			break;

		default:
			break;
		}
	}
}
