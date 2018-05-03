package com.test.testdragdropgridview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.test.testdragdropgridview.views.DragDropGridView;
import com.test.testdragdropgridview.views.DragDropGridView.OnDropListener;

public class AppListActivity extends Activity
{
	private ArrayList<String> data = new ArrayList<String>();
	private DragDropGridView _itemList;
	protected AppListAdapter _adapter;
	protected int _type;
	
	
	public AppListActivity()
	{
		for (int i = 0; i < 10; i++)
		{
			data.add(i + " 호호호");
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_app_list);
		
		_itemList = (DragDropGridView) findViewById(R.id.list_apps);
		_adapter = new AppListAdapter(this);
		
		for (String s : data)
			_adapter.addItem(1 + "", R.drawable.ic_launcher, s);
		
		_itemList.setAdapter(_adapter);
		_itemList.setOnDropListener(onDropListener);
	}
	
	/*************************
	 * Listener
	 ************************/
	// 아이템 이동 후 손을 뗐을 때
	private OnDropListener onDropListener = new OnDropListener()
	{
		
		@Override
		public void drop(int from, int to)
		{
			AppListItem item = _adapter.removeItemAt(from);
			_adapter.addItemAt(to, item);
			_adapter.notifyDataSetChanged();
		}
	};
}
