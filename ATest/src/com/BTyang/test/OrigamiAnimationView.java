package com.BTyang.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.nineoldandroids.view.ViewHelper;

public class OrigamiAnimationView extends FrameLayout {

	private final SpringSystem springSystem;
	private final Spring popAnimationSpring;
	// private final Spring popAnimationSpring;
	// private final Spring popAnimationSpring;
	// private final Spring popAnimationSpring;
	private final View layer;

	public OrigamiAnimationView(Context context) {
		this(context, null);
	}

	public OrigamiAnimationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OrigamiAnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// Hook up variables to your views here
		layer = LayoutInflater.from(context).inflate(R.layout.view_red_envelope, this);

		springSystem = SpringSystem.create();

		popAnimationSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(5, 10)).addListener(new SimpleSpringListener() {
			@Override
			public void onSpringUpdate(Spring spring) {
				setPopAnimationProgress((float) spring.getCurrentValue());
			}
		});

		// popAnimationSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(5, 10)).addListener(new SimpleSpringListener() {
		// @Override
		// public void onSpringUpdate(Spring spring) {
		// setPopAnimationProgress((float) spring.getCurrentValue());
		// }
		// });
		//
		// popAnimationSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(5, 10)).addListener(new SimpleSpringListener() {
		// @Override
		// public void onSpringUpdate(Spring spring) {
		// setPopAnimationProgress((float) spring.getCurrentValue());
		// }
		// });
		//
		// popAnimationSpring = springSystem.createSpring().setSpringConfig(SpringConfig.fromBouncinessAndSpeed(5, 10)).addListener(new SimpleSpringListener() {
		// @Override
		// public void onSpringUpdate(Spring spring) {
		// setPopAnimationProgress((float) spring.getCurrentValue());
		// }
		// });
	}

	// popAnimation transition

	public void popAnimation(boolean on) {
		popAnimationSpring.setCurrentValue(0);
		popAnimationSpring.setEndValue(on ? 1 : 0);
	}

	public void setPopAnimationProgress(float progress) {
		ViewHelper.setScaleX(layer, progress);
		ViewHelper.setScaleY(layer, progress);
//		layer.setScaleX(progress);
//		layer.setScaleY(progress);
		Log.i("progress", ""+ progress);
	}

	// // popAnimation transition
	//
	// public void popAnimation(boolean on) {
	// popAnimationSpring.setEndValue(on ? 1 : 0);
	// }
	//
	// public void setPopAnimationProgress(float progress) {
	// float transition2 = transition(progress, 0f, 0.8f);
	// layer.setOpacity(transition2);
	// }
	//
	// // popAnimation transition
	//
	// public void popAnimation(boolean on) {
	// popAnimationSpring.setEndValue(on ? 1 : 0);
	// }
	//
	// public void setPopAnimationProgress(float progress) {
	// float transition2 = transition(progress, 0f, 1f);
	// layer.setOpacity(transition2);
	//
	// float transition2 = transition(progress, 0f, 1f);
	// layer.setScaleX(transition2);
	// layer.setScaleY(transition2);
	// }

	// Utilities

	public float transition(float progress, float startValue, float endValue) {
		return (float) SpringUtil.mapValueFromRangeToRange(progress, 0, 1, startValue, endValue);
	}

}
