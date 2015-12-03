/**
 * 
 */
package com.BTyang.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * @author Mr.Yang
 *
 */
public class DoubleUnderlineSpan2 implements LineBackgroundSpan {

	private final static int UNDERLINE_SPACE = 2;

	private final Paint mPaint;
	private int mStart, mEnd;
	private boolean hasStarted = false;

	/**
	 * 
	 */
	public DoubleUnderlineSpan2(int start, int end) {
		mStart = start;
		mEnd = end;
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.style.LineBackgroundSpan#drawBackground(android.graphics.Canvas, android.graphics.Paint, int, int, int, int, int,
	 * java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
		if (mStart >= start && mStart <= end) {
			hasStarted = true;
			int startX = (int) p.measureText(text, start, mStart);
			int endX = 0;
			if (mEnd >= start && mEnd <= end) {
				endX = (int) p.measureText(text, start, mEnd);
				drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
			} else {
				endX = (int) p.measureText(text, start, end);
				drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
			}
		} else if (mEnd >= start && mEnd <= end) {
			int startX = left;
			int endX = (int) p.measureText(text, start, mEnd);
			drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
		} else if (hasStarted) {
			int startX = left;
			int endX = (int) p.measureText(text, start, end);
			drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
		}
	}

	private void drawDoubleUnderline(Canvas c, float startX, float startY, float stopX, float stopY, Paint paint) {
		c.drawLine(startX, startY, stopX, stopY, paint);
		c.drawLine(startX, startY + UNDERLINE_SPACE, stopX, stopY + UNDERLINE_SPACE, paint);
	}

}
