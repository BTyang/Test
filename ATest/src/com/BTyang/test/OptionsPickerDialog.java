/**
 * 
 */
package com.BTyang.test;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * @author Mr.Yang
 *
 */
public class OptionsPickerDialog extends DatePickerDialog {

	public OptionsPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
	}


}
