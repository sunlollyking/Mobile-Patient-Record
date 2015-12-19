package com.chrisdixon.medicalrecordinfo;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientFamilyHistory.
 */
public class PatientFamilyHistory implements Parcelable{

/** The history. */
private String history;

/** The priority. */
private String priority;

/**
 * Instantiates a new patient family history.
 *
 * @param history the history
 * @param priority the priority
 */
public PatientFamilyHistory(String history, String priority){
	this.history = history;
	this.priority = priority;
}

/**
 * Gets the history.
 *
 * @return the history
 */
public String getHistory() {
	return history;
}

/**
 * Gets the priority.
 *
 * @return the priority
 */
public String getPriority() {
	return priority;
}

/* (non-Javadoc)
 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
 */
@Override
public void writeToParcel(Parcel out, int flags) {
	// TODO Auto-generated method stub
	out.writeString(priority);
	out.writeString(history);
}

/**
 * Instantiates a new patient family history.
 *
 * @param source the source
 */
public PatientFamilyHistory(Parcel source) {
	priority = source.readString();
	history = source.readString();
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientFamilyHistory> CREATOR = new Parcelable.Creator<PatientFamilyHistory>() {

	public PatientFamilyHistory createFromParcel(Parcel in) {
		return new PatientFamilyHistory(in);
	}

	public PatientFamilyHistory[] newArray(int size) {
		return new PatientFamilyHistory[size];
	}
};

/* (non-Javadoc)
 * @see android.os.Parcelable#describeContents()
 */
@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientFamilyHistory [history=" + history + ", priority="
			+ priority + "]";
}
}

