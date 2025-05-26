package com.example.calingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPerfil extends AppCompatActivity {

    private EditText apelido, email, cidade, estado;
    private Button btn_expressao;
    private BottomNavigationView bottomNavigationView;
    FirebaseFirestore db_calingo = FirebaseFirestore.getInstance();
    String usuario_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_perfil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização dos componentes
        apelido = findViewById(R.id.edit_apelido);
        email = findViewById(R.id.edit_email);
        cidade = findViewById(R.id.edit_cidade);
        estado = findViewById(R.id.edit_estado);
        btn_expressao = findViewById(R.id.btn_sugerir_expressao);

        // Botão para sugestão de expressão
        btn_expressao.setOnClickListener(v -> {
            Intent it = new Intent(TelaPerfil.this, TelaSugestao.class);
            startActivity(it);
        });

        // Navegação inferior
        bottomNavigationView = findViewById(R.id.bottom_nav_perfil);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.desafio_nav) {
                startActivity(new Intent(this, TelaDesafio.class));
                finish();
                return true;
            } else if (id == R.id.expressoes_nav) {
                startActivity(new Intent(this, TelaSugestao.class));
                finish();
                return true;
            } else if (id == R.id.menu_nav) {
                startActivity(new Intent(this, TelaInicial.class));
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            usuario_id = user.getUid();
            DocumentReference documentReference = db_calingo.collection("Usuarios").document(usuario_id);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(TelaPerfil.this, "Erro ao carregar os dados: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        apelido.setText(documentSnapshot.getString("apelido"));
                        email.setText(documentSnapshot.getString("email"));
                        cidade.setText(documentSnapshot.getString("cidade"));
                        estado.setText(documentSnapshot.getString("estado"));
                    } else {
                        Toast.makeText(TelaPerfil.this, "Dados do usuário não encontrados.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TelaPerfil.this, TelaCadastro.class));
            finish();
        }
    }
}
