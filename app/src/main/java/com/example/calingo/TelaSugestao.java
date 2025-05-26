package com.example.calingo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaSugestao extends AppCompatActivity {

    private EditText expressao, significado, aplicacao_frase, estado;
    private Button bt_sugerir_expressao;
    private BottomNavigationView bottomNavigationView;
    String usuario_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_sugestao);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_sugestao);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.desafio_nav) {
                startActivity(new Intent(this, TelaDesafio.class));
                return true;
            } else if (id == R.id.menu_nav) {
                startActivity(new Intent(this, TelaInicial.class));
                return true;
            } else if (id == R.id.perfil_nav) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(this, TelaPerfil.class));
                } else {
                    Toast.makeText(TelaSugestao.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TelaSugestao.this, TelaCadastro.class));
                }
                return true;
            }
            return false;
        });

        expressao = findViewById(R.id.nome_expressao);
        significado = findViewById(R.id.significado);
        aplicacao_frase = findViewById(R.id.aplicacao_frase);
        estado = findViewById(R.id.estado);

        bt_sugerir_expressao = findViewById(R.id.btn_sugerir_expressao);
        bt_sugerir_expressao.setOnClickListener(v -> {
            String expressao_sugerida = expressao.getText().toString().trim();
            String significado_expressao = significado.getText().toString().trim();
            String aplicacao = aplicacao_frase.getText().toString().trim();
            String estado_expressao = estado.getText().toString().trim();

            if (expressao_sugerida.isEmpty() || significado_expressao.isEmpty() ||
                    aplicacao.isEmpty() || estado_expressao.isEmpty()) {
                Toast.makeText(TelaSugestao.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                cadastrarExpressao();
            }
        });
    }

    private void cadastrarExpressao() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(TelaSugestao.this, "Expressão não cadastrada! Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TelaSugestao.this, TelaCadastro.class));
            finish();
        } else {
            salvarDadosExpressao(user.getUid());
            Toast.makeText(TelaSugestao.this, "Expressão cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

            // Limpar os campos
            expressao.setText("");
            significado.setText("");
            aplicacao_frase.setText("");
            estado.setText("");
        }
    }

    private void salvarDadosExpressao(String userId) {
        String cad_expressao = expressao.getText().toString().trim();
        String cad_significado = significado.getText().toString().trim();
        String cad_aplicacao = aplicacao_frase.getText().toString().trim();
        String cad_estado = estado.getText().toString().trim();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> palavra = new HashMap<>();
        palavra.put("Expressão", cad_expressao);
        palavra.put("Significado", cad_significado);
        palavra.put("Aplicação", cad_aplicacao);
        palavra.put("Estado", cad_estado);
        palavra.put("UsuarioId", userId);

        db.collection("Palavra").add(palavra)
                .addOnSuccessListener(documentReference ->
                        Log.d("db_calingo", "Sucesso ao salvar a expressão: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.e("db_calingo_error", "Erro ao salvar a expressão: ", e));
    }
}
