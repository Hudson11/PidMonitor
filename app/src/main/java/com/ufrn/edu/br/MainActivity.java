package com.ufrn.edu.br;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private static final String CONTROLL_SETPOINT = "setpoint";

    private static final String CONTROLL = "controll";

    private DatabaseReference mReference;

    private EditText proporcional;
    private EditText integral;
    private EditText derivada;
    private EditText setpoint;

    private Button ok;
    private Button monitor;

    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            getSupportActionBar().setTitle("Pid Controll");
        } catch (NullPointerException e ){
            Log.i("Exception", "NullPointer SetTittle");
        }

        this.mReference = FirebaseDatabase.getInstance().getReference(CONTROLL);

        this.proporcional = findViewById(R.id.proporcional);
        this.integral = findViewById(R.id.integral);
        this.derivada = findViewById(R.id.derivada);
        this.setpoint = findViewById(R.id.setpoint);

        this.ok = findViewById(R.id.ok);
        this.monitor = findViewById(R.id.monitor);

        this.main = findViewById(R.id.mainLayout);

        // Click do button "OK"
        this.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(proporcional.getText().toString().equals("")) {
                    proporcional.setError("Campo vazio");
                    return;
                }
                if(integral.getText().toString().equals("")) {
                    integral.setError("Campo vazio");
                    return;
                }
                if(derivada.getText().toString().equals("")) {
                    derivada.setError("Campo vazio");
                    return;
                }

                // Recebe os valores digitados pelo usuário
                Integer dP = Integer.parseInt(proporcional.getText().toString()),
                        dI = Integer.parseInt(integral.getText().toString()),
                        dD = Integer.parseInt(derivada.getText().toString()),
                        dS = Integer.parseInt(setpoint.getText().toString());


                if(dP <= 0 ) {
                    proporcional.setError("Não permitido valores negativos");
                    return;
                }
                if(dI <= 0){
                    integral.setError("Não permitido valores negativos");
                    return;
                }
                if(dD <= 0){
                    derivada.setError("Não permitido valores negativos");
                    return;
                }
                if(dS <= 0){
                    derivada.setError("Não permitido valores negativos");
                    return;
                }

                Controll c1 = new Controll(dP);
                Controll c2 = new Controll(dI);
                Controll c3 = new Controll(dD);
                Controll c4 = new Controll(dS);

                mReference.child(CONTROLL_PROPORCIONAL).setValue(c1);
                mReference.child(CONTROLL_INTEGRAL).setValue(c2);
                mReference.child(CONTROLL_DEVIVADA).setValue(c3);
                mReference.child(CONTROLL_SETPOINT).setValue(c4);

                Snackbar snack = Snackbar.make(main, "Valores salvos com sucesso, use o monitor para monitorar o comportamento do sistema"
                    , Snackbar.LENGTH_LONG);
                snack.show();
            }
        });

        // Click do button "Monitor"
        this.monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MonitorActivity.class));
            }
        });

    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_inflate, menu);
        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.sair) {
            finish();
            LoginActivity.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
