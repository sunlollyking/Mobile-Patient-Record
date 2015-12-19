package com.chrisdixon.medicalrecordinfo;


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
 * The Class ShowFamilyHistoryAdapter.
 */
public class ShowFamilyHistoryAdapter extends BaseAdapter {
	
	/** The context. */
	Context context;
	
	/** The history. */
	List<PatientFamilyHistory> history;
	
	/**
	 * Instantiates a new show family history adapter.
	 *
	 * @param context the context
	 * @param history the history
	 */
	public ShowFamilyHistoryAdapter(Context context, List<PatientFamilyHistory> history) {
		this.context = context;
		this.history = history;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return history.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return history.get(h);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return history.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The family history. */
		TextView familyHistory;
		
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
			convertView = mInflater.inflate(R.layout.family_history_list_item, null);
			holder = new ViewHolder();

			holder.familyHistory = (TextView) convertView
					.findViewById(R.id.patientFamilyHistoryListItem);
			
			PatientFamilyHistory row_pos = history.get(position);
			
			holder.familyHistory.setText(row_pos.getHistory());
			if(row_pos.getPriority().equals("H")){
				holder.familyHistory.setTextColor(Color.RED);
				} else { 
				if(row_pos.getPriority().equals("M")){
			
				holder.familyHistory.setTextColor(Color.parseColor("#007AA3"));
				
}
		 else { 
			holder.familyHistory.setTextColor(Color.GREEN);
		}		
}
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			
		
		return convertView;
	}
}
