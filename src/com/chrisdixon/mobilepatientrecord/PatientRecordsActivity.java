package com.chrisdixon.mobilepatientrecord;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chrisdixon.medicalrecordinfo.MedicalRecord;
import com.chrisdixon.medicalrecordinfo.Patient;
import com.chrisdixon.medicalrecordinfo.PatientAllergy;
import com.chrisdixon.medicalrecordinfo.PatientBloodGroup;
import com.chrisdixon.medicalrecordinfo.PatientConcerns;
import com.chrisdixon.medicalrecordinfo.PatientFamilyHistory;
import com.chrisdixon.medicalrecordinfo.PatientHistory;
import com.chrisdixon.medicalrecordinfo.PatientImage;
import com.chrisdixon.medicalrecordinfo.PatientMedication;
import com.chrisdixon.medicalrecordinfo.PatientResuscitate;
import com.example.mobilepatientrecord.R;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientRecordsActivity.
 */
@SuppressLint("SimpleDateFormat")
public class PatientRecordsActivity extends Activity implements OnClickListener {
	
	/** The sense manager. */
	private SensorManager senseManager;
    
    /** The sense listen. */
    private ShakeEventListener senseListen;
	
	/** The mp. */
	private MediaPlayer mp;
	
	/** The user logged in. */
	private String userLoggedIn;
	
	/** The sdf. */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/** The p dialog. */
	private ProgressDialog pDialog;
	
	/** The Constant PATIENT_RECORD_URL. */
	private static final String PATIENT_RECORD_URL = "http://homepages.cs.ncl.ac.uk/c.dixon4/medicalRecord.php";
	
	/** The Constant TAG_SUCCESS. */
	private static final String TAG_SUCCESS = "success";
	
	/** The Constant TAG_MESSAGE. */
	private static final String TAG_MESSAGE = "message";
	
	/** The Constant TAG_PATIENT_ALLERGIES. */
	private static final String TAG_PATIENT_ALLERGIES = "patientAllergies";
	
	/** The Constant TAG_PALLERGY_PRIORITY. */
	private static final String TAG_PALLERGY_PRIORITY = "priority";
	
	/** The Constant TAG_PALLERGY_NAME. */
	private static final String TAG_PALLERGY_NAME = "allergy";
	
	/** The Constant TAG_PALLERGY_DATE_DIAGNOSED. */
	private static final String TAG_PALLERGY_DATE_DIAGNOSED = "date_diagnosed";
	
	/** The Constant TAG_PATIENT_BLOOD_GROUP. */
	private static final String TAG_PATIENT_BLOOD_GROUP = "patientBloodGroup";
	
	/** The Constant TAG_PBGROUP. */
	private static final String TAG_PBGROUP = "blood_group";
	
	/** The Constant TAG_PATIENT_CONCERNS. */
	private static final String TAG_PATIENT_CONCERNS = "patientConcerns";
	
	/** The Constant TAG_PCONCERNS. */
	private static final String TAG_PCONCERNS = "concerns";
	
	/** The Constant TAG_PCONCERNS_DATE_RAISED. */
	private static final String TAG_PCONCERNS_DATE_RAISED = "date_raised";	
	
	/** The Constant TAG_PATIENT_DRUG. */
	private static final String TAG_PATIENT_DRUG = "patientDrug";
	
	/** The Constant TAG_PDRUG_NAME. */
	private static final String TAG_PDRUG_NAME = "name";
	
	/** The Constant TAG_PDRUG_PRIORITY. */
	private static final String TAG_PDRUG_PRIORITY = "priority";
	
	/** The Constant TAG_PDRUG_DATE_STARTED. */
	private static final String TAG_PDRUG_DATE_STARTED = "date_started";
	
	/** The Constant TAG_PDRUG_DATE_FINISHED. */
	private static final String TAG_PDRUG_DATE_FINISHED = "date_finished";
	
	/** The Constant Tag_PDRUG_DOSAGE. */
	private static final String Tag_PDRUG_DOSAGE = "dosage";	
	
	/** The Constant TAG_PATIENT_FAMILY_HISTORY. */
	private static final String TAG_PATIENT_FAMILY_HISTORY = "patientFamilyHistory";
	
