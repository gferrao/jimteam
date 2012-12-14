package pt.jimteam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.jimteam.resources.Event;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.google.gson.Gson;

public class MainActivity extends FacebookActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		this.openSession();
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.my_events);
		
		Request req = Request.newGraphPathRequest(this.getSession(), "me/events", new Request.Callback() {
    		
    		@Override
    		public void onCompleted(Response response) {
    			
//    			Log.v("tag", response.toString());
    			
    			JSONObject responseJSON = response.getGraphObject().getInnerJSONObject();
    			
    			try {
				
    				JSONArray eventsJSON = responseJSON.getJSONArray("data");
    				
    				Gson gson = new Gson();
        			Event[] events = gson.fromJson(eventsJSON.toString(),  Event[].class);
        			
        			ListView listView = (ListView) findViewById(android.R.id.list);
	    			listView.setAdapter(new ListAdapter(MainActivity.this, events));
	    			listView.setTextFilterEnabled(true);
	    			
	    			listView.setOnItemClickListener(new OnItemClickListener() {
	    				
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
