package com.test.testdragdropgridview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter
{
	private ArrayList<AppListItem> _items = new ArrayList<AppListItem>();
	private LayoutInflater _inflater;
	private Context _context;
	
	
	public AppListAdapter(Context $context)
	{
		_context = $context;
		_inflater = LayoutInflater.from($context);
	}
	
	
	/**
	 * 항목 추가
	 * @param $id 고유아이디
	 * @param $icon 아이콘 drawable
	 * @param $title 앱 이름
	 */
	public void addItem(CharSequence $id, Drawable $icon, CharSequence $title)
	{
		AppListItem item = new AppListItem(getCount(), $id, $icon, $title);
		_items.add(item);
	}
	
	
	/**
	 * 항목 추가
	 * @param $id 고유아이디
	 * @param $icon 아이콘 리소스 id
	 * @param $title 앱 이름
	 */
	public void addItem(CharSequence $id, int $icon, CharSequence $title)
	{
		AppListItem item = new AppListItem(getCount(), $id, _context.getResources().getDrawable($icon), $title);
		_items.add(item);
	}
	
	
	/**
	 * 항목 추가
	 * @param $id 고유아이디
	 * @param $icon 아이콘 bitmap
	 * @param $title 앱 이름
	 */
	public void addItem(CharSequence $id, Bitmap $icon, CharSequence $title)
	{
		AppListItem item = new AppListItem(getCount(), $id, $icon, $title);
		_items.add(item);
	}
	
	
	/**
	 * 특정 위치에 아이템 넣기
	 * @param $index
	 * @param $item
	 */
	public void addItemAt(int $index, AppListItem $item)
	{
		_items.add($index, $item);
	}
	
	
	/**
	 * 아이콘 변경
	 * @param $index 위치
	 * @param $icon 아이콘 drawable
	 */
	public void setItemIcon(int $index, Drawable $icon)
	{
		_items.get($index)._icon = $icon;
		notifyDataSetChanged();
	}
	
	
	/**
	 * 특정 위치의 아이템 지우기
	 * @param $index
	 */
	public AppListItem removeItemAt(int $index)
	{
		return _items.remove($index);
	}
	
	
	/**
	 * 항목들 전부 지우기
	 */
	public void clear()
	{
		_items.clear();
	}
	
	
	@Override
	public int getCount()
	{
		return _items.size();
	}
	
	
	@Override
	public Object getItem(int $index)
	{
		return _items.get($index);
	}
	
	
	@Override
	public long getItemId(int $index)
	{
		return _items.get($index)._id;
	}
	
	
	/**
	 * 항목의 고유아이디
	 * @param $index 선택된 행
	 * @return
	 */
	public CharSequence getItemAuId(int $index)
	{
		return _items.get($index)._auid;
	}
	
	
	/**
	 * 앱 이름 가져오기
	 * @param $index
	 * @return
	 */
	public CharSequence getItemTitle(int $index)
	{
		return _items.get($index)._title;
	}
	
	
	/**
	 * 아이콘 가져오기
	 * @param $index
	 * @return
	 */
	public Drawable getItemIcon(int $index)
	{
		return _items.get($index)._icon;
	}
	
	
	@Override
	public View getView(int $index, View $convertView, ViewGroup $parent)
	{
		View view = _inflater.inflate(R.layout.list_item_app_list, null);
		
		ImageView icon = (ImageView) view.findViewById(R.id.item_icon);
		icon.setImageDrawable(_items.get($index)._icon);
		
		TextView title = (TextView) view.findViewById(R.id.item_title);
		title.setText(_items.get($index)._title);
		
		return view;
	}
}
