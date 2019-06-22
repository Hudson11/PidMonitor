package com.ufrn.edu.br;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ufrn.edu.br.Modelo.DataPointEsp;

import java.util.ArrayList;

public class MonitorActivity extends AppCompatActivity {

    LinearLayout content;
    GraphView graph;

    DatabaseReference mReference;

    private static final String ESPPOINT = "esppoint";

    private ArrayList<DataPointEsp> listPoint = new ArrayList<>();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;



    Integer count = 0;

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_monitor);

        try{
            getSupportActionBar().setTitle("Pid Monitor");
        } catch (NullPointerException e ){
            Log.i("Exception", "NullPointer SetTittle");
        }

        // Inciando direbase database
        this.mReference = FirebaseDatabase.getInstance().getReference(ESPPOINT);

        this.graph = findViewById(R.id.graph);
        this.content = findViewById(R.id.content);

        // Listener Implements firebase
        this.mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                DataPointEsp a = dataSnapshot.getValue(DataPointEsp.class);

                listPoint.add(a);

                DataPoint[] dr = new DataPoint[listPoint.size()];
                DataPoint[] dt = new DataPoint[listPoint.size()];

                for(int i = 0; i < listPoint.size(); i ++){
                    dr[i] = new DataPoint(listPoint.get(i).getTime(), listPoint.get(i).getEspPoint());
                    dt[i] = new DataPoint(listPoint.get(i).getTime(), listPoint.get(i).getSetPoint());
                }

                series = new LineGraphSeries<>(dr);
                series2 = new LineGraphSeries<>(dt);

                series.setColor(Color.BLUE);
                series2.setColor(Color.GREEN);

                series2.setDrawDataPoints(true);
                series.setDrawDataPoints(true);

                series.setDataPointsRadius(10);
                series.setThickness(8);

                graph.addSeries(series);
                graph.addSeries(series2);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                listPoint.clear();
                Log.i("remove", "onChildRemoved invocado");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("pause", "onPause");
        this.mReference.removeValue();
        finish();
    }

}
