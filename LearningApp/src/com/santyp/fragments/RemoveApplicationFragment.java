package com.santyp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santyp.learningapp.R;

public class RemoveApplicationFragment extends Fragment {
	
	public RemoveApplicationFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.remove_app_fragment, container, false);
		return v;
	}

}
