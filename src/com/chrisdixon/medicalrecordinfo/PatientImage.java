package com.chrisdixon.medicalrecordinfo;


import android.os.Parcel;
import android.os.Parcelable;


// TODO: Auto-generated Javadoc
/**
 * The Class CREATOR.
 */
public class PatientImage implements Parcelable{
	
	/** The url. */
	private String url;
	
	/**
	 * Instantiates a new patient image.
	 */
	public PatientImage(){
		this.url="url";
		}

	

/**
 * Sets the url.
 *
 * @param url the new url
 */
public void setUrl(String url) {
		this.url = url;
	}



/**
 * Gets the url.
 *
 * @return the url
 */
public String getURL(){
	return url;
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
	out.writeString(url);
}

/**
 * Instantiates a new patient image.
 *
 * @param source the source
 */
public PatientImage(Parcel source) {
	url = source.readString();
	
}

/** The Constant CREATOR. */
public static final Parcelable.Creator<PatientImage> CREATOR = new Parcelable.Creator<PatientImage>() {

	public PatientImage createFromParcel(Parcel in) {
		return new PatientImage(in);
	}

	public PatientImage[] newArray(int size) {
		return new PatientImage[size];
	}
};


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "PatientImage [url=" + url + "]";
}
}
