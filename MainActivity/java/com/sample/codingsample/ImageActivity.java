package com.sample.codingsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sample.csgallery.network.BitmapDownloaderTask;


public class ImageActivity extends ActionBarActivity {
    ZoomImage zoomImage;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent i = getIntent();
        String url = i.getExtras().getString("url");

        if (url.length() > 0) {

            zoomImage = (ZoomImage) findViewById(R.id.zoom_image);

            // start a task to download the image
            BitmapDownloaderTask task = new BitmapDownloaderTask(zoomImage);
            if (!task.searchCache(url))
                task.execute(url);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error Loading photo", Toast.LENGTH_SHORT).show();
        }
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
