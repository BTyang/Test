package com.BTyang.test;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TestActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
	private GridViewWithDivider mGrid;
	private SeekBar sizeBar;
	private TextView tvSize;
	private SeekBar lineSpaceBar;
	private TextView tvLineSpace;
	private EditText edtText;
	private int curSize = 12;
	private float curLineSpace = 1f;

	private List<Integer> subjectList = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sizeBar = (SeekBar) findViewById(R.id.size_bar);
		sizeBar.setOnSeekBarChangeListener(this);
		tvSize = (TextView) findViewById(R.id.tv_size);
		lineSpaceBar = (SeekBar) findViewById(R.id.line_space_bar);
		lineSpaceBar.setOnSeekBarChangeListener(this);
		tvLineSpace = (TextView) findViewById(R.id.tv_line_space);
		edtText = (EditText) findViewById(R.id.edt_text);
		refreshView();
	}

	public static String encrypt(String seed, String cleartext) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	public static String decrypt(String seed, byte[] encrypted) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = decrypt(rawKey, encrypted);
		return new String(result);
	}

	public static String decrypt(String seed, String encrypted) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES/PKCS5Padding");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private class SubjectAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			int count = subjectList.size();
			// if (count % 3 == 0) {
			return count;
			// } else {
			// return (count / 3 + 1) * 3;
			// }
		}

		@Override
		public Object getItem(int position) {
			return position < subjectList.size() ? subjectList.get(position) : null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SubjectViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new SubjectViewHolder();
				convertView = LayoutInflater.from(TestActivity.this).inflate(R.layout.view_exer_subject_item, null);
				viewHolder.icon = (ImageView) convertView.findViewById(R.id.subject_icon);
				viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subject_title);
				convertView.setTag(viewHolder);
				convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 240));
			} else {
				viewHolder = (SubjectViewHolder) convertView.getTag();
			}

			if (position < subjectList.size()) {
				String subject = String.valueOf(subjectList.get(position));
				viewHolder.subjectName.setVisibility(View.VISIBLE);
				viewHolder.icon.setVisibility(View.VISIBLE);
			}

			return convertView;
		}

	}

	private class SubjectViewHolder {
		ImageView icon;
		TextView subjectName;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LayoutInflater.from(TestActivity.this).inflate(R.layout.view_webview, null);
			WebView webView = (WebView) view.findViewById(R.id.webview);
			webView.loadUrl("http://img1.imgtn.bdimg.com/it/u=294434968,4242269251&fm=21&gp=0.jpg");
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (seekBar.getId() == R.id.size_bar) {
			tvSize.setText("字号：" + progress);
			curSize = progress;
		} else if(seekBar.getId() == R.id.line_space_bar) {
			curLineSpace = 1 + progress / 20f;
			tvLineSpace.setText("行间距：" + curLineSpace + "倍");
		}
		refreshView();
		Toast.makeText(this, UUID.randomUUID().toString().replace("-", ""), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
	
	private void refreshView(){
		edtText.setText(tvSize.getText() + "\n" + tvLineSpace.getText());
		edtText.setTextSize(curSize);
		edtText.setLineSpacing(0, curLineSpace);
		edtText.setSelection(edtText.getText().length());
	}

}
