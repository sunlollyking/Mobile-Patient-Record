package com.chrisdixon.medicalrecordinfo;


import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientAllergy.
 */
public class PatientAllergy implements Parcelable{

/** The priority. */
private String priority;

/** The allergy name. */
private String allergyName;

/** The date diagnosed. */
private Date dateDiagnosed;

/** The date as long. */
private long dateAsLong;

/**
 * Instantiates a new patient allergy.
 *
 * @param priority the priority
 * @param allergyName the allergy name
 * @param dateDiagnosed the date diagnosed
 */
public PatientAllergy(String priority, String allergyName, Date dateDiagnosed) {
this.priority=priority;
this.allergyName=allergyName;
this.dateDiagnosed=dateDiagnosed;
dateAsLong=dateDiagnosed.getTime();

}

/**
 * Gets the date as long.
 *
 * @return the date as long
 */
public long getDateAsLong() {
	return dateAsLong;
}

/**
 * Gets the creator.
 *
 * @return the creator
 */
public static Parcelable.Creator<PatientAllergy> getCreator() {
	return CREATOR;
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
 * Gets the allergy name.
 *
 * @return the allergyName
 */
public String getAllergyName() {
	return allergyName;
}

/**
 * Gets the date diagnosed.
 *
 * @return the dateDiagnosed
 */
public Date getDateDiagnosed() {
	return dateDiagnosed;
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
 * Instantiates a new patient allergy.
 *
 * @param source the source
 */
public PatientAllergy(Parcel source) {
	priority = source.readString();
	allergyName = source.readString();
	dateAsLong = source.readLong();
}

/* (non-Javadoc)
 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
 */
@Override
public void writeToParcel(Parcel out, int flags) {
	// TODO Auto-generated method stub
	out.writeString(priority);
	out.writeString(allergyName);
	out.writeLong(dateAsLong);
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientAllergy> CREATOR = new Parcelable.Creator<PatientAllergy>() {

	public PatientAllergy createFromParcel(Parcel in) {
		return new PatientAllergy(in);
	}

	public PatientAllergy[] newArray(int size) {
		return new PatientAllergy[size];
	}
};

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientAllergy [priority=" + priority + ", allergyName="
			+ allergyName + ", dateDiagnosed=" + dateDiagnosed
			+ ", dateAsLong=" + dateAsLong + "]";
}
}
