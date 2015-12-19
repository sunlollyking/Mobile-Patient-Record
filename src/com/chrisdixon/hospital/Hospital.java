package com.chrisdixon.hospital;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * This class holds information about hospitals including its name, a phone
 * number and geographical location data that a different part of the system can
 * read to interpret its location to and from the hospital object. Through
 * implementing the parcelable interface we are able to pass these objects
 * between different activities in the program.
 * 
 * @author Chris Dixon
 * @version 1.0
 * 
 */
public class Hospital implements Parcelable {
	
	/** The locality. */
	private String hospitalName, locality;
	
	/** The longitude. */
	private double longitude;
	
	/** The latitude. */
	private double latitude;
	
	/** The phone number. */
	private int phoneNumber;
	
	/** The distance to. */
	private float distanceTo;

	/**
	 * Instantiates a new hospital.
	 *
	 * @param hospitalName the hospital name
	 * @param longitude the longitude
	 * @param latitude the latitude
	 * @param phoneNumber the phone number
	 * @param locality the locality
	 */
	public Hospital(String hospitalName, double longitude, double latitude,
			int phoneNumber, String locality) {
		this.hospitalName = hospitalName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.phoneNumber = phoneNumber;
		this.locality = locality;
	}

	/**
	 * Method to return the hospital name.
	 *
	 * @return hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * Method to return the hospital's longitude.
	 *
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Method to return the hospital's latitude.
	 *
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Method to return the locality which the hospital is located in.
	 *
	 * @return locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * Return the phone number of the hospital.
	 *
	 * @return phoneNumber
	 */
	public int getPhoneNumber() {
		int i = phoneNumber;
		return i;
	}

	/**
	 * Method to allow the setting of a distance to the current location for
	 * determining the location of hospital's around a point.
	 *
	 * @param distanceTo the new distance to
	 */
	public void setDistanceTo(float distanceTo) {
		this.distanceTo = distanceTo;
	}

	/**
	 * Method to return a float value indicating the distance to the hospital
	 * object from the GPS tracker inside the user's device.
	 *
	 * @return distanceTo
	 */
	public float getDistanceTo() {
		float i = distanceTo;
		return i;
	}

	/**
	 * Method to return contents of object.
	 *
	 * @return the int
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Method to write a parcelable object that can be passed between different
	 * activities.
	 *
	 * @param out the out
	 * @param flags the flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(hospitalName);
		out.writeDouble(longitude);
		out.writeDouble(latitude);
		out.writeInt(phoneNumber);
		out.writeString(locality);
	}

	/**
	 * Constructor object specifically for parcelable interface as to detail in
	 * what way to unpack an object so it can conform to standards set out in
	 * default constructor.
	 *
	 * @param source the source
	 */
	public Hospital(Parcel source) {
		hospitalName = source.readString();
		longitude = source.readDouble();
		latitude = source.readDouble();
		phoneNumber = source.readInt();
		locality = source.readString();
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<Hospital> CREATOR = new Parcelable.Creator<Hospital>() {

		public Hospital createFromParcel(Parcel in) {
			return new Hospital(in);
		}

		public Hospital[] newArray(int size) {
			return new Hospital[size];
		}
	};
}
