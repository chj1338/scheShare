package com.test.testdragdropgridview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppListItem
{
	long _id;
	CharSequence _auid;
	Drawable _icon;
	CharSequence _title;
	
	
	public AppListItem(int $id, CharSequence $auid, Drawable $icon, CharSequence $title)
	{
		_auid = $auid;
		_icon = $icon;
		_title = $title;
	}
	
	
	public AppListItem(int $id, CharSequence $auid, Bitmap $icon, CharSequence $title)
	{
		_auid = $auid;
		_icon = (Drawable) new BitmapDrawable($icon);
		_title = $title;
	}
}
