package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {
    private float mMag;
    private String mLocation;
    private long mDate;
    private String mURL;

    public Earthquake(float mag, String location, long date, String url) {
        mMag = mag;
        mLocation = location;
        mDate = date;
        mURL = url;
    }

    /*public Earthquake(double mag, String location, int date) {    //        if (mDate.getClass() == "Integer")
        mMag = mag;
        mLocation = location;
        mDate = date;
    }*/

    public float getMag() {
        return mMag;
//        return new DecimalFormat("0.0").format(mMag);
    }

    public String getOffsetLocation() {     // split() method is better to be used instead of substring() & indexOf() methods
        if (mLocation.contains(" of ")) {
            return mLocation.substring(0, mLocation.indexOf("of") + 2);
        } else
            return "Near the";           // getString(R.string.near_the) can't be used here, but can be used in EarthquakeAdapter.java class
    }

    public String getPrimaryLocation() {    // split() method is better to be used instead of substring() & indexOf() methods
        if (mLocation.contains(" of ")) {
            return mLocation.substring(mLocation.indexOf("of") + 3);
        } else return mLocation;
    }

    /*public String getDate() {
//        if (mDate.getClass() == "Integer")
        return new SimpleDateFormat("MMM d, yyyy h:mm a").format(new Date(mDate * 1000L));
    }*/

    public String getDate() {
//        if (mDate.getClass() == "Long")
        return new SimpleDateFormat("MMM d, yyyy").format(new Date(mDate));
    }

    public String getTime() {
        return new SimpleDateFormat("h:mm a").format(new Date(mDate));
    }

    public void openWebPage(Context context) {
        Uri webpage = Uri.parse(mURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public int getCircle() {
        if (mMag >= 10.0)
            return R.color.magnitude10plus;
        else if (mMag >= 9.0)
            return R.color.magnitude9;
        else if (mMag >= 8.0)
            return R.color.magnitude8;
        else if (mMag >= 7.0)
            return R.color.magnitude7;
        else if (mMag >= 6.0)
            return R.color.magnitude6;
        else if (mMag >= 5.0)
            return R.color.magnitude5;
        else if (mMag >= 4.0)
            return R.color.magnitude4;
        else if (mMag >= 3.0)
            return R.color.magnitude3;
        else if (mMag >= 2.0)
            return R.color.magnitude2;
        else if (mMag >= 0.0)
            return R.color.magnitude1;
        else return R.color.colorPrimaryDark;
    }
}
