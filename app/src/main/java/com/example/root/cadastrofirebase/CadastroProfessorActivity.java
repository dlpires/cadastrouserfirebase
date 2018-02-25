package com.example.root.cadastrofirebase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CadastroProfessorActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtRg;
    private EditText txtCpf;
    private EditText dtNasc;
    private Button btnCadProf;
    private Button btnVincDisc;
    private Professor professor;
    private ImageView imageView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtRg = (EditText) findViewById(R.id.txtRg);
        dtNasc = (EditText) findViewById(R.id.txtDtNasc);
        imageView = (ImageView) findViewById(R.id.imgPerfil);

        btnCadProf = (Button) findViewById(R.id.btnCadProf);
        btnVincDisc = (Button) findViewById(R.id.btnVincDisc);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), 123);
            }
        });

        btnCadProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professor = new Professor();
                professor.setNomeProf(txtNome.getText().toString());
                professor.setEmailProf(txtEmail.getText().toString());
                professor.setSenhaProf(txtSenha.getText().toString());
                professor.setRgProf(txtRg.getText().toString());
                professor.setDataNascProf(dtNasc.getText().toString());
                professor.setDisciplinas(VincDisciplinaActivity.listaSelecionada);

                cadastrarUsuario();
            }
        });

        btnVincDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroProfessorActivity.this, VincDisciplinaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cadastrarUsuario(){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(professor.getEmailProf(), professor.getSenhaProf()).
                addOnCompleteListener(CadastroProfessorActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    cadastrarFotoPerfil();
                    insereUsuario();
                    finish();
                }
                else{
                    String erroExcecao = null;

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e com letras e numeros";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Email digitado é invalido! Redigite o campo EMAIL!";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Email já cadastrado!";
                    }catch (Exception e){
                        erroExcecao = "Erro ao cadastrar!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroProfessorActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean insereUsuario(){
        try{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("professor");//CRIA NÓ (TABELA)
            databaseReference.child(auth.getUid()).setValue(professor);//PUXANDO A CHAVE PRIMARIA E INSERINDO USUARIO

            Toast.makeText(CadastroProfessorActivity.this, "Usuário Cadastrado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch(Exception e){
            Toast.makeText(CadastroProfessorActivity.this, "Erro ao Gravar Usuário!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    private void cadastrarFotoPerfil() {
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference montarImagemReferencia = storageReference.child("fotoPerfilProfessor/" + auth.getUid() + ".jpg");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
        byte [] data = byteArray.toByteArray();

        UploadTask uploadTask = montarImagemReferencia.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastroProfessorActivity.this, "Falha ao Carregar a Imagem", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                professor.setUrl(downloadUrl.toString());
                Toast.makeText(CadastroProfessorActivity.this, "Imagem carregada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == 123){
                Uri uri = data.getData();
                Picasso.with(CadastroProfessorActivity.this).load(uri.toString()).resize(150, 150).centerCrop().into(imageView);
            }
        }

    }

}
