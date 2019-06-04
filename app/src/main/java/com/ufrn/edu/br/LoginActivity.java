package com.ufrn.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email;
    private EditText senha;

    private Button novaConta;
    private Button acessarConta;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        this.email = findViewById(R.id.email);
        this.senha = findViewById(R.id.senha);

        this.novaConta = findViewById(R.id.criarConta);
        this.acessarConta = findViewById(R.id.acessarConta);

        // Inicia objeto de Auth
        this.mAuth = FirebaseAuth.getInstance();

        // Criar conta
        this.novaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount(email.getText().toString(), senha.getText().toString());
            }
        });

        // Acessar conta
        this.acessarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sighInEmailPassword(email.getText().toString(), senha.getText().toString());
            }
        });
    }

    /*
    *   Cria um novo usuário
    * */
    private void createAccount(String email, String senha){

        if(!validateForm(email))
            return;

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Criado com sucesso
                            Log.i("account", "Criado com Sucesso");
                            Toast.makeText(getApplicationContext(), "Conta Criada", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Erro ao Criar conta", Toast.LENGTH_SHORT).show();
                            Log.i("account", "Erro ao Criar usuário");
                            updateUI(null);
                        }
                    }
                });

    }

    /*
    *   Verifica se usuário existe
    * */
    private void sighInEmailPassword(String email, String senha){

        if(!validateForm(email))
            return;

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("login", "Usuário logado");
                            Toast.makeText(getApplicationContext(), "Acesso Permitido", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Log.i("login", "Acesso negado");
                            Toast.makeText(getApplicationContext(), "Acesso Negado", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /*
    *   Logout user = deslogado o usuário do sistema
    * */
    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    /*
    *  Validação de Email ...
    * */
    public boolean validateForm(String email){

        if(email.contains("@") && email.contains(".")){
            return true;
        }

        Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_LONG).show();
        return false;

    }

    public void updateUI(FirebaseUser user){

        if(user != null){
            Log.i("user", "Usuario logado");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else{
            Log.i("user", "Usuario deslogado");
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser curretUser = mAuth.getCurrentUser();
        updateUI(curretUser);
    }

}
