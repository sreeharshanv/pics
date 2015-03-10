import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import org.json.JSONException;
import org.json.JSONObject;
import com.sample.csgallery.network.WebInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

   private JSONObject iData;
   private GridView gv;
    TaskRequested request;
    int num=0;
    Context con;
    private static int T_Width=300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv=(GridView)findViewById(R.id.grid_view);
        request=new TaskRequested("https://api.instagram.com/v1/tags/selfie/media/recent?access_token=1029582603.cc9365a.2df19a04b8614d35a4a2d2b36ea23ad8",this);
        request.execute();
        con=this;
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gv.setNumColumns(displayMetrics.widthPixels/T_Width);
        gv.setOnItemClickListener(new OnItemClickListener() {
        	 @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent i=new Intent(MainActivity.this, ImageActivity.class);
                 try
                 {
                     String url = iData.getJSONArray("data")
                             .getJSONObject(position).getJSONObject("images")
                             .getJSONObject("standard_resolution")
                             .getString("url");
                     i.putExtra("url", url);
                 }
                 catch(JSONException json)
                 {
                     i.putExtra("url", "");
                 }
                 startActivity(i);
             }
         });
       gv.setOnScrollListener(new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
		}
	})
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.menu_main, menu);
	        return true;
	    }

	    private Object getMenuInflater() {
		// TODO Auto-generated method stub
		return null;
	}
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();

	        if (id == R.id.action_settings) {
	            return true;
	        }

	        return super.onOptionsItemSelected(item);
	    }

	    private class TaskRequested extends AsyncTask<Void,Void,Void>
	    {
	        private String url;
	        private Context c;
	        public TaskRequested(String url,Context c)
	        {
	            super();
	            this.url=url;
	            this.c=c;
	        }

	        @Override
	        protected Void doInBackground(Void... params)
	        {
	            iData=WebInterface.requestWebService(url);
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void unused)
	        {
	            gv.setAdapter(new Adapter(c,iData,num));
	        }





	    }
	}