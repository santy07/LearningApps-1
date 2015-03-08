package com.santyp.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.santyp.learningapp.AppListAdapter;
import com.santyp.models.AppItem;

public class AddApplicationFragment extends ListFragment {

	public interface ListFragmentListener {
		void onListItemSelected(AppItem item);
	}

	private String []items= {"ONE", "TOW", "THREE", "FOUR"};
	private ArrayList<AppItem> applications;

	private ListFragmentListener listener;

	public AddApplicationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listener = (ListFragmentListener) getActivity();
		Bundle bundle = getArguments();
		if(bundle != null) {
			applications= (ArrayList<AppItem>) bundle.getSerializable("apps");
			Log.i(getTag(), "size => " + applications.size());
		}
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		setListAdapter(new AppListAdapter(getActivity(), applications));
		//setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, items));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(listener != null) {
			listener.onListItemSelected(applications.get(position));
		}
	}
}
