package com.chrisdixon.mobilepatientrecord;

import java.util.List;

import com.chrisdixon.hospital.Hospital;
import com.example.mobilepatientrecord.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class HospitalListAdapter.
 */
public class HospitalListAdapter extends BaseAdapter {

	/** The context. */
	Context context;
	
	/** The list of hospitals. */
	List<Hospital> listOfHospitals;

	/**
	 * Instantiates a new hospital list adapter.
	 *
	 * @param context the context
	 * @param listOfHospitals the list of hospitals
	 */
	public HospitalListAdapter(Context context, List<Hospital> listOfHospitals) {
		this.context = context;
		this.listOfHospitals = listOfHospitals;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return listOfHospitals.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return listOfHospitals.get(h);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return listOfHospitals.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The hospital name. */
		TextView hospitalName;
		
		/** The distance from current. */
		TextView distanceFromCurrent;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.hospital_list_item, null);
			holder = new ViewHolder();

			holder.hospitalName = (TextView) convertView
					.findViewById(R.id.hospitalName);
			holder.distanceFromCurrent = (TextView) convertView.findViewById(R.id.distanceFromHere);

			Hospital row_pos = listOfHospitals.get(position);
			
			holder.hospitalName.setText(row_pos.getHospitalName());
			holder.distanceFromCurrent.setText(String.valueOf(row_pos.getDistanceTo()));

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
}
