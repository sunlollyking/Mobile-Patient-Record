package com.chrisdixon.mobilepatientrecord;

import java.util.List;

import com.chrisdixon.medicalrecordinfo.PatientMedication;
import com.chrisdixon.medicalrecordinfo.ShowMedicationListAdapter;
import com.example.mobilepatientrecord.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowMedicationActivity.
 */
public class ShowMedicationActivity extends Activity {

/** The username. */
private String username;

/** The mp. */
private MediaPlayer mp;

/** The sense manager. */
private SensorManager senseManager;

/** The sense listen. */
private ShakeEventListener senseListen;

/** The p med. */
private List<PatientMedication> pMed;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
			View decorView = getWindow().getDecorView();
			// Hide the status bar.
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
			// Remember that you should never show the action bar if the
			// status bar is hidden, so hide that too if necessary.
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			}
		senseManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    senseListen = new ShakeEventListener();   

	    senseListen.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

	      public void onShake() {
	        Intent i = new Intent(ShowMedicationActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_medication);
		Bundle b = getIntent().getExtras();
		username = b.getString("uName");
		pMed = b.getParcelableArrayList("pMed");
		final ListView pView = (ListView) findViewById(R.id.show_medication_list_item);
        ShowMedicationListAdapter uLogList = new ShowMedicationListAdapter(ShowMedicationActivity.this, pMed);
		pView.setAdapter(uLogList);
		Button goBack = (Button) findViewById(R.id.goBack);
		goBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(v.getContext(), R.raw.beep);
    			mp.start();
			finish();
			}
		});
		TextView user = (TextView) findViewById(R.id.userLoggedInShowMedication);
        user.setText(username);
        String fontPath = "fonts/Sansation_Regular.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        user.setTypeface(tf);
        user.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            Intent i = new Intent(ShowMedicationActivity.this,
                    UserLogActivity.class);
            i.putExtra("uName", username);
            startActivity(i);
            }
        });
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_medication, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	  protected void onResume() {
	    super.onResume();
	    senseManager.registerListener(senseListen,
	        senseManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  /* (non-Javadoc)
  	 * @see android.app.Activity#onPause()
  	 */
  	@Override
	  protected void onPause() {
	    senseManager.unregisterListener(senseListen);
	    super.onPause();
	  }
}
