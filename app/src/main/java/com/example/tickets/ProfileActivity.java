package com.example.tickets;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.app.AlertDialog;
import com.bumptech.glide.Glide;
import com.example.tickets.dto.Bus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvBusName, tvPrice, tvPriceMonthly;
    private ImageView imgBus;
    private AppCompatButton btnSuscripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Obtener el objeto Bus de la Intent
        Bus bus = getIntent().getParcelableExtra("bus");

        // Referencias a los elementos del layout
        tvBusName = findViewById(R.id.tvBus);
        tvPrice = findViewById(R.id.tvUnitario);
        tvPriceMonthly = findViewById(R.id.tvMensual);
        imgBus = findViewById(R.id.imageprofile);
        btnSuscripcion = findViewById(R.id.btn_suscripcion);

        if (bus != null) {
            tvBusName.setText(bus.getName());
            tvPrice.setText(bus.getPrecioUnitario());
            tvPriceMonthly.setText(bus.getPrecioMensual());

            Glide.with(this)
                    .load(bus.getImagen1())
                    .into(imgBus);
        }

        btnSuscripcion.setOnClickListener(v -> onSuscripcionClick());
    }

    private void onSuscripcionClick() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            obtenerSaldoYProcesarSuscripcion(email);
        } else {
            Toast.makeText(ProfileActivity.this, "Por favor, inicia sesión para suscribirte.", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerSaldoYProcesarSuscripcion(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Buscar el usuario en la colección de usuarios
        db.collection("usuarios").whereEqualTo("correo", email).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        if (document.exists()) {
                            double saldo = document.getDouble("saldo");
                            Bus bus = getIntent().getParcelableExtra("bus");
                            if (bus != null) {
                                double costoSuscripcionMensual = Double.parseDouble(bus.getPrecioMensual());
                                procesarSuscripcion(saldo, costoSuscripcionMensual, document.getId());
                            }
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "Error al obtener los datos del usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void procesarSuscripcion(double saldoActual, double costoSuscripcionMensual, String userId) {
        if (saldoActual >= costoSuscripcionMensual) {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Confirmación de Suscripción")
                    .setMessage("¿Deseas suscribirte al plan mensual por S/. " + costoSuscripcionMensual + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        double nuevoSaldo = saldoActual - costoSuscripcionMensual;

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("usuarios").document(userId)
                                .update("saldo", nuevoSaldo)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProfileActivity.this, "Suscripción exitosa. Saldo actualizado.", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(ProfileActivity.this, "Error al procesar la suscripción.", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            Toast.makeText(ProfileActivity.this, "Saldo insuficiente para suscripción mensual.", Toast.LENGTH_SHORT).show();
        }
    }
}
