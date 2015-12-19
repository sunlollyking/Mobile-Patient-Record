package com.chrisdixon.mobilepatientrecord;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.chrisdixon.medicalrecordinfo.MedicalRecord;
import com.chrisdixon.medicalrecordinfo.Patient;
import com.example.mobilepatientrecord.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoAndAudioForHospital.
 */
public class PhotoAndAudioForHospital extends Activity implements
		MediaRecorder.OnInfoListener {
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The mp. */
	private MediaPlayer mp;
	
	/** The p. */
	private Patient p;
	
	/** The Constant TAKE_PICTURE. */
	private static final int TAKE_PICTURE = 2;
	
	/** The media recorder. */
	private static MediaRecorder mediaRecorder;
	
	/** The identify patient photo. */
	private Uri identifyPatientPhoto;
	
	/** The count. */
	private int count;
	
	/** The count string. */
	private String countString;
	
	/** The t. */
	private Timer t = new Timer();
	
	/** The counter t. */
	private TextView counterT;
	
	/** The start recording. */
	private static Button startRecording;
	
	/** The stop recording. */
	private static Button stopRecording;
	
	/** The take picture. */
	private Button takePicture;
	
	/** The user logged. */
	private String userLogged;
	
	/** The m r. */
	private MedicalRecord mR;
	
	/** The path. */
	private String path;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
			View decorView = getWindow().getDecorView();
			
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
			
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_and_audio_for_hospital);
		senseManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    senseListen = new ShakeEventListener();   

	    senseListen.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

	      public void onShake() {
	        Intent i = new Intent(PhotoAndAudioForHospital.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		Bundle b = getIntent().getExtras();
		mR = b.getParcelable("patientRecord");
		p = b.getParcelable("patient");
		userLogged = b.getString("uName");
		if(haveNetworkConnection()){
		TextView tv = (TextView) findViewById(R.id.usernameForAudio);
		TextView tv2 = (TextView) findViewById(R.id.testSwipe);
		tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		tv.setText(userLogged);
		tv.setTextColor(Color.GREEN);
		tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            Intent i = new Intent(PhotoAndAudioForHospital.this,
                    UserLogActivity.class);
            i.putExtra("uName", userLogged);
            startActivity(i);
            }
        });
		startRecording = (Button) findViewById(R.id.startButton);
		stopRecording = (Button) findViewById(R.id.stopButton);
		takePicture = (Button) findViewById(R.id.takePictureButton);

		// This will make it so that the picture button is the only button that can be pressed
		takePicture.setEnabled(true);
		startRecording.setEnabled(false);
		stopRecording.setEnabled(false);

		counterT = (TextView) findViewById(R.id.counterText);
		if (!hasMicrophone()) {
			Toast.makeText(
					PhotoAndAudioForHospital.this,
					"No Microphone, Please Ring Hospital After Photo Submitted",
					Toast.LENGTH_LONG).show();
		}
		startRecording.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
				try {
					recordAudio(v);
					
					startRecording.setEnabled(false);
					stopRecording.setEnabled(true);
					stopRecording.setBackgroundResource(R.drawable.button);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		stopRecording.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stopAudio(v);
				mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
			
				loadNewActivity();
			}
		});
		takePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
				takePic();
				  
				
			}
		});

		
		b.getParcelable("medicalRecords");
	} else {
		Toast.makeText(PhotoAndAudioForHospital.this,
				"No Connection Detected, Please Connect & Reload",
				Toast.LENGTH_LONG).show();
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
	
	/**
	 * Load new activity.
	 */
	public void loadNewActivity() {
		Intent i = new Intent(PhotoAndAudioForHospital.this,
				SelectHospitalActivity.class);
		i.putExtra("uName", userLogged);
		i.putExtra("patient", p);
		i.putExtra("records", mR);
		i.putExtra("photo",path);
		startActivity(i);
	}

	/**
	 * Record audio.
	 *
	 * @param v the v
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void recordAudio(View v) throws IOException {

		
		try {
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setMaxDuration(180000);
			mediaRecorder.setMaxFileSize(20000000);
			mediaRecorder.setOnInfoListener(this);
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mediaRecorder.setOutputFile(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/patientDescription.3gp");
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		startCounter();
		mediaRecorder.start();
		  
		try {
			PostMedicalLogData.excutePost(userLogged, "Recording Patient Memo");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Stop audio.
	 *
	 * @param v the v
	 */
	public void stopAudio(View v) {
		
		mediaRecorder.stop();
		mediaRecorder.reset();
		mediaRecorder.release();
		t.cancel();
		  
		try {
			PostMedicalLogData.excutePost(userLogged, "Stopped Recording Memo");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		

	}

	/**
	 * Checks for microphone.
	 *
	 * @return true, if successful
	 */
	protected boolean hasMicrophone() {
		PackageManager pmanager = this.getPackageManager();
		return pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
	}

	/**
	 * Gets the img file.
	 *
	 * @return the img file
	 */
	@SuppressLint("SimpleDateFormat")
	public File getImgFile() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		String imgName = "JPEG_" + timeStamp + "_";
		File storageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
						+ "/" + imgName + ".jpg");
		return storageDir;
	}

	/**
	 * Take pic.
	 */
	private void takePic() {
		// removed
		// buttonA=true;
		File f = getImgFile();
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		identifyPatientPhoto = Uri.fromFile(f);
		path =identifyPatientPhoto.getPath();
        Log.d("path= ", path);
		i.putExtra(MediaStore.EXTRA_OUTPUT, identifyPatientPhoto);
		startActivityForResult(i, TAKE_PICTURE);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == Activity.RESULT_OK) {
			
				takePicture.setEnabled(false);
				startRecording.setEnabled(true);
			}
			return;
		default: // do nothing
			super.onActivityResult(requestCode, resultCode, data);
		}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.photo_and_audio_for_hospital, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Start counter.
	 */
	public void startCounter() {

		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// Your cod
				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						count++;
						countString = count + " Seconds Recorded";
						Log.d("CounterString ==", countString);
						counterT.setText(countString);
					}
				});

			}
		}, 0, 1000);

	}

	/* (non-Javadoc)
	 * @see android.media.MediaRecorder.OnInfoListener#onInfo(android.media.MediaRecorder, int, int)
	 */
	@Override
	public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
		if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
			Log.v("AUDIORECORDING", "Maximum Duration Reached");
			stopAudio(stopRecording);
		}
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