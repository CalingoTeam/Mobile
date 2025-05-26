package com.example.calingo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class TelaSignificado extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_significado);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_inicio);
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
}
