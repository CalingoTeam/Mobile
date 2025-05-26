package com.example.calingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {

    private EditText inputNome, inputEmail, inputPassword;
    private View entrarButton, loginButton;
    private String usuario_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        // Vinculando os elementos com base no novo XML
        inputNome = findViewById(R.id.input_nome);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

        entrarButton = findViewById(R.id.entrar_bt);
        loginButton = findViewById(R.id.login_bt);

        // Voltar para tela de login
        loginButton.setOnClickListener(v -> {
            Intent it = new Intent(TelaCadastro.this, TelaLogin.class);
            startActivity(it);
        });

        // Cadastrar usuário
        entrarButton.setOnClickListener(v -> {
            String nome = inputNome.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String senha = inputPassword.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(TelaCadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                cadastrarUsuario(v, nome, email, senha);
            }
        });
    }

    private void cadastrarUsuario(View v, String nome, String email, String senha) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        salvarDadosUsuario(nome);
                        Toast.makeText(TelaCadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Limpar os campos
                        inputNome.setText("");
                        inputEmail.setText("");
                        inputPassword.setText("");

                        // Ir para tela inicial
                        startActivity(new Intent(TelaCadastro.this, TelaInicial.class));
                        finish();
                    } else {
                        String erro = "Erro desconhecido ao cadastrar.";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erro = "Digite uma senha com no mínimo 6 caracteres.";
                        } catch (FirebaseAuthUserCollisionException e) {
                            erro = "Email já cadastrado!";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erro = "Email inválido!";
                        } catch (Exception e) {
                            erro = "Erro ao cadastrar usuário: " + e.getMessage();
                            Log.e("CadastroErro", "Erro ao cadastrar: ", e);
                        }

                        Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                });
    }

    private void salvarDadosUsuario(String nome) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuario_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail(); // <- Pega o e-mail atual

        Map<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("apelido", nome);
        dadosUsuario.put("email", email);
        dadosUsuario.put("cidade", "");
        dadosUsuario.put("estado", "");

        DocumentReference docRef = db.collection("Usuarios").document(usuario_id);
        docRef.set(dadosUsuario)
                .addOnSuccessListener(unused -> Log.d("Firestore", "Dados salvos com sucesso!"))
                .addOnFailureListener(e -> Log.d("Firestore", "Erro ao salvar dados: " + e.getMessage()));
    }
}
