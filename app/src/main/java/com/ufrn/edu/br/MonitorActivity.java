package com.ufrn.edu.br;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    private static final String CONTROLL_INTEGRAL = "integral";
    private static final String CONTROLL_PROPORCIONAL = "proporcional";
    private static final String CONTROLL_DEVIVADA = "derivada";

    private static final String CONTROLL = "controll";

    private static final String ESPPOINT = "esppoint";

    private ArrayList<DataPointEsp> listDataPointEsp = new ArrayList<>();

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_monitor);

        // Inciando direbase database
        this.mReference = FirebaseDatabase.getInstance().getReference(ESPPOINT);

        this.graph = findViewById(R.id.graph);
        this.content = findViewById(R.id.content);

        // Iniciando a construção do gráfico
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(
                new DataPoint[]{
                        new DataPoint(0, 1),
                        new DataPoint(1, 5),
                        new DataPoint(2, 10),
                        new DataPoint(3, 13),
                        new DataPoint(4, 12),
                        new DataPoint(5, 17)
                }
        );

        graph.addSeries(series);

        // Listener Implements firebase
        this.mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    DataPointEsp esp = data.getValue(DataPointEsp.class);
                    listDataPointEsp.add(esp);
                }

                
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
