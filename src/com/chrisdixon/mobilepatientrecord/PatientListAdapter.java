package com.chrisdixon.mobilepatientrecord;

import java.util.List;

import com.chrisdixon.medicalrecordinfo.Patient;
import com.example.mobilepatientrecord.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The String url.
 */
public class PatientListAdapter extends BaseAdapter {

	/** The context. */
	Context context;
	
	/** The patient row. */
	List<Patient> patientRow;

	/**
	 * Instantiates a new patient list adapter.
	 *
	 * @param context the context
	 * @param patientRow the patient row
	 */
	public PatientListAdapter(Context context, List<Patient> patientRow) {
		this.context = context;
		this.patientRow = patientRow;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return patientRow.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int p) {
		return patientRow.get(p);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return patientRow.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The gender. */
		ImageView gender;
		
		/** The full_name. */
		TextView full_name;
		
		/** The age. */
		TextView age;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();

			holder.full_name = (TextView) convertView
					.findViewById(R.id.full_name);
			holder.gender = (ImageView) convertView.findViewById(R.id.gender);
			holder.age = (TextView) convertView.findViewById(R.id.age);

			Patient row_pos = patientRow.get(position);
			if (row_pos.getGender().equals("M")) {
				holder.gender.setImageResource(R.drawable.male);
			} else if (row_pos.getGender().equals("F")) {
				holder.gender.setImageResource(R.drawable.female);
			} else if (row_pos.getGender().equals("null")) {
				holder.gender.setImageResource(R.drawable.unknown_patient);
			}
		

			holder.full_name.setText(row_pos.getFullName());
			holder.age.setText(row_pos.getAgeAsString());

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
}
