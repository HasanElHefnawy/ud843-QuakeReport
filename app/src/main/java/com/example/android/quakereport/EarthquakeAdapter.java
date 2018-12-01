package com.example.android.quakereport;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView;
        final Earthquake earthquake = getItem(position);
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        } else {
            listItemView = convertView;
        }
        TextView mag = listItemView.findViewById(R.id.mag);
        mag.setText(new DecimalFormat("0.0").format(earthquake.getMag()));
        mag.getBackground().setColorFilter(getContext().getResources().getColor(earthquake.getCircle()), PorterDuff.Mode.SRC_ATOP);

        /*// Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(getContext().getResources().getColor(getMagnitudeColor(earthquake.getMag())));*/

        TextView offsetLocation = listItemView.findViewById(R.id.offset_location);
        offsetLocation.setText(earthquake.getOffsetLocation());
        TextView primaryLocation = listItemView.findViewById(R.id.primary_location);
        primaryLocation.setText(earthquake.getPrimaryLocation());
        TextView date = listItemView.findViewById(R.id.date);
        date.setText(earthquake.getDate());
        TextView time = listItemView.findViewById(R.id.time);
        time.setText(earthquake.getTime());
//        ImageView circle = listItemView.findViewById(R.id.circle);
//        circle.setImageResource(R.drawable.circle_24);;
        listItemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                earthquake.openWebPage(getContext());
            }
        });
        return listItemView;
    }

    private int getMagnitudeColor(float magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
//        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
        return magnitudeColorResourceId;
    }
}
