package com.chrisdixon.mobilepatientrecord;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.lang3.text.*;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chrisdixon.medicalrecordinfo.Patient;
import com.example.mobilepatientrecord.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectPatientActivity.
 */
public class SelectPatientActivity extends Activity implements OnClickListener {
	
	/** The house number t. */
	private EditText personNameT, postcodeT, houseNumberT;
	
	/** The username. */
	private String username;
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The p dialog. */
	private ProgressDialog pDialog;
	
	/** The mp. */
	private MediaPlayer mp;
	
	/** The Constant SELECT_PATIENT_URL. */
	private static final String SELECT_PATIENT_URL = "http://homepages.cs.ncl.ac.uk/c.dixon4/patientSearch.php";
	
	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";
	
	/** The Constant TAG_MESSAGE. */
	private static final String TAG_MESSAGE = "message";
	
	/** The Constant TAG_PERSONNAME. */
	private static final String TAG_PERSONNAME = "personName";
	
	/** The Constant TAG_HOUSENUMBER. */
	private static final String TAG_HOUSENUMBER = "houseNumber";
	
	/** The Constant TAG_POSTCODE. */
	private static final String TAG_POSTCODE = "postcode";
	
	/** The Constant TAG_AGE. */
	private static final String TAG_AGE = "age";
	
	/** The Constant TAG_PATIENTS. */
	private static final String TAG_PATIENTS = "patients";
	
	/** The Constant TAG_GENDER. */
	private static final String TAG_GENDER = "gender";
	
	/** The Constant TAG_NHSID. */
	private static final String TAG_NHSID = "nhsID";
	
	/** The json parser. */
	private GetJSONData jsonParser = new GetJSONData();

