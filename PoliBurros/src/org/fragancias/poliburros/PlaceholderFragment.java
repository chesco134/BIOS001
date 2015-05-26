package org.fragancias.poliburros;

import org.fragancias.poliburros.activities.ShowImage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Context activity;
	private View rootView;
	private TextView amountOfRebusnes;
	
	public View getRootView(){
		return rootView;
	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.historias, container,
				false);
		this.rootView = rootView;
		activity = inflater.getContext();
		View anotherStory = inflater.inflate(R.layout.historia, container,
				false);
		amountOfRebusnes = (TextView) anotherStory.findViewById(R.id.amount_of_rebusnes);
		ImageView img = (ImageView) anotherStory.findViewById(R.id.img);
		img.setBackgroundResource(R.drawable.poliburros_pve);
		img.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Intent i = new Intent(activity,ShowImage.class);
				i.putExtra("image", R.drawable.poliburros_pve);
				startActivity(i);
			}
		});
		TextView rebusne = (TextView)anotherStory.findViewById(R.id.rebusne);
		((TextView) anotherStory.findViewById(R.id.section_label)).setText("Poliburros se compromete CONTIGO a quitarte el hambre el día de mañana.\n#NoMásHambreEnUPIITA");
		rebusne.setOnClickListener(new OnClickListener(){
			private boolean clicked = false;
			private int defColor;
			private int rebusnes;
			@Override
			public void onClick(View view){
				TextView sel = (TextView)view;
				if( !clicked ){
					defColor = ((TextView)view).getTextColors().getDefaultColor();
					sel.setTextColor(getResources().getColor(R.color.ipn));
					sel.setText("REBUSNE");
					amountOfRebusnes.setBackgroundResource(R.drawable.number_bkg_selected);
					try{
						rebusnes = Integer.parseInt(amountOfRebusnes.getText().toString());
					}catch(NumberFormatException e){
						rebusnes = 0;
					}
					amountOfRebusnes.setText(Integer.toString(rebusnes+1));
					clicked = !clicked;
				}else{
					sel.setTextColor(defColor);
					sel.setText("+REBUSNE");
					amountOfRebusnes.setBackgroundResource(R.drawable.number_bkg);
					try{
						rebusnes = Integer.parseInt(amountOfRebusnes.getText().toString());
					}catch(NumberFormatException e){
						rebusnes = 0;
					}
					amountOfRebusnes.setText(Integer.toString(rebusnes-1));
					clicked = !clicked;
				}
			}
		});
		LinearLayout mainContainer = (LinearLayout) rootView
				.findViewById(R.id.main_container);
		mainContainer.addView(anotherStory, 0);
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		//Toast.makeText(activity, "OOOOOOOOOOOOOOOOO", Toast.LENGTH_SHORT).show();
	}
}
