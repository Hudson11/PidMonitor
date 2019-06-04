package com.ufrn.edu.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private static final String CONTROLL = "controll";

    private DatabaseReference mReference;

    private EditText proporcional;
    private EditText integral;
    private EditText derivada;

    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mReference = FirebaseDatabase.getInstance().getReference(CONTROLL);

        this.proporcional = findViewById(R.id.proporcional);
        this.integral = findViewById(R.id.integral);
        this.derivada = findViewById(R.id.derivada);

        this.ok = findViewById(R.id.ok);

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
                        dD = Integer.parseInt(derivada.getText().toString());


                if(dP < 0 ) {
                    proporcional.setError("Não permitido valores negativos");
                    return;
                }
                if(dI < 0){
                    integral.setError("Não permitido valores negativos");
                    return;
                }
                if(dD < 0){
                    derivada.setError("Não permitido valores negativos");
                    return;
                }

                Controll c1 = new Controll(dP);
                Controll c2 = new Controll(dI);
                Controll c3 = new Controll(dD);

                mReference.child(CONTROLL_PROPORCIONAL).setValue(c1);
                mReference.child(CONTROLL_INTEGRAL).setValue(c2);
                mReference.child(CONTROLL_DEVIVADA).setValue(c3);

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