	/** The Constant TAG_PFAM_HISTORY_PRIORITY. */
	private static final String TAG_PFAM_HISTORY_PRIORITY = "priority";
	
	/** The Constant TAG_PFAM_HISTORY. */
	private static final String TAG_PFAM_HISTORY = "history";	
	
	/** The Constant TAG_PATIENT_HISTORY. */
	private static final String TAG_PATIENT_HISTORY = "patientHistory";
	
	/** The Constant TAG_PHISTORY. */
	private static final String TAG_PHISTORY = "history";
	
	/** The Constant TAG_ORGAN_DONOR. */
	private static final String TAG_ORGAN_DONOR = "organ_donor";
	
	/** The Constant TAG_PHISTORY_PRIORITY. */
	private static final String TAG_PHISTORY_PRIORITY = "priority";
	
	/** The Constant TAG_PHISTORY_DATE. */
	private static final String TAG_PHISTORY_DATE = "date";	
	
	/** The Constant TAG_PATIENT_IMAGE. */
	private static final String TAG_PATIENT_IMAGE = "patientImage";
	
	/** The Constant TAG_PIMAGE_URL. */
	private static final String TAG_PIMAGE_URL = "img_url";
	
	/** The Constant TAG_SHOULD_RESUSCITATE. */
	private static final String TAG_SHOULD_RESUSCITATE = "should_resuscitate";
	
	/** The Constant TAG_PATIENT_RESUSCITATE. */
	private static final String TAG_PATIENT_RESUSCITATE = "patientResuscitate";
	
	/** The medical records. */
	private MedicalRecord medicalRecords;
	
	/** The pulse. */
	Animation pulse; 
	
	/** The nhs id. */
	private int nhsID;
	
	/** The nhs id as text. */
	private String nhsIDAsText; 
	
	/** The json parser. */
	private GetJSONData jsonParser = new GetJSONData();
	
	/** The p family history tv. */
	private TextView patientName, bloodType, medication, patientResTV, allergyTV, concernsTV, medicalHistoryTV, pFamilyHistoryTV;
	
	/** The p. */
	private Patient p;
	
	/** The organ donor img. */
	@SuppressWarnings("unused")
	private ImageView patientImg, medicationImg, organDonorImg;
	
	/** The tf. */
	private Typeface tf;
	
