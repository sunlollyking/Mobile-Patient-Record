package com.chrisdixon.medicalrecordinfo;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * This object holds full details of a patient's medical records. It has
 * categorized some of the sub-objects into lists since patients may have more
 * than one allergy, or be taking more than one kind of medication. For a
 * patient who does not have certain fields in the medical records filled, i
 * have made it possible for these objects to be set as null. The class
 * implements parcelable so the medical records can be passed between
 * activities.
 *
 * @author Chris Dixon
 * @version 1.0
 */
public class MedicalRecord implements Parcelable {

	/** The p al. */
	private List<PatientAllergy> pAl = new ArrayList<PatientAllergy>();
	
	/** The p bg. */
	private PatientBloodGroup pBG;
	
	/** The p c. */
	private List<PatientConcerns> pC = new ArrayList<PatientConcerns>();
	
	/** The p med. */
	private List<PatientMedication> pMed = new ArrayList<PatientMedication>();
	
	/** The p fam. */
	private List<PatientFamilyHistory> pFam = new ArrayList<PatientFamilyHistory>();
	
	/** The p hist. */
	private List<PatientHistory> pHist = new ArrayList<PatientHistory>();
	
	/** The p img. */
	private PatientImage pImg;
	
	/** The p res. */
	private PatientResuscitate pRes;

	/**
	 * Instantiates a new medical record.
	 *
	 * @param pAl the al
	 * @param pC the p c
	 * @param pMed the med
	 * @param pFam the fam
	 * @param pHist the hist
	 */
	public MedicalRecord(ArrayList<PatientAllergy> pAl,
			ArrayList<PatientConcerns> pC, ArrayList<PatientMedication> pMed,
			ArrayList<PatientFamilyHistory> pFam,
			ArrayList<PatientHistory> pHist) {
		this.pAl = pAl;
		this.pC = pC;
		this.pMed = pMed;
		this.pFam = pFam;
		this.pHist = pHist;
		this.pImg = null;
		this.pBG = null;
		this.pRes = null;
	}

	/**
	 * Method to set the patient resuscitation status.
	 *
	 * @param pRes the new res
	 */
	public void setRes(PatientResuscitate pRes) {
		this.pRes = pRes;
	}

	/**
	 * Method to set the patient's blood group.
	 *
	 * @param pBG the new p bg
	 */
	public void setpBG(PatientBloodGroup pBG) {
		this.pBG = pBG;
	}

	/**
	 * Method to set the url location of where a picture of the patient is
	 * stored.
	 *
	 * @param pImg the new p img
	 */
	public void setpImg(PatientImage pImg) {
		this.pImg = pImg;
	}

	/**
	 * Method to return the patient's allergy.
	 *
	 * @return patient's allergy
	 */
	public ArrayList<PatientAllergy> getpAl() {
		return (ArrayList<PatientAllergy>) pAl;
	}

	/**
	 * Method to return the patient's blood group.
	 *
	 * @return blood group
	 */
	public PatientBloodGroup getpBG() {
		return pBG;
	}

	/**
	 * Method to return whether the patient should be resuscitated and the organ
	 * donation status.
	 *
	 * @return resuscitation status
	 */
	public PatientResuscitate getpRes() {
		return pRes;
	}

	/**
	 * Method to return a list of patient concerns that a doctor may have.
	 *
	 * @return list of patient concerns
	 */
	public ArrayList<PatientConcerns> getpC() {
		return (ArrayList<PatientConcerns>) pC;
	}

	/**
	 * Gets the p med.
	 *
	 * @return the pMed
	 */
	public ArrayList<PatientMedication> getpMed() {
		return (ArrayList<PatientMedication>) pMed;
	}

	/**
	 * Gets the p fam.
	 *
	 * @return the pFam
	 */
	public ArrayList<PatientFamilyHistory> getpFam() {
		return (ArrayList<PatientFamilyHistory>) pFam;
	}

	/**
	 * Gets the p hist.
	 *
	 * @return the pHist
	 */
	public ArrayList<PatientHistory> getpHist() {
		return (ArrayList<PatientHistory>) pHist;
	}

	/**
	 * Gets the p img.
	 *
	 * @return the pImg
	 */
	public PatientImage getpImg() {
		return pImg;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeTypedList(pAl);
		out.writeParcelable(pBG, flags);
		out.writeTypedList(pC);
		out.writeTypedList(pMed);
		out.writeTypedList(pFam);
		out.writeTypedList(pHist);
		out.writeParcelable(pImg, flags);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<MedicalRecord> CREATOR = new Parcelable.Creator<MedicalRecord>() {

		public MedicalRecord createFromParcel(Parcel in) {
			return new MedicalRecord(in);
		}

		public MedicalRecord[] newArray(int size) {
			return new MedicalRecord[size];
		}
	};

	/**
	 * Instantiates a new medical record.
	 *
	 * @param in the in
	 */
	private MedicalRecord(Parcel in) {
		in.readTypedList(pAl, PatientAllergy.CREATOR);
		pBG = in.readParcelable(PatientBloodGroup.class.getClassLoader());
		in.readTypedList(pC, PatientConcerns.CREATOR);
		in.readTypedList(pMed, PatientMedication.CREATOR);
		in.readTypedList(pFam, PatientFamilyHistory.CREATOR);
		in.readTypedList(pHist, PatientHistory.CREATOR);
		pImg = in.readParcelable(PatientImage.class.getClassLoader());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MedicalRecord's Patient Allergies = " + pAl
				+ ", Patients Blood Group Is " + pBG
				+ ", Medical Concerns Associated with Patient= " + pC
				+ ", Patients Medication Is = " + pMed
				+ ", Family History For Patient Is " + pFam
				+ ", Patients Medical History is = " + pHist
				+ ", Image of Patient Found At " + pImg + "]";
	}
}
