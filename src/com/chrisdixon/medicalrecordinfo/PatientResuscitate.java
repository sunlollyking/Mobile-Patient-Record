package com.chrisdixon.medicalrecordinfo;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientResuscitate.
 */
public class PatientResuscitate implements Parcelable{

/** The should resuscitate. */
private int shouldResuscitate;

/** The organ donor. */
private int organDonor;

/**
 * Instantiates a new patient resuscitate.
 */
public PatientResuscitate(){
	this.shouldResuscitate= 1;
	this.organDonor=1;
}

/**
 * Gets the patient resuscitate.
 *
 * @return the patient resuscitate
 */
public int getPatientResuscitate(){
	return shouldResuscitate;
}

/**
 * Gets the donor.
 *
 * @return the donor
 */
public int getDonor(){
	return organDonor;
}

/* (non-Javadoc)
 * @see android.os.Parcelable#describeContents()
 */
@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}

/**
 * Sets the resuscitate.
 *
 * @param shouldResuscitate the new resuscitate
 */
public void setResuscitate(int shouldResuscitate) {
	this.shouldResuscitate = shouldResuscitate;
}

/**
 * Sets the donor.
 *
 * @param organDonor the new donor
 */
public void setDonor(int organDonor) {
	this.organDonor = organDonor;
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientResuscitate> CREATOR = new Parcelable.Creator<PatientResuscitate>() {

	public PatientResuscitate createFromParcel(Parcel in) {
		return new PatientResuscitate(in);
	}

	public PatientResuscitate[] newArray(int size) {
		return new PatientResuscitate[size];
	}
};

/* (non-Javadoc)
 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
 */
@Override
public void writeToParcel(Parcel out, int flags) {
	// TODO Auto-generated method stub
	out.writeInt(shouldResuscitate);
	out.writeInt(organDonor);
}

/**
 * Instantiates a new patient resuscitate.
 *
 * @param source the source
 */
public PatientResuscitate(Parcel source) {
	shouldResuscitate = source.readInt();
	organDonor=source.readInt();
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientResuscitate [Should Paramedic Resuscitate=" + shouldResuscitate + "Organ Donation = " + organDonor;
}
}