	/**
	 * Instantiates a new select patient activity.
	 */
	public SelectPatientActivity() {
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
	        Intent i = new Intent(SelectPatientActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		super.onCreate(savedInstanceState);
		String fontPath = "fonts/Sansation_Regular.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		setContentView(R.layout.activity_select_patient);
		Bundle b = getIntent().getExtras();
		username = b.getString("uName");
		if (haveNetworkConnection()) {
			final Button searchB = (Button) findViewById(R.id.searchButton);
			TextView user = (TextView) findViewById(R.id.userLoggedIn);
			user.setText(username);
			user.setTypeface(tf);
			user.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mp = MediaPlayer.create(v.getContext(), R.raw.beep);
					mp.start();
					Intent i = new Intent(SelectPatientActivity.this,
							UserLogActivity.class);
					i.putExtra("uName", username);
					startActivity(i);
				}
			});
			RelativeLayout selectPatientActivity = (RelativeLayout) findViewById(R.id.loginActivity);
			selectPatientActivity
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (!personNameT.getText().toString().equals("")
									&& !postcodeT.getText().toString()
											.equals("")) {
								searchB.setBackgroundResource(R.drawable.button);
								searchB.setText("Press To Search");
								searchB.setEnabled(true);
							} else {
								if (!personNameT.getText().toString()
										.equals("")
										&& !houseNumberT.getText().toString()
												.equals("")) {
									searchB.setBackgroundResource(R.drawable.button);
									searchB.setText("Press To Search");
									searchB.setEnabled(true);
								} else {
									if (!houseNumberT.getText().toString()
											.equals("")
											&& !postcodeT.getText().toString()
													.equals("")) {
										searchB.setBackgroundResource(R.drawable.button);
										searchB.setText("Press To Search");
										searchB.setEnabled(true);
									} else {
										if (!postcodeT.getText().toString()
												.equals("")
												&& !houseNumberT.getText()
														.toString().equals("")) {
											searchB.setBackgroundResource(R.drawable.button);
											searchB.setText("Press To Search");
											searchB.setEnabled(true);
										}
									}
								}
							}
							InputMethodManager imm = (InputMethodManager) v
									.getContext().getSystemService(
											Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						}
					});
			personNameT = (EditText) findViewById(R.id.personName);
			personNameT.setTypeface(tf);
			postcodeT = (EditText) findViewById(R.id.postcode);
			postcodeT.setTypeface(tf);
			houseNumberT = (EditText) findViewById(R.id.houseNo);
			houseNumberT.setTypeface(tf);
			personNameT.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!personNameT.getText().toString().equals("")
							&& !postcodeT.getText().toString().equals("")) {
						searchB.setBackgroundResource(R.drawable.button);
						searchB.setText("Press To Search");
						searchB.setEnabled(true);
					} else {
						if (!personNameT.getText().toString().equals("")
								&& !houseNumberT.getText().toString()
										.equals("")) {
							searchB.setBackgroundResource(R.drawable.button);
							searchB.setText("Press To Search");
							searchB.setEnabled(true);
						} else {
							if (!houseNumberT.getText().toString().equals("")
									&& !postcodeT.getText().toString()
											.equals("")) {
								searchB.setBackgroundResource(R.drawable.button);
								searchB.setText("Press To Search");
								searchB.setEnabled(true);
							} else {
								if (!postcodeT.getText().toString().equals("")
										&& !houseNumberT.getText().toString()
												.equals("")) {
									searchB.setBackgroundResource(R.drawable.button);
									searchB.setText("Press To Search");
									searchB.setEnabled(true);
								}
							}
						}
					}
					mp = MediaPlayer.create(v.getContext(), R.raw.click);
					mp.start();
				}
			});
			postcodeT.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!personNameT.getText().toString().equals("")
							&& !postcodeT.getText().toString().equals("")) {
						searchB.setBackgroundResource(R.drawable.button);
						searchB.setText("Press To Search");
						searchB.setEnabled(true);
					} else {
						if (!personNameT.getText().toString().equals("")
								&& !houseNumberT.getText().toString()
										.equals("")) {
							searchB.setBackgroundResource(R.drawable.button);
							searchB.setText("Press To Search");
							searchB.setEnabled(true);
						} else {
							if (!houseNumberT.getText().toString().equals("")
									&& !postcodeT.getText().toString()
											.equals("")) {
								searchB.setBackgroundResource(R.drawable.button);
								searchB.setText("Press To Search");
								searchB.setEnabled(true);
							} else {
								if (!postcodeT.getText().toString().equals("")
										&& !houseNumberT.getText().toString()
												.equals("")) {
									searchB.setBackgroundResource(R.drawable.button);
									searchB.setText("Press To Search");
									searchB.setEnabled(true);
								}
							}
						}
					}
					mp = MediaPlayer.create(v.getContext(), R.raw.click);
					mp.start();
				}
			});
			houseNumberT.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!personNameT.getText().toString().equals("")
							&& !postcodeT.getText().toString().equals("")) {
						searchB.setBackgroundResource(R.drawable.button);
						searchB.setText("Press To Search");
						searchB.setEnabled(true);
					} else {
						if (!personNameT.getText().toString().equals("")
								&& !houseNumberT.getText().toString()
										.equals("")) {
							searchB.setBackgroundResource(R.drawable.button);
							searchB.setText("Press To Search");
							searchB.setEnabled(true);
						} else {
							if (!houseNumberT.getText().toString().equals("")
									&& !postcodeT.getText().toString()
											.equals("")) {
								searchB.setBackgroundResource(R.drawable.button);
								searchB.setText("Press To Search");
								searchB.setEnabled(true);
							} else {
								if (!postcodeT.getText().toString().equals("")
										&& !houseNumberT.getText().toString()
												.equals("")) {
									searchB.setBackgroundResource(R.drawable.button);
									searchB.setText("Press To Search");
									searchB.setEnabled(true);
								}
							}
						}
					}
					mp = MediaPlayer.create(v.getContext(), R.raw.click);
					mp.start();
				}
			});
			searchB.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mp = MediaPlayer.create(v.getContext(), R.raw.beep);
					mp.start();
					Log.d("TEST", "Search patient Button was clicked");
					new LoadPatient().execute();
				}
			});
			TextView activityTitle = (TextView) findViewById(R.id.title_select_hospital);
			activityTitle.setTypeface(tf);
			activityTitle.setPaintFlags(activityTitle.getPaintFlags()
					| Paint.UNDERLINE_TEXT_FLAG);
		} else {
			Toast.makeText(SelectPatientActivity.this,
					"Please Check Device Connection", Toast.LENGTH_LONG).show();
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.searchButton) {
			mp = MediaPlayer.create(v.getContext(), R.raw.beep);
			mp.start();
			Log.d("TEST", "Search patient Button was clicked");
			new LoadPatient().execute();
		}
	}

	/**
	 * The Class LoadPatient.
	 */
	public class LoadPatient extends AsyncTask<String, String, String> {
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SelectPatientActivity.this);
			pDialog.setMessage("Loading Patients...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(String... args) {
			String fNameOld = "";
			fNameOld = personNameT.getText().toString();
			String fName = "";
			fName = WordUtils.capitalize(fNameOld);
			String postcodel = "";
			postcodel = postcodeT.getText().toString();
			String postcode = "";
			postcode = WordUtils.capitalizeFully(postcodel);
			String houseNumberText = "";
			houseNumberText = houseNumberT.getText().toString();
			String result;
			result = getPatientsFull(fName, postcode, houseNumberText);
			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(SelectPatientActivity.this, file_url,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Gets the patients full.
	 *
	 * @param fName the f name
	 * @param postcode the postcode
	 * @param houseNumber the house number
	 * @return the patients full
	 */
	private String getPatientsFull(String fName, String postcode,
			String houseNumber) {
		int success = 0;
		try {
			PostMedicalLogData.excutePost(username, "Srch " + fName + " @ "
					+ houseNumber + " " + postcode);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("personName", fName));
			params.add(new BasicNameValuePair("houseNumber", houseNumber));
			params.add(new BasicNameValuePair("postcode", postcode));
			Log.d("request!", "starting");
			// getting product details by making HTTP request
			JSONObject json = jsonParser.makeHttpRequest(SELECT_PATIENT_URL,
					"POST", params);
			// check your log for json response
			success = json.getInt(TAG_SUCCESS);
			if (success == 0) {
				Log.d("Patient Search Failure!", json.getString(TAG_MESSAGE));
				runOnUiThread(new Runnable(){
				    @Override
				    public void run(){
				    	Toast.makeText(SelectPatientActivity.this,
								"No patients found, please search again",
								Toast.LENGTH_SHORT).show();
				    }
				});
				
				return json.getString(TAG_MESSAGE);
			}
			if (success == 1) {
				Log.d("Patient Found", json.toString());
				List<Patient> patientsFound = new ArrayList<Patient>();
				JSONArray patients = new JSONArray();
				Intent i = new Intent(SelectPatientActivity.this,
						ShowPatientActivity.class);
				patients = json.getJSONArray(TAG_PATIENTS);
				for (int j = 0; j < patients.length(); j++) {
					patients.getJSONObject(j);
					Patient p = new Patient(patients.getJSONObject(j)
							.getString(TAG_PERSONNAME), patients.getJSONObject(
							j).getInt(TAG_HOUSENUMBER), patients.getJSONObject(
							j).getString(TAG_POSTCODE), patients.getJSONObject(
							j).getInt(TAG_AGE), patients.getJSONObject(j)
							.getString(TAG_GENDER), patients.getJSONObject(j)
							.getInt(TAG_NHSID));
					patientsFound.add(p);
				}
				i.putExtra("uName", username);
				i.putParcelableArrayListExtra("patients",
						(ArrayList<? extends Parcelable>) patientsFound);
				startActivity(i);
				return json.getString(TAG_MESSAGE);
			}
		} catch (JSONException e) {
			Log.d("TEST", "Caught JSON Exception in Select Patient");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Have network connection.
	 *
	 * @return true, if successful
	 */
	private boolean haveNetworkConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		for (NetworkInfo ni : cm.getAllNetworkInfo()) {
			if (ni.isConnected())
				return true;
		}
		return false;
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