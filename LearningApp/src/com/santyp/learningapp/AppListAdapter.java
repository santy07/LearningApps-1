package com.santyp.learningapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.santyp.models.AppItem;

public class AppListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<AppItem> apps;

	public AppListAdapter(Context context, ArrayList<AppItem> apps) {
		this.context= context;
		this.apps= apps;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return apps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return apps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("NewApi") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView= inflater.inflate(R.layout.application_list_row, parent, false);
		}
		// Set the values
		ImageView appIcon = (ImageView) convertView.findViewById(R.id.iconID);
		appIcon.setBackground(apps.get(position).getIcon());
		TextView appName = (TextView) convertView.findViewById(R.id.appNameID);
		appName.setText(apps.get(position).getLabel());
		
		return convertView;
	}

}
