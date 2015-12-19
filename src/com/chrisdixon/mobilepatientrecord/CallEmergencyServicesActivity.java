package com.chrisdixon.mobilepatientrecord;

import com.example.mobilepatientrecord.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class CallEmergencyServicesActivity.
 */
public class CallEmergencyServicesActivity extends Activity {

/** The toast message. */
private String toastMessage = "Unable To Make Call, Please Try Again Outside Of Application";

/** The user logged. */
private String userLogged;

/** The mp. */
private MediaPlayer mp;

/** The tf. */
private Typeface tf;

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
		        Intent i = new Intent(CallEmergencyServicesActivity.this, UserManualActivity.class);
		        startActivity(i);
		      }
		    });
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_emergency_services);
		Bundle b = getIntent().getExtras();
		userLogged = b.getString("uName");
		Button dialPolice = (Button) findViewById(R.id.dialPolice);
		dialPolice.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
            	String number = "01912364530";
            	 Intent i = new Intent(Intent.ACTION_CALL);
            	 i.setData(Uri.parse("tel:"+number));
            	 startActivity(i);
            } catch (ActivityNotFoundException e) {
            	Toast.makeText(CallEmergencyServicesActivity.this,
    					toastMessage,
    					Toast.LENGTH_LONG).show();
                Log.e("Error Dialling", "Call failed", e);
            }
            }
        });
		Button dialMilitary = (Button) findViewById(R.id.dialMilitary);
		dialMilitary.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
            		 String number = "01912364530";
                	 Intent i = new Intent(Intent.ACTION_CALL);
                	 i.setData(Uri.parse("tel:"+number));
                	 startActivity(i);
                } catch (ActivityNotFoundException e) {
                	Toast.makeText(CallEmergencyServicesActivity.this,
        					toastMessage,
        					Toast.LENGTH_LONG).show();
                    Log.e("Error Dialling", "Call failed", e);
                }
            }
        });
		Button dialTransplant = (Button) findViewById(R.id.dialTransplant);
		dialTransplant.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
                	String number = "01912364530";
                	 Intent i = new Intent(Intent.ACTION_CALL);
                	 i.setData(Uri.parse("tel:"+number));
                	 startActivity(i);
                } catch (ActivityNotFoundException e) {
                	Toast.makeText(CallEmergencyServicesActivity.this,
        					toastMessage,
        					Toast.LENGTH_LONG).show();
                    Log.e("Error Dialling", "Call failed", e);
                }
            }
        });
		Button dialCoastguard = (Button) findViewById(R.id.dialCoastguard);
		dialCoastguard.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
                	String number = "01912364530";
                	 Intent i = new Intent(Intent.ACTION_CALL);
                	 i.setData(Uri.parse("tel:"+number));
                	 startActivity(i);
                } catch (ActivityNotFoundException e) {
                	Toast.makeText(CallEmergencyServicesActivity.this,
        					toastMessage,
        					Toast.LENGTH_LONG).show();
                    Log.e("Error Dialling", "Call failed", e);
                }
            }
        });
		Button dialFire = (Button) findViewById(R.id.dialFire);
		dialFire.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
                	String number = "01912364530";
                	 Intent i = new Intent(Intent.ACTION_CALL);
                	 i.setData(Uri.parse("tel:"+number));
                	 startActivity(i);
                } catch (ActivityNotFoundException e) {
                	Toast.makeText(CallEmergencyServicesActivity.this,
        					toastMessage,
        					Toast.LENGTH_LONG).show();
                    Log.e("Error Dialling", "Call failed", e);
                }
            }
        });
		Button dialSearchNRescue = (Button) findViewById(R.id.dialSearchAndRescue);
		dialSearchNRescue.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            	try{
                	String number = "01912364530";
                	 Intent i = new Intent(Intent.ACTION_CALL);
                	 i.setData(Uri.parse("tel:"+number));
                	 startActivity(i);
                } catch (ActivityNotFoundException e) {
                	Toast.makeText(CallEmergencyServicesActivity.this,
        					toastMessage,
        					Toast.LENGTH_LONG).show();
                    Log.e("Error Dialling", "Call failed", e);
                }
            }
        });
		String fontPath = "fonts/Sansation_Regular.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		TextView tv = (TextView) findViewById(R.id.callEmergencyServicesUserLogged);
		tv.setText(userLogged);
		tv.setTextColor(Color.GREEN);
		tv.setTypeface(tf);
		tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            Intent i = new Intent(CallEmergencyServicesActivity.this,
                    UserLogActivity.class);
            i.putExtra("uName", userLogged);
            startActivity(i);
            }
        });
		RelativeLayout callEmergencyActivity = (RelativeLayout) findViewById(R.id.callEmergencyServicesLayout);
		callEmergencyActivity.setOnTouchListener(new SwipeListener(this) {
		    public void onSwipeBottom() {
		    	
		        }
		    public void onSwipeRight() {
				}
		    public void onSwipeTop() {
		    	finish();
			}
		    public void onSwipeLeft() {
			}
	});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_emergency_services, menu);
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
