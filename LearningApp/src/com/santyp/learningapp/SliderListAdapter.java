package com.santyp.learningapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SliderListAdapter extends BaseAdapter {

	private ArrayList<DrawerItem> items;
	private Context context;
	private LayoutInflater inflater;


	public SliderListAdapter(Context _context, ArrayList<DrawerItem> drawerItems) {
		this.items = drawerItems;
		this.context = _context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			if(inflater == null) {
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			convertView= inflater.inflate(R.layout.drawer_list_item, parent, false);
		}
		
		// Title
		TextView titleTV= (TextView) convertView.findViewById(R.id.title);
		if(position < items.size()) {
			titleTV.setText(items.get(position).getTitle());
		}
		return convertView;
		
	}

}
