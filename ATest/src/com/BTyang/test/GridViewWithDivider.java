/**
 * 
 */
package com.BTyang.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * @author Mr.Yang
 *
 */
public class GridViewWithDivider extends GridView {
	
	private Paint dividerPaint;

	/**
	 * @param context
	 */
	public GridViewWithDivider(Context context) {
		super(context);
		initPaint();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GridViewWithDivider(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public GridViewWithDivider(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}
	
	private void initPaint(){
		dividerPaint = new Paint();
		dividerPaint.setStyle(Paint.Style.STROKE);
	}
	
	public void setDividerColor(int color){
		dividerPaint.setColor(color);
		invalidate();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		View firstCell = getChildAt(0);
		int column = getWidth() / firstCell.getWidth();
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View cellView = getChildAt(i);
			if ((i + 1) % column == 0) {
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), dividerPaint);
			} else if ((i + 1) > (childCount - (childCount % column))) {
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), dividerPaint);
			} else {
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), dividerPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), dividerPaint);
			}
		}
	}

}
