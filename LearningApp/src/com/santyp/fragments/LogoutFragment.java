package com.santyp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santyp.learningapp.R;

public class LogoutFragment extends Fragment {
	
	public LogoutFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.logout_fragment, container, false);
		return rootView;
	}

}
