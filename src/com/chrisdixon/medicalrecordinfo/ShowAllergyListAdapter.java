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
 * The Class ShowAllergyListAdapter.
 */
public class ShowAllergyListAdapter extends BaseAdapter {
	
	/** The context. */
	Context context;
	
	/** The allergy. */
	List<PatientAllergy> allergy;
	
	/** The date format. */
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	/**
	 * Instantiates a new show allergy list adapter.
	 *
	 * @param context the context
	 * @param allergy the allergy
	 */
	public ShowAllergyListAdapter(Context context, List<PatientAllergy> allergy) {
		this.context = context;
		this.allergy = allergy;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return allergy.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return allergy.get(h);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return allergy.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The allergy name. */
		TextView allergyName;
		
		/** The date. */
		TextView date;
		
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
			convertView = mInflater.inflate(R.layout.medical_allergy_list_item, null);
			holder = new ViewHolder();

			holder.allergyName = (TextView) convertView
					.findViewById(R.id.allergyNameListText);
			holder.date = (TextView) convertView.findViewById(R.id.dateDiagnosedWithAllergy);
			PatientAllergy row_pos = allergy.get(position);
			
			holder.allergyName.setText(row_pos.getAllergyName());
			Date d = new Date();
			d.setTime(row_pos.getDateAsLong());
			String started = dateFormat.format(d);
			holder.date.setText(started);
			if(row_pos.getPriority().equals("H")){
				holder.allergyName.setTextColor(Color.RED);
				holder.date.setTextColor(Color.RED);
				} else { 
				if(row_pos.getPriority().equals("M")){
			
				holder.allergyName.setTextColor(Color.parseColor("#007AA3"));
				holder.date.setTextColor(Color.parseColor("#007AA3"));
				
}
		 else { 
			holder.allergyName.setTextColor(Color.GREEN);
			holder.date.setTextColor(Color.GREEN);
		}		
	
}
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			
		
		return convertView;
	}
}
