package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeScreen extends AppCompatActivity {

    GraphView weightSchema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_screen);

        weightSchema = (GraphView) findViewById(R.id.weightSchema);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        weightSchema.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
        weightSchema.getViewport().setMinX(1);
        weightSchema.getViewport().setMaxX(50);
        weightSchema.getViewport().setXAxisBoundsManual(true);
        weightSchema.addSeries(series);
    }
}