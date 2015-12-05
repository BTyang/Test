/**
 * 
 */
package com.BTyang.test;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BTyang.test.WenbaPopupMenu.WenbaMenu.MenuItem;

/**
 * @author Mr.Yang
 *
 */
public class WenbaPopupMenu extends FrameLayout {
	private static final String TAG = "WenbaPopupMenu";

	private Context mContext;
	private ArrayList<String> menuItems = new ArrayList<String>();
	private LinearLayout mMenuContainer;

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

	public static class MenuReader {

		/** Menu tag name in XML. */
		private static final String XML_MENU = "menu";

		/** Group tag name in XML. */
		private static final String XML_GROUP = "group";

		/** Item tag name in XML. */
		private static final String XML_ITEM = "item";

		public static final String ATTR_MENU_ID = "android:id";
		public static final String ATTR_MENU_TITLE = "android:title";

		private Context mContext;

		public MenuReader(Context context) {
			super();
			this.mContext = context;
		}

		/**
		 * Inflate a menu hierarchy from the specified XML resource. Throws {@link InflateException} if there is an error.
		 * 
		 * @param menuRes
		 *            Resource ID for an XML layout resource to load (e.g., <code>R.menu.main_activity</code>)
		 * @param menu
		 *            The Menu to inflate into. The items and submenus will be added to this Menu.
		 */
		public WenbaMenu read(int menuRes) {
			WenbaMenu wenbaMenu = new WenbaMenu();
			XmlResourceParser parser = null;
			try {
				parser = mContext.getResources().getLayout(menuRes);
				parseMenu(parser, wenbaMenu);
			} catch (XmlPullParserException e) {
				throw new InflateException("Error inflating menu XML", e);
			} catch (IOException e) {
				throw new InflateException("Error inflating menu XML", e);
			} finally {
				if (parser != null)
					parser.close();
			}
			return wenbaMenu;
		}

		/**
		 * Called internally to fill the given menu. If a sub menu is seen, it will call this recursively.
		 */
		private void parseMenu(XmlPullParser parser, WenbaMenu wenbaMenu) throws XmlPullParserException, IOException {
			int eventType = parser.getEventType();
			String tagName;
			boolean lookingForEndOfUnknownTag = false;
			String unknownTagName = null;

			// This loop will skip to the menu start tag
			do {
				if (eventType == XmlPullParser.START_TAG) {
					tagName = parser.getName();
					if (tagName.equals(XML_MENU)) {
						// Go to next tag
						eventType = parser.next();
						break;
					}

					throw new RuntimeException("Expecting menu, got " + tagName);
				}
				eventType = parser.next();
			} while (eventType != XmlPullParser.END_DOCUMENT);

			boolean reachedEndOfMenu = false;
			while (!reachedEndOfMenu) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (lookingForEndOfUnknownTag) {
						break;
					}

					tagName = parser.getName();
					if (tagName.equals(XML_ITEM)) {
						MenuItem item = new MenuItem();
						String strId = parser.getAttributeValue(null, ATTR_MENU_ID);
						if (strId != null) {
							item.setId(getIdByName(strId));
						}
						item.setTitle(parser.getAttributeValue(null, ATTR_MENU_TITLE));
					} else {
						lookingForEndOfUnknownTag = true;
						unknownTagName = tagName;
					}
					break;

				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
						lookingForEndOfUnknownTag = false;
						unknownTagName = null;
					} else if (tagName.equals(XML_ITEM)) {

					} else if (tagName.equals(XML_MENU)) {
						reachedEndOfMenu = true;
					}
					break;

				case XmlPullParser.END_DOCUMENT:
					throw new RuntimeException("Unexpected end of document");
				}

				eventType = parser.next();
			}
		}

		private int getIdByName(String idName) {
			return mContext.getResources().getIdentifier(idName, "id", mContext.getPackageName());
		}

	}

	public static class WenbaMenu {

		private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

		public void addMenuItem(MenuItem item) {
			menuItems.add(item);
		}

		public void removeMenuItem(MenuItem item) {
			menuItems.remove(item);
		}

		public ArrayList<MenuItem> getMenuItems() {
			return menuItems;
		}

		public static class MenuItem {
			private int id;
			private String title;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

		}

	}

}
