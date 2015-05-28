package org.biospolivoto.condomizer.fragments;

import org.biospolivoto.condomizer.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Convocatoria extends Fragment{

	Activity activity;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.convocatoria, parent, false);
		return rootView;
	}
}
