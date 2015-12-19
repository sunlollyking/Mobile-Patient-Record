package com.chrisdixon.medicalrecordinfo;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientConcerns.
 */
public class PatientConcerns implements Parcelable{

/** The concerns. */
private String concerns;

/** The date raised. */
private Date dateRaised;

/** The date as long. */
private Long dateAsLong;

/**
 * Instantiates a new patient concerns.
 *
 * @param concerns the concerns
 * @param dateRaised the date raised
 */
public PatientConcerns(String concerns, Date dateRaised){
	this.concerns = concerns;
	this.dateRaised = dateRaised;
	dateAsLong= dateRaised.getTime();
}

/**
 * Gets the date as long.
 *
 * @return the date as long
 */
public Long getDateAsLong() {
	return dateAsLong;
}

/**
 * Gets the creator.
 *
 * @return the creator
 */
public static Parcelable.Creator<PatientConcerns> getCreator() {
	return CREATOR;
}

/**
 * Gets the concerns.
 *
 * @return the concerns
 */
public String getConcerns() {
	return concerns;
}

/**
 * Gets the date raised.
 *
 * @return the dateRaised
 */
public Date getDateRaised() {
	return dateRaised;
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
	out.writeString(concerns);
	out.writeLong(dateAsLong);
}

/**
 * Instantiates a new patient concerns.
 *
 * @param source the source
 */
public PatientConcerns(Parcel source) {
	concerns = source.readString();
	dateAsLong = source.readLong();
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientConcerns> CREATOR = new Parcelable.Creator<PatientConcerns>() {

	public PatientConcerns createFromParcel(Parcel in) {
		return new PatientConcerns(in);
	}

	public PatientConcerns[] newArray(int size) {
		return new PatientConcerns[size];
	}
};

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientConcerns [concerns=" + concerns + ", dateRaised="
			+ dateRaised + ", dateAsLong=" + dateAsLong + "]";
}
}
