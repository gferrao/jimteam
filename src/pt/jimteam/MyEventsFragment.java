package pt.jimteam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.jimteam.resources.Event;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.gson.Gson;

public class MyEventsFragment extends Fragment {
	
	private static final String TAG = "MyEventsFragment";
	
	private TextView titleView;
	private ListView eventsView;
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
	    @Override
	    public void call(final Session session, final SessionState state, final Exception exception) {
	    	
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	private static final int REAUTH_ACTIVITY_CODE = 100;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.list, container, false);
		
		titleView = (TextView) view.findViewById(R.id.title);
		eventsView = (ListView) view.findViewById(android.R.id.list);
		
		// Check for an open session
	    Session session = Session.getActiveSession();
	    
	    if (session != null && session.isOpened()) {
	    	
	        // Get the user's data
	        makeRequest(session);
	    }
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    if (requestCode == REAUTH_ACTIVITY_CODE) {
	    	
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public void onResume() {
		
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		
	    super.onSaveInstanceState(bundle);
	    uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		
	    if (session != null && session.isOpened()) {
	    	
	        // Get the user's data.
	    	makeRequest(session);
	    }
	}
	
	private void makeRequest(final Session session) {
		
		if (session.isOpened()) {

			Request req = Request.newGraphPathRequest(session, "me/events", new Request.Callback() {

				@Override
				public void onCompleted(Response response) {

					// Log.v("tag", response.toString());

					JSONObject responseJSON = response.getGraphObject().getInnerJSONObject();

					try {

						JSONArray eventsJSON = responseJSON.getJSONArray("data");

						Gson gson = new Gson();
						Event[] events = gson.fromJson(eventsJSON.toString(), Event[].class);

						titleView.setText(R.string.my_events);
						
						eventsView.setAdapter(new ListAdapter(getActivity(), events));
						eventsView.setTextFilterEnabled(true);

						eventsView.setOnItemClickListener(new OnItemClickListener() {

							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

								// on click
							}
						});
					} catch (JSONException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			Request.executeBatchAsync(req);
		}
	} 
	
	private class ListAdapter extends ArrayAdapter<Event> {

		private Context context;
		private final Event[] events;

		public ListAdapter(Context context, Event[] events) {

			super(context, R.layout.list_item, events);
			this.context = context;
			this.events = events;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View rowView;

			if (convertView == null) {

				// get layout from list_item.xml
				rowView = inflater.inflate(R.layout.list_item, parent, false);
			} else {

				rowView = (View) convertView;
			}

			// set text based on selected text
			TextView first_line = (TextView) rowView.findViewById(R.id.first_line);
			TextView second_line = (TextView) rowView.findViewById(R.id.second_line);

			first_line.setText(events[position].getStart_time());
			second_line.setText(events[position].getName() + " @ " + events[position].getLocation());

			return rowView;
		}

		@Override
		public int getCount() {

			return events.length;
		}
	}
}