	/**
	 * Instantiates a new patient records activity.
	 */
	public PatientRecordsActivity(){}
	
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
	        Intent i = new Intent(PatientRecordsActivity.this, UserManualActivity.class);
	        startActivity(i);
	      }
	    });
		String fontPath = "fonts/Sansation_Regular.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_records);
		pulse =  AnimationUtils.loadAnimation(PatientRecordsActivity.this, R.anim.pulse);
		RelativeLayout patientRecordsActivity = (RelativeLayout) findViewById(R.id.patientRecordsActivityLayout);
		patientRecordsActivity.setOnTouchListener(new SwipeListener(this) {
		    public void onSwipeTop() {
		        }
		    public void onSwipeRight() {
		    	try {
					PostMedicalLogData.excutePost(userLoggedIn, "Moved to photo and audio screen");
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
		           Intent i = new Intent(PatientRecordsActivity.this,
						PhotoAndAudioForHospital.class);
			i.putExtra("patientRecord", medicalRecords);
			 i.putExtra("patient", p);
			i.putExtra("uName", userLoggedIn);
			finish();
			startActivity(i);
		    
		    }
		    public void onSwipeLeft() {
		    	try {
					PostMedicalLogData.excutePost(userLoggedIn, "Moved to photo and audio screen");
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
		        Intent i = new Intent(PatientRecordsActivity.this,
						PhotoAndAudioForHospital.class);
		        i.putExtra("uName", userLoggedIn);
		        i.putExtra("patient", p);
			i.putExtra("patientRecord", medicalRecords);
			finish();
			startActivity(i);
		    }
		    public void onSwipeBottom() {
		    	Intent i = new Intent(PatientRecordsActivity.this,
						CallEmergencyServicesActivity.class);
		        i.putExtra("uName", userLoggedIn);
			startActivity(i);
		    } 
		});
		
	  
		Bundle b = getIntent().getExtras();
		userLoggedIn = b.getString("uName");
		p = b.getParcelable("selectedPatient");
		nhsID = p.getNhsId();
		nhsIDAsText = String.valueOf(nhsID);
		Log.d("NHS ID = ", nhsIDAsText);
		final String userLogged = b.getString("uName");
		TextView tv = (TextView) findViewById(R.id.userLoggedInPatientRecord);
		tv.setText(userLogged);
		tv.setTextColor(Color.GREEN);
		tv.setTypeface(tf);
		tv.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
                mp.start();
            Intent i = new Intent(PatientRecordsActivity.this,
                    UserLogActivity.class);
            i.putExtra("uName", userLogged);
            startActivity(i);
            }
        });
		if(haveNetworkConnection()){
			
			new LoadPatientRecords().execute();
		} else {
			Toast.makeText(PatientRecordsActivity.this, "No Internet Connection Detected",
					Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * The Class LoadPatientRecords.
	 */
	public class LoadPatientRecords extends AsyncTask<String, String, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PatientRecordsActivity.this);
			pDialog.setMessage("Loading Records...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected String doInBackground(String... args) {
			patientName = (TextView) findViewById(R.id.setName);
			bloodType = (TextView) findViewById(R.id.bloodType);
			medication = (TextView) findViewById(R.id.medicationTextView);
			medicationImg =(ImageView) findViewById(R.id.medicationImage);
			patientImg = (ImageView) findViewById(R.id.personImage);
			allergyTV = (TextView) findViewById(R.id.allergies);
			concernsTV =(TextView) findViewById(R.id.doctorConcernT);
			medicalHistoryTV = (TextView) findViewById(R.id.medHistoryViewText);
			pFamilyHistoryTV = (TextView) findViewById(R.id.fMedHistoryView);
			patientResTV = (TextView) findViewById(R.id.patientResuscitate);
			organDonorImg = (ImageView) findViewById(R.id.organDonation);
			String result;
			result = getPatientsRecord(nhsIDAsText);
			return result;
		}
		

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String file_url) {
			patientName.setTypeface(tf); 
			bloodType.setTypeface(tf);
			medication.setTypeface(tf); 
			allergyTV.setTypeface(tf);
			concernsTV.setTypeface(tf);
			medicalHistoryTV.setTypeface(tf);
			pFamilyHistoryTV.setTypeface(tf);
			patientResTV.setTypeface(tf);
			
			if(medicalRecords.getpRes().getDonor()==1){
				organDonorImg.setImageResource(R.drawable.liver);
			}
			if(medicalRecords.getpRes().getPatientResuscitate()==0){
				patientResTV.setText("DO NOT RESUSCITATE");
				patientResTV.setTextColor(Color.RED);
			} else {
				patientResTV.setText("Resuscitate");
				patientResTV.setTextColor(Color.GREEN);
			}
					if(medicalRecords.getpBG().getPatientBloodGroup()!="null"){
			    	bloodType.setText(medicalRecords.getpBG().getPatientBloodGroup());
			    	bloodType.setTextColor(Color.parseColor("#007AA3")); }
					
					patientName.setText(p.getFullName());
					if(medicalRecords.getpImg().getURL()!="null"){
					Picasso.with(PatientRecordsActivity.this).load(medicalRecords.getpImg().getURL()).into(patientImg);
					}
					
					if(medicalRecords.getpHist().size()!=0){
						medicalHistoryTV.setTextColor(Color.parseColor("#007AA3"));
						medicalHistoryTV.setOnClickListener(new OnClickListener() {
				            public void onClick(View v) {
				            	try {
									PostMedicalLogData.excutePost(userLoggedIn, "Viewed Medical Records");
								} catch (UnsupportedEncodingException e) {
									
									e.printStackTrace();
								}
				                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
				                mp.start();
				            Intent i = new Intent(PatientRecordsActivity.this,
				                    ShowMedicalHistoryActivity.class);
				            i.putExtra("uName", userLoggedIn);
				            i.putParcelableArrayListExtra("pHist", medicalRecords.getpHist());
				            startActivity(i);
				            }
				        });
						medicalHistoryTV.startAnimation(pulse);
					} 
					if(medicalRecords.getpFam().size()!=0){
						pFamilyHistoryTV.setTextColor(Color.parseColor("#007AA3"));
						pFamilyHistoryTV.setOnClickListener(new OnClickListener() {
				            public void onClick(View v) {
				            	try {
									PostMedicalLogData.excutePost(userLoggedIn, "Viewed Family History");
								} catch (UnsupportedEncodingException e) {
									
									e.printStackTrace();
								}
				                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
				                mp.start();
				            Intent i = new Intent(PatientRecordsActivity.this,
				                    ShowPatientFamilyHistoryActivity.class);
				            i.putExtra("uName", userLoggedIn);
				            i.putExtra("pFam", medicalRecords.getpFam());
				            startActivity(i);
				            }
				        });
						pFamilyHistoryTV.startAnimation(pulse);
					} 
					if(medicalRecords.getpC().size()!=0){
						concernsTV.setTextColor(Color.parseColor("#007AA3"));
						concernsTV.setOnClickListener(new OnClickListener() {
				            public void onClick(View v) {
				            	try {
									PostMedicalLogData.excutePost(userLoggedIn, "Viewed Medical Concerns");
								} catch (UnsupportedEncodingException e) {
									
									e.printStackTrace();
								}
				                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
				                mp.start();
				            Intent i = new Intent(PatientRecordsActivity.this,
				                    ShowConcernsActivity.class);
				            i.putExtra("uName", userLoggedIn);
				            i.putParcelableArrayListExtra("pConcern", medicalRecords.getpC());
				            startActivity(i);
				            }
				        });
						concernsTV.startAnimation(pulse);
					} 
					if(medicalRecords.getpMed().size()!=0){
						medication.setTextColor(Color.parseColor("#007AA3"));
						medication.setOnClickListener(new OnClickListener() {
				            public void onClick(View v) {
				            	try {
									PostMedicalLogData.excutePost(userLoggedIn, "Viewed Patients Medication");
								} catch (UnsupportedEncodingException e) {
									
									e.printStackTrace();
								}
				                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
				                mp.start();
				            Intent i = new Intent(PatientRecordsActivity.this,
				                    ShowMedicationActivity.class);
				            i.putExtra("uName", userLoggedIn);
				            
				            i.putParcelableArrayListExtra("pMed", medicalRecords.getpMed());
				            startActivity(i);
				            }
				        });
						
						medication.startAnimation(pulse);
					} 
					
					if(medicalRecords.getpAl().size()!=0){
						allergyTV.setTextColor(Color.parseColor("#007AA3"));
						allergyTV.setOnClickListener(new OnClickListener() {
				            public void onClick(View v) {
				            	try {
									PostMedicalLogData.excutePost(userLoggedIn, "Viewed Patient Allergies");
								} catch (UnsupportedEncodingException e) {
									
									e.printStackTrace();
								}
				                mp = MediaPlayer.create(v.getContext(), R.raw.beep);
				                mp.start();
				            Intent i = new Intent(PatientRecordsActivity.this,
				                    ShowAllergyActivity.class);
				            i.putExtra("uName", userLoggedIn);
				            
				            i.putParcelableArrayListExtra("pAllergy", medicalRecords.getpAl());
				            Log.d("Allergy size = ", String.valueOf(medicalRecords.getpAl().size()));
				            startActivity(i);
				            }
				        });
						
						allergyTV.startAnimation(pulse);
					} 
			
			
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(PatientRecordsActivity.this, file_url,
						Toast.LENGTH_SHORT).show();
			}
			
		}
	
	/**
	 * Gets the patients record.
	 *
	 * @param nhsIDAsText the nhs id as text
	 * @return the patients record
	 */
	private String getPatientsRecord(String nhsIDAsText) {
		int success = 0;		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nhsID", nhsIDAsText));
			Log.d("request!", "starting");
			// getting product details by making HTTP request
			JSONObject json = jsonParser.makeHttpRequest(PATIENT_RECORD_URL,
					"POST", params);
			// check your log for json response
			success = json.getInt(TAG_SUCCESS);
			if (success == 0) {
				Log.d("Login Failure!", json.getString(TAG_MESSAGE));
				return json.getString(TAG_MESSAGE);
			}
			if (success == 1) {
				
				new Intent(PatientRecordsActivity.this,
						ShowPatientActivity.class);
				Log.d("Medical Records Found", json.toString());
				ArrayList<PatientAllergy> patientAllergies = new ArrayList<PatientAllergy>();
				JSONArray JSONAllergies = new JSONArray();
				Log.d("PATIENT ALLERGIES = ", json.get(TAG_PATIENT_ALLERGIES).toString()) ;
				 if(json.get(TAG_PATIENT_ALLERGIES).toString()!="null") {
					 JSONAllergies =json.getJSONArray(TAG_PATIENT_ALLERGIES);
					 for (int j = 0; j < JSONAllergies.length(); j++) {
							JSONAllergies.getJSONObject(j);
							PatientAllergy p = new PatientAllergy(JSONAllergies.getJSONObject(j).getString(TAG_PALLERGY_PRIORITY),
									JSONAllergies.getJSONObject(j).getString(TAG_PALLERGY_NAME), sdf.parse( 
									JSONAllergies.getJSONObject(j).getString(TAG_PALLERGY_DATE_DIAGNOSED)));
									patientAllergies.add(p);
									
						}
						Log.d("Allergies", patientAllergies.toString());
				 }
				
				JSONArray JSONConcerns = new JSONArray();
				ArrayList<PatientConcerns> patientConcerns = new ArrayList<PatientConcerns>();
				if(json.get(TAG_PATIENT_CONCERNS).toString()!="null") {
				JSONConcerns = json.getJSONArray(TAG_PATIENT_CONCERNS);
				for (int j = 0; j<JSONConcerns.length(); j++){
					JSONConcerns.getJSONObject(j);
					PatientConcerns p = new PatientConcerns(JSONConcerns.getJSONObject(j).
							getString(TAG_PCONCERNS), sdf.parse( 
							JSONConcerns.getJSONObject(j).getString(TAG_PCONCERNS_DATE_RAISED)));
					patientConcerns.add(p);
					
				}
				Log.d("Concerns", patientConcerns.toString());
				}
				
				
				JSONArray JSONFamilyHistory = new JSONArray();
				ArrayList<PatientFamilyHistory> patientFamilyHistory = new ArrayList<PatientFamilyHistory>();
				if(json.get(TAG_PATIENT_FAMILY_HISTORY).toString()!="null") {
				JSONFamilyHistory = json.getJSONArray(TAG_PATIENT_FAMILY_HISTORY);
				for (int j=0; j<JSONFamilyHistory.length(); j++) {
					JSONFamilyHistory.getJSONObject(j);
					PatientFamilyHistory p = new PatientFamilyHistory(JSONFamilyHistory.getJSONObject(j).
							getString(TAG_PFAM_HISTORY),JSONFamilyHistory.getJSONObject(j).
							getString(TAG_PFAM_HISTORY_PRIORITY));
				patientFamilyHistory.add(p);
				}
				Log.d("Family History", patientFamilyHistory.toString());
				}
				JSONArray JSONHistory = new JSONArray();
				ArrayList<PatientHistory> patientHistory = new ArrayList<PatientHistory>();
				if(json.get(TAG_PATIENT_HISTORY).toString()!="null") {
				JSONHistory = json.getJSONArray(TAG_PATIENT_HISTORY);
				for (int j=0; j<JSONHistory.length(); j++) {
					JSONHistory.getJSONObject(j);
					PatientHistory p = new PatientHistory(JSONHistory.getJSONObject(j).
							getString(TAG_PHISTORY),JSONHistory.getJSONObject(j).
							getString(TAG_PHISTORY_PRIORITY), sdf.parse( 
							JSONHistory.getJSONObject(j).getString(TAG_PHISTORY_DATE)));
					patientHistory.add(p);
					
				}
				Log.d("History", patientHistory.toString());
				}
				JSONArray JSONMedication = new JSONArray();
				ArrayList<PatientMedication>patientMedication = new ArrayList<PatientMedication>();
				if(json.get(TAG_PATIENT_DRUG).toString()!="null") {
				JSONMedication = json.getJSONArray(TAG_PATIENT_DRUG);
				for(int j=0; j<JSONMedication.length(); j++){
					JSONMedication.getJSONObject(j);
					PatientMedication p = new PatientMedication(JSONMedication.getJSONObject(j).getString(TAG_PDRUG_PRIORITY),
							JSONMedication.getJSONObject(j).getString(TAG_PDRUG_NAME), sdf.parse( 
							JSONMedication.getJSONObject(j).getString(TAG_PDRUG_DATE_STARTED)),sdf.parse( 
							JSONMedication.getJSONObject(j).getString(TAG_PDRUG_DATE_FINISHED)), 
							JSONMedication.getJSONObject(j).getString(Tag_PDRUG_DOSAGE));
					patientMedication.add(p);
					
				}
				Log.d("Medication ", patientMedication.toString());
				}
				medicalRecords = new MedicalRecord(patientAllergies,patientConcerns, patientMedication,
						patientFamilyHistory, patientHistory);
				
				JSONArray JSONRes = new JSONArray();
				PatientResuscitate pRes = new PatientResuscitate();
				ArrayList<PatientResuscitate> pResList = new ArrayList<PatientResuscitate>();
				if(json.get(TAG_PATIENT_BLOOD_GROUP).toString()!="null") {
				JSONRes = json.getJSONArray(TAG_PATIENT_RESUSCITATE);
				for(int j=0; j<JSONRes.length(); j++){
					pRes.setDonor(JSONRes.getJSONObject(j).getInt(TAG_ORGAN_DONOR));					
					pRes.setResuscitate(JSONRes.getJSONObject(j).getInt(TAG_SHOULD_RESUSCITATE)); 
					pResList.add(pRes);
					}				
				medicalRecords.setRes(pRes);
				} else { medicalRecords.setRes(pRes); 
				}
				Log.d("Do not Resuscitate = ", medicalRecords.getpRes().toString());
				
				JSONArray JSONBlood = new JSONArray();
				PatientBloodGroup pBlood1 = new PatientBloodGroup();
				ArrayList<PatientBloodGroup> pBlood = new ArrayList<PatientBloodGroup>();
				if(json.get(TAG_PATIENT_BLOOD_GROUP).toString()!="null") {
				JSONBlood = json.getJSONArray(TAG_PATIENT_BLOOD_GROUP);
				for(int j=0; j<JSONBlood.length(); j++){
				pBlood1.setBloodGroup(JSONBlood.getJSONObject(j).getString(TAG_PBGROUP)); 
				pBlood.add(pBlood1);
				}
				medicalRecords.setpBG(pBlood.get(0));
				} else { medicalRecords.setpBG(pBlood1);
				}
				
				
				JSONArray JSONImage = new JSONArray();
				PatientImage pImage = new PatientImage();
				ArrayList<PatientImage>patientImage = new ArrayList<PatientImage>();
				if(json.get(TAG_PATIENT_IMAGE).toString()!="null") {
				JSONImage = json.getJSONArray(TAG_PATIENT_IMAGE);
				for(int j=0; j<JSONImage.length(); j++){
				pImage.setUrl((JSONImage.getJSONObject(j).getString(TAG_PIMAGE_URL)));
				patientImage.add(pImage);
				}
				medicalRecords.setpImg(patientImage.get(0));
				} else { medicalRecords.setpImg(pImage);
				}
				
				}
			
			    Log.d("Medical Records", medicalRecords.toString());
			
				
				//finish();
				//startActivity(i);
				return json.getString(TAG_MESSAGE);
			} 

		 catch (JSONException e) {
			Log.d("TEST", "Caught JSON Exception");
			e.printStackTrace();
		} catch (ParseException e) {
			Log.d("Test", "Caught parseException");
			e.printStackTrace();
		}
		return null;
	}

	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
