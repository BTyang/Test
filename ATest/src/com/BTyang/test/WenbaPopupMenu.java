/**
 * 
 */
package com.BTyang.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author Mr.Yang
 *
 */
public class WenbaPopupMenu extends FrameLayout {

	public WenbaPopupMenu(Context context) {
		super(context);
		initView(context, null);
	}

	public WenbaPopupMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	public WenbaPopupMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.view_popup_menu, this);
		
		LinearLayout mMenuContainer = (LinearLayout) findViewById(R.id.skin_menu_container);
		Menu menu = null;
		MenuInflater menuInflater = new MenuInflater(context);
		menuInflater.inflate(R.menu.feed_search_more, menu);
	}

}
