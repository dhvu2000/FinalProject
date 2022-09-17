package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomeScreen extends AppCompatActivity {

    GraphView weightSchema;
    GraphView BMISchema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_screen);
        setUpWeightSchema();
    }

    private void setUpWeightSchema()
    {
        weightSchema = (GraphView) findViewById(R.id.weightSchema);
        weightSchema.getGridLabelRenderer().setLabelFormatter( new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });
        weightSchema.getGridLabelRenderer().setNumHorizontalLabels(3);

        weightSchema.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
        weightSchema.setCursorMode(true);
        weightSchema.addSeries(getLineData());
    }

    private LineGraphSeries<DataPoint> getLineData()
    {
        Calendar myCalendar = new GregorianCalendar(2014, Calendar.FEBRUARY, 11);
        Date d1 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2014, Calendar.FEBRUARY, 12);
        Date d2 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2014, Calendar.FEBRUARY, 14);
        Date d3 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2014, Calendar.FEBRUARY, 15);
        Date d4 = myCalendar.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 20),
                new DataPoint(d4, 25),
        });

        series.setColor(Color.rgb(102, 204, 255));
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60,95, 226, 156));
        return series;
    }
}

