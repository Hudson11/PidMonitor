package com.ufrn.edu.br;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ufrn.edu.br.Modelo.Controll;

public class MainActivity extends AppCompatActivity {

    private static final String CONTROLL_INTEGRAL = "integral";
    private static final String CONTROLL_PROPORCIONAL = "proporcional";
    private static final String CONTROLL_DEVIVADA = "derivada";

    private static final String CONTROLL = "controll";

    private DatabaseReference mReference;

    private EditText valor;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mReference = FirebaseDatabase.getInstance().getReference(CONTROLL);

        this.valor = findViewById(R.id.valor);
        this.ok = findViewById(R.id.ok);

        this.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controll c = new Controll(Double.parseDouble(valor.getText().toString()));

                mReference.child(CONTROLL_DEVIVADA).push().setValue(c);

            }
        });

    }
}
