package com.chrisdixon.medicalrecordinfo;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientMedication.
 */
public class PatientMedication implements Parcelable{

/** The priority. */
private String priority;

/** The drug name. */
private String drugName;

/** The date started. */
private Date dateStarted;

/** The date started as long. */
private long dateStartedAsLong;

/** The date finished. */
private Date dateFinished;

/** The date finished as long. */
private long dateFinishedAsLong;

/** The dosage. */
private String dosage;

/**
 * Instantiates a new patient medication.
 *
 * @param priority the priority
 * @param drugName the drug name
 * @param dateStarted the date started
 * @param dateFinished the date finished
 * @param dosage the dosage
 */
public PatientMedication(String priority, String drugName, Date dateStarted, Date dateFinished, String dosage){
	this.priority=priority;
	this.drugName=drugName;
	this.dateStarted=dateStarted;
	this.dateFinished=dateFinished;
	this.dosage=dosage;
	dateStartedAsLong=dateStarted.getTime();
    dateFinishedAsLong = dateFinished.getTime();
}

/**
 * Gets the priority.
 *
 * @return the priority
 */
public String getPriority() {
	return priority;
}

/**
 * Gets the drug name.
 *
 * @return the drugName
 */
public String getDrugName() {
	return drugName;
}

/**
 * Gets the date started.
 *
 * @return the dateStarted
 */
public Date getDateStarted() {
	return dateStarted;
}

/**
 * Gets the date finished.
 *
 * @return the dateFinished
 */
public Date getDateFinished() {
	return dateFinished;
}

/**
 * Gets the dosage.
 *
 * @return the dosage
 */
public String getDosage() {
	return dosage;
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
	out.writeString(dosage);
	out.writeString(drugName);	
	out.writeLong(dateStartedAsLong);
	out.writeLong(dateFinishedAsLong);
	out.writeString(priority);
	
	
}

/**
 * Instantiates a new patient medication.
 *
 * @param source the source
 */
public PatientMedication(Parcel source) {
	dosage = source.readString();
	drugName = source.readString();
    dateStartedAsLong = source.readLong();
	dateFinishedAsLong = source.readLong();
	priority = source.readString();
}

/**
 * Gets the date started as long.
 *
 * @return the date started as long
 */
public long getDateStartedAsLong() {
	return dateStartedAsLong;
}

/**
 * Gets the date finished as long.
 *
 * @return the date finished as long
 */
public long getDateFinishedAsLong() {
	return dateFinishedAsLong;
}

/**
 * Gets the creator.
 *
 * @return the creator
 */
public static Parcelable.Creator<PatientMedication> getCreator() {
	return CREATOR;
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientMedication> CREATOR = new Parcelable.Creator<PatientMedication>() {

	public PatientMedication createFromParcel(Parcel in) {
		return new PatientMedication(in);
	}

	public PatientMedication[] newArray(int size) {
		return new PatientMedication[size];
	}
};

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientMedication [priority=" + priority + ", drugName=" + drugName
			+ ", dateStarted=" + dateStarted + ", dateStartedAsLong="
			+ dateStartedAsLong + ", dateFinished=" + dateFinished
			+ ", dateFinishedAsLong=" + dateFinishedAsLong + ", dosage="
			+ dosage + "]";
}
}
