package com.example.calingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TelaLogin extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button loginButton, cadastroButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.entrar_bt);
        cadastroButton = findViewById(R.id.cadastro_bt);


        loginButton.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(TelaLogin.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            checkUserInFirestore(email);
                        } else {
                            Toast.makeText(TelaLogin.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        cadastroButton.setOnClickListener(v -> {
            Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
            startActivity(intent);
        });
    }

    private void checkUserInFirestore(String email) {
        db.collection("Usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        Toast.makeText(TelaLogin.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TelaLogin.this, TelaInicial.class));
                    } else {
                        Toast.makeText(TelaLogin.this, "Usuário não encontrado no Firestore.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
