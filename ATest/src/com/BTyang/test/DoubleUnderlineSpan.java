/**
 * 
 */
package com.BTyang.test;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * @author Mr.Yang
 *
 */
public class DoubleUnderlineSpan implements LineBackgroundSpan {

	private final static int UNDERLINE_SPACE = 2;

	private final Paint mPaint;

	private List<UndelineNode> nodeList = new ArrayList<UndelineNode>();

	/**
	 * 
	 */
	public DoubleUnderlineSpan(List<UndelineNode> nodeList) {
		this.nodeList = nodeList;
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
		for (UndelineNode node : nodeList) {
			int mStart = node.getStart();
			int mEnd = node.getEnd();
			if (mStart >= start && mStart <= end) {
				node.setDrawing(true);
				int startX = (int) p.measureText(text, start, mStart);
				int endX = 0;
				if (mEnd >= start && mEnd <= end) {
					node.setDrawing(false);
					endX = (int) p.measureText(text, start, mEnd);
					drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
				} else {
					endX = (int) p.measureText(text, start, end);
					drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
				}
			} else if (mEnd >= start && mEnd <= end) {
				node.setDrawing(false);
				int startX = left;
				int endX = (int) p.measureText(text, start, mEnd);
				drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
			} else if (node.isDrawing()) {
				int startX = left;
				int endX = (int) p.measureText(text, start, end);
				drawDoubleUnderline(c, startX, bottom, endX, bottom, mPaint);
			}
		}
	}

	private void drawDoubleUnderline(Canvas c, float startX, float startY, float stopX, float stopY, Paint paint) {
		c.drawLine(startX, startY, stopX, stopY, paint);
		c.drawLine(startX, startY + UNDERLINE_SPACE, stopX, stopY + UNDERLINE_SPACE, paint);
	}

	public static class UndelineNode {
		private int start;
		private int end;
		private boolean drawing = false;

		public UndelineNode(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public boolean isDrawing() {
			return drawing;
		}

		public void setDrawing(boolean drawing) {
			this.drawing = drawing;
		}

	}

}
