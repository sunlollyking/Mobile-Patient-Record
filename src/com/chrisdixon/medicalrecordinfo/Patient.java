package com.chrisdixon.medicalrecordinfo;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * The Class Patient.
 */
public class Patient implements Parcelable {
	
	/** The gender. */
	private String fullName, postCode, gender;
	
	/** The nhs id. */
	private int houseNumber, age, nhsID;

	/**
	 * Instantiates a new patient.
	 *
	 * @param fullName the full name
	 * @param houseNumber the house number
	 * @param postCode the post code
	 * @param age the age
	 * @param gender the gender
	 * @param nhsID the nhs id
	 */
	public Patient(String fullName, int houseNumber, String postCode, int age,
			String gender, int nhsID) {
		this.age = age;
		this.fullName = fullName;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.gender = gender;
		this.nhsID=nhsID;
	}

	/**
	 * Instantiates a new patient.
	 *
	 * @param source the source
	 */
	public Patient(Parcel source) {
		age = source.readInt();
		fullName = source.readString();
		postCode = source.readString();
		houseNumber = source.readInt();
		gender = source.readString();
		nhsID = source.readInt();
	}

	/**
	 * Gets the full name.
	 *
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets the house number.
	 *
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		int i = new Integer(houseNumber);
		return i;
	}
	
	/**
	 * Gets the nhs id.
	 *
	 * @return the nhs id
	 */
	public int getNhsId() {
		int i = new Integer(nhsID);
		return i;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		int i = new Integer(age);
		return i;
	}

	/**
	 * Gets the age as string.
	 *
	 * @return the age as string
	 */
	public String getAgeAsString() {
		String a = String.valueOf(age);
		return a;
	}

	/**
	 * Gets the post code.
	 *
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Sets the house number.
	 *
	 * @param houseNumber            the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * Sets the post code.
	 *
	 * @param postCode            the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fullName + " " + postCode + " " + houseNumber + " " + age + " "
				+ gender + " " + nhsID;
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
		out.writeInt(age);
		out.writeString(fullName);
		out.writeString(postCode);
		out.writeInt(houseNumber);
		out.writeString(gender);
		out.writeInt(nhsID);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {

		public Patient createFromParcel(Parcel in) {
			return new Patient(in);
		}

		public Patient[] newArray(int size) {
			return new Patient[size];
		}
	};
}
