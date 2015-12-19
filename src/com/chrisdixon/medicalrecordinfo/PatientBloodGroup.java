package com.chrisdixon.medicalrecordinfo;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Void SelectPatientActivity().
 */
public class PatientBloodGroup implements Parcelable{

/** The blood group. */
private String bloodGroup;

/**
 * Instantiates a new patient blood group.
 */
public PatientBloodGroup(){
	this.bloodGroup= "null";
}

/**
 * Gets the patient blood group.
 *
 * @return the patient blood group
 */
public String getPatientBloodGroup(){
	return bloodGroup;
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
 * Sets the blood group.
 *
 * @param bloodGroup the new blood group
 */
public void setBloodGroup(String bloodGroup) {
	this.bloodGroup = bloodGroup;
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientBloodGroup> CREATOR = new Parcelable.Creator<PatientBloodGroup>() {

	public PatientBloodGroup createFromParcel(Parcel in) {
		return new PatientBloodGroup(in);
	}

	public PatientBloodGroup[] newArray(int size) {
		return new PatientBloodGroup[size];
	}
};

/* (non-Javadoc)
 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
 */
@Override
public void writeToParcel(Parcel out, int flags) {
	// TODO Auto-generated method stub
	out.writeString(bloodGroup);
}

/**
 * Instantiates a new patient blood group.
 *
 * @param source the source
 */
public PatientBloodGroup(Parcel source) {
	bloodGroup = source.readString();
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientBloodGroup [bloodGroup=" + bloodGroup + "]";
}
}