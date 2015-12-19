package com.chrisdixon.medicalrecordinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.mobilepatientrecord.R;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowPatientConcernsListAdapter.
 */
public class ShowPatientConcernsListAdapter extends BaseAdapter{
	
	/** The context. */
	Context context;
	
	/** The p concern. */
	List<PatientConcerns> pConcern;
	
	/** The date format. */
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	/**
	 * Instantiates a new show patient concerns list adapter.
	 *
	 * @param context the context
	 * @param pConcern the concern
	 */
	public ShowPatientConcernsListAdapter(Context context, List<PatientConcerns> pConcern) {
		this.context = context;
		this.pConcern = pConcern;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return pConcern.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return pConcern.get(h);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return pConcern.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The history v. */
		TextView historyV;
		
		/** The date. */
		TextView date;
		
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
			convertView = mInflater.inflate(R.layout.concerns_list_item, null);
			holder = new ViewHolder();

			holder.historyV = (TextView) convertView
					.findViewById(R.id.patientConcernsNameListItem);
			holder.date = (TextView) convertView.findViewById(R.id.patientConcernsDateRaisedListItem);
			PatientConcerns row_pos = pConcern.get(position);
			holder.historyV.setText(row_pos.getConcerns());
			Date d = new Date();
			d.setTime(row_pos.getDateAsLong());
			String started = dateFormat.format(d);
			holder.date.setText(started);
				holder.historyV.setTextColor(Color.parseColor("#007AA3"));
				holder.date.setTextColor(Color.parseColor("#007AA3"));

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			
		
		return convertView;
	}
}


