package com.chrisdixon.medicalrecordinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.example.mobilepatientrecord.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowMedicationListAdapter.
 */
public class ShowMedicationListAdapter extends BaseAdapter {

	/** The context. */
	Context context;

	/** The drugs. */
	List<PatientMedication> drugs;

	/** The date format. */
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * Instantiates a new show medication list adapter.
	 * 
	 * @param context
	 *            the context
	 * @param drugs
	 *            the drugs
	 */
	public ShowMedicationListAdapter(Context context,
			List<PatientMedication> drugs) {
		this.context = context;
		this.drugs = drugs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return drugs.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return drugs.get(h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return drugs.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {

		/** The name. */
		TextView name;

		/** The dosage. */
		TextView dosage;

		/** The date started. */
		TextView dateStarted;

		/** The date finished. */
		TextView dateFinished;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.medication_list_item, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.drugName);
			holder.dosage = (TextView) convertView
					.findViewById(R.id.drugDosage);
			holder.dateStarted = (TextView) convertView
					.findViewById(R.id.dateStartedDrug);
			holder.dateFinished = (TextView) convertView
					.findViewById(R.id.dateFinishedDrug);
			PatientMedication row_pos = drugs.get(position);

			holder.name.setText(row_pos.getDrugName());
			holder.dosage.setText(row_pos.getDosage());
			Date d = new Date();
			d.setTime(row_pos.getDateStartedAsLong());
			String started = dateFormat.format(d);
			holder.dateStarted.setText(started);
			Date e = new Date();
			e.setTime(row_pos.getDateFinishedAsLong());
			String finished = dateFormat.format(e);
			holder.dateFinished.setText(finished);
			if (row_pos.getPriority().equals("H")) {
				holder.name.setTextColor(Color.RED);
				holder.dosage.setTextColor(Color.RED);
				holder.dateStarted.setTextColor(Color.RED);
				holder.dateFinished.setTextColor(Color.RED);
			} else {
				if (row_pos.getPriority().equals("M")) {

					holder.name.setTextColor(Color.parseColor("#007AA3"));
					holder.dosage.setTextColor(Color.parseColor("#007AA3"));
					holder.dateStarted
							.setTextColor(Color.parseColor("#007AA3"));
					holder.dateFinished.setTextColor(Color
							.parseColor("#007AA3"));
					convertView.setTag(holder);
				} else {
					holder.name.setTextColor(Color.GREEN);
					holder.dosage.setTextColor(Color.GREEN);
					holder.dateStarted.setTextColor(Color.GREEN);
					holder.dateFinished.setTextColor(Color.GREEN);
				}

			}
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
}
