package com.chrisdixon.mobilepatientrecord;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
 * The Class LoginActivity.
 *
 * @author Chris Dixon This is the main application class and is what a user is
 *         first presented with upon logging into the application. It provides a
 *         log in system that is connected to a MySQL database which can
 *         authenticate and check a user's credentials to see if they are a
 *         valid user and if so allow them to further use the program. If wrong
 *         credentials are entered then the program will prompt the user to
 *         re-enter credentials and will not allow them to progress.
 * @version 1.0
 */

public class LoginActivity extends Activity implements OnClickListener {

	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The json data. */
	private GetJSONData jsonData = new GetJSONData();
	
	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";
	
	/** The Constant TAG_MESSAGE. */
	private static final String TAG_MESSAGE = "message";
	
	/** The Constant LOGIN_URL. */
	private static final String LOGIN_URL = "http://homepages.cs.ncl.ac.uk/c.dixon4/index.php";
	
	/** The p dialog. */
	private ProgressDialog pDialog;
	
	/** The mp. */
	private MediaPlayer mp;
	

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
	        Intent i = new Intent(LoginActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if (haveNetworkConnection()) {
			TextView title = (TextView) findViewById(R.id.loginTitle);
			title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			String fontPathBold = "fonts/Sansation_Bold.ttf";
			String fontPath = "fonts/Sansation_Regular.ttf";
			Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
			Typeface tfBold = Typeface.createFromAsset(getAssets(), fontPathBold);
			title.setTypeface(tfBold);
			
			Button loginB = (Button) findViewById(R.id.loginButton);
			loginB.setTypeface(tf);
			loginB.setOnClickListener(this);
			
			EditText userIdText = (EditText) findViewById(R.id.userIdentificationBox);
			userIdText.setTypeface(tf);
			userIdText.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
				    if(hasFocus){
				    	mp = MediaPlayer.create(v.getContext(), R.raw.click);
	        			mp.start();
				    }
				   }
				});
			
			
			RelativeLayout loginActivity = (RelativeLayout) findViewById(R.id.loginActivity);
			loginActivity.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
			});
			
			EditText passwordText = (EditText) findViewById(R.id.passwordBox);
			passwordText.setTypeface(tf);
			passwordText.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
                	mp = MediaPlayer.create(v.getContext(), R.raw.click);
        			mp.start();
        			
                }
			});
		} else {
			Toast.makeText(LoginActivity.this,
					"No Connection Detected, Please Connect & Reload",
					Toast.LENGTH_LONG).show();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.loginButton) {
			mp = MediaPlayer.create(this, R.raw.beep);
			mp.start();
			new AttemptLogin().execute();
			mp.release();
			Log.d("TEST", "Login Button Was Clicked");
		}
	}

/**
 * The Class AttemptLogin.
 */
public class AttemptLogin extends AsyncTask<String, String, String> {
		
		/** Before starting background thread Show Progress Dialog. */
		boolean failure = false;

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Please wait..logging in");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(String... args) {
			
			// Check for success tag
			int success;
			EditText passwordText = (EditText) findViewById(R.id.passwordBox);
			EditText userIdText = (EditText) findViewById(R.id.userIdentificationBox);
			String username = userIdText.getText().toString();
			String password = passwordText.getText().toString();
	
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonData.makeHttpRequest(LOGIN_URL, "POST",
						params);
				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 0) {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
				if (success == 1) {
					
					Log.d("Login Successful!", json.toString());
					try {
						PostMedicalLogData.excutePost(username, username + " Logged in");
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					}
				
					Intent i = new Intent(LoginActivity.this,
							SelectPatientActivity.class);
					Bundle b = new Bundle();
					b.putString("uName", username);
					i.putExtras(b);
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException  | NullPointerException e ) {
				runOnUiThread(new Runnable() {
					public void run() {

				
				Toast.makeText(LoginActivity.this,
						"Error Reading Login Data, Please Contact System Admin",
						Toast.LENGTH_LONG).show();
				Log.d("TEST", "Caught Exception Exception");
				e.printStackTrace();
			}});
				}
			return null;

		}
		
	

		/**
		 * After completing background task Dismiss the progress dialog
		 * *.
		 *
		 * @param file_url the file_url
		 */
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_SHORT)
						.show();
			}

		}

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	

