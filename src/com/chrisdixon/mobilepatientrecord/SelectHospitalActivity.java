package com.chrisdixon.mobilepatientrecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chrisdixon.hospital.Hospital;
import com.chrisdixon.medicalrecordinfo.MedicalRecord;
import com.chrisdixon.medicalrecordinfo.Patient;
import com.example.mobilepatientrecord.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectHospitalActivity.
 */
public class SelectHospitalActivity extends Activity implements OnItemClickListener {
	
	/** The mp. */
	private MediaPlayer mp;
	
	/** The reference no. */
	private String referenceNo;
	
	/** The list of hospitals. */
	private List<Hospital> listOfHospitals = new ArrayList<Hospital>();
	
	/** The user logged. */
	private String userLogged;
	
	/** The longitude f. */
	@SuppressWarnings("unused")
	private float longitudeF;  
	
	/** The position. */
	private int position;
	
	/** The latitude f. */
	@SuppressWarnings("unused")
	private float latitudeF;
	
	/** The locality. */
	private String locality; 
	
	/** The lm. */
	private LocationManager lm;  
	
	/** The location. */
	private Location location; 
    
    /** The longitude. */
    private double longitude; 
	
	/** The latitude. */
	private double latitude; 
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The p dialog. */
	private ProgressDialog pDialog;
	
	/** The hospital list. */
	private List<Hospital>hospitalList;
	
	/** The Constant FIND_HOSPITAL_URL. */
	private static final String FIND_HOSPITAL_URL = "http://homepages.cs.ncl.ac.uk/c.dixon4/findHospital.php";
	
	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";
	
	/** The Constant TAG_MESSAGE. */
	private static final String TAG_MESSAGE = "message";
	
	/** The Constant TAG_HOSPITALS. */
	private static final String TAG_HOSPITALS = "hospitals";
	
	/** The Constant TAG_HOSPITAL_NAME. */
	private static final String TAG_HOSPITAL_NAME ="hospital_name";
	
	/** The Constant TAG_LONGITUDE. */
	private static final String TAG_LONGITUDE = "longitude";
	
	/** The Constant TAG_LATITUDE. */
	private static final String TAG_LATITUDE = "latitude";
	
	/** The Constant TAG_PHONE_NUMBER. */
	private static final String TAG_PHONE_NUMBER = "phone_number";
	
	/** The Constant TAG_LOCALITY. */
	private static final String TAG_LOCALITY = "locality";
	
	/** The p. */
	private Patient p;
	
	/** The m r. */
	private MedicalRecord mR;
	
	/** The img. */
	private String img;
	
