package com.example.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail, edPasswd;
    private AppCompatButton btnIngresar;
    private static final String TAG = "msg-test";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        edEmail = findViewById(R.id.ed_email);
        edPasswd = findViewById(R.id.ed_password);
        btnIngresar = findViewById(R.id.btn_signup_init);

        btnIngresar.setOnClickListener(v -> {
            String email = edEmail.getText().toString().trim();
            String password = edPasswd.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, ingrese una contraseña", Toast.LENGTH_SHORT).show();
            } else {
                checkIfEmailExists(email);
            }
        });
    }

    private void checkIfEmailExists(String email) {
        db.collection("usuarios")
                .whereEqualTo("correo", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            querySnapshot.getDocuments().forEach(document -> {
                                String role = document.getString("rol");
                                String name = document.getString("name");
                                String saldo = document.getString("saldo");  // Obtener saldo

                                if (role != null && name != null && saldo != null) {
                                    if (role.equalsIgnoreCase("administrador")) {
                                        Intent intent = new Intent(LoginActivity.this, MainAdmActivity.class);
                                        intent.putExtra("nombreUsuario", name);
                                        intent.putExtra("saldo", saldo);  // Enviar saldo
                                        startActivity(intent);
                                    } else if (role.equalsIgnoreCase("cliente")) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("nombreUsuario", name);
                                        intent.putExtra("saldo", saldo);  // Enviar saldo
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Rol no reconocido", Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                } else {
                                    Log.d(TAG, "Documento incompleto: Falta el rol, el nombre o el saldo");
                                    Toast.makeText(LoginActivity.this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Correo no registrado. Redirigiendo al registro...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Log.d(TAG, "Error al verificar el correo: ", task.getException());
                        Toast.makeText(LoginActivity.this, "Error al verificar el correo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
