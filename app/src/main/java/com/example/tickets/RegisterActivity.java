package com.example.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tickets.dto.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class RegisterActivity extends AppCompatActivity {
    private EditText edName, edApellido, edCorreo,edPasswd, edPasswdR;
    private AppCompatButton btnIngresar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        edName = findViewById(R.id.ed_nombre);
        edApellido = findViewById(R.id.ed_apellido);
        edCorreo = findViewById(R.id.ed_correo);
        edPasswd = findViewById(R.id.ed_passwd);
        edPasswdR = findViewById(R.id.ed_passwdr);
        btnIngresar = findViewById(R.id.btn_signup_init);

        db = FirebaseFirestore.getInstance();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }
    private void saveUser() {
        String nombre = edName.getText().toString().trim();
        String apellido = edApellido.getText().toString().trim();
        String correo = edCorreo.getText().toString().trim();
        String password = edPasswd.getText().toString().trim();
        String passwordR = edPasswdR.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty() || passwordR.isEmpty()) {
            Toast.makeText(this, "Correo y contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordR)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
// Registrar en Firebase Authentication
        auth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        if (firebaseUser != null) {
                            // Crear objeto usuario para guardar en Firestore
                            User usuario = new User();
                            usuario.setName(nombre);
                            usuario.setLastName(apellido);
                            usuario.setCorreo(correo);
                            usuario.setPwd(password);
                            usuario.setSaldo("50.00");

                            // Guardar datos en Firestore
                            db.collection("usuarios")
                                    .document(firebaseUser.getUid()) // Usa el UID como identificador único
                                    .set(usuario)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Cierra la actividad de registro
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar en Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        // Manejo de errores al registrar en Firebase Authentication
                        Toast.makeText(this, "Error al registrar usuario: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
