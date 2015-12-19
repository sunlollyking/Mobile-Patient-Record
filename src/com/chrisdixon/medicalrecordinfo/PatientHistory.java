package com.chrisdixon.medicalrecordinfo;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientHistory.
 */
public class PatientHistory implements Parcelable{

/** The history. */
private String history;

/** The priority. */
private String priority;

/** The date. */
private Date date;

/** The date as long. */
private long dateAsLong;

/**
 * Instantiates a new patient history.
 *
 * @param history the history
 * @param priority the priority
 * @param date the date
 */
public PatientHistory(String history, String priority, Date date){
	this.history=history;
	this.priority=priority;
	this.date=date;
	dateAsLong = date.getTime();
	
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
 * @return the ${e.g(1).rsfl()}
 */
public static Parcelable.Creator<PatientHistory> getCreator() {
	return CREATOR;
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

/**
 * Gets the date.
 *
 * @return the date
 */
public Date getDate() {
	return date;
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
	out.writeLong(dateAsLong);
	out.writeString(priority);
	out.writeString(history);
}

/**
 * Instantiates a new patient history.
 *
 * @param source the source
 */
public PatientHistory(Parcel source) {
	dateAsLong = source.readLong();
	priority = source.readString();
	history = source.readString();
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientHistory> CREATOR = new Parcelable.Creator<PatientHistory>() {

	public PatientHistory createFromParcel(Parcel in) {
		return new PatientHistory(in);
	}

	public PatientHistory[] newArray(int size) {
		return new PatientHistory[size];
	}
};

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientHistory [history=" + history + ", priority=" + priority
			+ ", date=" + date + ", dateAsLong=" + dateAsLong + "]";
}
}
