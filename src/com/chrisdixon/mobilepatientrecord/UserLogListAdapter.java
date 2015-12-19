package com.chrisdixon.mobilepatientrecord;

import java.util.List;

import com.chrisdixon.hospital.UserLog;
import com.example.mobilepatientrecord.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLogListAdapter.
 */
public class UserLogListAdapter extends BaseAdapter {

	/** The context. */
	Context context;
	
	/** The user logs. */
	List<UserLog> userLogs;

	/**
	 * Instantiates a new user log list adapter.
	 *
	 * @param context the context
	 * @param userLogs the user logs
	 */
	public UserLogListAdapter(Context context, List<UserLog> userLogs) {
		this.context = context;
		this.userLogs = userLogs;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return userLogs.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int h) {
		return userLogs.get(h);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return userLogs.indexOf(getItem(position));
	}

	/* private view holder class */
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder {
		
		/** The user. */
		TextView user;
		
		/** The log. */
		TextView log;
		
		/** The current_t_d. */
		TextView current_t_d;
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
			convertView = mInflater.inflate(R.layout.log_list_item, null);
			holder = new ViewHolder();

			holder.user = (TextView) convertView
					.findViewById(R.id.usernameLogItem);
			holder.log = (TextView) convertView.findViewById(R.id.log_log_item);
			holder.current_t_d = (TextView) convertView.findViewById(R.id.currentTD_log_item);

			UserLog row_pos = userLogs.get(position);
			
			holder.user.setText(row_pos.getUsername());
			holder.log.setText(row_pos.getLog());
			holder.current_t_d.setText(row_pos.getCurrent_t_d());

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
}
