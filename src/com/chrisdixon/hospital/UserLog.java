package com.chrisdixon.hospital;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Auto-generated Javadoc
/**
 * This class implements the parcelable interface allowing objects to be passed
 * between different activities in the system. It also holds details of data of
 * the user's activity in the form of a UserLog object which references a
 * specfic action at a unique point in time.
 * 
 * @author Chris Dixon
 * @version 1.0
 * 
 */
public class UserLog implements Parcelable {
	
	/** The log. */
	private String current_t_d, username, log;

	/**
	 * Instantiates a new user log.
	 *
	 * @param username the username
	 * @param log the log
	 * @param current_t_d the current_t_d
	 */
	public UserLog(String username, String log, String current_t_d) {
		this.username = username;
		this.log = log;
		this.current_t_d = current_t_d;
	}

	/**
	 * Method to return a string representation of the current time and date.
	 *
	 * @return current_t_d
	 */
	public String getCurrent_t_d() {
		return current_t_d;
	}

	/**
	 * Returns the username of the medical professional on the system.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Method to return the action described in the variable log.
	 *
	 * @return log
	 */
	public String getLog() {
		return log;
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
	 * Constructor object specifically for parcelable interface as to detail in
	 * what way to unpack an object so it can conform to standards set out in
	 * default constructor.
	 *
	 * @param source the source
	 */
	public UserLog(Parcel source) {
		username = source.readString();
		log = source.readString();
		current_t_d = source.readString();
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
		out.writeString(username);
		out.writeString(log);
		out.writeString(current_t_d);

	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<UserLog> CREATOR = new Parcelable.Creator<UserLog>() {

		public UserLog createFromParcel(Parcel in) {
			return new UserLog(in);
		}

		public UserLog[] newArray(int size) {
			return new UserLog[size];
		}
	};
}
