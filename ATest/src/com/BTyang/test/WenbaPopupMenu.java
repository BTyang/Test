/**
 * 
 */
package com.BTyang.test;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.nineoldandroids.view.ViewHelper;

/**
 * @author Mr.Yang
 *
 */
public class WenbaPopupMenu extends FrameLayout implements OnClickListener {
	private static final String TAG = "WenbaPopupMenu";

	private Context mContext;
	private ArrayList<String> menuItems = new ArrayList<String>();
	private LinearLayout mMenuContainer;
	private SpringSystem springSystem;
	private Spring translationSpring;
	private Spring alphaSpring;

	private OnWenbaPopUpMenuClickListener onWenbaPopUpMenuClickListener;

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
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.view_popup_menu, this);
		mMenuContainer = (LinearLayout) findViewById(R.id.skin_menu_container);
		initAnimation();
	}

	public void setMenuItems(ArrayList<String> menuItems) {
		this.menuItems = menuItems;
		int paddingUnit = 16;
		int menuWidth = 0;
		for (int i = 0; i < menuItems.size(); i++) {
			String menuTitle = menuItems.get(i);
			TextView item = new TextView(mContext);
			item.setGravity(Gravity.CENTER);
			item.setPadding(paddingUnit * 4, paddingUnit, paddingUnit * 4, paddingUnit);
			item.setText(menuTitle);
			item.setTextSize(18);
			item.setBackgroundColor(Color.GREEN);
			item.setId(i);
			item.setOnClickListener(this);
			mMenuContainer.addView(item);
			int itemWidth = getMeasuredWidth(item);
			Log.e(TAG, "itemWidth:" + itemWidth);
			if (itemWidth > menuWidth) {
				menuWidth = itemWidth;
			}
			// mMenuContainer.addView(item, new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
			// android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			if (i < menuItems.size() - 1) {
				View divider = new View(mContext);
				divider.setBackgroundColor(Color.RED);
				mMenuContainer.addView(divider, new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
			}
		}
		Log.e(TAG, "menuWidth:" + menuWidth);
		mMenuContainer.getLayoutParams().width = menuWidth + 2;
	}

	private int getMeasuredWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredWidth();
	}

	public interface OnWenbaPopUpMenuClickListener {
		void onMenuItemClick(int index, String title);
	}

	public OnWenbaPopUpMenuClickListener getOnWenbaPopUpMenuClickListener() {
		return onWenbaPopUpMenuClickListener;
	}

	public void setOnWenbaPopUpMenuClickListener(OnWenbaPopUpMenuClickListener onWenbaPopUpMenuClickListener) {
		this.onWenbaPopUpMenuClickListener = onWenbaPopUpMenuClickListener;
	}

	@Override
	public void onClick(View v) {
		if (onWenbaPopUpMenuClickListener != null) {
			onWenbaPopUpMenuClickListener.onMenuItemClick(v.getId(), ((TextView) v).getText().toString());
		}
	}

	private void initAnimation() {
		springSystem = SpringSystem.create();

		translationSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(5, 10)).addListener(new SimpleSpringListener() {
			@Override
			public void onSpringUpdate(Spring spring) {
				setAnimationProgress((float) spring.getCurrentValue());
			}
		});
		alphaSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(0, 8)).addListener(new SimpleSpringListener() {
			@Override
			public void onSpringUpdate(Spring spring) {
				setAlphaProgress((float) spring.getCurrentValue());
			}
		});
	}

	public void setAnimationProgress(float progress) {
		ViewHelper.setPivotX(mMenuContainer, mMenuContainer.getMeasuredWidth());
		ViewHelper.setPivotY(mMenuContainer, 0);
		ViewHelper.setScaleX(mMenuContainer, progress);
		ViewHelper.setScaleY(mMenuContainer, progress);
	}

	public void setAlphaProgress(float progress) {
	}

	public void popAnimation(boolean show) {
		translationSpring.setCurrentValue(show ? 0 : 1);
		translationSpring.setEndValue(show ? 1 : 0);
		alphaSpring.setCurrentValue(1);
		alphaSpring.setEndValue(0);
	}

	public float transition(float progress, float startValue, float endValue) {
		return (float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, startValue, endValue);
	}
	
	public void show(){
		this.setVisibility(View.VISIBLE);
		popAnimation(true);
	}
	
	public void dismiss(){
		this.setVisibility(View.GONE);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
}
