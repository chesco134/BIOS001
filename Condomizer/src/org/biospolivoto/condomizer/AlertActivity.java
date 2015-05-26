package org.biospolivoto.condomizer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle b){
		super.onCreate(b);
		TextView text = new TextView(this);
		text.setText(getIntent().getExtras().getString("message"));
		setContentView(text);
	}
	@Override
	protected void onResume(){
		super.onResume();
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run(){
				finish();
			}
		},5000);
	}
}