	/**
	 * Instantiates a new select hospital activity.
	 */
	public SelectHospitalActivity(){}
	
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
	        Intent i = new Intent(SelectHospitalActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		Bundle b = getIntent().getExtras();
		userLogged = b.getString("uName");
		p = b.getParcelable("patient");
		mR = b.getParcelable("records");
		img = b.getString("photo");
		locality = getLongLat();
		if(haveNetworkConnection()){
		new LoadHospitals().execute();
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_select_hospital_activity);
		TextView tv = (TextView) findViewById(R.id.selectPatientUserLogin);
		tv.setText(userLogged);
		tv.setTextColor(Color.GREEN);
		tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            Intent i = new Intent(SelectHospitalActivity.this,
                    UserLogActivity.class);
            i.putExtra("uName", userLogged);
            startActivity(i);
            }
        });
		 Random generator = new Random(); //Creates a new random generator
	        int a  = generator.nextInt(1000000); //Sets integer a to a number between 0 and 9999 inclusively
	        char c = (char) (generator.nextInt(26) + 'a'); //Finds a random letter between a and z
	        referenceNo = c + ""+ a;
		} else {
			Toast.makeText(SelectHospitalActivity.this,
					"No Connection Detected, Please Connect & Reload",
					Toast.LENGTH_LONG).show();
		}
	}
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_hospital, menu);
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
	
	/**
	 * Gets the long lat.
	 *
	 * @return the long lat
	 */
	public String getLongLat(){
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null)
		{
			longitude = location.getLongitude();
			latitude = location.getLatitude(); 
			longitudeF = (float) longitude;
			latitudeF = (float) latitude;
			Log.d("Longitude = ", String.valueOf(longitude));
			Log.d("Latitude = ", String.valueOf(latitude));
			Geocoder geocoder = new Geocoder(SelectHospitalActivity.this, Locale.getDefault());
            try
            {
            	Log.d("geocoderToString",geocoder.toString());
            	List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            	Log.d(addresses.toString(), addresses.toString());
            	if (addresses.size() > 0) { 
            		if(addresses.get(0).getLocality()==null){
            		locality = WordUtils.capitalize(addresses.get(0).getAddressLine(0));
            		Log.d("ADDRESS SIZE",String.valueOf(addresses.size()));
            		Log.d("ADDRESS",addresses.get(0).getAddressLine(0).toString());}
            	else {
            		locality = WordUtils.capitalize(addresses.get(0).getLocality()); 
            		}
            		return locality;
            	
            } else { 
    			Toast.makeText(SelectHospitalActivity.this, "No Location Data Please Load Google Maps Then Reload", Toast.LENGTH_LONG).show();
    			Log.d("NullPointer", "Locations are null"); } }
            catch (IOException e)
            {
                e.printStackTrace();

            }
		}
		
		
		return null;
	}
	
	/**
	 * The Class LoadHospitals.
	 */
	public class LoadHospitals extends AsyncTask<String, String, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SelectHospitalActivity.this);
			pDialog.setMessage("Loading Hospitals From Location...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(String... args) {

			String result;
			
			result = getHospitals(locality);
			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			
			Log.d("ArrayListAdded", listOfHospitals.toString());		
			Log.d("ArrayListSize", String.valueOf((listOfHospitals.size())));
			final ListView pView = (ListView) findViewById(R.id.hospital_list_item);
			HospitalListAdapter hList = new HospitalListAdapter(SelectHospitalActivity.this, listOfHospitals);
			pView.setAdapter(hList);
			pView.setOnItemClickListener(SelectHospitalActivity.this);
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(SelectHospitalActivity.this, file_url,
						Toast.LENGTH_SHORT).show();
			}

		}
		
		/**
		 * Gets the hospitals.
		 *
		 * @param locality the locality
		 * @return the hospitals
		 */
		public String getHospitals(String locality) {
			int success = 0;
			GetJSONData jsonParser = new GetJSONData();
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("locality", locality));
				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(FIND_HOSPITAL_URL,
						"POST", params);
				// check your log for json response
				success = json.getInt(TAG_SUCCESS);
				if (success == 0) {
					Log.d("Failure!", json.getString(TAG_MESSAGE));
					return null;
				}
				if (success == 1) {
					Log.d("Hospitals Found", json.toString());
					hospitalList = new ArrayList<Hospital>();
					JSONArray hospitals = new JSONArray();
					hospitals = json.getJSONArray(TAG_HOSPITALS);
					for (int j = 0; j < hospitals.length(); j++) {
						hospitals.getJSONObject(j);
						Hospital h = new Hospital(hospitals.getJSONObject(j)
								.getString(TAG_HOSPITAL_NAME), hospitals.getJSONObject(
								j).getDouble(TAG_LONGITUDE), hospitals.getJSONObject(
								j).getDouble(TAG_LATITUDE), hospitals.getJSONObject(
								j).getInt(TAG_PHONE_NUMBER), hospitals.getJSONObject(
								j).getString(TAG_LOCALITY));
						hospitalList.add(h);
						Log.d("Prelim list size = " , String.valueOf(hospitalList.size()));
						Log.d("Prelim list", hospitalList.toString());
					}
					
					for(Hospital hospital : hospitalList){
	            		Location locationNew = new Location(location);
	            		locationNew.setLatitude(hospital.getLatitude());
	            		locationNew.setLongitude(hospital.getLongitude());
	            		Log.d(hospital.getHospitalName(), String.valueOf(locationNew.distanceTo(location)));
	            		if(locationNew.distanceTo(location)<15000){
	            			float distance = locationNew.distanceTo(location);
	            		    hospital.setDistanceTo(distance);
	            			listOfHospitals.add(hospital);
	            		}
					
				}
					return json.getString(TAG_MESSAGE);
					}
			}

			 catch (JSONException e) {
				Log.d("TEST", "Caught JSON Exception in Select Hospital");
				e.printStackTrace();
			}

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {	
		this.position = position;
        Log.d("An item from the list has been clicked", "Success");
        new PostDataToLog().execute();
		final Intent i = new Intent(SelectHospitalActivity.this,
				SentPatient.class);
		new AlertDialog.Builder(this)
		.setTitle("Patient Send Confirmation")
		.setMessage("Do you want to send patient and details to selected hospital?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int whichButton) {
		    	new SFTPConnection().execute();
		    	PendingIntent contentIntent = PendingIntent.getActivity(SelectHospitalActivity.this,
				        1, i,
				        PendingIntent.FLAG_CANCEL_CURRENT);

				NotificationManager nm = (NotificationManager) SelectHospitalActivity.this
				        .getSystemService(Context.NOTIFICATION_SERVICE);

				@SuppressWarnings("unused")
				Resources res = SelectHospitalActivity.this.getResources();
				Notification.Builder builder = new Notification.Builder(SelectHospitalActivity.this);

				builder.setContentIntent(contentIntent)
				            .setSmallIcon(R.drawable.ambulance)
				            .setTicker("Patient Sent Successfully")
				            .setWhen(System.currentTimeMillis())
				            .setAutoCancel(true);
				Notification n = builder.build();

				nm.notify(1, n);

			  
			new PostDataToLog2().execute();
			i.putExtra("selectedHospital", listOfHospitals.get(position));
			i.putExtra("uName", userLogged);
		    i.putExtra("patient", p);
		    i.putExtra("records", mR);
		    i.putExtra("referenceNo", referenceNo);
			startActivity(i);
		    }})
		 .setNegativeButton(android.R.string.no, null).show();
		
}
	
	/**
	 * The Class PostDataToLog.
	 */
	public class PostDataToLog extends AsyncTask<Void, Void, Void>
	{
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		protected Void doInBackground(Void... params){
			try {
				PostMedicalLogData.excutePost(userLogged, "Selected " + hospitalList.get(position).getHospitalName());
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			return null;
			
		}
	}
	
	/**
	 * The Class PostDataToLog2.
	 */
	public class PostDataToLog2 extends AsyncTask<Void, Void, Void>
	{
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		protected Void doInBackground(Void... params){
			try {
				PostMedicalLogData.excutePost(userLogged, "Posted data about patient ref: "+ referenceNo);
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			return null;
			
		}
	}
	
	/**
	 * The Class SFTPConnection.
	 */
	public class SFTPConnection extends AsyncTask<Void,Void,Void>
	{

	    /* (non-Javadoc)
    	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
    	 */
    	@Override
	    protected Void doInBackground(Void... params) {
	        // TODO Auto-generated method stub
	    	
	    	
	        boolean conStatus = false;
	        Session session = null;
	        Channel channel = null;
	        java.util.Properties config = new java.util.Properties(); 
	        config.put("StrictHostKeyChecking", "no");

	        Log.i("Session","is"+conStatus);
	        try {
	            JSch ssh = new JSch();
	            session = ssh.getSession("b3065237", "linux.cs.ncl.ac.uk", 22);
	            session.setPassword("Ihatemeffs18");
	            session.setConfig(config);
	            session.connect();
	             conStatus = session.isConnected();
	            Log.i("Session","is"+conStatus);
	            channel = session.openChannel("sftp");
	            channel.connect();
	            ChannelSftp sftp = (ChannelSftp) channel;
	            String directory = sftp.pwd();
	            Log.d("Currently In =", directory);
	            sftp.cd("/ncl/homepages.cs/c.dixon4/");
	            String directory2 = sftp.pwd();
	            Log.d("Currently In =", directory2);
	            sftp.mkdir(referenceNo);
	            sftp.put(Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/patientDescription.3gp", "/ncl/homepages.cs/c.dixon4/"+referenceNo);
	            sftp.put(img, "/ncl/homepages.cs/c.dixon4/"+referenceNo);
	            String patient = p.toString();
	            Log.d(patient, patient);
	            
	            String records = mR.toString();
	            Log.d("records", records);
	            String patientRecords = patient+" " + records;
	            String fileName = "/"+referenceNo+".txt";
	            BufferedWriter writer = null;
	            try
	            {
	            	File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
							+ fileName);
	                writer = new BufferedWriter(new FileWriter(f));
	                writer.write(patientRecords);
	               

	            }
	            catch ( IOException e)
	            {
	            	Log.d("Could Not Create Text File", e.getMessage());
					e.printStackTrace();
	            }
	           
	                
	                    if ( writer != null)
							try {
								writer.close( );
								Log.d("Writer Closed", "Writer Closed");
								 sftp.put(Environment.getExternalStorageDirectory().getAbsolutePath()
											+ fileName, "/ncl/homepages.cs/c.dixon4/"+referenceNo);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Log.d("Could Not Create Text File", e.getMessage());
								e.printStackTrace();
							}
	                
	                
	            
	           
	          
	        } catch (JSchException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            Log.i("Session","is"+conStatus);
	        } catch (SftpException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            Log.i("Session","is"+conStatus);
	        
	       
	    
	    }
			return null;

	}
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

