/**
 * 
 */
package com.BTyang.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Mr.Yang
 * 
 */
@SuppressLint("NewApi")
public class SearchActionProvider extends ActionProvider {

	private Context mContext;

	/**
	 * @param context
	 */
	public SearchActionProvider(Context context) {
		super(context);
		this.mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.actionbarsherlock.view.ActionProvider#onCreateActionView()
	 */
	@Override
	public View onCreateActionView() {

		View view = LayoutInflater.from(mContext).inflate(R.layout.action_provide_search, null);
		return view;
	}

}
