package com.example.myapplication.Views.HomeScreen;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.RoutineListItem.RoutineAdapter;
import com.example.myapplication.Adapter.SchemaListItem.SchemaAdapter;
import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineAct;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.NotificationCreator;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.MainActivity;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageFragment extends Fragment {

    GraphView weightSchema;
    LinearLayout staticBar;
    TextView txtGraphStartDate, txtGraphEndDate, txtRecentRoutineBar, btnRoutines;
    Users user;
    CircleImageView image;
    LinearLayout areaSettingSchema;
    TextView txtWeight, txtHeight, txtBMI, txtBMIResult, txtUserName, txtEmail;
    UserSchema userSchema;
    SharePreferenceManager sharePreferenceManager;
    Button  btnStatic, btnRecord;
    RecyclerView recyclerView1, recyclerView2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        sharePreferenceManager = new SharePreferenceManager(getContext());

        staticBar = view.findViewById(R.id.StaticBar);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtEmail = view.findViewById(R.id.txtEmail);
        weightSchema =  view.findViewById(R.id.weightSchema);
        txtBMI = view.findViewById(R.id.txtBMI);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtBMIResult = view.findViewById(R.id.txtBMIResult);
        areaSettingSchema = view.findViewById(R.id.areaSettingSchema);
        image = view.findViewById(R.id.img);
        btnStatic = view.findViewById(R.id.btnStatic);
        btnRecord = view.findViewById(R.id.btnRecord);
        txtGraphStartDate = view.findViewById(R.id.txtGraphStartDate);
        txtGraphEndDate = view.findViewById(R.id.txtGraphEndDate);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        txtRecentRoutineBar = view.findViewById(R.id.txtRecentRoutineBar);
        btnRoutines = view.findViewById(R.id.btnRoutines);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        areaSettingSchema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SchemaSettingDialog schemaSettingDialog = new SchemaSettingDialog();
                if(userSchema != null)
                {
                    schemaSettingDialog = new SchemaSettingDialog(userSchema.getHeight(), userSchema.getWeight());
                }
                schemaSettingDialog.show(getParentFragmentManager(), "SchemaSettingDialog");
            }
        });

        btnStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SchemaScreen.class);
                startActivity(intent);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), RecordScreen.class);
                startActivity(intent);
            }
        });

        btnRoutines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity)
                {
                    ((MainActivity)getActivity()).movePage(R.id.btnCollections);
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateSchema();
        updateRecentRoutine();
        setRoutines();

        if(user.getImg()!=null && !user.getImg().isEmpty())
        {
            Picasso.get().load(user.getImg()).error(R.drawable.avatar).into(image);
        }else
        {
            Picasso.get().load(R.drawable.avatar).into(image);
        }

        txtEmail.setText(user.getEmail());
        txtUserName.setText(user.getUsername());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity)
                {
                    ((MainActivity)getActivity()).movePage(R.id.btnAccount);
                }
            }
        });
    }

    private void setRoutines() {
        Routine[] data = (Routine[]) sharePreferenceManager.getObject("Routines", Routine[].class);
        ArrayList<Routine> displayedRoutines = new ArrayList<>();
        if(data != null && data.length > 0)
        {
            btnRoutines.setVisibility(View.VISIBLE);
            ArrayList<Routine> routines = new ArrayList<>(Arrays.asList(data));
            int count = 0;
            for(Routine i : routines)
            {
                if(count == 3) {
                    break;
                }
                displayedRoutines.add(i);
                count ++;
            }
        }
        else {
            btnRoutines.setVisibility(View.GONE);
        }
        RoutineAdapter adapter = new RoutineAdapter(displayedRoutines, getContext(), false);
        recyclerView2.setAdapter(adapter);
    }

    private void updateRecentRoutine() {
        RoutineAct[] data =(RoutineAct[]) sharePreferenceManager.getObject("Progress", RoutineAct[].class);
        Routine currentRoutine = null;
        if(data != null)
        {
            ArrayList<RoutineAct> progress = new ArrayList<>(Arrays.asList(data));
            if(progress!= null && progress.size() > 0)
            {
                currentRoutine = progress.get(progress.size() - 1).getRoutine();
            }
        }

        if(currentRoutine != null)
        {
            txtRecentRoutineBar.setVisibility(View.VISIBLE);
            recyclerView1.setVisibility(View.VISIBLE);
            ArrayList<Routine> routines = new ArrayList<>();
            routines.add(currentRoutine);
            RoutineAdapter adapter = new RoutineAdapter(routines, getContext(), false);
            recyclerView1.setAdapter(adapter);
        }
        else {
            txtRecentRoutineBar.setVisibility(View.GONE);
            recyclerView1.setVisibility(View.GONE);
        }
    }

    public void updateSchema()
    {
        user = (Users)  sharePreferenceManager.getObject("User", Users.class);
        if( user.getSchemas() != null && user.getSchemas().size() > 0){
            userSchema = user.getSchemas().get(user.getSchemas().size()-1);
            staticBar.setVisibility(View.VISIBLE);
            weightSchema.setVisibility(View.VISIBLE);
            txtGraphEndDate.setVisibility(View.VISIBLE);
            txtGraphStartDate.setVisibility(View.VISIBLE);
        }
        else
        {
            txtWeight.setText( "-.- kg");
            txtHeight.setText( "-.- cm");
            txtBMI.setText("-.-");
            txtBMIResult.setText("N/A");
            txtBMIResult.setTextColor(getResources().getColor(R.color.bluesky));
            staticBar.setVisibility(View.GONE);
            weightSchema.setVisibility(View.GONE);
            txtGraphEndDate.setVisibility(View.GONE);
            txtGraphStartDate.setVisibility(View.GONE);
            return;
        }

        if(userSchema != null && userSchema.getWeight() > 0)
        {
            String result = String.format("%.1f", userSchema.getWeight());
            txtWeight.setText(result + " kg");
        }

        if(userSchema != null && userSchema.getHeight() > 0)
        {
            String result = String.format("%.1f", userSchema.getHeight());
            txtHeight.setText(result + " cm");
            double bmi = userSchema.getBmi();
            result = String.format("%.1f", bmi);
            txtBMI.setText(result);
            if(bmi < 18.5)
            {
                txtBMIResult.setText("UNDERWEIGHT");
                txtBMIResult.setTextColor(getResources().getColor(R.color.grey));
            }
            else if (bmi < 25.0)
            {
                txtBMIResult.setText("GOOD");
                txtBMIResult.setTextColor(getResources().getColor(R.color.green));
            }
            else
            {
                txtBMIResult.setText("OVERWEIGHT");
                txtBMIResult.setTextColor(getResources().getColor(R.color.red));
            }
        }


        setUpWeightSchema();
    }

    private void setUpWeightSchema()
    {

        weightSchema.removeAllSeries();
        weightSchema.getGridLabelRenderer().setLabelFormatter( new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if(user.getSchemas() != null && user.getSchemas().size() > 0)
        {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            weightSchema.getViewport().setXAxisBoundsManual(true);
            Date sd = TimeFormatter.convertToDate(user.getSchemas().get(0).getUpdatedDate());
            Date ed = TimeFormatter.convertToDate(user.getSchemas().get(user.getSchemas().size()-1).getUpdatedDate());
            if(user.getSchemas().size() == 1) ed = dt;
            weightSchema.getViewport().setMinX(sd.getTime());
            weightSchema.getViewport().setMaxX(ed.getTime());
            txtGraphStartDate.setText(simpleDateFormat.format(sd));
            txtGraphEndDate.setText(simpleDateFormat.format(ed));
        }
        weightSchema.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        weightSchema.getGridLabelRenderer().setNumVerticalLabels(3);
        weightSchema.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL );
        weightSchema.getGridLabelRenderer().setGridColor(Color.GRAY);
        weightSchema.getGridLabelRenderer().reloadStyles();
        weightSchema.setCursorMode(true);
        weightSchema.getGridLabelRenderer().setPadding(40);
        weightSchema.addSeries(getLineData());
    }

    private LineGraphSeries<DataPoint> getLineData()
    {

        DataPoint[] dataPoints = new DataPoint[] {};
        if(user.getSchemas() != null && user.getSchemas().size() > 0)
        {
            ArrayList<UserSchema> schemas = (ArrayList<UserSchema>) user.getSchemas();
            ArrayList<DataPoint> dps = new ArrayList<>();
            int start = schemas.size() - 10;
            int finish = schemas.size();

            if(schemas.size() < 10)
            {
                start = 0;
            }

            for(int i = start; i < finish; i++)
            {
                UserSchema schema = schemas.get(i);
                DataPoint dp = new DataPoint(TimeFormatter.convertToDate(schema.getUpdatedDate()),schema.getWeight());
                dps.add(dp);
            }
            Date d = TimeFormatter.convertToDate(schemas.get(start).getUpdatedDate());
            weightSchema.getViewport().setMinX(d.getTime());
            dataPoints = dps.toArray(new DataPoint[dps.size()]);
        }


        ArrayList<DataPoint> arrayList = new ArrayList<>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

        series.setColor(Color.rgb(102, 204, 255));
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60,95, 226, 156));
        return series;
    }
}
