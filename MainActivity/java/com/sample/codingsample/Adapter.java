package com.sample.codingsample;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sample.csgallery.network.BitmapDownloaderTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Adapter extends BaseAdapter
{
    Context c;
    JSONObject imageData;
    private HashMap<ImageView,BitmapDownloaderTask> competitors;
    int size=0;
    @Override
    public int getCount()
    {
        try
        {
            return imageData.getJSONArray("data").length();
        } catch (JSONException e)
        {
            // simply return 0
            return 0;
        }
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null)
        {

            if (size == 0)
            {
                size++;
                imageView = new ImageView(c);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            } else if (size == 1)
            {
                size++;

                imageView = new ImageView(c);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            }
            else
            {
                size = 0;
                imageView = new ImageView(c);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));

            }
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(null);

        try
        {
            BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);

            BitmapDownloaderTask other = competitors.put(imageView, task);

            if (other != null)
                other.cancel(false);

            String url = imageData.getJSONArray("data").getJSONObject(position)
                    .getJSONObject("images").getJSONObject("thumbnail")
                    .getString("url");

            if (!task.searchCache(url))
                task.execute(url);

        }
        catch (JSONException e)
        {
            Log.d("JSONError","Error has occurred with the JSON format");
        }

        return imageView;
    }


    public Adapter(Context c,JSONObject imageData,int num)
    {
        this.c=c;
        this.imageData=imageData;
        competitors=new HashMap<ImageView,BitmapDownloaderTask>();
        size=num;
    }
}
