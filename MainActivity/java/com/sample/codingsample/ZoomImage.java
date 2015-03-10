package com.sample.codingsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;


public class ZoomImage extends ImageView
{
    //stores the size of the screen along with its smallest dimension
    private int iWidth,iHeight;
    private int width,height;

    //track pinching points
    private float scale;
    private static float minZoom=0.6f,maxZoom=4f;
    private Matrix matrix;
    private ScaleGestureDetector scaleGestureDetector;


    public ZoomImage(Context context) {
        super(context);
    }

    public ZoomImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        super.setImageBitmap(bm);

        iWidth = bm.getWidth();
        iHeight = bm.getHeight();

        setupPinchZoom();

        Log.i("crb", "image size = (" + iWidth + ", " + iHeight + ")");
    }
    public void setupPinchZoom()
    {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        // set the size of the picture to fit the smallest orientation of the screen
        if (width > height)
        {
            scale = (float) height / iHeight;
        }
        else
        {
            scale = (float) width / iWidth;
        }

        // adjust scaling limits if needed
        if (scale > maxZoom)
            maxZoom = scale;
        if (scale < minZoom)
            minZoom = scale;

        // create a transformation matrix and set the scale and translation based on the initial scale
        matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(-306 * scale, -306 * scale);

        // set up a listener for pinch and zoom
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new GuestureListener());
    }
    private class GuestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            // adjust our scaling based on the gesture
            scale *= detector.getScaleFactor();
            scale = Math.max(minZoom, Math.min(scale, maxZoom));

            // adjust matrix
            matrix.setScale(scale, scale);
            matrix.postTranslate(-306 * scale, -306 * scale);

            // tell the canvas to redraw
            invalidate();
            return true;
        }
    }
}


