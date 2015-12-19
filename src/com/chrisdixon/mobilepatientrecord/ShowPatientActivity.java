package com.chrisdixon.mobilepatientrecord;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.chrisdixon.medicalrecordinfo.Patient;
import com.example.mobilepatientrecord.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowPatientActivity.
 */
public class ShowPatientActivity extends Activity implements
		OnItemClickListener {
	
	/** The patients found. */
	private List<Patient> patientsFound;
	
	/** The mp. */
	private MediaPlayer mp;
	
	/** The username. */
	private String username;
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;

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
	        Intent i = new Intent(ShowPatientActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_patient);
		String fontPath = "fonts/Sansation_Regular.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Bundle data = getIntent().getExtras();
		username = data.getString("uName");
		TextView usernameT = (TextView) findViewById(R.id.userLoggedInShowPatient);
		usernameT.setText(username);
		usernameT.setTypeface(tf);
		usernameT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					PostMedicalLogData.excutePost(username, "Viewed Own Log");
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
				mp = MediaPlayer.create(v.getContext(), R.raw.beep);
    			mp.start();
			Intent i = new Intent(ShowPatientActivity.this,
					UserLogActivity.class);
			i.putExtra("uName", username);
			startActivity(i);
			}
		});
		patientsFound = data.getParcelableArrayList("patients");
		final ListView pView = (ListView) findViewById(R.id.patient_list);
	
		TextView textView = new TextView(ShowPatientActivity.this);
		textView.setText("List of patients at specified parameters");
		textView.setBackgroundColor(Color.parseColor("#007AA3"));
		textView.setTextSize(18);
		textView.setTypeface(tf);
		pView.addHeaderView(textView);
		TextView title = (TextView) findViewById(R.id.showPatientTitle);
		title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		Log.d("ArrayListAdded", patientsFound.toString());
		Log.d("ArrayListSize", String.valueOf((patientsFound.size())));
		PatientListAdapter imgAdapter = new PatientListAdapter(this, patientsFound);
		pView.setAdapter(imgAdapter);
		pView.setOnItemClickListener(this);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_patient, menu);
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
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {	
		mp = MediaPlayer.create(view.getContext(), R.raw.beep);
		mp.start();
		Intent i = new Intent(ShowPatientActivity.this,
					PatientRecordsActivity.class);
		try {
			PostMedicalLogData.excutePost(username, "Selected NHS ID"+patientsFound.get(position-1).getNhsId());
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		i.putExtra("selectedPatient", patientsFound.get(position-1));
		i.putExtra("uName", username);
		Log.d("An item from the list has been clicked", "Success");
		startActivity(i);
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
