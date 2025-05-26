package com.example.calingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calingo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaDesafio extends AppCompatActivity {

    private TextView tvPalavra;
    private EditText etFrase;
    private Button btnEnviar;
    private BottomNavigationView bottomNavigationView;

    private FirebaseFirestore db;

    private String termoDesafio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_desafio);

        tvPalavra = findViewById(R.id.tv_palavra);
        etFrase = findViewById(R.id.et_frase);
        btnEnviar = findViewById(R.id.btn_enviar_frase);
        bottomNavigationView = findViewById(R.id.bottom_nav_sugestao);

        db = FirebaseFirestore.getInstance();

        carregarDesafio();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String frase = etFrase.getText().toString().trim();
                if (!frase.isEmpty()) {
                    enviarFrase(frase);
                } else {
                    Toast.makeText(TelaDesafio.this, "Digite uma frase!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_sugestao);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.desafio_nav) {
                startActivity(new Intent(this, TelaDesafio.class));
                return true;

            } else if (id == R.id.expressoes_nav) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(this, TelaSugestao.class));
                } else {
                    Toast.makeText(this, "Faça login para sugerir uma expressão.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, TelaCadastro.class));
                }
                return true;

            } else if (id == R.id.perfil_nav) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(this, TelaPerfil.class));
                } else {
                    Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, TelaCadastro.class));
                }
                return true;

            } else if (id == R.id.menu_nav) {
                startActivity(new Intent(this, TelaInicial.class));
                return true;
            }

            return false;
        });
    }

    private void carregarDesafio() {
        db.collection("calingo").document("desafio")
                .get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            termoDesafio = documentSnapshot.getString("termo");
                            if (termoDesafio != null) {
                                tvPalavra.setText(termoDesafio);
                            }
                        } else {
                            Toast.makeText(TelaDesafio.this, "Desafio não encontrado!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TelaDesafio.this, "Erro ao carregar desafio!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void enviarFrase(String frase) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("frase", frase);
        resposta.put("termo", termoDesafio);
        resposta.put("timestamp", System.currentTimeMillis());

        db.collection("respostas")
                .add(resposta)
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<com.google.firebase.firestore.DocumentReference>() {
                    @Override
                    public void onSuccess(com.google.firebase.firestore.DocumentReference documentReference) {
                        Toast.makeText(TelaDesafio.this, "Frase enviada com sucesso!", Toast.LENGTH_SHORT).show();
                        etFrase.setText("");
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TelaDesafio.this, "Erro ao enviar frase!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
