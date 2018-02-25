package com.example.root.cadastrofirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private FirebaseAuth auth;
    private EditText email;
    private EditText senha;
    private Button btnLogin;

    private String txtEmail;
    private String txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        permission();

        if (usuarioLogado()){
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        else{
            email = findViewById(R.id.email);
            senha = findViewById(R.id.senha);
            btnLogin = findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(email.toString().equals("") && senha.toString().equals(""))){
                        txtEmail = email.getText().toString();
                        txtSenha = senha.getText().toString();
                        validarLogin();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Preencha os Campos de Email e Senha", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void validarLogin(){
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(txtEmail, txtSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login Efetuado com Sucesso!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Falha no Login: Usuario ou Senha Incorreta!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        finish();
        startActivity(intent);
    }

    public Boolean usuarioLogado(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user != null;
    }


    //DANDO PERMISSION PARA USO DE ARQUIVOS DO DISPOSITIVO

    public void permission(){
        int PERMISSION_ALL = 1;
        String [] PERMISSION = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_ALL);
    }
}