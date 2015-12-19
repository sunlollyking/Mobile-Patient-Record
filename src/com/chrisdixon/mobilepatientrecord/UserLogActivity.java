package com.chrisdixon.mobilepatientrecord;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chrisdixon.hospital.UserLog;
import com.example.mobilepatientrecord.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLogActivity.
 */
public class UserLogActivity extends Activity{

	/** The p dialog. */
	private ProgressDialog pDialog;
	
	/** The username. */
	private String username;
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The json parser. */
	private GetJSONData jsonParser = new GetJSONData();
	
	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";
	
	/** The Constant TAG_MESSAGE. */
	private static final String TAG_MESSAGE = "message";
	
	/** The Constant GET_LOG_URL. */
	private static final String GET_LOG_URL = "http://homepages.cs.ncl.ac.uk/c.dixon4/getLogData.php";
	
	/** The Constant TAG_USER_LOG. */
	private static final String TAG_USER_LOG = "userLog";
	
	/** The Constant TAG_USERNAME. */
	private static final String TAG_USERNAME = "username";
	
	/** The Constant TAG_LOG. */
	private static final String TAG_LOG = "log";
	
	/** The Constant TAG_CURRENT_T_D. */
	private static final String TAG_CURRENT_T_D = "current_t_d";
	
	/** The logs. */
	private List<UserLog> logs;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bundle b = getIntent().getExtras();
		username = b.getString("uName"); 
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
	        Intent i = new Intent(UserLogActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_log);
		new LoadUserLog().execute();
		if(haveNetworkConnection()){
		Button goBack = (Button) findViewById(R.id.goBack);
		goBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(v.getContext(), R.raw.beep);
    			mp.start();
			finish();
			}
		});
	}
		 else {
				Toast.makeText(UserLogActivity.this,
						"No Connection Detected, Please Connect & Reload",
						Toast.LENGTH_LONG).show();
			}
	}		
		
		
	
/**
 * The Class LoadUserLog.
 */
public class LoadUserLog extends AsyncTask<String, String, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UserLogActivity.this);
			pDialog.setMessage("Loading User Log...");
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

				result = getUserLog(username);
			

			return result;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			
				
				final ListView pView = (ListView) findViewById(R.id.user_log_list_item);
		        UserLogListAdapter uLogList = new UserLogListAdapter(UserLogActivity.this, logs);
				pView.setAdapter(uLogList);
				pDialog.dismiss();
				if (file_url != null) {
					Toast.makeText(UserLogActivity.this, file_url,
							Toast.LENGTH_SHORT).show();
			}

		}

	/**
	 * Gets the user log.
	 *
	 * @param username the username
	 * @return the user log
	 */
	private String getUserLog(String username) {
		int success = 0;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			Log.d("request!", "starting");
			// getting product details by making HTTP request
			JSONObject json = jsonParser.makeHttpRequest(GET_LOG_URL,
					"POST", params);
			// check your log for json response
			success = json.getInt(TAG_SUCCESS);
			if (success == 0) {
				Log.d("Login Failure!", json.getString(TAG_MESSAGE));
				return json.getString(TAG_MESSAGE);
			}
			if (success == 1) {
				Log.d("Log Found", json.toString());
				logs = new ArrayList<UserLog>();
				JSONArray JSONLogs = new JSONArray();
				JSONLogs = json.getJSONArray(TAG_USER_LOG);
				for (int j = 0; j < JSONLogs.length(); j++) {
					JSONLogs.getJSONObject(j);
					UserLog u = new UserLog(JSONLogs.getJSONObject(j)
							.getString(TAG_USERNAME), JSONLogs.getJSONObject(
							j).getString(TAG_LOG), JSONLogs.getJSONObject(
							j).getString(TAG_CURRENT_T_D));
					logs.add(u);
				}
				return json.getString(TAG_MESSAGE);
			}

		} catch (JSONException e) {
			Log.d("TEST", "Caught JSON Exception in UserLogActivity");
			e.printStackTrace();
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